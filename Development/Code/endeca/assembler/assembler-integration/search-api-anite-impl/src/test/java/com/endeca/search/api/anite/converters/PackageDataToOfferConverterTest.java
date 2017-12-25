package com.endeca.search.api.anite.converters;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.endeca.search.api.response.dtos.BoardData;
import com.endeca.search.api.response.dtos.OfferData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.TotalPriceData;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.AltBoard;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Transport;
import com.endeca.tui.anite.response.YesNo;


@RunWith(PowerMockRunner.class)
@PrepareForTest(
{ HotelDataToAccomConverter.class, FlightDataToTransportConverter.class, BoardDataToAltBoardConverter.class })
public class PackageDataToOfferConverterTest
{

	@Test
	public void packageDataShouldCovertedToOfferData() throws ParseException, DatatypeConfigurationException
	{
		short i = 4;
		PackageData packageData = new PackageData();
		List<BoardData> boardDataList = new ArrayList<BoardData>();
		BoardData boardData = new BoardData();
		OfferData offerData = new OfferData();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		AltBoard altBoard = new AltBoard();
		Accom accom = new Accom();
		Transport transport = new Transport();
		offerData.setDate(dateFormat.parse("2015-02-02"));
		offerData.setCarHire(true);
		offerData.setCoachTransfer(false);
		offerData.setStay(4);
		offerData.setDeposit(new BigDecimal(5));
		offerData.setWorldCare(true);
		TotalPriceData totalPriceData = new TotalPriceData();
		totalPriceData.setTotalPrice(new BigDecimal(34));
		packageData.setTotalPriceData(totalPriceData);
		packageData.setOfferData(offerData);
		//packageData.setBoardsData(boardDataList);
		PowerMockito.mockStatic(HotelDataToAccomConverter.class);
		PowerMockito.mockStatic(BoardDataToAltBoardConverter.class);
		PowerMockito.mockStatic(FlightDataToTransportConverter.class);
		PowerMockito.when(HotelDataToAccomConverter.convert(packageData)).thenReturn(accom);
		//PowerMockito.when(BoardDataToAltBoardConverter.convert(packageData.getBoardData())).thenReturn(altBoard);
		PowerMockito.when(FlightDataToTransportConverter.convert(packageData)).thenReturn(transport);
		final GregorianCalendar calendar = new GregorianCalendar();
		Date expectedDate = (dateFormat.parse("2015-02-02"));
		calendar.setTime(expectedDate);
		XMLGregorianCalendar expectedResult = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		Offer offer = PackageDataToOfferConverter.convert(packageData);
		assertThat((offer.getStay()), is(i));
		assertThat((offer.getCarHire()), is(YesNo.Y));
		assertThat((offer.getCoachTransfer()), is(YesNo.N));
		assertThat((offer.getDeposit()), is(new BigDecimal(5)));
		assertThat((offer.getWorldCare()), is(YesNo.Y));
		assertThat((offer.getDate()), is(expectedResult));
		assertThat((offer.getPricePP()), is(new BigDecimal(34)));
		assertThat((offer.getPrice()), is(new BigDecimal(34)));
		assertNotNull((offer.getAccom()));
		assertNotNull((offer.getAltBoard()));
		assertNotNull((offer.getTransport()));

	}
}
