package com.endeca.search.api.response.dtos;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * The Class DurationCalendarInfo. Response dto to store available durations and calendar response.
 */
public class DurationCalendarData
{

	/** The duration info. */
	private List<Integer> availableDurations;

	/** The calendar availability info. */
	private Map<Date, Boolean> calendarAvailabilityInfo;

	/**
	 * Gets the duration info.
	 *
	 * @return the duration info
	 */
	public List<Integer> getDurationInfo()
	{
		return availableDurations;
	}

	/**
	 * Sets the duration info.
	 *
	 * @param durationInfo
	 *           the new duration info
	 */
	public void setDurationInfo(final List<Integer> durationInfo)
	{
		this.availableDurations = durationInfo;
	}

	/**
	 * Gets the calendar availability info.
	 *
	 * @return the calendar availability info
	 */
	public Map<Date, Boolean> getCalendarAvailabilityInfo()
	{
		return calendarAvailabilityInfo;
	}

	/**
	 * Sets the calendar availability info.
	 *
	 * @param calendarAvailabilityInfo
	 *           the calendar availability info
	 */
	public void setCalendarAvailabilityInfo(final Map<Date, Boolean> calendarAvailabilityInfo)
	{
		this.calendarAvailabilityInfo = calendarAvailabilityInfo;
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
