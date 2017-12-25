package com.endeca.search.api.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.infront.navigation.model.FilterState;
import com.endeca.infront.navigation.model.RangeFilter;
import com.endeca.infront.navigation.model.RangeFilter.Operation;
import com.endeca.infront.navigation.request.RecordsMdexQuery;
import com.endeca.navigation.ENEQueryResults;
import com.endeca.navigation.ERec;
import com.endeca.navigation.ERecList;
import com.endeca.navigation.PropertyMap;
import com.endeca.search.api.anite.parameters.provider.AniteParametersProvider;
import com.endeca.search.api.constants.ConfiguratorConstants;
import com.endeca.search.api.crs.accom.board.service.CRSAccomBoardService;
import com.endeca.search.api.exceptions.SearchAPIException;
import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;
import com.endeca.search.api.keygenerator.impl.FlightPackageKeyGenerator;
import com.endeca.search.api.request.dtos.SearchAPIRequestData;
import com.endeca.search.api.response.dtos.AirportNames;
import com.endeca.search.api.response.dtos.BoardData;
import com.endeca.search.api.response.dtos.ConfiguratorResponseData;
import com.endeca.search.api.response.dtos.ExtraFlightDetails;
import com.endeca.search.api.response.dtos.FacetCategoryData;
import com.endeca.search.api.response.dtos.FlightData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.PackagesData;
import com.endeca.search.api.response.dtos.RoomData;
import com.endeca.search.api.services.ClubPackagesOnFlightClassService;
import com.endeca.search.api.services.SearchAPIService;
import com.endeca.tui.anite.AniteInterface;
import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteException;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;
import com.endeca.tui.anite.parameters.HolidayParameters;


/**
 * The Class SearchAPIService this is the anite specific implementation of {@link SearchAPIService}. This class provides
 * anite implementation for all the methods in {@link SearchAPIService} to fetch all possible types of search results.
 * <p>
 * The request parameter required to fetch the response is {@code SearchAPIRequestData} which is populate with request
 * parameters and response from Mdex.
 * <p>
 * The response fetched is converted into results dtos with {@code ResponseConverter}.
 */
public class AniteSearchAPIServiceImpl implements SearchAPIService
{

	//	/** The mdex request broker. */
	//	private MdexRequestBroker mdexRequestBroker;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AniteSearchAPIServiceImpl.class);

	/** The anite required parameters. */
	private AniteRequiredParameters aniteRequiredParameters = null;

	/** The anite implementation. */

	private AniteInterface aniteInterface;

	/** The anite parameters provider. */
	private AniteParametersProvider aniteParametersProvider;

	/** The anite parameters provider. */
	private AniteParametersProvider durationAutoSelectProvider;

	/** The crs soap service. */
	private CRSAccomBoardService crsSoapService;

	/** The configured board basis. */
	private Map<String, String> configuredBoardBasis;

	private ClubPackagesOnFlightClassService clubPackagesOnFlightClassService;

	/**
	 * Gets the packages results from anite.
	 *
	 * @param searchAPIRequestData
	 *           the search api request data populated with request parameters and mdex data.
	 * @return the packages results
	 * @throws SearchAPIException
	 *            the search api exception in case exception occurred in fetching the response or converting the response
	 *            to {@code PackagesData}.
	 */
	@Override
	public PackagesData getPackageAndFacetData(final SearchAPIRequestData searchAPIRequestData) throws SearchAPIException
	{
		PackagesData packagesData;
		final long time1 = new Date().getTime();
		try
		{
			packagesData = getAniteInterface().query(AniteQueryType.LIST_BY_ACCOMMODATION,
					getAniteParameters(AniteQueryType.LIST_BY_ACCOMMODATION, searchAPIRequestData));

			// DEV-25952 , include extra room details in search response from assembler in case of single accommodation search.
			if (packagesData.getPackages().size() == 1)
			{
				final PackageData singleSearchPackage = packagesData.getPackages().get(0);
				final Map<String, ENEQueryResults> resultsMap = new HashMap<String, ENEQueryResults>();

				for (final RoomData room : singleSearchPackage.getRoomsData())
				{
					setExtraRoomDetails(singleSearchPackage.getHotelData().getApcId(), room, resultsMap);
				}
			}

			final long time2 = new Date().getTime();
			final long timetaken = time2 - time1;
			LOGGER.info("Anite Response-->Thread ID: " + Thread.currentThread().getId() + " Time Taken: " + timetaken
					+ " ms Record Count: " + packagesData.getCount());
		}
		catch (final AniteException e)
		{
			throw new SearchAPIException(" Exception Occurred In Fetching Package Response From Anite " + e.getMessage(), e);
		}
		return packagesData;
	}

