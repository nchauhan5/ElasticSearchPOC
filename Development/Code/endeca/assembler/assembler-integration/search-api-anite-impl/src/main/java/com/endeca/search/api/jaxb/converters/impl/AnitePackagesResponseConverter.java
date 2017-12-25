package com.endeca.search.api.jaxb.converters.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.endeca.search.api.constants.AniteFacetCodes;
import com.endeca.search.api.constants.ConfiguratorConstants;
import com.endeca.search.api.constants.SearchRequestParams;
import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;
import com.endeca.search.api.jaxb.converters.PackagesResponseConverter;
import com.endeca.search.api.response.dtos.FacetCategoryData;
import com.endeca.search.api.response.dtos.FacetData;
import com.endeca.search.api.response.dtos.FlightData;
import com.endeca.search.api.response.dtos.HotelData;
import com.endeca.search.api.response.dtos.OfferData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.PackagesData;
import com.endeca.search.api.response.dtos.PartyData;
import com.endeca.search.api.response.dtos.RoomData;
import com.endeca.search.api.response.dtos.TotalPriceData;
import com.endeca.tui.anite.response.AvCache;
import com.endeca.tui.anite.response.AvCache.Result;
import com.endeca.tui.anite.response.AvCache.Result.Offers;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Facets;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Facets.Cat;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Facets.Cat.Facet;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Lists;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom.Unit;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Transport;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Transport.Route;
import com.endeca.tui.anite.response.YesNo;


/**
 * The Class AnitePackagesResponseConverter this is the anite specific implementation of
 * {@link PackagesResponseConverter} used to convert anite specific jaxb response to {@link PackagesData}.
 */
public class AnitePackagesResponseConverter implements PackagesResponseConverter
{
	/** The Constant OUTBOUND. */
	public static final String OUTBOUND = "O";

	/** The Constant INBOUND. */
	public static final String INBOUND = "I";

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.search.api.jaxb.converters.PackagesResponseConverter# convertJaxbResponse(java.lang.Object)
	 */
	@Override
	public PackagesData convertJaxbResponse(final Object jaxbElement) throws SearchAPIUnmarshallingException
	{
		final PackagesData packagesData = new PackagesData();
		final List<PackageData> packages = new ArrayList<PackageData>();

		final AvCache avCache = (AvCache) jaxbElement;

		if (null != avCache.getError())
		{
			final AvCache.Error aniteError = avCache.getError();
			throw new SearchAPIUnmarshallingException(
					" Received error code : " + aniteError.getCode() + " with text : " + aniteError.getText(), new Throwable());
		}

		else
		{
			final Result result = avCache.getResult();
			final Offers offers = result.getOffers();

			convertOffersToPackages(offers, packages);

			packagesData.setCount(packages.size());
			packagesData.setPackages(packages);
			setFacetdata(offers, packagesData);
			return packagesData;
		}
	}

	/**
	 * Convert offers to packages.
	 *
	 * @param offers
	 *           the offers
	 * @param packages
	 *           the packages
	 * @throws SearchAPIUnmarshallingException
	 *            the search api unmarshalling exception
	 */
	public void convertOffersToPackages(final Offers offers, final List<PackageData> packages)
			throws SearchAPIUnmarshallingException
	{
		final Set<String> uniqueBaseAccIDs = new HashSet<String>();
		for (final Offer offer : offers.getOffer())
		{
			uniqueBaseAccIDs.add(offer.getAccom().getId());
			final PackageData packageData = new PackageData();
			packageData.setRoomsData(getRoomData(offer.getAccom()));
			setDataInPackage(offer, packageData);
			packages.add(packageData);
		}
		if (uniqueBaseAccIDs.size() == 1)
		{
			setPackageId(offers, packages);
		}
	}

