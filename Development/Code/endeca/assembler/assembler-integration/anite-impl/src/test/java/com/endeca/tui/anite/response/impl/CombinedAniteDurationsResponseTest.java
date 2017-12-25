package com.endeca.tui.anite.response.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.endeca.tui.anite.response.durations.AvCache;
import com.endeca.tui.anite.response.durations.AvCache.Result;
import com.endeca.tui.anite.response.durations.AvCache.Result.Offers;
import com.endeca.tui.anite.response.durations.AvCache.Result.Offers.Offer;
import com.endeca.tui.anite.response.impl.jaxb.JaxbDurationsResponse;
import com.endeca.tui.anite.responses.AniteDurationsResponse;




/**
 * Test class for {@link CombinedAniteDurationsResponse}.
 * 
 */
public class CombinedAniteDurationsResponseTest
{

	/**
	 * Test case Responses joined correctly.
	 */
	@Test
	public void responsesJoinedCorrectly()
	{
		final List<Object> responses = createTestData();
		final CombinedAniteDurationsResponse combineAniteDurationsResponse = new CombinedAniteDurationsResponse(responses);
		assertThat(combineAniteDurationsResponse.getStays().size(), is(10));

		final Short[] stays1 = ((AniteDurationsResponse) responses.get(0)).getStays().toArray(new Short[5]);
		final Short[] stays2 = ((AniteDurationsResponse) responses.get(1)).getStays().toArray(new Short[5]);

		assertThat(combineAniteDurationsResponse.getStays(), Matchers.hasItems(stays1));
		assertThat(combineAniteDurationsResponse.getStays(), Matchers.hasItems(stays2));

	}

	/**
	 * Creates the test data.
	 * 
	 * @return the list
	 */
	private List<Object> createTestData()
	{
		final List<Integer> stays = Arrays.asList(1, 5, 6, 3, 4, 2, 9, 7, 8, 10);
		final Offers offers = new Offers();
		int i = 0;
		while (i < 5)
		{
			final Offer offer = new Offer();
			offer.setStay(stays.get(i).shortValue());
			offers.getOffer().add(offer);
			i++;
		}
		offers.setCount((long) offers.getOffer().size());
		final Result result = new Result();
		result.setOffers(offers);
		final AvCache avCache = new AvCache();
		avCache.setResult(result);

		final Offers offers2 = new Offers();
		while (i < 10)
		{
			final Offer offer = new Offer();
			offer.setStay(stays.get(i).shortValue());
			offers2.getOffer().add(offer);
			i++;
		}
		offers2.setCount((long) offers2.getOffer().size());
		final Result result2 = new Result();
		result2.setOffers(offers2);
		final AvCache avCache2 = new AvCache();
		avCache2.setResult(result2);
		return Arrays.asList((Object) new JaxbDurationsResponse(avCache), (Object) new JaxbDurationsResponse(avCache2));
	}
}
