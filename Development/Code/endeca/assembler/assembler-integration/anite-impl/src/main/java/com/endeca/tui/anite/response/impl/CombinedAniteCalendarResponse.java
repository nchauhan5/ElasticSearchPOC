package com.endeca.tui.anite.response.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.list.UnmodifiableList;

import com.endeca.tui.anite.responses.AniteCalendarResponse;


// TODO: Auto-generated Javadoc
/**
 * The Class CombinedAniteCalendarResponse.
 */
public class CombinedAniteCalendarResponse implements AniteCalendarResponse
{

	/** The list. */
	private final List<AniteCalendarResponseDateAndAvailability> list;

	/** The response version. */
	protected String responseVersion;

	/**
	 * Instantiates a new combined anite calendar response.
	 * 
	 * @param responses
	 *           the responses
	 */
	public CombinedAniteCalendarResponse(final List<Object> responses)
	{
		list = new ArrayList<AniteCalendarResponse.AniteCalendarResponseDateAndAvailability>();
		for (final Object object : responses)
		{
			final AniteCalendarResponse c = (AniteCalendarResponse) object;
			setResponseVersion(c.getResponseVersion());
			list.addAll(c.getDateAndAvailabilityList());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteCalendarResponse#getDateAndAvailabilityList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AniteCalendarResponseDateAndAvailability> getDateAndAvailabilityList()
	{
		return UnmodifiableList.decorate(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteCalendarResponse#getResponseVersion()
	 */
	@Override
	public String getResponseVersion()
	{
		return responseVersion;
	}

	/**
	 * Sets the response version.
	 * 
	 * @param responseVersion
	 *           the new response version
	 */
	private void setResponseVersion(final String responseVersion)
	{
		this.responseVersion = responseVersion;
	}
}