	/**
	 * Sets the data in package.
	 *
	 * @param offer
	 *           the offer
	 * @param packageData
	 *           the package data
	 * @return the package data
	 * @throws SearchAPIUnmarshallingException
	 *            the search api unmarshalling exception
	 */
	public PackageData setDataInPackage(final Offer offer, final PackageData packageData) throws SearchAPIUnmarshallingException
	{
		packageData.setOfferData(getOfferData(offer));
		packageData.setHotelData(getHotelData(offer.getAccom()));
		setTransportData(offer, packageData);
		setTotalPrice(offer, packageData);

		return packageData;
	}

	/**
	 * Sets the package id only in case of single accommodation search or configurator matrix results to keep the json
	 * response from assembler as concise as possible.
	 *
	 * @param offers
	 *           the offers
	 * @param packages
	 *           the packages list
	 */
	public void setPackageId(final Offers offers, final List<PackageData> packages)
	{
		for (int i = 0; i < offers.getOffer().size(); i++)
		{
			packages.get(i).getOfferData().setPackageID(offers.getOffer().get(i).getPkg());
		}
	}

	/**
	 * Sets the total price.
	 *
	 * @param offer
	 *           the offer
	 * @param packageData
	 *           the package data
	 */
	public void setTotalPrice(final Offer offer, final PackageData packageData)
	{
		//
		// discounted data
		final BigDecimal discountedPrice = packageData.getOfferData().getDiscountedPrice();
		// discounted data per person
		final BigDecimal discountedPricePerPerson = packageData.getOfferData().getDiscountedPricePerson();
		final List<RoomData> roomsData = packageData.getRoomsData();
		BigDecimal totalPrice = BigDecimal.ZERO;
		BigDecimal totalPerPersonPrice = BigDecimal.ZERO;
		BigDecimal totalDiscount = BigDecimal.ZERO;
		BigDecimal totalDiscountPerPerson = BigDecimal.ZERO;
		if (CollectionUtils.isNotEmpty(packageData.getRoomsData()))
		{
			for (final RoomData roomData : roomsData)
			{
				// discount total is always returned in negative,so it is
				// subtracted from already discounted price to get the total
				// price of package.
				totalDiscount = totalDiscount.add(roomData.getUnitDiscount());
				// discount per person
				totalDiscountPerPerson = totalDiscountPerPerson.add(roomData.getUnitDiscountPPP());
			}
		}
		totalPrice = discountedPrice.subtract(totalDiscount);
		totalPerPersonPrice = discountedPricePerPerson.subtract(totalDiscountPerPerson);
		final TotalPriceData totalPriceData = new TotalPriceData();
		totalPriceData.setTotalPrice(totalPrice);
		totalPriceData.setTotalPricePerPerson(totalPerPersonPrice);
		packageData.setTotalPriceData(totalPriceData);

	}

	/**
	 * Sets the transport data.
	 *
	 * @param offer
	 *           the offer
	 * @param packageData
	 *           the package data
	 * @throws SearchAPIUnmarshallingException
	 *            the search api unmarshalling exception
	 */
	public void setTransportData(final Offer offer, final PackageData packageData) throws SearchAPIUnmarshallingException
	{
		for (final Transport transport : offer.getTransport())
		{
			final DateFormat dateFormat = new SimpleDateFormat("HHmm");

			final List<FlightData> inboundFlightList = new ArrayList<FlightData>();
			final List<FlightData> outboundFlightList = new ArrayList<FlightData>();
			for (final Route route : transport.getRoute())
			{
				final FlightData flightData = new FlightData();
				flightData.setArrivalDate(route.getArrDate().toGregorianCalendar().getTime());
				flightData.setArrivalPort(route.getArrPt());
				// we need to clean this sysInfo as anite is sending extra
				// space( ) and (-), we had to clean that.
				flightData.setFlightInfo(getFlightInfo(transport.getSysInfo()));
				try
				{
					flightData.setArrivalTime(dateFormat.parse(route.getArrTime()));
					flightData.setDepartureTime(dateFormat.parse(route.getDepTime()));
				}
				catch (final ParseException e)
				{
					throw new SearchAPIUnmarshallingException("Exception Occurred in parsing date" + e.getMessage(), e);
				}
				flightData.setDepartureDate(route.getDepDate().toGregorianCalendar().getTime());
				flightData.setDeparturePort(route.getDepPt());

				flightData.setAvailability((int) route.getAvail());
				flightData.setMeals((null != route.getMeal()) && (route.getMeal().equals(YesNo.Y) ? true : false));
				flightData.setFlightType((null != route.getExt()) && (route.getExt().equals(YesNo.Y)) ? "EXTERNAL" : "INTERNAL");
				flightData.setFlightNo(route.getFltNo());

				if (OUTBOUND.equals(route.getDir()))
				{
					outboundFlightList.add(flightData);
				}

				if (INBOUND.equals(route.getDir()))
				{
					inboundFlightList.add(flightData);
				}
			}
			packageData.setOutboundFlight(outboundFlightList);
			packageData.setInboundFlight(inboundFlightList);
			packageData.setBookingFlightClass(getBookingClass(transport.getSysInfo()));
		}
	}

