package com.endeca.search.api.populators;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.endeca.search.api.request.dtos.RemovedFacetPriceData;


/**
 * Test class for {@link RemovedFacetPriceDataPopulator}.
 */
public class RemovedFacetPriceDataPopulatorTest
{

	/** The removed facet price data populator. */
	private final RemovedFacetPriceDataPopulator removedFacetPriceDataPopulator = new RemovedFacetPriceDataPopulator();

	/**
	 * Test Removed facet param when present removed facet parameter is set in
	 * {@link RemovedFacetPriceData#setRemovedFacet(String)}.
	 */
	@Test
	public void removedFacetParamPresentSetInRemovedFacet()
	{
		final Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("removedFacet", "location");
		final RemovedFacetPriceData response = removedFacetPriceDataPopulator.populate(requestParams);
		assertThat(response.getRemovedFacet(), is("location"));
		assertThat(response.getId(), nullValue());
		assertThat(response.getType(), nullValue());
	}

	/**
	 * Test when Price id total is present its set in removed facet.
	 */
	@Test
	public void priceIdTotalSetInRemovedFacet()
	{
		final Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("priceIdTotal", "2145");
		final RemovedFacetPriceData response = removedFacetPriceDataPopulator.populate(requestParams);
		assertThat(response.getId(), is(new BigDecimal(2145)));
		assertThat(response.getType(), is("PriceTotal"));
		assertThat(response.getRemovedFacet(), nullValue());
	}


	/**
	 * Test when Price id per person is present its set in removed facet.
	 */
	@Test
	public void priceIdPerPersonSetInRemovedFacet()
	{
		final Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("priceIdPP", "2145");
		final RemovedFacetPriceData response = removedFacetPriceDataPopulator.populate(requestParams);
		assertThat(response.getId(), is(new BigDecimal(2145)));
		assertThat(response.getType(), is("PricePerPerson"));
		assertThat(response.getRemovedFacet(), nullValue());
	}

}
