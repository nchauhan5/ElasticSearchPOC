package com.endeca.search.api.populators;

import java.util.HashMap;
import java.util.Map;

import com.endeca.search.api.constants.SearchRequestParams;
import com.endeca.search.api.exceptions.SearchAPIException;
import com.endeca.search.api.request.dtos.BrandMarketData;
import com.endeca.search.api.request.dtos.DepartureData;
import com.endeca.search.api.request.dtos.DurationData;
import com.endeca.search.api.request.dtos.PartyData;
import com.endeca.search.api.request.dtos.RemovedFacetPriceData;
import com.endeca.search.api.request.dtos.SearchAPIRequestData;
import com.endeca.search.api.request.dtos.SortPaginationData;


/**
 * The Class SearchAPIRequestDataPopulator.
 */
public class SearchAPIRequestDataPopulator
{

	/** The departure data populator. */
	private DepartureDataPopulator departureDataPopulator;

	/** The duration data populator. */
	private DurationDataPopulator durationDataPopulator;

	/** The party data populator. */
	private PartyDataPopulator partyDataPopulator;

	/** The mandatory attributes map. */
	private Map<String, String> mandatoryAttributesMap = new HashMap<String, String>();

	/** The sort pagination data populator. */
	private SortPaginationDataPopulator sortPaginationDataPopulator;

	/** The sort pagination data populator. */
	private FacetsDataPopulator facetsDataPopulator;

	//added for DE32941
	private RemovedFacetPriceDataPopulator removedFacetPriceDataPopulator;

	/**
	 * Populate.
	 * 
	 * @param paramMap
	 * @param requestPath
	 * 
	 * @return the party data
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	public SearchAPIRequestData populate(final Map<String, String> paramMap, String requestPath) throws SearchAPIException
	{
		final DepartureData departureData = departureDataPopulator.populate(
				paramMap.get(SearchRequestParams.DEPARTURE_AIRPORTS_PARAM), paramMap.get(SearchRequestParams.ARRIVAL_AIRPORTS_PARAM));
		final DurationData durationData = durationDataPopulator.populate(
				paramMap.get(SearchRequestParams.EARLIEST_DEPARTURE_DATE_PARAM),
				paramMap.get(SearchRequestParams.LATEST_DEPARTURE_DATE_PARAM), paramMap.get(SearchRequestParams.DURATIONS_PARAM),
				paramMap.get(SearchRequestParams.STAY_PARAM));
		final PartyData partyData = partyDataPopulator.populate(paramMap.get(SearchRequestParams.ADULT_PAX_PARAM),
				paramMap.get(SearchRequestParams.CHILD_AGE_PARAM), paramMap.get(SearchRequestParams.ROOM_DATA));
		final SortPaginationData sortPaginationData = sortPaginationDataPopulator.populate(
				paramMap.get(SearchRequestParams.SORT_PARAMETER), paramMap.get(SearchRequestParams.PAGINATION_PARAMETER));
		final Map<String, String> facetParametersMap = facetsDataPopulator.populate(paramMap
				.get(SearchRequestParams.SELECTED_FACETS));
		final Map<String, String> miscellaneousAttributesMap = populateMiscellaneousAttributesMap(paramMap);
		miscellaneousAttributesMap.putAll(facetParametersMap);
		BrandMarketData sourceMarket = extractSourceMarketFromRequest(requestPath);

		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData(departureData, durationData, partyData,
				sourceMarket, sortPaginationData, miscellaneousAttributesMap);
		return searchAPIRequestData;
	}

	private BrandMarketData extractSourceMarketFromRequest(String contextPath)
	{
		String[] contextPathLength = contextPath.split("-");
		BrandMarketData marketData = null;
		if (contextPathLength.length == 3)
		{
			String brandName = contextPathLength[2];
			marketData = new BrandMarketData();
			marketData.setMarket(brandName);
		}
		return marketData;
	}

	public RemovedFacetPriceDataPopulator getRemovedFacetPriceDataPopulator()
	{
		return removedFacetPriceDataPopulator;
	}

	public void setRemovedFacetPriceDataPopulator(final RemovedFacetPriceDataPopulator removedFacetPriceDataPopulator)
	{
		this.removedFacetPriceDataPopulator = removedFacetPriceDataPopulator;
	}

	//added for DE32941
	public RemovedFacetPriceData populateRemovalFacetattributes(Map<String, String> paramMap) throws SearchAPIException
	{
		final RemovedFacetPriceData facetRemovalData = removedFacetPriceDataPopulator.populate(paramMap);
		return facetRemovalData;
	}

	/**
	 * Populate miscellaneous attributes map.
	 * 
	 * @param paramMap
	 *           the request
	 * @return the map
	 */
	public Map<String, String> populateMiscellaneousAttributesMap(final Map<String, String> paramMap)
	{

		final Map<String, String> miscellaneousAttributesMap = new HashMap<String, String>();

		if (null != paramMap.get(SearchRequestParams.ROOM_ALLOCATION1))
		{
			miscellaneousAttributesMap.put(SearchRequestParams.ROOM_ALLOCATION1, paramMap.get(SearchRequestParams.ROOM_ALLOCATION1));
		}
		if (null != paramMap.get(SearchRequestParams.ROOM_ALLOCATION2))
		{
			miscellaneousAttributesMap.put(SearchRequestParams.ROOM_ALLOCATION2, paramMap.get(SearchRequestParams.ROOM_ALLOCATION2));

		}
		if (null != paramMap.get(SearchRequestParams.ROOM_ALLOCATION3))
		{
			miscellaneousAttributesMap.put(SearchRequestParams.ROOM_ALLOCATION3, paramMap.get(SearchRequestParams.ROOM_ALLOCATION3));

		}
		if (null != paramMap.get(SearchRequestParams.PKG_ID))
		{
			miscellaneousAttributesMap.put(SearchRequestParams.PKG_ID, paramMap.get(SearchRequestParams.PKG_ID));
		}

		if (null != paramMap.get(SearchRequestParams.TYPE_PARAMETER))
		{
			miscellaneousAttributesMap.put(SearchRequestParams.TYPE_PARAMETER, paramMap.get(SearchRequestParams.TYPE_PARAMETER));
		}


		if (null != paramMap.get(SearchRequestParams.SEARCH_TYPE))
		{
			miscellaneousAttributesMap.put(SearchRequestParams.SEARCH_TYPE, paramMap.get(SearchRequestParams.SEARCH_TYPE));
		}


		//adding attribute for flight only. Whether user is  interested in one way flight or round trip.
		if (null != paramMap.get(SearchRequestParams.FLIGHT_SEARCH_TYPE))
		{
			miscellaneousAttributesMap.put(SearchRequestParams.FLIGHT_SEARCH_TYPE,
					paramMap.get(SearchRequestParams.FLIGHT_SEARCH_TYPE));
		}


		miscellaneousAttributesMap.putAll(getMandatoryAttributesMap());
		miscellaneousAttributesMap.putAll(paramMap);
		return miscellaneousAttributesMap;
	}

