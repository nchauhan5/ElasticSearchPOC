package com.endeca.search.api.jaxb.converters.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.hamcrest.core.IsNull;
import org.hamcrest.number.OrderingComparison;
import org.junit.Before;
import org.junit.Test;

import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;
import com.endeca.search.api.response.dtos.OfferData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.PackagesData;
import com.endeca.search.api.response.dtos.RoomData;
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
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom.Unit.Priced;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.AltBoard;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.AltBoard.Board;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Transport;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Transport.Route;
import com.endeca.tui.anite.response.YesNo;


public class AnitePackagesResponseConverterTest
{
	private final AnitePackagesResponseConverter anitePackagesResponseConvert = new AnitePackagesResponseConverter();

	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

	PackageData packageData = new PackageData();

	final List<PackageData> packages = new ArrayList<PackageData>();

	final PackagesData packagesData = new PackagesData();

	AvCache avCache = new AvCache();

	Result results = new Result();

	Offers offers = new Offers();

	Offer offer = new Offer();

	Accom accom = new Accom();

	int cases = 0;

	@Before
	public void setUp() throws SearchAPIUnmarshallingException, ParseException, DatatypeConfigurationException
	{
		avCache.setResult(results);
		results.setOffers(offers);
		offer.setAccom(accom);
		packages.add(packageData);
	}