	/**
	 * Gets the packages results from anite.
	 *
	 * @param searchAPIRequestData
	 *           the search api request data populated with request parameters and mdex data.
	 * @return the packages results
	 * @throws SearchAPIException
	 *            the search api exception in case exception occurred in fetching the response or converting the response
	 *            to {@code PackagesData}.
	 */
	@Override
	public PackagesData getPackageDataForPPC(final SearchAPIRequestData searchAPIRequestData) throws SearchAPIException
	{
		PackagesData packagesData;
		final long time1 = new Date().getTime();
		try
		{
			packagesData = getAniteInterface().query(AniteQueryType.LIST_BY_PACKAGEPRODUCTS,
					getAniteParameters(AniteQueryType.LIST_BY_PACKAGEPRODUCTS, searchAPIRequestData));

			final int intialCount = packagesData.getCount();
			final List<PackageData> reducedPackages = clubPackagesOnFlightClassService
					.reduceAndFillPackagesWithFlightClass(packagesData.getPackages(), new FlightPackageKeyGenerator());
			packagesData.setPackages(reducedPackages);
			packagesData.setCount(reducedPackages.size());

			//setJourneyExtrasForPackages(packagesData);
			final long time2 = new Date().getTime();
			final long timetaken = time2 - time1;
			LOGGER.info("Anite Response-->Thread ID: " + Thread.currentThread().getId() + " Time Taken: " + timetaken
					+ " ms Initial Record Count: " + intialCount + " Compressed count : " + packagesData.getCount());
		}
		catch (final AniteException e)
		{
			throw new SearchAPIException(" Exception Occurred In Fetching Package Response From Anite " + e.getMessage(), e);
		}
		return packagesData;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.search.api.services.SearchAPIService#getCalendarData(com.endeca.search.api.request.dtos.
	 * SearchAPIRequestData)
	 */
	@Override
	public Map<Date, Boolean> getCalendarData(final SearchAPIRequestData searchAPIRequestData) throws SearchAPIException
	{
		Map<Date, Boolean> dateAvailability;
		try
		{
			dateAvailability = getAniteInterface().query(AniteQueryType.CALENDAR,
					getAniteParameters(AniteQueryType.CALENDAR, searchAPIRequestData));
			LOGGER.info("Total Date Availability Response Fetched " + dateAvailability.size());
		}
		catch (final AniteException e)
		{
			throw new SearchAPIException(" Exception Occurred In Fetching Calendar Response From Anite " + e.getMessage(), e);
		}
		return dateAvailability;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.search.api.services.SearchAPIService#getDurationsData(com.endeca.search.api.request.dtos.
	 * SearchAPIRequestData)
	 */
	@Override
	public List<Integer> getDurationsData(final SearchAPIRequestData searchAPIRequestData) throws SearchAPIException
	{
		List<Integer> availableDurations;
		try
		{
			availableDurations = getAniteInterface().query(AniteQueryType.DURATIONS,
					getAniteParameters(AniteQueryType.DURATIONS, searchAPIRequestData));
			LOGGER.info("Total Duration Response Fetched: " + availableDurations.size());

		}
		catch (final AniteException e)
		{
			throw new SearchAPIException(" Exception Occurred In Fetching Durations Response From Anite " + e.getMessage(), e);
		}
		return availableDurations;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.search.api.services.SearchAPIService#getFacetsData(com.endeca.search.api.request.dtos.
	 * SearchAPIRequestData )
	 */
	@Override
	public List<FacetCategoryData> getFacetsData(final SearchAPIRequestData searchAPIRequestData) throws SearchAPIException
	{
		final List<FacetCategoryData> facetCategoryDatas = new ArrayList<FacetCategoryData>();
		AniteQueryType aniteQueryType = null;
		if (searchAPIRequestData.getDepartureData().getCandidateCodes().size() == 1)
		{
			aniteQueryType = AniteQueryType.SINGLE_ACCOMM_ALTERNATIVE_FLIGHTS;
		}
		else
		{
			aniteQueryType = AniteQueryType.LIST_BY_ACCOMMODATION;
		}
		try
		{
			final HolidayParameters aniteParameters = (HolidayParameters) getAniteParameters(aniteQueryType, searchAPIRequestData);
			getAniteInterface().query(aniteQueryType, aniteParameters);

		}
		catch (final AniteException e)
		{
			throw new SearchAPIException(" Exception Occurred In Fetching Facets Data From Anite " + e.getMessage(), e);
		}
		return facetCategoryDatas;
	}

