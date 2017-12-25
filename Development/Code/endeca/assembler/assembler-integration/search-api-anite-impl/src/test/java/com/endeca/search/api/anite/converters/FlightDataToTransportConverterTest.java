package com.endeca.search.api.anite.converters;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

import com.endeca.search.api.response.dtos.FlightData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Transport;
import com.endeca.tui.anite.response.YesNo;

public class FlightDataToTransportConverterTest {
	
	DateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	PackageData source=new PackageData();

	
	/*This test method is to test,whether flight data including arrivaltime,departuretime,meals,ports,etc should be successfully converted to Transport data*/
	@Test
	public void flightDataShouldBeConvertedToTransportData() throws ParseException, DatatypeConfigurationException {
		
		long expectedAvalaibilityforOutbound=0;
		long expectedAvalaibilityforInbound=1;
		List<FlightData> outboundFlight=new ArrayList<FlightData>();
		List<FlightData> inboundFligh=new ArrayList<FlightData>();

		FlightData outboundFligh=new FlightData() ;
		FlightData inboundFlight=new FlightData();
		outboundFligh.setDepartureDate(dateformat.parse("2015-03-07 11:33:32"));
		inboundFlight.setDepartureDate(dateformat.parse("2015-03-07 11:33:32"));
		outboundFligh.setArrivalDate(dateformat.parse("2015-03-07 11:33:32"));
		inboundFlight.setArrivalDate(dateformat.parse("2015-03-07 11:33:32"));
		outboundFligh.setDepartureTime(dateformat.parse("2015-03-08 12:33:32"));
		inboundFlight.setDepartureTime(dateformat.parse("2015-03-08 14:39:32"));
		outboundFligh.setArrivalTime(dateformat.parse("2015-03-07 11:33:32"));
		inboundFlight.setArrivalTime(dateformat.parse("2015-03-08 21:39:32"));
		outboundFligh.setDeparturePort("ARN");
		inboundFlight.setDeparturePort("CPS");
		outboundFligh.setArrivalPort("SER");
		inboundFlight.setArrivalPort("KKL");
		outboundFligh.setAvailability(0);
		inboundFlight.setAvailability(1);
		outboundFligh.setDreamliner(true);
		inboundFlight.setDreamliner(false);
		outboundFligh.setMeals(false);
		inboundFlight.setMeals(true);
		outboundFligh.setAirplaneCode("A1");
		inboundFlight.setAirplaneCode("A2");
		outboundFligh.setFlightType("TYPE");
		inboundFlight.setFlightType("TYPE2");
		inboundFlight.setFlightNo("test-12");
		outboundFligh.setFlightNo("test-65");
		outboundFlight.add(outboundFligh);
		inboundFligh.add(inboundFlight);

		source.setInboundFlight(inboundFligh);
		source.setOutboundFlight(outboundFlight);
	     GregorianCalendar calendar = new GregorianCalendar();
		 Date depdate=(dateformat.parse("2015-03-07 11:33:32"));
		 calendar.setTime(depdate);
		 XMLGregorianCalendar expectedDate=DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		Transport result=FlightDataToTransportConverter.convert(source);
		assertThat(result.getRoute().get(0).getMeal(),is(YesNo.N));
		assertThat(result.getRoute().get(0).getDreamliner(),is(YesNo.Y));
		assertThat(result.getRoute().get(0).getAvail(),is(expectedAvalaibilityforOutbound));
		assertThat(result.getRoute().get(0).getId(),is("TYPE"));
		assertThat(result.getRoute().get(0).getFltNo(),is("test-65"));
		assertThat(result.getRoute().get(0).getEquip(),is("A1"));
		assertThat(result.getRoute().get(0).getArrTime(),is("11:33"));
		assertThat(result.getRoute().get(0).getDepTime(),is("12:33"));
		assertThat(result.getRoute().get(0).getArrPt(),is("SER"));
		assertThat(result.getRoute().get(0).getArrDate(),is(expectedDate));
		assertThat(result.getRoute().get(0).getDepPt(),is("ARN"));
		assertThat(result.getRoute().get(0).getDepDate(),is(expectedDate));
		assertThat(result.getRoute().get(1).getMeal(),is(YesNo.Y));
		assertThat(result.getRoute().get(1).getDreamliner(),is(YesNo.N));
		assertThat(result.getRoute().get(1).getAvail(),is(expectedAvalaibilityforInbound));
		assertThat(result.getRoute().get(1).getId(),is("TYPE2"));
		assertThat(result.getRoute().get(1).getFltNo(),is("test-12"));
		assertThat(result.getRoute().get(1).getEquip(),is("A2"));
		assertThat(result.getRoute().get(1).getArrTime(),is("09:39"));
		assertThat(result.getRoute().get(1).getDepTime(),is("02:39"));
		assertThat(result.getRoute().get(1).getArrPt(),is("KKL"));
		assertThat(result.getRoute().get(1).getArrDate(),is(expectedDate));
		assertThat(result.getRoute().get(1).getDepPt(),is("CPS"));
		assertThat(result.getRoute().get(1).getDepDate(),is(expectedDate));
	}
	

	/*This test method is to test,whether exception is thrown when date with wrong format is added*/
	@Test(expected=ParseException.class)
	public void exceptionShouldBethownWhenDatewithwrongFormatIsInserted() throws ParseException, DatatypeConfigurationException {
		List<FlightData> outBoundFlight=new ArrayList<FlightData>();
		List<FlightData> inBoundFlight=new ArrayList<FlightData>();

		FlightData outboundFligh=new FlightData() ;
		FlightData inboundFlight=new FlightData();
		outboundFligh.setDepartureDate(dateformat.parse("20150311 11:33:32"));
		inboundFlight.setDepartureDate(dateformat.parse("2015-03-07 11:33:32"));
		outboundFligh.setArrivalDate(dateformat.parse("2015-03-09 11:33:32"));
		source.setInboundFlight(inBoundFlight);
		source.setOutboundFlight(outBoundFlight);
	     GregorianCalendar calendar = new GregorianCalendar();
		 Date depdate=(dateformat.parse("20150307 11:33:32"));
		 calendar.setTime(depdate);
		 XMLGregorianCalendar expectedDate=DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		Transport result=FlightDataToTransportConverter.convert(source);
	}
}
