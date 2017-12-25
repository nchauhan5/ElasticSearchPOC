package com.endeca.search.api.request.dtos;

import java.util.Date;
import java.util.List;


/**
 * The Class DurationInformation. Request data class contains duration information to be passed to third pary provider
 * to fetch results such as start data, end date, duration etc.
 */
public class DurationData
{

	/** The start date. */
	private Date startDate;

	/** The end date. */
	private Date endDate;

	/** The durations priority list. */
	private List<Integer> durationsPriorityList;

	/** The durations priority list. */
	private String duration;

	/**
	 * Gets the start date.
	 * 
	 * @return the start date
	 */
	public Date getStartDate()
	{
		return (Date) startDate.clone();
	}

	/**
	 * Sets the start date.
	 * 
	 * @param startDate
	 *           the new start date
	 */
	public void setStartDate(final Date startDate)
	{
		this.startDate = new Date(startDate.getTime());
	}

	/**
	 * Gets the end date.
	 * 
	 * @return the end date
	 */
	public Date getEndDate()
	{
		return (Date) endDate.clone();
	}

	/**
	 * Sets the end date.
	 * 
	 * @param endDate
	 *           the new end date
	 */
	public void setEndDate(final Date endDate)
	{
		this.endDate = new Date(endDate.getTime());
	}

	/**
	 * Gets the durations priority list.
	 * 
	 * @return the durations priority list
	 */
	public List<Integer> getDurationsPriorityList()
	{
		return durationsPriorityList;
	}

	/**
	 * Sets the durations priority list.
	 * 
	 * @param durationsPriorityList
	 *           the new durations priority list
	 */
	public void setDurationsPriorityList(final List<Integer> durationsPriorityList)
	{
		this.durationsPriorityList = durationsPriorityList;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
