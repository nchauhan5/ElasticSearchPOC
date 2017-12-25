package com.endeca.search.api.services.impl;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.endeca.infront.navigation.MdexRequestBroker;
import com.endeca.infront.navigation.NavigationException;
import com.endeca.infront.navigation.model.FilterState;
import com.endeca.infront.navigation.request.MdexQuery;
import com.endeca.infront.navigation.request.MdexRequest;
import com.endeca.navigation.ENEQueryResults;
import com.endeca.navigation.ERec;
import com.endeca.navigation.ERecList;
import com.endeca.navigation.Navigation;
import com.endeca.navigation.PropertyMap;
import com.endeca.search.api.anite.parameters.provider.AniteParametersProvider;
import com.endeca.search.api.constants.ConfiguratorConstants;
import com.endeca.search.api.exceptions.SearchAPIException;
import com.endeca.search.api.keygenerator.impl.FlightPackageKeyGenerator;
import com.endeca.search.api.request.dtos.BrandMarketData;
import com.endeca.search.api.request.dtos.DepartureData;
import com.endeca.search.api.request.dtos.DurationData;
import com.endeca.search.api.request.dtos.PartyData;
import com.endeca.search.api.request.dtos.RemovedFacetPriceData;
import com.endeca.search.api.request.dtos.SearchAPIRequestData;
import com.endeca.search.api.request.dtos.SortPaginationData;
import com.endeca.search.api.response.dtos.ConfiguratorResponseData;
import com.endeca.search.api.response.dtos.FacetCategoryData;
import com.endeca.search.api.response.dtos.FlightData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.PackagesData;
import com.endeca.search.api.services.ClubPackagesOnFlightClassService;
import com.endeca.tui.anite.AniteInterface;
import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteException;
import com.endeca.tui.anite.parameters.HolidayParameters;
import com.endeca.tui.anite.parameters.impl.DefaultHolidayParameters;


public class AniteSearchAPIServiceImplTest
{
	@InjectMocks
	AniteSearchAPIServiceImpl aniteSearchAPIServiceImpl = new AniteSearchAPIServiceImpl();

	@Mock
	AniteParametersProvider aniteParametersProvider;

	@Mock
	AniteInterface aniteInterface;

	@Mock
	MdexRequestBroker mdexRequestBroker;

	@Mock
	private ENEQueryResults mockEneQueryResult;

	@Mock
	private ERec eRecord;

	@Mock
	private Navigation navigation;

	@Mock
	private ERecList eRecList;

	@Mock
	private PropertyMap propertyMap;

	@Mock
	private MdexRequest mockMdexRequest;

	@Mock
	private AniteParametersProvider durationAutoSelectProvider;

	@Mock
	private ClubPackagesOnFlightClassService clubPackagesOnFlightClassService;

