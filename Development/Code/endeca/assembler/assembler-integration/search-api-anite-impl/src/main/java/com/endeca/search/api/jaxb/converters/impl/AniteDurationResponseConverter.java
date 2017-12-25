package com.endeca.search.api.jaxb.converters.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;
import com.endeca.search.api.jaxb.converters.DurationResponseConverter;
import com.endeca.tui.anite.response.durations.AvCache;
import com.endeca.tui.anite.response.durations.AvCache.Result;
import com.endeca.tui.anite.response.durations.AvCache.Result.Offers;
import com.endeca.tui.anite.response.durations.AvCache.Result.Offers.Offer;


/**
 * The Class AniteDurationResponseConverter this is the anite specific implementation of
 * {@link DurationResponseConverter} used to convert anite specific jaxb response to duration data.
 */
public class AniteDurationResponseConverter implements DurationResponseConverter
{

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.search.api.jaxb.converters.DurationResponseConverter#convertJaxbResponse(java.lang.Object)
	 */
	@Override
	public List<Integer> convertJaxbResponse(final Object jaxbElement) throws SearchAPIUnmarshallingException
	{
		final List<Integer> availableDurations = new ArrayList<Integer>();
		final AvCache avCache = (AvCache) jaxbElement;

		if (null != avCache.getError())
		{
			final AvCache.Error aniteError = avCache.getError();
			throw new SearchAPIUnmarshallingException(
					" Received error code : " + aniteError.getCode() + " with text : " + aniteError.getText(), new Throwable());
		}

		else
		{
			final Result result = avCache.getResult();
			final Offers offers = result.getOffers();
			for (final Offer offer : offers.getOffer())
			{
				availableDurations.add((int) offer.getStay());
			}
			Collections.sort(availableDurations);
			return availableDurations;
		}
	}
}
