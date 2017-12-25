package com.endeca.search.api.anite.converters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.endeca.search.api.response.dtos.FacetCategoryData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.PackagesData;
import com.endeca.tui.anite.response.AvCache;
import com.endeca.tui.anite.response.AvCache.Result;
import com.endeca.tui.anite.response.AvCache.Result.Offers;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Facets;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Facets.Cat;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer;


/**
 * The Class PackagesDataToAvCacheConverter.
 */
public class PackagesDataToAvCacheConverter
{

	/**
	 * Instantiates a new packages data to av cache converter.
	 */
	private PackagesDataToAvCacheConverter()
	{
		// Added to avoid instantiation since this is a static utility class.
	}

	/**
	 * Convert.
	 * 
	 * @param source
	 *           the source
	 * @return the av cache
	 */
	public static AvCache convert(final PackagesData source)
	{
		final AvCache target = new AvCache();
		final Result result = new Result();
		final Offers offers = new Offers();
		target.setResult(result);

		result.setOffers(offers);

		for (final PackageData packageData : source.getPackages())
		{
			final Offer offer = PackageDataToOfferConverter.convert(packageData);
			offers.getOffer().add(offer);
		}

		final List<Cat> catElements = new ArrayList<Cat>();
		if (CollectionUtils.isNotEmpty(source.getFacetCategoryDatas()))
		{
			for (final FacetCategoryData facetCategoryData : source.getFacetCategoryDatas())
			{
				final Cat cat = FacetCategoryDataToFacetConverter.convert(facetCategoryData);
				catElements.add(cat);
			}

			final Facets facets = new Facets();
			facets.getCat().addAll(catElements);
			result.getOffers().setFacets(facets);
		}
		return target;

	}
}
