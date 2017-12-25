package com.endeca.search.api.populators;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.endeca.search.api.constants.AniteFacetCodes;


/**
 * Test class for {@link FacetsDataPopulator}.
 */
public class FacetsDataPopulatorTest
{

	/** The facets data populator. */
	private final FacetsDataPopulator facetsDataPopulator = new FacetsDataPopulator();

	/** The Constant overrideMap. */
	private static final Map<String, String> overrideMap = new HashMap<String, String>();
	static
	{
		overrideMap.put("DEPARTUREAIRPORTS", "dep");
		overrideMap.put("PRICEPERPERSON", "max_prc");
		overrideMap.put("PRICETOTAL", "max_prc");
		overrideMap.put("DEPARTURETIME", "slot_o");
	}

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp()
	{
		facetsDataPopulator.setOverrideParamsMap(overrideMap);
	}

	/**
	 * Test that Only valid external facets i.e. FacetType#external are processed.
	 */
	@Test
	public void validExternalFacetsProcessed()
	{

		final Map<String, String> response = facetsDataPopulator.populate("EXTERNAL|DEPARTUREAIRPORTS|ARN|CPH");
		assertThat(response.size(), greaterThan(0));
	}

	/**
	 * Test that only external valid facets are processed i.e. either present in Override map or they should be one of
	 * PriceTotal, PricePerPerson or Facility type.
	 */

	public void invalidExternalFacetsNotProcessed()
	{

		final Map<String, String> response = facetsDataPopulator.populate("EXTERNAL|BOGUS|ARN|CPH");
		assertThat(response.size(), is(0));
	}

	/**
	 * Test that internal facets i.e. FacetType#internal are processed rest are skipped.
	 */
	@Test
	public void internalFacetsTypeNotProcessed()
	{

		final Map<String, String> response = facetsDataPopulator.populate("INTERNAL|CONCEPTS|215467|213876");
		assertThat(response.size(), is(0));
	}

	/**
	 * Test that Anite list type i.e. facets whose keys are present in Override map are processed.
	 */
	@Test
	public void validAniteListTypeProcessed()
	{
		final Map<String, String> overrideMapM = new HashMap<String, String>();
		overrideMapM.put("DEPARTUREAIRPORTS", "dep");
		overrideMapM.put("PRICEPERPERSON", "max_prc");
		overrideMapM.put("PRICETOTAL", "max_prc");

		facetsDataPopulator.setOverrideParamsMap(overrideMapM);

		final Map<String, String> response = facetsDataPopulator
				.populate("EXTERNAL|DEPARTUREAIRPORTS|ARN|CPH,EXTERNAL|PRICEPERPERSON|150,EXTERNAL|DEPARTURETIME|0645");
		assertThat(response, Matchers.hasEntry(overrideMapM.get("DEPARTUREAIRPORTS"), "ARN,CPH"));
		assertThat(response, Matchers.hasEntry(overrideMapM.get("PRICEPERPERSON"), "150"));
		assertThat(response, not(hasValue("0645")));
	}


	/**
	 * Test that anite customer rating facet {@link AniteFacetCodes#CUSTOMER_RATING}.
	 */
	@Test
	public void customerRatingFacetProcessedCorrectly()
	{

		final Map<String, String> response = facetsDataPopulator.populate("EXTERNAL|CR0001|4");
		assertThat(response, Matchers.hasEntry("fa_1", "CR01|4|10.0"));
		assertThat(response, Matchers.hasEntry("f_no", "1"));
		assertThat(response, Matchers.hasEntry("f_1", "CR0001"));
	}

	/**
	 * Test that anite hotel rating facet {@link AniteFacetCodes#HOTEL_RATING}.
	 */
	@Test
	public void hotelRatingFacetProcessedCorrectly()
	{

		final Map<String, String> response = facetsDataPopulator.populate("EXTERNAL|HR0001|2");
		assertThat(response, Matchers.hasEntry("fa_1", "HR01|2|5.0"));
		assertThat(response, Matchers.hasEntry("f_no", "1"));
		assertThat(response, Matchers.hasEntry("f_1", "HR0001"));
	}

	/**
	 * Test that anite hotel rating facet {@link AniteFacetCodes#FACILITIES}.
	 */
	@Test
	public void facilitiesFacetProcessedCorrectly()
	{

		final Map<String, String> response = facetsDataPopulator.populate("EXTERNAL|FC0001|0026|0028");
		assertThat(response, Matchers.hasEntry("fa_1", "0026,0028"));
		assertThat(response, Matchers.hasEntry("f_no", "1"));
		assertThat(response, Matchers.hasEntry("f_1", "FC0001"));
	}

	/**
	 * Test For price per person facet "p_tp=PP" extra param appended along with max_prc param.
	 */
	@Test
	public void forPricePerPersonPPParamAppended()
	{
		final Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("facets", new String[] {});
		final Map<String, String> response = facetsDataPopulator.populate("EXTERNAL|PRICEPERPERSON|150");
		assertThat(response, Matchers.hasEntry("p_tp", "PP"));
		assertThat(response, Matchers.hasEntry("max_prc", "150"));

	}

	/**
	 * Test For price total facet "p_tp=TP" extra param appended along with max_prc param.
	 */
	@Test
	public void forPriceTotalTPParamAppendedWithPriceParam()
	{
		final Map<String, String> response = facetsDataPopulator.populate("EXTERNAL|PRICETOTAL|1200");
		assertThat(response, Matchers.hasEntry("p_tp", "TP"));
		assertThat(response, Matchers.hasEntry("max_prc", "1200"));

	}
}