	/**
	 * Gets the anite parameters.
	 *
	 * @param aniteQueryType
	 *           the anite query type
	 * @param searchAPIRequestData
	 *           the search api request data
	 * @return the anite parameters
	 */
	private AniteRequiredParameters getAniteParameters(final AniteQueryType aniteQueryType,
			final SearchAPIRequestData searchAPIRequestData)
	{
		if (null != searchAPIRequestData.getFacetRemoval())
		{
			aniteRequiredParameters = null;
		}
		if (aniteRequiredParameters == null)
		{
			if (AniteQueryType.DURATIONS.equals(aniteQueryType))
			{
				aniteRequiredParameters = durationAutoSelectProvider.buildAniteParameters(searchAPIRequestData);
			}
			else
			{
				aniteRequiredParameters = aniteParametersProvider.buildAniteParameters(searchAPIRequestData);
			}
		}
		else
		{
			aniteRequiredParameters = aniteParametersProvider.buildAniteParameters(searchAPIRequestData);
		}
		return aniteRequiredParameters;

	}

	/**
	 * Gets the anite parameters provider.
	 *
	 * @return the anite parameters provider
	 */
	public AniteParametersProvider getAniteParametersProvider()
	{
		return aniteParametersProvider;
	}

	/**
	 * Sets the anite parameters provider.
	 *
	 * @param aniteParametersProvider
	 *           the new anite parameters provider
	 */
	public void setAniteParametersProvider(final AniteParametersProvider aniteParametersProvider)
	{
		this.aniteParametersProvider = aniteParametersProvider;
	}

	/**
	 * Sets the anite required parameters.
	 *
	 * @param aniteRequiredParameters
	 *           the new anite required parameters
	 */
	public void setAniteRequiredParameters(final AniteRequiredParameters aniteRequiredParameters)
	{
		this.aniteRequiredParameters = aniteRequiredParameters;
	}

	/**
	 * Sets the anite interface.
	 *
	 * @param aniteInterface
	 *           the new anite interface
	 */
	public void setAniteInterface(final AniteInterface aniteInterface)
	{
		this.aniteInterface = aniteInterface;
	}

	/**
	 * Gets the anite interface.
	 *
	 * @return the anite interface
	 */
	public AniteInterface getAniteInterface()
	{
		return aniteInterface;
	}

	/**
	 * Gets the duration auto select provider.
	 *
	 * @return the duration auto select provider
	 */
	public AniteParametersProvider getDurationAutoSelectProvider()
	{
		return durationAutoSelectProvider;
	}

