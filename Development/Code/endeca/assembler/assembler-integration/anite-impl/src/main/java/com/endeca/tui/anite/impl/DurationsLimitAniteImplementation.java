package com.endeca.tui.anite.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.tui.anite.AniteInterface;
import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteException;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;
import com.endeca.tui.anite.parameters.impl.DefaultAniteParameters;
import com.endeca.tui.anite.response.impl.CombinedAniteCalendarResponse;
import com.endeca.tui.anite.response.impl.CombinedAniteDurationsResponse;


// TODO: Auto-generated Javadoc
/**
 * The Class DurationsLimitAniteImplementation.
 */
public class DurationsLimitAniteImplementation implements AniteInterface
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DurationsLimitAniteImplementation.class);

	/** The max durations. */
	private int maxDurations = 7;

	/** The backing anite interface. */
	private AniteInterface backingAniteInterface;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteInterface#query(com.endeca.tui.anite.AniteQueryType,
	 * com.endeca.tui.anite.AniteRequiredParameters)
	 */
	@Override
	public <T> T query(final AniteQueryType type, final AniteRequiredParameters params) throws AniteException
	{
		final String durationsString = params.getDurationsString();
		LOGGER.trace("Durations string: " + durationsString);
		if (StringUtils.isEmpty(durationsString))
		{
			LOGGER.trace("No durations specified, falling through to wrapped interface");
			return getBackingAniteInterface().query(type, params);
		}
		else
		{
			final String[] durations = durationsString.split(",");
			LOGGER.trace("Durations array: " + ArrayUtils.toString(durations));
			final int durationsCount = durations.length;
			if (durationsCount <= getMaxDurations())
			{
				if (LOGGER.isTraceEnabled())
				{
					LOGGER.trace("Durations (" + durationsCount + ") within maximum limit (" + getMaxDurations()
							+ "), falling through to wrapped interface");
				}
				return getBackingAniteInterface().query(type, params);
			}
			else
			{
				LOGGER.debug("Breaking request into sub requests");
				final List<Object> responses = new ArrayList<Object>();
				for (int i = 0; i < durations.length; i += getMaxDurations())
				{
					final int last = Math.min(i + getMaxDurations(), durationsCount);
					final DefaultAniteParameters subQueryParams = new DefaultAniteParameters(params);
					final String[] subQueryDurations = Arrays.copyOfRange(durations, i, last);
					subQueryParams.setDurations(subQueryDurations);
					LOGGER.debug("Querying with durations = " + ArrayUtils.toString(subQueryDurations));
					responses.add(getBackingAniteInterface().query(type, subQueryParams));
				}
				LOGGER.debug("Combining responses");
				return combine(type, responses);
			}
		}
	}

	/**
	 * Combine.
	 * 
	 * @param <T>
	 *           the generic type
	 * @param type
	 *           the type
	 * @param responses
	 *           the responses
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	protected <T> T combine(final AniteQueryType type, final List<Object> responses)
	{
		switch (type)
		{
			case CALENDAR:
				return (T) new CombinedAniteCalendarResponse(responses);
			case DURATIONS:
				return (T) new CombinedAniteDurationsResponse(responses);
			default:
				// just return the first one
				return (T) responses.get(0);
		}
	}

	/**
	 * Gets the backing anite interface.
	 * 
	 * @return the backing anite interface
	 */
	public AniteInterface getBackingAniteInterface()
	{
		return backingAniteInterface;
	}

	/**
	 * Sets the backing anite interface.
	 * 
	 * @param wrappedInterface
	 *           the new backing anite interface
	 */
	public void setBackingAniteInterface(final AniteInterface wrappedInterface)
	{
		this.backingAniteInterface = wrappedInterface;
	}

	/**
	 * Gets the max durations.
	 * 
	 * @return the max durations
	 */
	public int getMaxDurations()
	{
		return maxDurations;
	}

	/**
	 * Sets the max durations.
	 * 
	 * @param maxDurations
	 *           the new max durations
	 */
	public void setMaxDurations(final int maxDurations)
	{
		this.maxDurations = maxDurations;
	}
}
