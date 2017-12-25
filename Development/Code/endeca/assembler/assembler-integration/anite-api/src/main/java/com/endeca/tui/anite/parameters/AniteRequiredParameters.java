package com.endeca.tui.anite.parameters;

import java.io.Serializable;

import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.enums.HolidayType;
import com.endeca.tui.anite.enums.PriceType;


// TODO: Auto-generated Javadoc
/**
 * The Interface AniteRequiredParameters.
 */
public interface AniteRequiredParameters extends Serializable
{

	/**
	 * Gets the accommodations string.
	 * 
	 * @return the accommodations string
	 */
	public String getAccommodationsString();

	/**
	 * Gets the accommodations count.
	 * 
	 * @return the accommodations count
	 */
	public int getAccommodationsCount();

	/**
	 * Gets the departure airports string.
	 * 
	 * @return the departure airports string
	 */
	public String getDepartureAirportsString();

	/**
	 * Gets the adult pax.
	 * 
	 * @return the adult pax
	 */
	public int getAdultPax();

	/**
	 * Gets the child ages.
	 * 
	 * @return the child ages
	 */
	public int[] getChildAges();

	/**
	 * Gets the room count.
	 * 
	 * @return the room count
	 */
	public int getRoomCount();

	/**
	 * Gets the earliest departure date string.
	 * 
	 * @return the earliest departure date string
	 */
	public String getEarliestDepartureDateString();

	/**
	 * Gets the latest departure date string.
	 * 
	 * @return the latest departure date string
	 */
	public String getLatestDepartureDateString();

	/**
	 * Gets the durations string.
	 * 
	 * @return the durations string
	 */
	public String getDurationsString();

	/**
	 * Gets the market.
	 * 
	 * @return the market
	 */
	public String getMarket();

	/**
	 * Gets the promotions string.
	 * 
	 * @return the promotions string
	 */
	public String getPromotionsString();

	/**
	 * Gets the promotions count.
	 * 
	 * @return the promotions count
	 */
	public int getPromotionsCount();

	/**
	 * Gets the holiday type.
	 * 
	 * @return the holiday type
	 */
	public HolidayType getHolidayType();

	/**
	 * Gets the price type.
	 * 
	 * @return the price type
	 */
	public PriceType getPriceType();

	/**
	 * Gets the anite query type.
	 * 
	 * @return the anite query type
	 */
	public AniteQueryType getAniteQueryType();
}
