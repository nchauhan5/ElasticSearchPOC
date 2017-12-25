package com.endeca.tui.anite.responses;

import java.util.GregorianCalendar;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Interface AniteCalendarResponse.
 */
public interface AniteCalendarResponse
{

	/**
	 * The Interface AniteCalendarResponseDateAndAvailability.
	 */
	public interface AniteCalendarResponseDateAndAvailability
	{

		/**
		 * Gets the date.
		 * 
		 * @return the date
		 */
		public GregorianCalendar getDate();

		/**
		 * Checks if is available.
		 * 
		 * @return true, if is available
		 */
		public boolean isAvailable();
	}

	/**
	 * Gets the date and availability list.
	 * 
	 * @return the date and availability list
	 */
	public List<AniteCalendarResponseDateAndAvailability> getDateAndAvailabilityList();

	/**
	 * Gets the response version.
	 * 
	 * @return the response version
	 */
	public String getResponseVersion();
}