	/**
	 * Gets the departure data populator.
	 * 
	 * @return the departure data populator
	 */
	public DepartureDataPopulator getDepartureDataPopulator()
	{
		return departureDataPopulator;
	}

	/**
	 * Sets the departure data populator.
	 * 
	 * @param departureDataPopulator
	 *           the new departure data populator
	 */
	public void setDepartureDataPopulator(final DepartureDataPopulator departureDataPopulator)
	{
		this.departureDataPopulator = departureDataPopulator;
	}

	/**
	 * Gets the duration data populator.
	 * 
	 * @return the duration data populator
	 */
	public DurationDataPopulator getDurationDataPopulator()
	{
		return durationDataPopulator;
	}

	/**
	 * Sets the duration data populator.
	 * 
	 * @param durationDataPopulator
	 *           the new duration data populator
	 */
	public void setDurationDataPopulator(final DurationDataPopulator durationDataPopulator)
	{
		this.durationDataPopulator = durationDataPopulator;
	}

	/**
	 * Gets the party data populator.
	 * 
	 * @return the party data populator
	 */
	public PartyDataPopulator getPartyDataPopulator()
	{
		return partyDataPopulator;
	}

	/**
	 * Sets the party data populator.
	 * 
	 * @param partyDataPopulator
	 *           the new party data populator
	 */
	public void setPartyDataPopulator(final PartyDataPopulator partyDataPopulator)
	{
		this.partyDataPopulator = partyDataPopulator;
	}

	/**
	 * Sets the mandatory attributes map.
	 * 
	 * @param mandatoryAttributesMap
	 *           the mandatory attributes map
	 */
	public void setMandatoryAttributesMap(final Map<String, String> mandatoryAttributesMap)
	{
		this.mandatoryAttributesMap = mandatoryAttributesMap;
	}

	/**
	 * Gets the mandatory attributes map.
	 * 
	 * @return the mandatory attributes map
	 */
	public Map<String, String> getMandatoryAttributesMap()
	{
		return mandatoryAttributesMap;
	}

	/**
	 * Gets the sort pagination data populator.
	 * 
	 * @return the sort pagination data populator
	 */
	public SortPaginationDataPopulator getSortPaginationDataPopulator()
	{
		return sortPaginationDataPopulator;
	}

	/**
	 * Sets the sort pagination data populator.
	 * 
	 * @param sortPaginationDataPopulator
	 *           the sort pagination data populator
	 */
	public void setSortPaginationDataPopulator(final SortPaginationDataPopulator sortPaginationDataPopulator)
	{
		this.sortPaginationDataPopulator = sortPaginationDataPopulator;
	}

	/**
	 * Sets the facets data populator.
	 * 
	 * @param facetsDataPopulator
	 *           the new facets data populator
	 */
	public void setFacetsDataPopulator(final FacetsDataPopulator facetsDataPopulator)
	{
		this.facetsDataPopulator = facetsDataPopulator;
	}

	/**
	 * Gets the facets data populator.
	 * 
	 * @return the facets data populator
	 */
	public FacetsDataPopulator getFacetsDataPopulator()
	{
		return facetsDataPopulator;
	}



}
