package com.endeca.search.api.populators;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import com.endeca.search.api.constants.SearchRequestParams;
import com.endeca.search.api.request.dtos.RemovedFacetPriceData;


/**
 * 
 *
 */
public class RemovedFacetPriceDataPopulator
{

	/**
	 * @param paramMap
	 * @return
	 */
	public RemovedFacetPriceData populate(final Map<String, String> paramMap)
	{
		final Set<String> paramKeys = paramMap.keySet();
		final RemovedFacetPriceData facetRemovalData = new RemovedFacetPriceData();
		if (paramKeys.contains(SearchRequestParams.REMOVED_FACET))
		{
			facetRemovalData.setRemovedFacet(paramMap.get(SearchRequestParams.REMOVED_FACET));
		}
		if (paramKeys.contains(SearchRequestParams.PRICE_TOTAL))
		{
			facetRemovalData.setId(new BigDecimal(paramMap.get(SearchRequestParams.PRICE_TOTAL)));
			facetRemovalData.setType("PriceTotal");
		}
		if (paramKeys.contains(SearchRequestParams.PRICE_PERPERSON))
		{
			facetRemovalData.setId(new BigDecimal(paramMap.get(SearchRequestParams.PRICE_PERPERSON)));
			facetRemovalData.setType("PricePerPerson");
		}

		return facetRemovalData;
	}


}
