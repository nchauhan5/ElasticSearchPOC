package com.endeca.search.api.response.dtos;

/**
 * The Class AirportNames.
 */
public class AirportNames
{

	/** The departure airport name. */
	private String departureAirportName;

	/** The arrival airport name. */
	private String arrivalAirportName;

	/**
	 * Gets the departure airport name.
	 * 
	 * @return the departure airport name
	 */
	public String getDepartureAirportName()
	{
		return departureAirportName;
	}

	/**
	 * Sets the departure airport name.
	 * 
	 * @param departureAirportName
	 *           the new departure airport name
	 */
	public void setDepartureAirportName(final String departureAirportName)
	{
		this.departureAirportName = departureAirportName;
	}

	/**
	 * Gets the arrival airport name.
	 * 
	 * @return the arrival airport name
	 */
	public String getArrivalAirportName()
	{
		return arrivalAirportName;
	}

	/**
	 * Sets the arrival airport name.
	 * 
	 * @param arrivalAirportName
	 *           the new arrivfinal port name
	 */
	public void setArrivalAirportName(final String arrivalAirportName)
	{
		this.arrivalAirportName = arrivalAirportName;
	}


}