	/**
	 * Gets the room data.
	 *
	 * @param accom
	 *           the accom
	 * @return the room data
	 */
	public List<RoomData> getRoomData(final Accom accom)
	{
		final List<RoomData> rooms = new ArrayList<RoomData>();
		for (final Unit unit : accom.getUnit())
		{
			RoomData room = populateRoomData(unit);
			rooms.add(room);
		}
		return rooms;
	}

	protected RoomData populateRoomData(final Unit unit)
	{
		final RoomData room = new RoomData();
		room.setAvailability((int) unit.getAvail());
		room.setDefaultBoard(unit.getBoard());
		room.setUnitDiscountPPP(unit.getDiscPP());
		room.setUnitDiscountedPrice(unit.getPrice());
		room.setUnitDisPricePerPerson(unit.getPricePP());
		room.setRoomCode(unit.getCode());
		room.setRoomInfo(unit.getSysInfo());
		room.setUnitDiscount(unit.getDisc());
		final PartyData partyData = new PartyData();
		partyData.setAdults(unit.getPriced().getAd());
		partyData.setChildren(unit.getPriced().getCh());
		partyData.setInfants(unit.getPriced().getIn());
		partyData.setYouth(unit.getPriced().getYth());
		room.setPartyData(partyData);
		return room;
	}


	/**
	 * Gets the hotel data.
	 *
	 * @param accom
	 *           the accom
	 * @return the hotel data
	 */
	public HotelData getHotelData(final Accom accom)
	{
		final HotelData hotelData = new HotelData();
		hotelData.setBrand(accom.getBrand());
		hotelData.setCommercialPriority(Integer.parseInt(accom.getCommPri()));
		hotelData.setId(accom.getId());
		hotelData.setCode(accom.getCode());
		hotelData.setAccomType((null != accom.getExt()) && (accom.getExt().equals(YesNo.Y)) ? "EXTERNAL" : "INTERNAL");

		final Map<String, String> map = getSysInfoData(accom.getSysInfo());
		hotelData.setApcId(map.get(SearchRequestParams.APC_ID));
		hotelData.setSysInfo(map.get(SearchRequestParams.SYS_INFO));
		return hotelData;
	}

	/**
	 * Gets the offer data.
	 *
	 * @param offer
	 *           the offer
	 * @return the offer data
	 */
	public OfferData getOfferData(final Offer offer)
	{
		final OfferData offerData = new OfferData();
		offerData.setWorldCare(offer.getWorldCare().equals(YesNo.Y) ? true : false);

		offerData.setDate(offer.getDate().toGregorianCalendar().getTime());
		offerData.setStay(offer.getStay());

		offerData.setDiscountedPrice(offer.getPrice());
		offerData.setDiscountedPricePerson(offer.getPricePP());
		offerData.setDeposit(offer.getDeposit());
		return offerData;
	}