	/**
	 * Sets the duration auto select provider.
	 *
	 * @param durationAutoSelectProvider
	 *           the new duration auto select provider
	 */
	public void setDurationAutoSelectProvider(final AniteParametersProvider durationAutoSelectProvider)
	{
		this.durationAutoSelectProvider = durationAutoSelectProvider;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.search.api.services.SearchAPIService#getConfiguratorMatrix(com.endeca.search.api.request.dtos.
	 * SearchAPIRequestData, java.lang.String)
	 */
	@Override
	public ConfiguratorResponseData getConfiguratorResponse(final SearchAPIRequestData searchAPIRequestData,
			final AniteQueryType queryType) throws SearchAPIException
	{
		final long time = new Date().getTime();
		final ConfiguratorResponseData configuratorResponseData = new ConfiguratorResponseData();
		try
		{
			aniteRequiredParameters = null;
			final PackagesData packagesForOneDuration = getAniteInterface().query(queryType,
					getAniteParameters(queryType, searchAPIRequestData));
			setJourneyExtrasForPackages(packagesForOneDuration);
			configuratorResponseData.setResponseOffers(packagesForOneDuration.getPackages());
			configuratorResponseData.setTotalOffers(packagesForOneDuration.getPackages().size());
			final long timetaken = new Date().getTime() - time;
			LOGGER.info("Anite Response-->Thread ID: " + Thread.currentThread().getId() + " Time Taken: " + timetaken
					+ " ms Record Count: " + configuratorResponseData.getTotalOffers());
		}
		catch (final AniteException e)
		{
			throw new SearchAPIException(" Exception Occurred In Fetching Configurator Response From Anite " + e.getMessage(), e);
		}
		return configuratorResponseData;
	}


	/**
	 * Gets the configurator full response.
	 *
	 * @param searchAPIRequestData
	 *           the search api request data
	 * @param queryType
	 *           the query type
	 * @param apcIDActualIDMap
	 *           the apc id actual id map
	 * @return the configurator full response
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	public ConfiguratorResponseData getConfiguratorFullResponse(final SearchAPIRequestData searchAPIRequestData,
			final AniteQueryType queryType, final Map<String, String> apcIDActualIDMap) throws SearchAPIException
	{
		final ConfiguratorResponseData configuratorResponseData = getConfiguratorResponse(searchAPIRequestData, queryType);
		final List<PackageData> packages = configuratorResponseData.getResponseOffers();

		int counter = 0;

		final List<List<BoardData>> boardsForRoomsForFirstOffer = new ArrayList<List<BoardData>>();
		final List<RoomData> roomsForFirstOffer = new ArrayList<RoomData>();
		final Map<String, ENEQueryResults> resultsMap = new HashMap<String, ENEQueryResults>();
		for (final PackageData packageData : packages)
		{
			int listIndex = 0;
			for (final RoomData roomData : packageData.getRoomsData())
			{
				setExtraRoomDetails(packageData.getHotelData().getApcId(), roomData, resultsMap);
				final String defaultHybrisBoardCode = configuredBoardBasis.get(roomData.getDefaultBoard());
				roomData.setDefaultBoard(defaultHybrisBoardCode != null ? defaultHybrisBoardCode : roomData.getDefaultBoard());
				if (queryType.equals(AniteQueryType.SINGLE_ACCOMM_ALL_ROOMS)
						&& (packageData.getHotelData().getAccomType().equals("INTERNAL")))
				{
					if (counter == 0)
					{
						boardsForRoomsForFirstOffer.add(crsSoapService.getAvailableBoardBasis(packageData, roomData));
						roomData.setAvailableBoards(boardsForRoomsForFirstOffer.get(listIndex));
						roomsForFirstOffer.add(roomData);
						continue;
					}
					roomData.setAvailableBoards(boardsForRoomsForFirstOffer.get(listIndex));
					roomData.setCrsErrorCode(roomsForFirstOffer.get(listIndex).getCrsErrorCode());
					roomData.setCrsErrorMessage(roomsForFirstOffer.get(listIndex).getCrsErrorMessage());
					++listIndex;
				}
			}
			++counter;

			packageData.getHotelData().setApcId(apcIDActualIDMap.get(packageData.getHotelData().getApcId()));
		}

		return configuratorResponseData;
	}


	/**
	 * Sets the extra room details.
	 *
	 * @param apcId
	 *           the apc id
	 * @param room
	 *           the room
	 * @param resultsMap
	 * @throws SearchAPIUnmarshallingException
	 *            the search api unmarshalling exception
	 */
	private void setExtraRoomDetails(final String apcId, final RoomData room, final Map<String, ENEQueryResults> resultsMap)
			throws SearchAPIUnmarshallingException
	{
		ENEQueryResults results = null;
		final String roomCode = room.getRoomCode();
		if (!resultsMap.containsKey(roomCode))
		{
			results = getRoomTypeRecord(roomCode, apcId);
			resultsMap.put(roomCode, results);
		}
		else
		{
			results = resultsMap.get(roomCode);
		}

		if (null != results)
		{
			updateRoomDataFromEndecaResponse(room, results, apcId);
		}

	}


