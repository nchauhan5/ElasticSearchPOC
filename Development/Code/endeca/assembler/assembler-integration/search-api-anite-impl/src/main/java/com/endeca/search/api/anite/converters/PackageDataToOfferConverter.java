package com.endeca.search.api.anite.converters;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.search.api.response.dtos.OfferData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer;
import com.endeca.tui.anite.response.YesNo;


/**
 * The Class PackageDataToOfferConverter.
 */
public class PackageDataToOfferConverter
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PackageDataToOfferConverter.class);

	/**
	 * Instantiates a new package data to offer converter.
	 */
	private PackageDataToOfferConverter()
	{
		// Added to avoid instantiation since this is a static utility class.
	}

	/**
	 * Convert.
	 * 
	 * @param source
	 *           the source
	 * @return the offer
	 */
	public static Offer convert(final PackageData source)
	{
		Offer offer = null;
		try
		{
			offer = new Offer();
			final OfferData offerData = source.getOfferData();

			offer.setAccom(HotelDataToAccomConverter.convert(source));
			//	offer.setAltBoard(BoardDataToAltBoardConverter.convert(source.getBoardData()));
			offer.getTransport().add(FlightDataToTransportConverter.convert(source));

			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(offerData.getDate());

			offer.setCarHire(getYesNoValue(offerData.isCarHire()));
			offer.setCoachTransfer(getYesNoValue(offerData.isCoachTransfer()));
			offer.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
			offer.setDeposit(offerData.getDeposit());
			offer.setPrice(source.getTotalPriceData().getTotalPrice());
			offer.setPricePP(source.getTotalPriceData().getTotalPrice());
			offer.setStay((short) offerData.getStay());
			offer.setWorldCare(getYesNoValue(offerData.isWorldCare()));

		}
		catch (final DatatypeConfigurationException e)
		{
			LOGGER.error("Error Occured while converting Offer Data to Offer", e);
		}

		return offer;

	}

	/**
	 * Gets the yes no value.
	 * 
	 * @param condition
	 *           the condition
	 * @return the yes no value
	 */
	private static YesNo getYesNoValue(final boolean condition)
	{
		final YesNo yesNo;
		if (condition)
		{
			yesNo = YesNo.Y;
		}
		else
		{
			yesNo = YesNo.N;
		}

		return yesNo;
	}
}
