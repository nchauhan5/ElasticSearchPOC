package com.endeca.search.api.anite.converters;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.endeca.tui.anite.response.durations.AvCache;
import com.endeca.tui.anite.response.durations.AvCache.Result;
import com.endeca.tui.anite.response.durations.AvCache.Result.Offers;
import com.endeca.tui.anite.response.durations.AvCache.Result.Offers.Offer;

public class DurationsDataToAvCacheConverterTest {

	/*This Test method is to test ,whether duration data containing durations converted to  target Avcache successfully*/
	@Test
	public void durationDataShouldBeConvertedToAvCache() 
	{
		int expectedcount=5;
		short expectedStay=32;
		List<Integer> durations= Arrays.asList(32,23,67,89,76);
		AvCache result=DurationsDataToAvCacheConverter.convert(durations);
		assertThat(result.getResult().getOffers().getCount().intValue(),is(expectedcount));
		assertThat(result.getResult().getOffers().getOffer().get(0).getStay(),is(expectedStay));
	}
	
	
	/*This Test method is to test ,whether duration data is set when list is passed empty*/
	@Test
	public void durationDataShouldNotBeSetToAvCacheWhenDurationListIsPassedNull() 
	{
		int expectedcount=5;
		List<Integer> durations=new ArrayList<Integer>();
		AvCache result=DurationsDataToAvCacheConverter.convert(durations);
		assertTrue(result.getResult().getOffers().getOffer().isEmpty());
	}
}