	/**
	 * Update room data from endeca response.
	 *
	 * @param room
	 *           the room
	 * @param results
	 *           the results
	 */
	private void updateRoomDataFromEndecaResponse(final RoomData room, final ENEQueryResults results, final String apcId)
	{
		final ERecList records = results.getNavigation().getERecs();
		if (CollectionUtils.isNotEmpty(records))
		{
			final ERec singleRecord = (ERec) records.get(0);

			final PropertyMap properties = singleRecord.getProperties();
			room.setRoomTypeCategoryDescription(
					(String) properties.get(ConfiguratorConstants.ROOMTYPE_ROOMTYPECATEGORYDESCRIPTION_PROPERTY));
			room.setCategoryDescription((String) properties.get(ConfiguratorConstants.ROOMTYPE_CATEGORYDESCRIPTION_PROPERTY));
			room.setRoomTypeDescription((String) properties.get(ConfiguratorConstants.ROOMTYPE_DESCRIPTION_PROPERTY));
			room.setAdditionalText((String) properties.get(ConfiguratorConstants.ROOMTYPE_ADDITIONALTEXT_PROPERTY));
			room.setCategoryImageUrl((String) properties.get(ConfiguratorConstants.ROOMTYPE_CATEGORYIMAGEURL_PROPERTY));
		}

		else
		{
			LOGGER.error("No records found in Endeca for Accommodation : " + apcId + " & RoomTypeGroup : " + room.getRoomCode());
		}

	}

	/**
	 * Gets the room type record.
	 *
	 * @param code
	 *           the code
	 * @param apcId
	 *           the apc id
	 * @return the room type record
	 * @throws SearchAPIUnmarshallingException
	 *            the search api unmarshalling exception
	 */
	private ENEQueryResults getRoomTypeRecord(final String code, final String apcId) throws SearchAPIUnmarshallingException
	{
		ENEQueryResults results = null;
		//		try
		//		{
		final RecordsMdexQuery query = new RecordsMdexQuery();
		final FilterState filterState = new FilterState();
		query.setOffset(0);
		query.setRecordsPerPage(Integer.MAX_VALUE);

		final List<String> recordFilters = new ArrayList<String>();
		final StringBuilder roomGroupCodeRecordFilterBuilder = new StringBuilder();
		roomGroupCodeRecordFilterBuilder.append(ConfiguratorConstants.ROOMTYPE_ROOMGROUPCODE_PROPERTY)
				.append(ConfiguratorConstants.COLON).append(code);
		final StringBuilder accommodationCodeRecordFilterBuilder = new StringBuilder();
		accommodationCodeRecordFilterBuilder.append(ConfiguratorConstants.ROOMTYPE_ACCOMMODATIONCODE_PROPERTY)
				.append(ConfiguratorConstants.COLON).append(apcId);
		final String roomGroupCodeRecordFilter = roomGroupCodeRecordFilterBuilder.toString();
		final String accommodationCodeRecordFilter = accommodationCodeRecordFilterBuilder.toString();
		recordFilters.add(roomGroupCodeRecordFilter);
		recordFilters.add(accommodationCodeRecordFilter);

		filterState.setRecordFilters(recordFilters);

		//			results = getMdexRequestBroker().createMdexRequest(filterState, query).execute();
		//		}
		//		catch (final NavigationException e)
		//		{
		//			throw new SearchAPIUnmarshallingException("Unable to filter records from Endeca " + e.getMessage(), e);
		//		}
		return results;

	}


