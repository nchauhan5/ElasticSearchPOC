package com.endeca.search.api.populators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.search.api.constants.AniteFacetCodes;
import com.endeca.search.api.enums.FacetType;



/**
 * The Class FacetsDataPopulator. Used to populate Facets Data i.e. facet parameters to anite and their values. Separate
 * implementation to populate facet attributes for Anite List type and Anite Facet type is written in this populator.
 */
public class FacetsDataPopulator
{

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FacetsDataPopulator.class);
	/**
	 * The override params map. This map contains keys and parameter name for Anite List type facets. If selected facets
	 * is of type Anite List type i.e. the facet code is in the key of this map. The the parameter that is needed to be
	 * sent to anite is fetched from this map as value and sent to anite.
	 */
	private Map<String, String> overrideParamsMap = Collections.emptyMap();

	private int categoryCount = 0;

	/** The Constant HOTEL_RATING_MAX. */
	public static final String HOTEL_RATING_MAX = "5.0";

	/** The Constant CUSTOMER_RATING_MAX. */
	private static final String CUSTOMER_RATING_MAX = "10.0";

	/**
	 * Populate.
	 * 
	 * @param selectedFacet
	 * 
	 *           the request
	 * @return the list
	 */
	public Map<String, String> populate(String selectedFacet)
	{
		LOG.debug("Populating Anitte Facet Data.");
		Map<String, String> facetParamsMap = Collections.emptyMap();
		if (null != selectedFacet)
		{
			final List<String> selectedFacets = new ArrayList<String>();
			selectedFacets.addAll(Arrays.asList(StringUtils.split(selectedFacet, ",")));

			CollectionUtils.filter(selectedFacets, new Predicate()
			{

				@Override
				public boolean evaluate(final Object selectedFacetString)
				{
					return FacetType.EXTERNAL.getFacetType().equalsIgnoreCase(((String) selectedFacetString).split("\\|")[0]);
				}
			});

			if (CollectionUtils.isNotEmpty(selectedFacets))
			{
				facetParamsMap = new HashMap<String, String>();
				for (final String facet : selectedFacets)
				{
					facetParamsMap.putAll(populateOverrideParams(facet));
				}
				if (categoryCount > 0)
				{
					facetParamsMap.put("f_no", Integer.toString(categoryCount));
					categoryCount = 0;
				}
			}
		}

		return facetParamsMap;
	}

	/**
	 * Populate override params.
	 * 
	 * @param selectedFacet
	 *           the selected facet
	 * @return the map
	 */
	private Map<String, String> populateOverrideParams(final String selectedFacet)
	{
		final String[] facetAttributes = selectedFacet.split("\\|");
		final Map<String, String> paramsMap = new HashMap<String, String>();
		if (getOverrideParamsMap().containsKey(facetAttributes[1]))
		{
			//This is for the attributes which are of list type in Anite but treated as facets.
			paramsMap.putAll(populateParamsMapForLists(facetAttributes));
		}
		else
		{
			// This is used for the actual Anite facets.
			paramsMap.putAll(populateParamsMapForFacets(facetAttributes));
		}
		return paramsMap;
	}

	/**
	 * Populate params map for facet.
	 * 
	 * 
	 * @param facetAttributes
	 *           the facet attributes
	 * @return the map
	 */
	private Map<String, String> populateParamsMapForFacets(final String[] facetAttributes)
	{
		final List<String> facetAttributesList = Arrays.asList(facetAttributes);
		final List<String> facetValuesList = facetAttributesList.subList(2, facetAttributes.length);
		final Map<String, String> paramsMap = new HashMap<String, String>();
		if (AniteFacetCodes.CUSTOMER_RATING.equals(facetAttributes[1]))
		{
			categoryCount++;
			paramsMap.put("f_" + categoryCount, facetAttributes[1]);
			paramsMap.put("fa_" + categoryCount, AniteFacetCodes.CUSTOMER_RATING_FACET_CODE + "|" + facetAttributes[2] + "|"
					+ CUSTOMER_RATING_MAX);
		}
		if (AniteFacetCodes.HOTEL_RATING.equals(facetAttributes[1]))
		{
			categoryCount++;
			paramsMap.put("f_" + categoryCount, facetAttributes[1]);
			paramsMap.put("fa_" + categoryCount, AniteFacetCodes.HOTEL_RATING_FACET_CODE + "|" + facetAttributes[2] + "|"
					+ HOTEL_RATING_MAX);
		}
		if (AniteFacetCodes.FACILITIES.equals(facetAttributes[1]))
		{
			categoryCount++;
			paramsMap.put("f_" + categoryCount, facetAttributes[1]);
			paramsMap.put("fa_" + categoryCount, StringUtils.join(facetValuesList, ","));
		}
		return paramsMap;
	}

	/**
	 * Populate params map for facet. Commetting this method to avoid the sonar violations
	 * 
	 * @param facetAttributes
	 *           the facet attributes
	 * @return the map
	 */
	/**
	 * private void populateParamsMapForFacet(final String[] facetAttributes) {
	 * if(!CollectionUtils.sizeIsEmpty(facetAttributes)){ LOG.debug("facet attributes value is"+facetAttributes); } }
	 **/

	/**
	 * Populate params map for list.
	 * 
	 * @param facetAttributes
	 *           the facet attributes
	 * @return the map
	 */
	private Map<String, String> populateParamsMapForLists(final String[] facetAttributes)
	{

		final List<String> paramValues = new ArrayList<String>();
		final Map<String, String> paramsMap = new HashMap<String, String>();
		int i = 2;
		while (i < facetAttributes.length)
		{
			if (AniteFacetCodes.ARRIVALTIME.equals(facetAttributes[1]) || AniteFacetCodes.DEPARTURETIME.equals(facetAttributes[1]))
			{
				paramValues.add(facetAttributes[i].split("_")[1]);
			}
			else
			{
				paramValues.add(facetAttributes[i]);
			}
			i++;
		}
		if (AniteFacetCodes.PRICEPERPERSON.equals(facetAttributes[1]))
		{
			paramsMap.put("p_tp", "PP");
		}
		else if (AniteFacetCodes.PRICETOTAL.equals(facetAttributes[1]))
		{
			paramsMap.put("p_tp", "TP");
		}
		else if (AniteFacetCodes.DEPARTURETIME.equals(facetAttributes[1]))
		{
			paramsMap.put("slot_o", facetAttributes[2].split("_")[1]);
		}
		else if (AniteFacetCodes.ARRIVALTIME.equals(facetAttributes[1]))
		{
			paramsMap.put("slot_i", facetAttributes[2].split("_")[1]);
		}
		paramsMap.put(getOverrideParamsMap().get(facetAttributes[1]), StringUtils.join(paramValues, ","));
		return paramsMap;
	}

	/**
	 * Sets the override params map.
	 * 
	 * @param overrideParamsMap
	 *           the override params map
	 */
	public void setOverrideParamsMap(final Map<String, String> overrideParamsMap)
	{
		this.overrideParamsMap = overrideParamsMap;
	}

	/**
	 * Gets the override params map.
	 * 
	 * @return the override params map
	 */
	public Map<String, String> getOverrideParamsMap()
	{
		return overrideParamsMap;
	}

}
