package com.endeca.search.api.anite.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;

import com.endeca.search.api.response.dtos.FacetCategoryData;
import com.endeca.search.api.response.dtos.FacetData;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Facets.Cat;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Facets.Cat.Facet;


/**
 * The Class FacetCategoryDataToFacetConverter.
 */
public class FacetCategoryDataToFacetConverter
{

	/**
	 * Instantiates a new facet category data to facet converter.
	 */
	private FacetCategoryDataToFacetConverter()
	{
		// Added to avoid instantiation since this is a static utility class.
	}


	/**
	 * Convert.
	 * 
	 * @param facetCategoryData
	 *           the facet category data
	 * @return the cat
	 */
	public static Cat convert(final FacetCategoryData facetCategoryData)
	{
		final Cat cat = new Cat();
		cat.setCode(facetCategoryData.getCategoryCode());

		final List<Facet> facets = new ArrayList<Facet>();
		CollectionUtils.forAllDo(facetCategoryData.getFacets(), new Closure()
		{
			@Override
			public void execute(final Object input)
			{
				final FacetData facetData = (FacetData) input;
				final Facet facet = new Facet();
				facet.setCode(facetData.getCode());
				facet.setCount(facetData.getCount());
				facet.setMax(BigDecimal.valueOf(facetData.getMax()));
				facet.setMin(BigDecimal.valueOf(facetData.getMin()));
				facets.add(facet);
			}
		});

		cat.getFacet().addAll(facets);
		return cat;

	}
}