	/**
	 * Sets the journey duration for packages.
	 *
	 * @param packages
	 *           the new journey duration for packages
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	public void setJourneyExtrasForPackages(final PackagesData packages) throws SearchAPIException
	{
		for (final PackageData currentPackage : packages.getPackages())
		{
			final int numberOfFlightsInOutBound = currentPackage.getOutboundFlight().size();
			final int numberOfFlightsInInBound = currentPackage.getInboundFlight().size();
			final List<FlightData> outBoundFlights = currentPackage.getOutboundFlight();
			final List<FlightData> inBoundFlights = currentPackage.getInboundFlight();
			currentPackage.setOutBoundExtraFlightDetails(
					getExtraFlightDetails(outBoundFlights.get(0), outBoundFlights.get(numberOfFlightsInOutBound - 1)));
			currentPackage.setInboundExtraFlightDetails(
					getExtraFlightDetails(inBoundFlights.get(0), inBoundFlights.get(numberOfFlightsInInBound - 1)));
			setExtraFlightDetailsForEveryRoute(currentPackage.getOutboundFlight());
			setExtraFlightDetailsForEveryRoute(currentPackage.getInboundFlight());
		}
	}


	/**
	 * Sets the extra flight details for every route.
	 *
	 * @param flights
	 *           the flights
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	public void setExtraFlightDetailsForEveryRoute(final List<FlightData> flights) throws SearchAPIException
	{
		for (int i = 0; i < flights.size(); i++)
		{
			final FlightData currentFlight = flights.get(i);
			currentFlight.setJourneyExtras(getExtraFlightDetails(currentFlight, currentFlight));
		}
	}

	/**
	 * Gets the extra flight details.
	 *
	 * @param startingRoute
	 *           the starting route
	 * @param endingRoute
	 *           the ending route
	 * @return the extra flight details
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	public ExtraFlightDetails getExtraFlightDetails(final FlightData startingRoute, final FlightData endingRoute)
			throws SearchAPIException
	{
		final Date startDate = getClubbedDateTime(startingRoute.getDepartureDate(), startingRoute.getDepartureTime());
		final Long startDateInMilli = startDate.getTime();
		final String departureAirportCode = startingRoute.getDeparturePort();
		final ENEQueryResults startTimeZone = getTimeZoneRecord(startDateInMilli, departureAirportCode);

		final Date endDate = getClubbedDateTime(endingRoute.getArrivalDate(), endingRoute.getArrivalTime());
		final Long endDateInMilli = endDate.getTime();
		final String arrivalAirportCode = endingRoute.getArrivalPort();
		final ENEQueryResults endTimeZone = getTimeZoneRecord(endDateInMilli, arrivalAirportCode);

		if (CollectionUtils.isNotEmpty(startTimeZone.getNavigation().getERecs())
				&& CollectionUtils.isNotEmpty(endTimeZone.getNavigation().getERecs()))
		{
			return calculateExtraFlightDetails(startTimeZone, endTimeZone, startDateInMilli, endDateInMilli);
		}
		else
		{
			LOGGER.error("Number of Time zone records in endeca for Departure Airport : " + departureAirportCode + " are : "
					+ startTimeZone.getNavigation().getERecs().size());
			LOGGER.error("Number of Time zone records in endeca for Arrival Airport : " + arrivalAirportCode + " are : "
					+ endTimeZone.getNavigation().getERecs().size());
			LOGGER.error(
					"Flight duration cannot be calculated because one of time zones related to departure or arrival leg is missing in endeca records");
			return null;
		}
	}


	/**
	 * Calculate extra flight details.
	 *
	 * @param startTimeZone
	 *           the start time zone
	 * @param endTimeZone
	 *           the end time zone
	 * @param startDateInMilli
	 *           the start date in milli
	 * @param endDateInMilli
	 *           the end date in milli
	 * @return the extra flight details
	 */
	public ExtraFlightDetails calculateExtraFlightDetails(final ENEQueryResults startTimeZone, final ENEQueryResults endTimeZone,
			Long startDateInMilli, Long endDateInMilli)
	{
		final ERec startRec = (ERec) startTimeZone.getNavigation().getERecs().get(0);
		final ERec endRec = (ERec) endTimeZone.getNavigation().getERecs().get(0);
		String departureAirportName = (String) startRec.getProperties().get(ConfiguratorConstants.TIMEZONE_AIRPORTNAME_PROPERTY);

		if (StringUtils.isEmpty(departureAirportName))
		{
			departureAirportName = StringUtils.EMPTY;
		}
		final String stringStartOffset = (String) startRec.getProperties().get(ConfiguratorConstants.TIMEZONE_OFFSET_PROPERTY);
		final Integer startTimeOffsetValue = Integer.parseInt(stringStartOffset);
		startDateInMilli = startDateInMilli - TimeUnit.MINUTES.toMillis(startTimeOffsetValue);
		final Date adjustedStartDate = new Date(startDateInMilli);

		String arrivalAirportName = (String) endRec.getProperties().get(ConfiguratorConstants.TIMEZONE_AIRPORTNAME_PROPERTY);
		if (StringUtils.isEmpty(arrivalAirportName))
		{
			arrivalAirportName = StringUtils.EMPTY;
		}
		final String stringEndOffset = (String) endRec.getProperties().get(ConfiguratorConstants.TIMEZONE_OFFSET_PROPERTY);
		final Integer endTimeOffsetValue = Integer.parseInt(stringEndOffset);
		endDateInMilli = endDateInMilli - TimeUnit.MINUTES.toMillis(endTimeOffsetValue);
		final Date adjustedEndDate = new Date(endDateInMilli);

		final DateTime adjustedJodaStartDate = new DateTime(adjustedStartDate);
		final DateTime adjustedJodaEndDate = new DateTime(adjustedEndDate);

		final ExtraFlightDetails extraFlightDetails = new ExtraFlightDetails();
		extraFlightDetails.setDays(Days.daysBetween(adjustedJodaStartDate, adjustedJodaEndDate).getDays());
		extraFlightDetails.setHours(Hours.hoursBetween(adjustedJodaStartDate, adjustedJodaEndDate).getHours() % 24);
		extraFlightDetails.setMinutes(Minutes.minutesBetween(adjustedJodaStartDate, adjustedJodaEndDate).getMinutes() % 60);

		final AirportNames airports = new AirportNames();
		airports.setDepartureAirportName(departureAirportName);
		airports.setArrivalAirportName(arrivalAirportName);

		extraFlightDetails.setAirportNames(airports);
		return extraFlightDetails;
	}

