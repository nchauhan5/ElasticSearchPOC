package com.endeca.search.api.anite.paramteres.provider.impl;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.search.api.request.dtos.SearchAPIRequestData;
import com.endeca.tui.anite.AniteInterface;
import com.endeca.tui.anite.DurationsPreprocessor;
import com.endeca.tui.anite.exceptions.AniteException;
import com.endeca.tui.anite.parameters.HolidayParameters;


/**
 * The Class DurationAutoSelectProvider specific implementation written to query anite and fetch a selected duration
 * which will be used further as a anite parameter used to query anite for rest of the results.
 * <p>
 * This class fetches duration results select a specific duration which can be further used to query for other search
 * types such as facets and packages.
 */
public class DurationAutoSelectProvider extends DefaultAniteParametersProvider
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DurationAutoSelectProvider.class);

	/** The anite interface. */
	private AniteInterface aniteInterface;

	/**
	 * Gets the durations priority list.
	 * 
	 * @param durationsString
	 *           the durations string
	 * @return the durations priority list
	 */
	public short[] getDurationsPriorityList(final String durationsString)
	{
		if (StringUtils.isBlank(durationsString))
		{
			return ArrayUtils.EMPTY_SHORT_ARRAY;
		}
		final String[] durationStrings = durationsString.split("\\s*\\,\\s*");
		final short[] durations = new short[durationStrings.length];
		for (int i = 0; i < durationStrings.length; i++)
		{
			durations[i] = Short.parseShort(durationStrings[i]);
		}
		return durations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.request.populator.AniteParametersProvider#
	 * buildAniteParameters(com.endeca.search.api.request .dtos.SearchAPIRequestData)
	 */
	@Override
	public HolidayParameters buildAniteParameters(final SearchAPIRequestData request)
	{
		HolidayParameters holidayParameters = null;
/*		try
		{
		*/	holidayParameters = super.buildAniteParameters(request);
			final String candidateDurationsString = StringUtils.join(request.getDurationData().getDurationsPriorityList(), ",");
			holidayParameters.setCandidateDurationsString(candidateDurationsString);
			final short[] durationsPriorityList = getDurationsPriorityList(candidateDurationsString);
			if (null != durationsPriorityList && durationsPriorityList.length > 0)
			{
				final String singleAccommodationCode = request.getMiscellaneousAttributesMap().get("accommodationCode");
				if (StringUtils.isEmpty(singleAccommodationCode))
				{
					holidayParameters.setAccommodations(request.getDepartureData().getCandidateCodes());
				}
				else
				{
					holidayParameters.setAccommodationsString(singleAccommodationCode);
				}
				//holidayParameters.setDurationsString(StringUtils.join(request.getDurationData().getDurationsPriorityList(), ","));
				short selectedAvailableDuration;

			/*	selectedAvailableDuration = DurationsPreprocessor.selectAvailableDuration(aniteInterface, holidayParameters,
						durationsPriorityList);
				holidayParameters.setAutoSelectedDuration(selectedAvailableDuration);*/
				holidayParameters.setDurationsString(candidateDurationsString);
			}
			/*		}
		catch (final AniteException e)
		{
			LOGGER.error("Exception Occurred in fetching response from anite" + e.getMessage(), e);
		}*/
		return holidayParameters;
	}

	/**
	 * Gets the anite interface.
	 * 
	 * @return the anite interface
	 */
	public AniteInterface getAniteInterface()
	{
		return aniteInterface;
	}

	/**
	 * Sets the anite interface.
	 * 
	 * @param aniteInterface
	 *           the new anite interface
	 */
	public void setAniteInterface(final AniteInterface aniteInterface)
	{
		this.aniteInterface = aniteInterface;
	}

}
