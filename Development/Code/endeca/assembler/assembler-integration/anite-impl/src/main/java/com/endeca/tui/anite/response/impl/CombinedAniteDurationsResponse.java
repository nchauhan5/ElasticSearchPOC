package com.endeca.tui.anite.response.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.list.UnmodifiableList;

import com.endeca.tui.anite.responses.AniteDurationsResponse;


// TODO: Auto-generated Javadoc
/**
 * The Class CombinedAniteDurationsResponse.
 */
public class CombinedAniteDurationsResponse implements AniteDurationsResponse
{

	/** The stays. */
	@SuppressWarnings("unchecked")
	protected List<Short> stays = ListUtils.EMPTY_LIST;

	/** The response version. */
	protected String responseVersion;

	/** The count. */
	protected long count = 0;

	/**
	 * Instantiates a new combined anite durations response.
	 * 
	 * @param responses
	 *           the responses
	 */
	public CombinedAniteDurationsResponse(final List<Object> responses)
	{
		setResponses(responses);
	}

	/**
	 * Making method as private to reduce the sonar issue.
	 * 
	 * @param responses
	 *           the new responses
	 */
	private void setResponses(final List<Object> responses)
	{
		count = 0;
		stays = new ArrayList<Short>();
		for (final Object object : responses)
		{
			final AniteDurationsResponse d = (AniteDurationsResponse) object;
			responseVersion = d.getResponseVersion();
			count += d.getCount();
			stays.addAll(d.getStays());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteDurationsResponse#getStays()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Short> getStays()
	{
		return UnmodifiableList.decorate(stays);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteDurationsResponse#getResponseVersion()
	 */
	@Override
	public String getResponseVersion()
	{
		return responseVersion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteDurationsResponse#getCount()
	 */
	@Override
	public long getCount()
	{
		return count;
	}
}