	/**
	 * Gets the time zone record.
	 *
	 * @param date
	 *           the date
	 * @param airportCode
	 *           the airport code
	 * @return the time zone record
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	public ENEQueryResults getTimeZoneRecord(final Long date, final String airportCode) throws SearchAPIException
	{
		ENEQueryResults singleMatchingTimeZone = null;

		final RecordsMdexQuery query = new RecordsMdexQuery();
		final FilterState filterState = new FilterState();
		query.setOffset(0);
		query.setRecordsPerPage(Integer.MAX_VALUE);

		final List<String> recordFilters = new ArrayList<String>();
		final StringBuilder timeZoneRecordFilterBuilder = new StringBuilder();
		timeZoneRecordFilterBuilder.append(ConfiguratorConstants.TIMEZONE_AIRPORTCODE_PROPERTY).append(ConfiguratorConstants.COLON)
				.append(airportCode);
		final String recordFilter = timeZoneRecordFilterBuilder.toString();
		recordFilters.add(recordFilter);

		filterState.setRecordFilters(recordFilters);

		final List<RangeFilter> dateRangeFilters = new ArrayList<RangeFilter>();

		final RangeFilter startDateFilter = new RangeFilter(ConfiguratorConstants.TIMEZONE_STARTDATE_PROPERTY, date,
				Operation.LTEQ);
		final RangeFilter endDateFilter = new RangeFilter(ConfiguratorConstants.TIMEZONE_ENDDATE_PROPERTY, date, Operation.GTEQ);
		dateRangeFilters.add(startDateFilter);
		dateRangeFilters.add(endDateFilter);

		filterState.setRangeFilters(dateRangeFilters);
		//			singleMatchingTimeZone = getMdexRequestBroker().createMdexRequest(filterState, query).execute();

		//		catch (final NavigationException e)
		//		{
		//			throw new SearchAPIException("Unable to filter records from Endeca" + e.getMessage(), e);
		//		}
		return singleMatchingTimeZone;

	}

	/**
	 * Gets the clubbed date time.
	 *
	 * @param date
	 *           the date
	 * @param time
	 *           the time
	 * @return the clubbed date time
	 */
	public Date getClubbedDateTime(final Date date, final Date time)
	{
		final Calendar calendarForDate = Calendar.getInstance();
		calendarForDate.setTime(date);
		final Calendar calendarForTime = Calendar.getInstance();
		calendarForTime.setTime(time);

		calendarForDate.set(Calendar.HOUR_OF_DAY, calendarForTime.get(Calendar.HOUR_OF_DAY));
		calendarForDate.set(Calendar.MINUTE, calendarForTime.get(Calendar.MINUTE));
		calendarForDate.set(Calendar.SECOND, calendarForTime.get(Calendar.SECOND));
		calendarForDate.set(Calendar.MILLISECOND, calendarForTime.get(Calendar.MILLISECOND));

		final Date result = calendarForDate.getTime();
		return result;
	}

