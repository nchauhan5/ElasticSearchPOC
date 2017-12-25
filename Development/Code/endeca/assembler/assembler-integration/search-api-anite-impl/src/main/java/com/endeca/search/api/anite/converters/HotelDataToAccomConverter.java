package com.endeca.search.api.anite.converters;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.search.api.response.dtos.HotelData;
import com.endeca.search.api.response.dtos.OfferData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.RoomData;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom;


/**
 * The Class HotelDataToAccomConverter.
 */
public class HotelDataToAccomConverter
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(HotelDataToAccomConverter.class);

	private HotelDataToAccomConverter()
	{
		// Added to avoid instantiation.
	}

	/**
	 * Convert.
	 * 
	 * @param packageData
	 *           the package data
	 * @return the accom
	 */
	public static Accom convert(final PackageData packageData)
	{
		Accom accom = null;

		try
		{
			final HotelData hotelData = packageData.getHotelData();
			final OfferData offerData = packageData.getOfferData();

			accom = new Accom();

			for (final RoomData roomData : packageData.getRoomsData())
			{
				accom.getUnit().add(RoomDataToUnitDataConverter.convert(roomData));
			}
			accom.setBrand(hotelData.getBrand());
			accom.setCode(hotelData.getId());
			accom.setCommPri(Integer.toString(hotelData.getCommercialPriority()));

			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(offerData.getDate());
			XMLGregorianCalendar date;
			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

			accom.setDate(date);
			accom.setId(hotelData.getId());
			accom.setCode(hotelData.getCode());
			accom.setRating(null);
			accom.setStay((short) offerData.getStay());
			accom.setTracs(null);
		}
		catch (final DatatypeConfigurationException e)
		{
			LOGGER.error("Error Occured in Populating \"Accomm\" from HotelData", e);
		}
		return accom;


	}
}
