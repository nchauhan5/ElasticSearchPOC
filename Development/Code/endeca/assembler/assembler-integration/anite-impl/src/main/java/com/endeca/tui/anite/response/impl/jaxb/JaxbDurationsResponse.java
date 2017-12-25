package com.endeca.tui.anite.response.impl.jaxb;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.tui.anite.response.durations.AvCache;
import com.endeca.tui.anite.response.durations.AvCache.Result.Offers.Offer;
import com.endeca.tui.anite.responses.AniteDurationsResponse;


// TODO: Auto-generated Javadoc
/**
 * The Class JaxbDurationsResponse.
 */
public class JaxbDurationsResponse implements AniteDurationsResponse
{

	/** The response. */
	protected final AvCache response;

	/** The Constant OFFER2STAY. */
	protected static final Transformer OFFER2STAY = new Transformer()
	{
		@Override
		public Short transform(final Object input)
		{
			return ((Offer) input).getStay();
		}
	};

	/** The stays. */
	protected List<Short> stays;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(JaxbDurationsResponse.class);

	/**
	 * Instantiates a new jaxb durations response.
	 * 
	 * @param response
	 *           the response
	 */
	public JaxbDurationsResponse(final AvCache response)
	{
		this.response = response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteDurationsResponse#getStays()
	 */
	@Override
	public synchronized List<Short> getStays()
	{
		if (null == stays)
		{
			stays = buildStays();
		}
		return stays;
	}

	/**
	 * Builds the stays.
	 * 
	 * @return the list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected synchronized List<Short> buildStays()
	{

		try
		{
			stays = UnmodifiableList.decorate((List) CollectionUtils
					.collect(response.getResult().getOffers().getOffer(), OFFER2STAY));
		}
		catch (final Exception e)
		{
			stays = ListUtils.EMPTY_LIST;
			LOGGER.error("Error getting build Stays", e);
		}

		return stays;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteDurationsResponse#getResponseVersion()
	 */
	@Override
	public String getResponseVersion()
	{
		return response.getVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteDurationsResponse#getCount()
	 */
	@Override
	public long getCount()
	{
		try
		{
			// TODO how is this different from
			// response.getStatus().getTotal().getCount()?
			return response.getResult().getOffers().getCount();
		}
		catch (final Exception e)
		{
			LOGGER.error("Error getting offer count", e);
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return StringUtils.join(getStays(), ',');
	}
}
