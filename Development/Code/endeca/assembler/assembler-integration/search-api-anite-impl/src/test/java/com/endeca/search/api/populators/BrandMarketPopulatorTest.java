package com.endeca.search.api.populators;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import static org.hamcrest.Matchers.is;
import org.junit.Assert;
import org.junit.Test;

import com.endeca.search.api.constants.SearchRequestParams;
import com.endeca.search.api.request.dtos.BrandMarketData;

public class BrandMarketPopulatorTest {

	BrandMarketPopulator brandMarketPopulator=new BrandMarketPopulator();
	
	@Test
    public void marketShouldPopulateDataWhenParamKeysContainsMarketParameter()
	{
		Map<String, String> paramMap= new HashMap<String, String>();
		paramMap.put(SearchRequestParams.DEPARTURE_AIRPORTS_PARAM, "departure");
		paramMap.put(SearchRequestParams.ARRIVAL_AIRPORTS_PARAM,"arrival");
		paramMap.put(SearchRequestParams.MARKET_PARAMETER, "market");
		BrandMarketData result=brandMarketPopulator.populate(paramMap);
		Assert.assertThat(result.getMarket(), is("market"));
	}
	
	@Test
    public void marketShouldNotPopulateDataWhenParamKeysDoNotContainMarketParameter()
	{
		Map<String, String> paramMap= new HashMap<String, String>();
		paramMap.put(SearchRequestParams.DEPARTURE_AIRPORTS_PARAM, "departure");
		paramMap.put(SearchRequestParams.ARRIVAL_AIRPORTS_PARAM,"arrival");
		paramMap.put(SearchRequestParams.DURATIONS_PARAM, "market");
		BrandMarketData result=brandMarketPopulator.populate(paramMap);
		Assert.assertNull(result.getMarket());
	}
}