	final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData();

	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	final DefaultHolidayParameters defaultHolidayParameters = new DefaultHolidayParameters();

	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);

		defaultHolidayParameters.setAccommodationsString("00003424|s");
		Mockito.when(aniteParametersProvider.buildAniteParameters(searchAPIRequestData)).thenReturn(defaultHolidayParameters);
		Mockito.when(durationAutoSelectProvider.buildAniteParameters(searchAPIRequestData)).thenReturn(defaultHolidayParameters);
		Mockito.when(clubPackagesOnFlightClassService.reduceAndFillPackagesWithFlightClass(Mockito.anyList(),
				Mockito.any(FlightPackageKeyGenerator.class))).thenReturn(new ArrayList<PackageData>());
	}


	/*
	 * This test method is to test ,whether available date is returned when anite query type calender is called and
	 * facets removal value is set to some value
	 */
	@Test
	public void getDateAvailabilityWhenGetCalenderDataIsCalledWithNotNullFacetRemovalValue()
			throws SearchAPIException, ParseException, AniteException
	{
		final List<String> dateslist = Arrays.asList("2015-03-01", "2015-03-02");
		final Map<Date, Boolean> Calender = new HashMap<Date, Boolean>();
		Calender.put(dateFormat.parse(dateslist.get(0)), true);
		Calender.put(dateFormat.parse(dateslist.get(1)), false);
		searchAPIRequestData.setFacetRemoval(null);
		aniteSearchAPIServiceImpl.setAniteInterface(aniteInterface);
		Mockito.when(aniteInterface.query(AniteQueryType.CALENDAR, defaultHolidayParameters)).thenReturn(Calender);
		final Map<Date, Boolean> dateAvailability = aniteSearchAPIServiceImpl.getCalendarData(searchAPIRequestData);
		verify(aniteInterface).query(AniteQueryType.CALENDAR, defaultHolidayParameters);
		hasKey(dateAvailability.get(dateFormat.parse(dateslist.get(0))));
	}

	/*
	 * This test method is to test ,whether available date is returned when anite query with type calender is called and
	 * facets removal value is set to null
	 */
	@Test
	public void getDateAvailabilityWhenGetCalenderDataIsCalledWithNullFacetRemovalValue()
			throws SearchAPIException, ParseException, AniteException
	{
		final List<String> dateslist = Arrays.asList("2015-03-01", "2015-03-02");
		final Map<Date, Boolean> Calender = new HashMap<Date, Boolean>();
		Calender.put(dateFormat.parse(dateslist.get(0)), true);
		Calender.put(dateFormat.parse(dateslist.get(1)), false);
		searchAPIRequestData.setFacetRemoval(null);
		aniteSearchAPIServiceImpl.setAniteInterface(aniteInterface);
		Mockito.when(aniteInterface.query(AniteQueryType.CALENDAR, defaultHolidayParameters)).thenReturn(Calender);
		final Map<Date, Boolean> dateAvailability = aniteSearchAPIServiceImpl.getCalendarData(searchAPIRequestData);
		verify(aniteInterface).query(AniteQueryType.CALENDAR, defaultHolidayParameters);
		assertTrue(dateAvailability.get(dateFormat.parse(dateslist.get(0))));
	}

	/*
	 * This test method is to test ,whether searchAPI exception is thrown when Anite query with type calender is called
	 */
	@Test(expected = SearchAPIException.class)
	public void searchExceptionShouldBeThrownWhenAniteExceptionOccurswhenCallingQuery()
			throws SearchAPIException, ParseException, AniteException
	{
		final List<String> dateslist = Arrays.asList("2015-03-01", "2015-03-02");
		final Map<Date, Boolean> Calender = new HashMap<Date, Boolean>();
		Calender.put(dateFormat.parse(dateslist.get(0)), true);
		Calender.put(dateFormat.parse(dateslist.get(1)), false);
		searchAPIRequestData.setFacetRemoval(null);
		aniteSearchAPIServiceImpl.setAniteInterface(aniteInterface);
		Mockito.when(aniteInterface.query(AniteQueryType.CALENDAR, defaultHolidayParameters)).thenThrow(new AniteException());
		aniteSearchAPIServiceImpl.getCalendarData(searchAPIRequestData);
	}

	/*
	 * This test method is to test ,whether package data is returned when anite query with type LIST_BY_ACCOMMODATION is
	 * called and facets removal value is set to some value
	 */
	@Test
	public void getPackageDataWhenGetPackageAndFacetataIsCalledWithNotNullFacetRemovalValue()
			throws SearchAPIException, ParseException, AniteException
	{
		final PackagesData packagesData = new PackagesData();
		packagesData.setPackages(new ArrayList<PackageData>());
		searchAPIRequestData.setFacetRemoval(new RemovedFacetPriceData());
		aniteSearchAPIServiceImpl.setAniteInterface(aniteInterface);
		Mockito.when(aniteInterface.query(AniteQueryType.LIST_BY_ACCOMMODATION, defaultHolidayParameters)).thenReturn(packagesData);
		final PackagesData packagesData1 = aniteSearchAPIServiceImpl.getPackageAndFacetData(searchAPIRequestData);
		verify(aniteInterface).query(AniteQueryType.LIST_BY_ACCOMMODATION, defaultHolidayParameters);
		Assert.assertNotNull(packagesData1);
	}

	/*
	 * This test method is to test ,whether searchAPI exception is thrown when Anite query with type
	 * LIST_BY_ACCOMMODATION is called
	 */
	@Test(expected = SearchAPIException.class)
	public void searchExceptionShouldBeThrownWhenAniteExceptionOccurswhenCallingQueryInGetPackageData()
			throws SearchAPIException, ParseException, AniteException
	{
		final PackagesData packagesData;
		searchAPIRequestData.setFacetRemoval(null);
		aniteSearchAPIServiceImpl.setAniteInterface(aniteInterface);
		Mockito.when(aniteInterface.query(AniteQueryType.LIST_BY_ACCOMMODATION, defaultHolidayParameters))
				.thenThrow(new AniteException());
		aniteSearchAPIServiceImpl.getPackageAndFacetData(searchAPIRequestData);
	}

	/*
	 * This test method is to test ,whether package data is returned when anite query with type calender is
	 * LIST_BY_PACKAGEPRODUCTS and facets removal value is set to some value
	 */
	@Test
	public void getPackageDataWhenGetPackageDataForPPCIsCalledWithNotNullFacetRemovalValue()
			throws SearchAPIException, ParseException, AniteException
	{
		final PackagesData packagesData = new PackagesData();
		packagesData.setPackages(new ArrayList<PackageData>());
		searchAPIRequestData.setFacetRemoval(new RemovedFacetPriceData());
		aniteSearchAPIServiceImpl.setAniteInterface(aniteInterface);
		Mockito.when(aniteInterface.query(AniteQueryType.LIST_BY_PACKAGEPRODUCTS, defaultHolidayParameters))
				.thenReturn(packagesData);
		final PackagesData packagesData1 = aniteSearchAPIServiceImpl.getPackageDataForPPC(searchAPIRequestData);
		verify(aniteInterface).query(AniteQueryType.LIST_BY_PACKAGEPRODUCTS, defaultHolidayParameters);
		Assert.assertNotNull(packagesData1);
	}

	/*
	 * This test method is to test ,whether searchAPI exception is thrown when Anite query with type
	 * LIST_BY_PACKAGEPRODUCTS is called
	 */
	@Test(expected = SearchAPIException.class)
	public void searchExceptionShouldBeThrownWhenAniteExceptionOccurswhenCallingQueryInGetPackageDataForPPC()
			throws SearchAPIException, ParseException, AniteException
	{
		final PackagesData packagesData;
		searchAPIRequestData.setFacetRemoval(null);
		aniteSearchAPIServiceImpl.setAniteInterface(aniteInterface);
		Mockito.when(aniteInterface.query(AniteQueryType.LIST_BY_PACKAGEPRODUCTS, defaultHolidayParameters))
				.thenThrow(new AniteException());
		aniteSearchAPIServiceImpl.getPackageDataForPPC(searchAPIRequestData);
	}

	/*
	 * This test method is to test ,whether Available duartion is returned when anite query with type
	 * LIST_BY_PACKAGEPRODUCTS and facets removal value is set to some value
	 */
	@Test
	public void getAvalaibleDurationWhenGetDurationDataIsCalledWithNotNullFacetRemovalValue()
			throws SearchAPIException, ParseException, AniteException
	{
		final List<Integer> availableDurations = Arrays.asList(5, 6, 7);
		searchAPIRequestData.setFacetRemoval(new RemovedFacetPriceData());
		aniteSearchAPIServiceImpl.setAniteInterface(aniteInterface);
		Mockito.when(aniteInterface.query(AniteQueryType.DURATIONS, defaultHolidayParameters)).thenReturn(availableDurations);
		final List<Integer> availableDuration = aniteSearchAPIServiceImpl.getDurationsData(searchAPIRequestData);
		verify(aniteInterface).query(AniteQueryType.DURATIONS, defaultHolidayParameters);
		assertThat(availableDuration.get(0), is(5));
	}

	/*
	 * This test method is to test ,whether searchAPI exception is thrown when Anite query with type calender is called
	 */
	@Test(expected = SearchAPIException.class)
	public void searchExceptionShouldBeThrownWhenAniteExceptionOccurswhenCallingQueryInGetDurationData()
			throws SearchAPIException, ParseException, AniteException
	{
		final List<Integer> availableDurations = Arrays.asList(5, 6, 7);
		searchAPIRequestData.setFacetRemoval(null);
		aniteSearchAPIServiceImpl.setAniteInterface(aniteInterface);
		Mockito.when(aniteInterface.query(AniteQueryType.DURATIONS, defaultHolidayParameters)).thenThrow(new AniteException());
		aniteSearchAPIServiceImpl.getDurationsData(searchAPIRequestData);
	}

	/*
	 * This test method is to test ,whether facet category data is returned when anite query with type
	 * SINGLE_ACCOMMODATION and facets removal value is set to some value
	 */
	@Test
	public void getfacetCategoryDatasWhenGetDepartureDataHasSingleAccommodation()
			throws SearchAPIException, ParseException, AniteException
	{
		final List<FacetCategoryData> facetCategoryDatas = new ArrayList<FacetCategoryData>();
		final FacetCategoryData facetCategryData = new FacetCategoryData();
		facetCategoryDatas.add(facetCategryData);
		final Map<String, String> passThroughMap = new HashMap<String, String>();
		final DepartureData departureData = new DepartureData();
		final Set<String> candidateCodes = new HashSet<String>();
		candidateCodes.add("1");
		departureData.setCandidateCodes(candidateCodes);
		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData(departureData, new DurationData(),
				new PartyData(), new BrandMarketData(), new SortPaginationData(), passThroughMap);
		searchAPIRequestData.setFacetRemoval(new RemovedFacetPriceData());
		aniteSearchAPIServiceImpl.setAniteInterface(aniteInterface);
		defaultHolidayParameters.setAccommodationsString("00003424|s");
		Mockito.when(aniteParametersProvider.buildAniteParameters(searchAPIRequestData)).thenReturn(defaultHolidayParameters);
		Mockito.when(aniteInterface.query(AniteQueryType.SINGLE_ACCOMMODATION, defaultHolidayParameters))
				.thenReturn(facetCategoryDatas);
		final List<FacetCategoryData> facetCategoryData = aniteSearchAPIServiceImpl.getFacetsData(searchAPIRequestData);
		Assert.assertNotNull(facetCategoryData);
	}

	/*
	 * This test method is to test ,whether facet category data is returned when anite query with type
	 * LIST_BY_ACCOMMODATION and facets removal value is set to some value
	 */
	@Test
	public void getfacetCategoryDatasWhenGetDepartureDataHasManyAccommodation()
			throws SearchAPIException, ParseException, AniteException
	{
		final List<FacetCategoryData> facetCategoryDatas = new ArrayList<FacetCategoryData>();
		final Map<String, String> passThroughMap = new HashMap<String, String>();
		final DepartureData departureData = new DepartureData();
		final Set<String> accommodationCodes = new HashSet<String>();
		accommodationCodes.add("1");
		accommodationCodes.add("2");
		departureData.setCandidateCodes(accommodationCodes);
		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData(departureData, new DurationData(),
				new PartyData(), new BrandMarketData(), new SortPaginationData(), passThroughMap);
		departureData.setCandidateCodes(accommodationCodes);
		searchAPIRequestData.setFacetRemoval(new RemovedFacetPriceData());
		aniteSearchAPIServiceImpl.setAniteInterface(aniteInterface);
		defaultHolidayParameters.setAccommodationsString("00003424|s");
		Mockito.when(aniteParametersProvider.buildAniteParameters(searchAPIRequestData)).thenReturn(defaultHolidayParameters);
		Mockito.when(aniteInterface.query(AniteQueryType.LIST_BY_ACCOMMODATION, defaultHolidayParameters))
				.thenReturn(facetCategoryDatas);
		final List<FacetCategoryData> facetCategoryData = aniteSearchAPIServiceImpl.getFacetsData(searchAPIRequestData);
		verify(aniteInterface).query(AniteQueryType.LIST_BY_ACCOMMODATION, defaultHolidayParameters);
		Assert.assertNotNull(facetCategoryData);
	}

	/*
	 * This test method is to test ,whether searchAPI exception is thrown when Anite query with type
	 * LIST_BY_ACCOMMODATION is called
	 */
	@Test(expected = SearchAPIException.class)
	public void searchExceptionShouldBeThrownWhenAniteExceptionOccurswhenCallingQueryInGetFacetData()
			throws SearchAPIException, ParseException, AniteException
	{
		final List<FacetCategoryData> facetCategoryDatas = new ArrayList<FacetCategoryData>();
		final Map<String, String> passThroughMap = new HashMap<String, String>();
		final DepartureData departureData = new DepartureData();
		final Set<String> accommodationCodes = new HashSet<String>();
		accommodationCodes.add("1");
		accommodationCodes.add("2");
		departureData.setCandidateCodes(accommodationCodes);
		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData(departureData, new DurationData(),
				new PartyData(), new BrandMarketData(), new SortPaginationData(), passThroughMap);
		departureData.setCandidateCodes(accommodationCodes);
		searchAPIRequestData.setFacetRemoval(null);
		aniteSearchAPIServiceImpl.setAniteInterface(aniteInterface);
		final HolidayParameters holidayParameters = null;
		Mockito.when(aniteInterface.query(AniteQueryType.LIST_BY_ACCOMMODATION, holidayParameters)).thenThrow(new AniteException());
		aniteSearchAPIServiceImpl.getFacetsData(searchAPIRequestData);

	}

	/**
	 * This test method is to test ,whether response returned from Configurator is not null.
	 *
	 * @throws SearchAPIException
	 *            the search api exception
	 * @throws ParseException
	 *            the parse exception
	 * @throws AniteException
	 *            the anite exception
	 * @throws NavigationException
	 *            the navigation exception
	 */
	@Test
	public void testConfiguratorResponseForMatrix() throws SearchAPIException, ParseException, AniteException, NavigationException
	{
		final DefaultHolidayParameters defaultHolidayParameters = new DefaultHolidayParameters();
		defaultHolidayParameters.setAccommodationsString("00012690|s");
		aniteSearchAPIServiceImpl.setAniteInterface(aniteInterface);
		aniteSearchAPIServiceImpl.setAniteParametersProvider(aniteParametersProvider);
		//		aniteSearchAPIServiceImpl.setMdexRequestBroker(mdexRequestBroker);

		final PackagesData packagesData = createPackagesData();

		Mockito.when(aniteInterface.query(AniteQueryType.SINGLE_ACCOMM_CHEAPEST_PER_DAY, null)).thenReturn(packagesData);
		Mockito.when(mdexRequestBroker.createMdexRequest(Mockito.any(FilterState.class), Mockito.any(MdexQuery.class)))
				.thenReturn(mockMdexRequest);

		Mockito.when(mockMdexRequest.execute()).thenReturn(mockEneQueryResult);
		Mockito.when(mockEneQueryResult.getNavigation()).thenReturn(navigation);
		Mockito.when(navigation.getERecs()).thenReturn(eRecList);
		Mockito.when(eRecList.get(0)).thenReturn(eRecord);
		Mockito.when(eRecord.getProperties()).thenReturn(propertyMap);
		Mockito.when(propertyMap.get(ConfiguratorConstants.TIMEZONE_AIRPORTNAME_PROPERTY)).thenReturn("ARN");
		Mockito.when(propertyMap.get(ConfiguratorConstants.TIMEZONE_OFFSET_PROPERTY)).thenReturn("120");

		final SearchAPIRequestData requestData = new SearchAPIRequestData();
		final ConfiguratorResponseData matrixData = aniteSearchAPIServiceImpl.getConfiguratorResponse(requestData,
				AniteQueryType.SINGLE_ACCOMM_CHEAPEST_PER_DAY);
		Assert.assertNotNull(matrixData);
	}

	/*
	 * This test method is to create mock PackagesData.
	 */
	private PackagesData createPackagesData()
	{
		final PackagesData packagesData = new PackagesData();

		final List<PackageData> packages = new ArrayList<PackageData>();
		final PackageData packageData = new PackageData();
		final List<FlightData> outboundFlights = new ArrayList<FlightData>();
		final List<FlightData> inboundFlights = new ArrayList<FlightData>();
		final FlightData outboundFlight = new FlightData();
		outboundFlight.setDepartureDate(new Date());
		outboundFlight.setDepartureTime(new Date());
		outboundFlight.setArrivalDate(new Date());
		outboundFlight.setArrivalTime(new Date());
		final FlightData inboundFlight = new FlightData();
		inboundFlight.setDepartureDate(new Date());
		inboundFlight.setDepartureTime(new Date());
		inboundFlight.setArrivalDate(new Date());
		inboundFlight.setArrivalTime(new Date());
		outboundFlights.add(outboundFlight);
		inboundFlights.add(inboundFlight);
		packageData.setOutboundFlight(outboundFlights);
		packageData.setInboundFlight(inboundFlights);
		packages.add(packageData);
		packagesData.setPackages(packages);

		return packagesData;
	}
}
