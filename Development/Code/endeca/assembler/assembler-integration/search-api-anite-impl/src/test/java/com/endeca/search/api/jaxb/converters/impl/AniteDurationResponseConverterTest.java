package com.endeca.search.api.jaxb.converters.impl;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;
import com.endeca.tui.anite.response.durations.AvCache;
import com.endeca.tui.anite.response.durations.AvCache.Result;
import com.endeca.tui.anite.response.durations.AvCache.Result.Offers;
import com.endeca.tui.anite.response.durations.AvCache.Result.Offers.Offer;


public class AniteDurationResponseConverterTest
{

	private final AniteDurationResponseConverter aniteDurationResponseConverter = new AniteDurationResponseConverter();

	AvCache avCache = new AvCache();

	Offers offers = new Offers();

	final Result result = new Result();


	@Test
	public void sortedAvailableDurationShouldBeReturnedWhenAvailableDurationListIsPassed()
			throws SearchAPIUnmarshallingException, ParseException
	{

		List<Integer> value = Arrays.asList(15, 8, 22);
		int i = 0;
		while (i <= 2)
		{
			final Offer offer = new Offer();
			offer.setStay(value.get(i).shortValue());
			offers.getOffer().add(offer);
			i++;
		}
		result.setOffers(offers);
		avCache.setResult(result);

		final List<Integer> expectedDurations = Arrays.asList(8, 15, 22);
		final List<Integer> availableDurations = aniteDurationResponseConverter.convertJaxbResponse(avCache);
		assertTrue(expectedDurations.equals(availableDurations));
	}

	@Test
	public void nodAvailableDurationShouldBeReturnedrWhenAvailableDurationListIsPAssedEmpty()
			throws SearchAPIUnmarshallingException, ParseException
	{
		List<Integer> value = new ArrayList<Integer>();
		result.setOffers(offers);
		avCache.setResult(result);
		final List<Integer> expectedDurations = Arrays.asList(8, 15, 22);
		final List<Integer> availableDurations = aniteDurationResponseConverter.convertJaxbResponse(avCache);
		assertTrue(availableDurations.isEmpty());
	}
}
