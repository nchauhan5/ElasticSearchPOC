package com.endeca.search.api.populators;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.endeca.search.api.request.dtos.DepartureData;


/**
 * The Class DepartureDataPopulator. Used to populate {@code DepartureData}.
 */
public class DepartureDataPopulator
{


	/**
	 * Populate.
	 * 
	 * @param airportFrom
	 *           the parameter map
	 * @param airportTo
	 * @return the departure data
	 */
	public DepartureData populate(final String airportFrom, String airportTo)
	{
		final DepartureData departureData = new DepartureData();
		if (null != airportFrom)
		{
			departureData.setDepartureAirports(Arrays.asList(StringUtils.split(airportFrom, ",")));
		}
		if (null != airportTo)
		{
			departureData.setArrivalAirports(Arrays.asList(StringUtils.split(airportTo, ",")));
		}
		return departureData;
	}
}