	@Test
	public void packageDataShouldNotBeEmptyWhenOfferListIsEmpty()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertTrue(packageDatas.getPackages().isEmpty());
	}

	@Test
	public void totalPriceSetWhenRoomDataIsNotEmpty()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{
		final int cases = 5;
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getPackages().get(0).getTotalPriceData().getTotalPrice(), is(new BigDecimal(2066)));
	}

	@Test
	public void totalPriceWhenRoomListIsPassdEmpty()
			throws DatatypeConfigurationException, ParseException, SearchAPIUnmarshallingException
	{
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getPackages().get(0).getTotalPriceData().getTotalPrice(), is(new BigDecimal(2300)));
	}

	@Test
	public void facetDataShouldNotBeSetToFacetCategoryWhenFacetIsPassedEmpty()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		offers.setFacets(null);
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getFacetCategoryDatas().size(), is(5));
		assertThat(packageDatas.getFacetCategoryDatas().get(0).getFacets().get(0).getName(), is("ARN"));
		assertThat(packageDatas.getFacetCategoryDatas().get(1).getFacets().get(0).getName(), is("DFR"));
		assertThat(packageDatas.getFacetCategoryDatas().get(2).getFacets().get(0).getName(), is("AWE"));
		assertThat(packageDatas.getFacetCategoryDatas().get(3).getFacets().get(0).getName(), is("PRICETOTAL"));
		assertThat(packageDatas.getFacetCategoryDatas().get(4).getFacets().get(0).getName(), is("PRICEPERPERSON"));


	}

	@Test
	public void facetDataShouldBeSetToFacetCategoryWhenFacetIsPassedNotEmpty()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getFacetCategoryDatas().get(0).getFacets().get(0).getMax(),
				is((new BigDecimal(3456)).floatValue()));
		assertThat(packageDatas.getFacetCategoryDatas().get(0).getFacets().get(0).getMin(),
				is((new BigDecimal(5674)).floatValue()));
		assertThat(packageDatas.getFacetCategoryDatas().get(0).getFacets().get(0).getCount(), is(3));
		assertThat(packageDatas.getFacetCategoryDatas().get(0).getFacets().get(0).getName(), is(""));
	}

	@Test
	public void facetDataShouldNotBeSetToFacetCategoryWhenListIsPassedEmpty()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		offers.setLists(null);
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getFacetCategoryDatas().size(), is(1));
		assertThat(packageDatas.getFacetCategoryDatas().get(0).getFacets().get(0).getCount(), is(3));
	}

	@Test
	public void facetDataShouldBeSetToFacetCategoryWhenListIsNotEmpty()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getFacetCategoryDatas().get(1).getFacets().get(0).getCode(), is("ARN"));
		assertThat(packageDatas.getFacetCategoryDatas().get(1).getFacets().get(0).getName(), is("ARN"));
	}

	@Test
	public void flightDataShouldBeAddedToOutBoundFlightAndAdeddToPackageDataListWhenDirOfRouteIsOUTBOUND()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{
		final int cases = 1;
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		final Date expectedDate = dateformat.parse("2015-09-09 11:12:12");
		final SimpleDateFormat simpleDateformat = new SimpleDateFormat("hh:mm");
		final Date expectedtime = simpleDateformat.parse("23:24");
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getPackages().get(0).getOutboundFlight().get(0).getAvailability(), is(78));
		assertThat(packageDatas.getPackages().get(0).getOutboundFlight().get(0).getArrivalTime(), is(expectedtime));
		assertThat(packageDatas.getPackages().get(0).getOutboundFlight().get(0).getArrivalDate(), is(expectedDate));
		assertThat(packageDatas.getPackages().get(0).getOutboundFlight().get(0).getArrivalPort(), is("ARN"));
		assertThat(packageDatas.getPackages().get(0).getOutboundFlight().get(0).getDepartureDate(), is(expectedDate));
		assertThat(packageDatas.getPackages().get(0).getOutboundFlight().get(0).getDeparturePort(), is("CPM"));
		assertThat(packageDatas.getPackages().get(0).getOutboundFlight().get(0).getDepartureTime(), is(expectedtime));
		assertThat(packageDatas.getPackages().get(0).getOutboundFlight().get(0).getFlightNo(), is("FLIGHT-009"));

	}

	@Test
	public void flightDataShouldBeAddedToInBoundFlightAndThenAddedToPackageDataListWhenDirOfRouteIsINBOUND()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{
		final int cases = 2;
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);

		final Date expectedDate = dateformat.parse("2015-09-09 11:12:12");
		final SimpleDateFormat simpleDateformat = new SimpleDateFormat("hh:mm");
		final Date expectedtime = simpleDateformat.parse("23:24");
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getPackages().get(0).getInboundFlight().get(0).getAvailability(), is(78));
		assertThat(packageDatas.getPackages().get(0).getInboundFlight().get(0).getArrivalTime(), is(expectedtime));
		assertThat(packageDatas.getPackages().get(0).getInboundFlight().get(0).getArrivalDate(), is(expectedDate));
		assertThat(packageDatas.getPackages().get(0).getInboundFlight().get(0).getArrivalPort(), is("ARN"));
		assertThat(packageDatas.getPackages().get(0).getInboundFlight().get(0).getDepartureDate(), is(expectedDate));
		assertThat(packageDatas.getPackages().get(0).getInboundFlight().get(0).getDeparturePort(), is("CPM"));
		assertThat(packageDatas.getPackages().get(0).getInboundFlight().get(0).getDepartureTime(), is(expectedtime));
		assertThat(packageDatas.getPackages().get(0).getInboundFlight().get(0).getFlightNo(), is("FLIGHT-009"));

	}


	@Test
	public void sysInfoShouldBeSetToHotelDataWhenSysInfoIsSetNotNull()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{
		final int cases = 1;
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getPackages().get(0).getHotelData().getApcId(), is("23"));
		assertThat(packageDatas.getPackages().get(0).getHotelData().getCode(), is("A1"));
		assertThat(packageDatas.getPackages().get(0).getHotelData().getCommercialPriority(), is(3));
		assertThat(packageDatas.getPackages().get(0).getHotelData().getBrand(), is("testBrand"));
		assertThat(packageDatas.getPackages().get(0).getHotelData().getId(), is("A1"));
	}

	@Test
	public void sysInfoShouldNotBeSetToHotelDataWhenSysInfoIsSetNull()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertTrue(packageDatas.getPackages().get(0).getHotelData().getApcId().isEmpty());
		assertTrue(packageDatas.getPackages().get(0).getHotelData().getSysInfo().isEmpty());
		assertThat(packageDatas.getPackages().get(0).getHotelData().getCode(), is("A1"));
		assertThat(packageDatas.getPackages().get(0).getHotelData().getCommercialPriority(), is(3));
		assertThat(packageDatas.getPackages().get(0).getHotelData().getBrand(), is("testBrand"));
		assertThat(packageDatas.getPackages().get(0).getHotelData().getId(), is("A1"));
	}

	@Test
	public void sysInfoShouldNotBeSetToHotelDataWhenInvalidSysInfoIsSet()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{
		cases = 3;
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertTrue(packageDatas.getPackages().get(0).getHotelData().getApcId().isEmpty());
		assertTrue(packageDatas.getPackages().get(0).getHotelData().getSysInfo().isEmpty());
		assertThat(packageDatas.getPackages().get(0).getHotelData().getCode(), is("A1"));
		assertThat(packageDatas.getPackages().get(0).getHotelData().getCommercialPriority(), is(3));
		assertThat(packageDatas.getPackages().get(0).getHotelData().getBrand(), is("testBrand"));
		assertThat(packageDatas.getPackages().get(0).getHotelData().getId(), is("A1"));
	}

	@Test
	public void packageIDShouldBeSetToOffersDataWhenSingleOfferAvailalbe()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);

		offer.setPkg("ARNLPA3..151125 08 STOLPAS");

		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getPackages().get(0).getOfferData().getPackageID(), is(IsNull.notNullValue()));
		assertThat(packageDatas.getPackages().get(0).getOfferData().getPackageID(), is("ARNLPA3..151125 08 STOLPAS"));
	}

	@Test
	public void packageIDShouldNotBeSetToOffersDataWhenMultipleOfferAvailalbe()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);

		offer.getAccom().setId("1234");

		final Offer offer2 = offer;
		offer2.getAccom().setId("12345");

		offers.getOffer().add(offer2);

		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getPackages().size(), is(OrderingComparison.greaterThan(1)));
		assertThat(packageDatas.getPackages().get(0).getOfferData().getPackageID(), is(IsNull.nullValue()));
		assertThat(packageDatas.getPackages().get(1).getOfferData().getPackageID(), is(IsNull.nullValue()));
	}


	@Test
	public void sysInfoWithoutSpaceAndHyphen()
			throws DatatypeConfigurationException, ParseException, SearchAPIUnmarshallingException
	{
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		offer.getTransport().get(0).setSysInfo("0008TIHKTCPHHKT2C.BLX847 151104CPH F  J                              1 B ");
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getPackages().get(0).getInboundFlight().get(0).getFlightInfo(),
				is("0008TIHKTCPHHKT2C.BLX847 151104CPH F  J                              1 B "));
	}

	@Test
	public void sysInfoWithoutSpaceAndWithHyphen()
			throws SearchAPIUnmarshallingException, DatatypeConfigurationException, ParseException
	{
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		offer.getTransport().get(0).setSysInfo(" -0008TIHKTCPHHKT2C.BLX847 151104CPH F  J                              1 B ");
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getPackages().get(0).getInboundFlight().get(0).getFlightInfo(),
				is("0008TIHKTCPHHKT2C.BLX847 151104CPH F  J                              1 B "));
	}

	@Test
	public void sysInfoWithSpaceAndHyphen() throws SearchAPIUnmarshallingException, DatatypeConfigurationException, ParseException
	{
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		offer.getTransport().get(0).setSysInfo(" -0008TIHKTCPHHKT2C.BLX847 151104CPH F  J                              1 B ");
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getPackages().get(0).getInboundFlight().get(0).getFlightInfo(),
				is("0008TIHKTCPHHKT2C.BLX847 151104CPH F  J                              1 B "));
	}

	@Test
	public void sysInfoWithFlightClassAndHyphen()
			throws DatatypeConfigurationException, ParseException, SearchAPIUnmarshallingException
	{
		offers.getOffer().add(offer);
		setOffersData(offer);
		setHotelsData(accom, cases);
		setRoomData(accom, cases);
		setTransportsData(offer, packageData, cases);
		setBoardsData(offer, packageData);
		setTotalPrices(offer, packageData);
		setFacetdata(offers, packagesData);
		offer.getTransport().get(0).setSysInfo("G-0008TIHKTCPHHKT2C.BLX847 151104CPH F  J                              1 B ");
		final PackagesData packageDatas = anitePackagesResponseConvert.convertJaxbResponse(avCache);
		assertThat(packageDatas.getPackages().get(0).getInboundFlight().get(0).getFlightInfo(),
				is("0008TIHKTCPHHKT2C.BLX847 151104CPH F  J                              1 B "));
	}



	private void setTotalPrices(final Offer offer, final PackageData packageData)
	{
		final OfferData offerData = new OfferData();
		packageData.setOfferData(offerData);
		offerData.setDiscountedPrice(new BigDecimal(4523));
		offerData.setDiscountedPricePerson(new BigDecimal(334));
		final List<RoomData> roomsData = new ArrayList<RoomData>();
		final RoomData roomData = new RoomData();
		roomsData.add(roomData);
		roomData.setUnitDiscount(new BigDecimal(67));
		roomData.setUnitDisPricePerPerson(new BigDecimal(67));
		roomsData.add(roomData);
		packageData.setRoomsData(roomsData);
	}

	private void setOffersData(final Offer offer) throws DatatypeConfigurationException, ParseException
	{
		final short i = 2;
		offer.setWorldCare(YesNo.Y);
		final GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(dateformat.parse("2015-09-09 11:12:12"));
		offer.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
		offer.setStay(i);
		offer.setPrice(new BigDecimal(2300));
		offer.setPricePP(new BigDecimal(348));
		offer.setDeposit(new BigDecimal(898));
	}

	private void setHotelsData(final Accom accom, final int cases)
	{
		accom.setBrand("testBrand");
		accom.setCommPri("3");
		accom.setId("A1");
		accom.setCode("A1");
		if (cases == 1)
		{
			accom.setSysInfo("23-34-56");
		}
		if (cases == 3)
		{
			accom.setSysInfo("234");
		}
	}

	private void setRoomData(final Accom accom, final int cases)
	{
		final long avalaibility = 2;
		final short infant = 2;
		final short children = 2;
		final short youth = 3;
		final Unit unit = new Unit();
		final Priced priced = new Priced();
		if (cases == 5)
		{
			accom.getUnit().add(unit);
		}
		unit.setAvail(avalaibility);
		unit.setBoard("Testboard");
		unit.setDiscPP(new BigDecimal(234));
		unit.setPrice(new BigDecimal(234));
		unit.setPricePP(new BigDecimal(234));
		unit.setCode("A1");
		unit.setDisc(new BigDecimal(234));
		unit.setPriced(priced);
		priced.setCh(children);
		priced.setIn(infant);
		priced.setYth(youth);
	}


	private void setTransportsData(final Offer offer, final PackageData packageData, final int cases)
			throws DatatypeConfigurationException, ParseException
	{
		final long availability = 78;
		final Transport transport = new Transport();
		final Route route = new Route();
		offer.getTransport().add(transport);
		transport.getRoute().add(route);
		final GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(dateformat.parse("2015-09-09 11:12:12"));
		route.setArrDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
		route.setArrPt("ARN");
		transport.setSysInfo("12-23");
		route.setArrTime("2324");
		route.setDepTime("2324");
		route.setDepDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
		route.setDepPt("CPM");
		route.setAvail(availability);
		route.setMeal(YesNo.Y);
		route.setFltNo("FLIGHT-009");
		if (cases == 1)
		{
			route.setDir("O");
		}
		else
		{
			route.setDir("I");
		}
	}

	private void setBoardsData(final Offer offer, final PackageData packageData)
	{
		final AltBoard altBoard = new AltBoard();
		final Board board = new Board();
		offer.setAltBoard(altBoard);
		altBoard.getBoard().add(board);
		board.setCode("A111");
		board.setPrice(BigDecimal.ONE);
	}


	private void setFacetdata(final Offers offers, final PackagesData packagesData)
	{
		final long count = 3;
		final Facets facets = new Facets();
		offers.setFacets(facets);
		final Cat cat = new Cat();
		facets.getCat().add(cat);
		cat.setCode("A123");
		final Facet facet = new Facet();
		;
		cat.getFacet().add(facet);
		facet.setCount(count);
		facet.setMax(new BigDecimal(3456));
		facet.setMin(new BigDecimal(5674));
		final Lists lists = new Lists();
		offers.setLists(lists);
		lists.setDepPt("ARN,CPM,MPO");
		lists.setOutSlots("DFR");
		lists.setInSlots("AWE");

		lists.setPrice("343");
		lists.setPricePP("34");
	}
}