	//	/**
	//	 * Gets the mdex request broker.
	//	 *
	//	 * @return the mdex request broker
	//	 */
	//	public MdexRequestBroker getMdexRequestBroker()
	//	{
	//		return mdexRequestBroker;
	//	}
	//
	//	/**
	//	 * Sets the mdex request broker.
	//	 *
	//	 * @param mdexRequestBroker
	//	 *           the new mdex request broker
	//	 */
	//	public void setMdexRequestBroker(final MdexRequestBroker mdexRequestBroker)
	//	{
	//		this.mdexRequestBroker = mdexRequestBroker;
	//	}

	/**
	 * Gets the crs soap service.
	 *
	 * @return the crs soap service
	 */
	public CRSAccomBoardService getCrsSoapService()
	{
		return crsSoapService;
	}

	/**
	 * Sets the crs soap service.
	 *
	 * @param crsSoapService
	 *           the new crs soap service
	 */
	public void setCrsSoapService(final CRSAccomBoardService crsSoapService)
	{
		this.crsSoapService = crsSoapService;
	}

	/**
	 * Gets the configured board basis.
	 *
	 * @return the configured board basis
	 */
	public Map<String, String> getConfiguredBoardBasis()
	{
		return configuredBoardBasis;
	}

	/**
	 * Sets the configured board basis.
	 *
	 * @param configuredBoardBasis
	 *           the configured board basis
	 */
	public void setConfiguredBoardBasis(final Map<String, String> configuredBoardBasis)
	{
		this.configuredBoardBasis = configuredBoardBasis;
	}

	/**
	 * Sets the club packages on flight class service.
	 *
	 * @param clubPackagesOnFlightClassService
	 *           the new club packages on flight class service
	 */
	public void setClubPackagesOnFlightClassService(ClubPackagesOnFlightClassService clubPackagesOnFlightClassService)
	{
		this.clubPackagesOnFlightClassService = clubPackagesOnFlightClassService;
	}

}