	/**
	 * This method is used to create the Map of apc ID and sys Info based on the sysInfo attribute fetched from Anite.
	 *
	 * @param sysInfo
	 *           sys Info
	 * @return the Map
	 */
	public Map<String, String> getSysInfoData(final String sysInfo)
	{
		final Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(sysInfo))
		{
			final int indexOfDash = sysInfo.indexOf("-");

			if (indexOfDash > 0)
			{
				map.put(SearchRequestParams.APC_ID, sysInfo.substring(0, indexOfDash).trim());
				map.put(SearchRequestParams.SYS_INFO, sysInfo.substring(indexOfDash + 1).trim());
			}
			else
			{
				map.put(SearchRequestParams.APC_ID, StringUtils.EMPTY);
				map.put(SearchRequestParams.SYS_INFO, StringUtils.EMPTY);
			}
		}
		else
		{
			map.put(SearchRequestParams.APC_ID, StringUtils.EMPTY);
			map.put(SearchRequestParams.SYS_INFO, StringUtils.EMPTY);
		}

		return map;
	}

	/**
	 * Sets the facetdata.
	 *
	 * @param offers
	 *           the offers
	 * @param packagesData
	 *           the packages data
	 */
	private void setFacetdata(final Offers offers, final PackagesData packagesData)
	{
		List<FacetCategoryData> facetCategoryDataList = new ArrayList<FacetCategoryData>();
		if (null != offers.getFacets())
		{
			final Facets facets = offers.getFacets();
			final List<Cat> fcategory = facets.getCat();
			for (final Cat c : fcategory)
			{
				final List<FacetData> facetDataList = new ArrayList<FacetData>();
				final FacetCategoryData facetCategory = new FacetCategoryData();
				facetCategory.setCategoryCode(c.getCode());
				facetCategory.setCategoryName("");
				final List<Facet> facetList = c.getFacet();
				for (final Facet fat : facetList)
				{
					final FacetData fdata = new FacetData();
					fdata.setCode(fat.getCode());
					fdata.setCount((int) fat.getCount());
					fdata.setMax(null != fat.getMax() ? fat.getMax().floatValue() : 0);
					fdata.setMin(null != fat.getMin() ? fat.getMin().floatValue() : 0);
					fdata.setName("");
					facetDataList.add(fdata);
				}
				facetCategory.setFacets(facetDataList);
				facetCategoryDataList.add(facetCategory);
			}
		}
		/*
		 * Converting Anite Lists containing Departure Airports To Facet
		 */
		if (null != offers.getLists())
		{
			final Lists lists = offers.getLists();

			// Adding Departure airport in the facet Category Data
			addListFacetToFacetData(lists.getDepPt(), AniteFacetCodes.DEPARTUREAIRPORTS, facetCategoryDataList);

			// Adding Departure time in the facet Category Data
			addListFacetToFacetData(lists.getOutSlots(), AniteFacetCodes.DEPARTURETIME, facetCategoryDataList);

			// Adding Arrival time in the facet Category Data
			addListFacetToFacetData(lists.getInSlots(), AniteFacetCodes.ARRIVALTIME, facetCategoryDataList);

			// Adding Total Price in the facet Category Data
			facetCategoryDataList = addPriceTotalToFacetData(facetCategoryDataList, lists);

			// Adding Price per Person in the facet Category Data
			facetCategoryDataList = addPricePerPersonToFacetData(facetCategoryDataList, lists);

		}

		packagesData.setFacetCategoryDatas(facetCategoryDataList);
	}

	/**
	 * Adds the list facet to facet data.
	 *
	 * @param facetValue
	 *           the facet value
	 * @param anitFacetCode
	 *           the anit facet code
	 * @param facetCategoryDataList
	 *           the facet category data list
	 */
	private void addListFacetToFacetData(final String facetValue, final String anitFacetCode,
			final List<FacetCategoryData> facetCategoryDataList)
	{
		final String[] facetValueList = StringUtils.split(facetValue, ",");
		final FacetCategoryData facetCategory = new FacetCategoryData();
		facetCategory.setCategoryCode(anitFacetCode);
		facetCategory.setCategoryName(anitFacetCode);
		final List<FacetData> facetDataList = new ArrayList<FacetData>();
		for (final String facet : facetValueList)
		{
			final FacetData facetData = new FacetData();
			facetData.setCode(facet);
			facetData.setName(facet);
			facetDataList.add(facetData);
		}
		facetCategory.setFacets(facetDataList);
		facetCategoryDataList.add(facetCategory);
	}

	/**
	 * Adds the price total to facet data.
	 *
	 * @param facetCategoryDataList
	 *           the facet category data list
	 * @return the list
	 */
	private List<FacetCategoryData> addPriceTotalToFacetData(final List<FacetCategoryData> facetCategoryDataList,
			final Lists lists)
	{
		final FacetCategoryData totalPriceFacetCategory = new FacetCategoryData();
		final List<FacetData> facetDataList = new ArrayList<FacetData>();

		totalPriceFacetCategory.setCategoryCode(AniteFacetCodes.PRICETOTAL);
		totalPriceFacetCategory.setCategoryName(AniteFacetCodes.PRICETOTAL);

		final String totalPrice = lists.getPrice();
		final String[] totalPriceArray = totalPrice.split(",");
		for (final String priceTotal : totalPriceArray)
		{
			final FacetData facetData = new FacetData();
			facetData.setCode(priceTotal);
			facetData.setName(AniteFacetCodes.PRICETOTAL);
			facetDataList.add(facetData);
		}
		totalPriceFacetCategory.setFacets(facetDataList);
		facetCategoryDataList.add(totalPriceFacetCategory);
		return facetCategoryDataList;
	}

	/**
	 * Adds the price per person to facet data.
	 *
	 * @return the list
	 */
	private List<FacetCategoryData> addPricePerPersonToFacetData(final List<FacetCategoryData> facetCategoryDataList,
			final Lists lists)
	{
		final FacetCategoryData pricePerPersonFacetCategory = new FacetCategoryData();
		final List<FacetData> facetDataList = new ArrayList<FacetData>();

		pricePerPersonFacetCategory.setCategoryCode(AniteFacetCodes.PRICEPERPERSON);
		pricePerPersonFacetCategory.setCategoryName(AniteFacetCodes.PRICEPERPERSON);

		final String pricePerPerson = lists.getPricePP();
		final String[] pricePerPersonArray = pricePerPerson.split(",");
		for (final String ppPrice : pricePerPersonArray)
		{
			final FacetData facetData = new FacetData();
			facetData.setCode(ppPrice);
			facetData.setName(AniteFacetCodes.PRICEPERPERSON);
			facetDataList.add(facetData);
		}
		pricePerPersonFacetCategory.setFacets(facetDataList);
		facetCategoryDataList.add(pricePerPersonFacetCategory);
		return facetCategoryDataList;
	}

	/**
	 * This method will clean the additional input from the flightinfo,it will provide return the data after the (-) if
	 * it contains else it will provide the actual value.
	 *
	 * @param flightInfo
	 * @return String
	 */
	private String getFlightInfo(final String flightInfo)
	{
		final int indexOfDash = flightInfo.indexOf("-");
		final String response = flightInfo.substring(indexOfDash + 1);
		return response;
	}

	/**
	 * Gets the sys info data.
	 *
	 * @param sysInfo
	 *           the sys info
	 * @return the sys info data
	 */
	private String getBookingClass(final String sysInfo)
	{
		final int indexOfDash = sysInfo.indexOf("-");
		if (indexOfDash > 0)
		{
			String bookingClass = sysInfo.substring(indexOfDash - 1, indexOfDash);
			if (StringUtils.isBlank(bookingClass))
			{
				bookingClass = ConfiguratorConstants.CLASS_CODE_E;
			}
			return bookingClass;
		}
		return null;
	}
}
