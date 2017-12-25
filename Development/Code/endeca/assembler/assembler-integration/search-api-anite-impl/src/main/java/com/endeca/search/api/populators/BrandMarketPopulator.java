package com.endeca.search.api.populators;

import java.util.Map;
import java.util.Set;

import com.endeca.search.api.constants.SearchRequestParams;
import com.endeca.search.api.request.dtos.BrandMarketData;


/**
 * The Class BrandMarketPopulator used to populate Brand And Market information.
 */
public class BrandMarketPopulator
{

	/**
	 * Populate.
	 * 
	 * @param paramMap
	 *           the request
	 * @return the brand market data
	 */
	public BrandMarketData populate(final Map<String, String> paramMap)
	{
		final BrandMarketData brandMarketData = new BrandMarketData();
		final Set<String> paramKeys = paramMap.keySet();

		if (paramKeys.contains(SearchRequestParams.MARKET_PARAMETER))
		{
			brandMarketData.setMarket(paramMap.get(SearchRequestParams.MARKET_PARAMETER));
		}
		return brandMarketData;
	}
}
