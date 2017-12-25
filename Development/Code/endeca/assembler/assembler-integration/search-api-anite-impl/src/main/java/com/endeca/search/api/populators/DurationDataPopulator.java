package com.endeca.search.api.populators;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.endeca.search.api.exceptions.SearchAPIException;
import com.endeca.search.api.request.dtos.DurationData;


/**
 * The Class DurationDataPopulator. Used to populate {@code DurationData} such as start date, end date, stay, durations
 * etc.
 */
public class DurationDataPopulator
{

	/** The Constant ERROR_OCCURED_IN_PARSING_START_OR_END_DATE. */
	private static final String ERROR_OCCURED_IN_PARSING_START_OR_END_DATE = "Error Occured in Parsing Start or End Date";

	/**
	 * Populate.
	 * 
	 * @param sDate
	 * @param eDate
	 * @param durations
	 * @param stay
	 * 
	 * @return the duration data
	 * @throws SearchAPIException
	 */
	public DurationData populate(String sDate, String eDate, String durations, String stay) throws SearchAPIException
	{
		final DurationData durationData = new DurationData();
		if (null != sDate && null != eDate)
		{

			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			try
			{
				durationData.setStartDate(dateFormat.parse(sDate));
				durationData.setEndDate(dateFormat.parse(eDate));
			}
			catch (final ParseException e)
			{
				throw new SearchAPIException(ERROR_OCCURED_IN_PARSING_START_OR_END_DATE + e.getMessage(), e);
			}
		}
		final List<Integer> durationsList = new ArrayList<Integer>();
		if (null != durations)
		{
			final String[] durationList = StringUtils.split(durations, ",");
			for (final String duration : durationList)
			{
				durationsList.add(Integer.valueOf(duration));
			}
		}
		durationData.setDurationsPriorityList(durationsList);
		if (null != stay)
		{
			durationData.setDuration(stay);
		}

		return durationData;
	}
}
