package com.endeca.search.api.anite.converters;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import static org.hamcrest.Matchers.is;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.modules.junit4.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.endeca.search.api.response.dtos.HotelData;
import com.endeca.search.api.response.dtos.OfferData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.RoomData;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom.Unit;


@RunWith(PowerMockRunner.class)
@PrepareForTest(
{RoomDataToUnitDataConverter.class})
public class HotelDataToAccomConverterTest {

	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	
	/*This method is to test method,Whether hotel data,offer data and package data including brand,date,discounts,etc should be set to Accom*/
	@Test
	public void hotelDataShouldBeConvertedToAccomData() throws ParseException, DatatypeConfigurationException {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(dateFormat.parse("2015-02-02 12:34:00"));
		XMLGregorianCalendar date;
		date = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		PackageData packageData=new PackageData();
		HotelData hotelData=new HotelData();
		hotelData.setBrand("Suneo");
		hotelData.setId("A1");
		hotelData.setCommercialPriority(4);
		packageData.setHotelData(hotelData);
		OfferData offerData=new OfferData();
		
		offerData.setDate(dateFormat.parse("2015-02-02 12:34:00"));
		packageData.setOfferData(offerData);
		List<RoomData> roomsData=new ArrayList<RoomData>();
		RoomData roomData=new RoomData();
		roomsData.add(roomData);
		roomData.setDefaultBoard("Luxury");
		roomData.setRoomCode("A-001");
		roomData.setUnitDiscount(new BigDecimal(2300));
		roomData.setUnitDiscountPPP(new BigDecimal(12));
		roomData.setRoomTypeName("5star");
		packageData.setRoomsData(roomsData);
		Unit unit=new Unit();
		unit.setBoard("Luxury");
		unit.setCode("A-001");
		PowerMockito.mockStatic(RoomDataToUnitDataConverter.class);
		Mockito.when(RoomDataToUnitDataConverter.convert(roomData)).thenReturn(unit);
		Accom result=HotelDataToAccomConverter.convert(packageData);
		assertThat(result.getUnit().get(0).getBoard(),is("Luxury"));
	    assertThat(result.getBrand(),is("Suneo"));
		assertThat(result.getCommPri(),is("4"));
		assertThat(result.getDate(),is(date));
		assertThat(result.getId(),is("A1"));
		assertNull(result.getRating());	
	}
	
	/*This method is to test method,Whether offer data and package data including brand,date,discounts,etc should be set to Accom when roomdata is empty*/
	@Test
	public void unitDoNotSetInAccomWhenPackageDataHasRoomDataEmpty() throws ParseException, DatatypeConfigurationException {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(dateFormat.parse("2015-02-02 12:34:00"));
		XMLGregorianCalendar date;
		date = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		PackageData packageData=new PackageData();
		HotelData hotelData=new HotelData();
		hotelData.setBrand("Suneo");
		hotelData.setId("A1");
		hotelData.setCommercialPriority(4);
		packageData.setHotelData(hotelData);
		OfferData offerData=new OfferData();
		offerData.setDate(dateFormat.parse("2015-02-02 12:34:00"));
		packageData.setOfferData(offerData);
		List<RoomData> room=new ArrayList<RoomData>();
		packageData.setRoomsData(room);
		Unit unit=new Unit();
		unit.setBoard("Luxury");
		unit.setCode("A-001");
		Accom result=HotelDataToAccomConverter.convert(packageData);
		assertTrue(result.getUnit().isEmpty());
	    assertThat(result.getBrand(),is("Suneo"));
		assertThat(result.getCommPri(),is("4"));
		assertThat(result.getDate(),is(date));
		assertThat(result.getId(),is("A1"));
		assertNull(result.getRating());	
	}
}
