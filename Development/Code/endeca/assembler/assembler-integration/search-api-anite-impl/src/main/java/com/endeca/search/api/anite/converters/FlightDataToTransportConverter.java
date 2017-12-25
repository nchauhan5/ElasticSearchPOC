package com.endeca.search.api.anite.converters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.search.api.response.dtos.FlightData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Transport;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Transport.Route;
import com.endeca.tui.anite.response.YesNo;


/**
 * The Class FlightDataToTransportConverter.
 */
public class FlightDataToTransportConverter
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightDataToTransportConverter.class);

	/**
	 * Instantiates a new flight data to transport converter.
	 */
	private FlightDataToTransportConverter()
	{
		// Added to avoid instantiation since this is a static utility class.
	}

	/**
	 * Convert.
	 * 
	 * @param source
	 *           the source
	 * @return the transport
	 */
	public static Transport convert(final PackageData source)
	{

		final Transport transport = new Transport();

		final List<FlightData> outboundFlightData = source.getOutboundFlight();
		final List<FlightData> inboundFlightData = source.getInboundFlight();
		try
		{
			for (final FlightData flight : outboundFlightData)
			{

				final Route route = new Route();

				final GregorianCalendar depDate = new GregorianCalendar();
				depDate.setTime(flight.getDepartureDate());

				final GregorianCalendar arrDate = new GregorianCalendar();
				arrDate.setTime(flight.getArrivalDate());

				final DateFormat dateFormat = new SimpleDateFormat("hh:mm");

				route.setDepDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(depDate));
				route.setDepTime(dateFormat.format(flight.getDepartureTime().getTime()));
				route.setDepPt(flight.getDeparturePort());

				route.setArrDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(arrDate));
				route.setArrTime(dateFormat.format(flight.getArrivalTime().getTime()));
				route.setArrPt(flight.getArrivalPort());

				route.setAvail(flight.getAvailability());
				route.setDreamliner(getYesNoValue(flight.isDreamliner()));
				route.setEquip(flight.getAirplaneCode());
				//		route.setExt("");
				route.setFltNo(flight.getFlightNo());
				route.setId(flight.getFlightType());
				route.setMeal(getYesNoValue(flight.isMeals()));
				transport.getRoute().add(route);
			}
			for (final FlightData flight : inboundFlightData)
			{

				final Route route = new Route();

				final GregorianCalendar depDate = new GregorianCalendar();
				depDate.setTime(flight.getDepartureDate());

				final GregorianCalendar arrDate = new GregorianCalendar();
				arrDate.setTime(flight.getArrivalDate());

				final DateFormat dateFormat = new SimpleDateFormat("hh:mm");

				route.setDepDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(depDate));
				route.setDepTime(dateFormat.format(flight.getDepartureTime().getTime()));
				route.setDepPt(flight.getDeparturePort());

				route.setArrDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(arrDate));
				route.setArrTime(dateFormat.format(flight.getArrivalTime().getTime()));
				route.setArrPt(flight.getArrivalPort());

				route.setAvail(flight.getAvailability());
				route.setDreamliner(getYesNoValue(flight.isDreamliner()));
				route.setEquip(flight.getAirplaneCode());
				//		route.setExt("");
				route.setFltNo(flight.getFlightNo());
				route.setId(flight.getFlightType());
				route.setMeal(getYesNoValue(flight.isMeals()));
				transport.getRoute().add(route);
			}

		}
		catch (final DatatypeConfigurationException e)
		{
			LOGGER.error("Error Occured In Converting Flight Data To Transport Data", e);
		}
		return transport;
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
