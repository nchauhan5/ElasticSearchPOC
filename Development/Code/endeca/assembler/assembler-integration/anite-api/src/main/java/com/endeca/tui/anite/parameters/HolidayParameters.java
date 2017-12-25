//$Rev: 758 $
package com.endeca.tui.anite.parameters;

import java.util.Date;

import com.endeca.tui.anite.enums.AniteJoinPolicy;


// TODO: Auto-generated Javadoc
/**
 * The Interface HolidayParameters.
 */
public interface HolidayParameters extends MutableAniteParameters, Cloneable
{

	/**
	 * Ignore anite.
	 * 
	 * @return true, if successful
	 */
	boolean ignoreAnite();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteParameters#clone()
	 */
	@Override
	HolidayParameters clone();

	/**
	 * Sets the earliest departure date.
	 * 
	 * @param startDate
	 *           the new earliest departure date
	 */
	void setEarliestDepartureDate(Date startDate);

	/**
	 * Sets the latest departure date.
	 * 
	 * @param endDate
	 *           the new latest departure date
	 */
	void setLatestDepartureDate(Date endDate);

	/**
	 * Sets the dates by days from today.
	 * 
	 * @param dateRangeString
	 *           the new dates by days from today
	 */
	void setDatesByDaysFromToday(String dateRangeString);

	/**
	 * Gets the anite join policy.
	 * 
	 * @return the anite join policy
	 */
	AniteJoinPolicy getAniteJoinPolicy();

	/**
	 * Sets the anite join policy.
	 * 
	 * @param policy
	 *           the new anite join policy
	 */
	void setAniteJoinPolicy(AniteJoinPolicy policy);

	/**
	 * Sets the auto selected duration.
	 * 
	 * @param selectedAvailableDuration
	 *           the new auto selected duration
	 */
	void setAutoSelectedDuration(String selectedAvailableDuration);

	/**
	 * Gets the auto selected duration.
	 * 
	 * @return the auto selected duration
	 */
	String getAutoSelectedDuration();
}
