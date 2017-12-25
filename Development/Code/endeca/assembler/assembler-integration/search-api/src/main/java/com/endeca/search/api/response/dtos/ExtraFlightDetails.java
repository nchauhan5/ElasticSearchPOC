package com.endeca.search.api.response.dtos;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * The Class FlightDuration.
 */
public class ExtraFlightDetails
{

	/** The days. */
	private int days;

	/** The hours. */
	private int hours;

	/** The minutes. */
	private int minutes;

	/** The airport names. */
	private AirportNames airportNames;

	/**
	 * Gets the days.
	 *
	 * @return the days
	 */
	public int getDays()
	{
		return days;
	}

	/**
	 * Sets the days.
	 *
	 * @param days
	 *           the new days
	 */
	public void setDays(final int days)
	{
		this.days = days;
	}

	/**
	 * Gets the hours.
	 *
	 * @return the hours
	 */
	public int getHours()
	{
		return hours;
	}

	/**
	 * Sets the hours.
	 *
	 * @param hours
	 *           the new hours
	 */
	public void setHours(final int hours)
	{
		this.hours = hours;
	}

	/**
	 * Gets the minutes.
	 *
	 * @return the minutes
	 */
	public int getMinutes()
	{
		return minutes;
	}

	/**
	 * Sets the minutes.
	 *
	 * @param minutes
	 *           the new minutes
	 */
	public void setMinutes(final int minutes)
	{
		this.minutes = minutes;
	}

	/**
	 * Gets the airport names.
	 *
	 * @return the airport names
	 */
	public AirportNames getAirportNames()
	{
		return airportNames;
	}

	/**
	 * Sets the airport names.
	 *
	 * @param airportNames
	 *           the new airport names
	 */
	public void setAirportNames(final AirportNames airportNames)
	{
		this.airportNames = airportNames;
	}

	@Override
	public boolean equals(final Object o)
	{
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
