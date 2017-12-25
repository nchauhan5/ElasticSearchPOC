package com.endeca.search.api.anite.converters;

import java.util.List;

import com.endeca.tui.anite.response.durations.AvCache;
import com.endeca.tui.anite.response.durations.AvCache.Result;
import com.endeca.tui.anite.response.durations.AvCache.Result.Offers;
import com.endeca.tui.anite.response.durations.AvCache.Result.Offers.Offer;


/**
 * The Class CalendarDataToAvCacheConverter.
 */
public class DurationsDataToAvCacheConverter
{

	/**
	 * Instantiates a new durations data to av cache converter.
	 */
	private DurationsDataToAvCacheConverter()
	{
		// Added to avoid instantiation since this is a static utility class.
	}

	/**
	 * Convert.
	 * 
	 * @param durations
	 *           the durations
	 * @return the av cache
	 */
	public static AvCache convert(final List<Integer> durations)
	{
		final AvCache target = new AvCache();
		final Result result = new Result();
		final Offers offers = new Offers();

		target.setResult(result);
		result.setOffers(offers);
		int offerCount = 0;
		for (final Integer duration : durations)
		{
			final Offer offer = new Offer();
			offer.setStay(duration.shortValue());
			offers.getOffer().add(offer);
			offerCount++;
		}
		result.getOffers().setCount((long) offerCount);

		return target;
	}
}
