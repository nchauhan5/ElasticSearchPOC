package com.endeca.search.api.request.dtos;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


// TODO: Auto-generated Javadoc
/**
 * The Class SearchAPIRequestData. Request parameter for
 * {@code SearchAPIService#getPackagesResults(SearchAPIRequestData)}
 * <p>
 * This wrapper dto contains further request DTOs.
 */
public class SearchAPIRequestData
{

	/** The departure data. */
	private final DepartureData departureData;

	/** The duration data. */
	private final DurationData durationData;

	/** The party data. */
	private final PartyData partyData;

	/** The brand market data. */
	private final BrandMarketData brandMarketData;

	/** The sort pagination data. */
	private final SortPaginationData sortPaginationData;

	//added for DE32941
	private RemovedFacetPriceData facetRemoval;

	/** The miscellaneous attributes map. */
	private Map<String, String> miscellaneousAttributesMap = Collections.emptyMap();

	/**
	 * Instantiates a new search api request data.
	 * 
	 * @param departureData
	 *           the departure data
	 * @param durationData
	 *           the duration data
	 * @param partyData
	 *           the party data
	 * @param brandMarketData
	 *           the brand market data
	 * @param sortPaginationData
	 *           the sort pagination data
	 */
	public SearchAPIRequestData(final DepartureData departureData, final DurationData durationData, final PartyData partyData,
			final BrandMarketData brandMarketData, final SortPaginationData sortPaginationData)
	{
		this.durationData = durationData;
		this.departureData = departureData;
		this.partyData = partyData;
		this.brandMarketData = brandMarketData;
		this.sortPaginationData = sortPaginationData;
	}

	/**
	 * Instantiates a new search api request data.
	 * 
	 * @param departureData
	 *           the departure data
	 * @param durationData
	 *           the duration data
	 * @param partyData
	 *           the party data
	 * @param brandMarketData
	 *           the brand market data
	 * @param sortPaginationDatas
	 *           the sort pagination datas
	 * @param passThroughMap
	 *           the pass through map
	 */
	public SearchAPIRequestData(final DepartureData departureData, final DurationData durationData, final PartyData partyData,
			final BrandMarketData brandMarketData, final SortPaginationData sortPaginationDatas,
			final Map<String, String> passThroughMap)
	{
		this.durationData = durationData;
		this.departureData = departureData;
		this.partyData = partyData;
		this.brandMarketData = brandMarketData;
		this.sortPaginationData = sortPaginationDatas;
		this.miscellaneousAttributesMap = passThroughMap;
	}

	/**
	 * Instantiates a new search api request data.
	 */
	public SearchAPIRequestData()
	{
		this.durationData = new DurationData();
		this.departureData = new DepartureData();
		this.partyData = new PartyData();
		this.brandMarketData = new BrandMarketData();
		this.sortPaginationData = new SortPaginationData();
		this.miscellaneousAttributesMap = new HashMap<String, String>();
	}

	/**
	 * Gets the departure data.
	 * 
	 * @return the departure data
	 */
	public DepartureData getDepartureData()
	{
		return departureData;
	}

	/**
	 * Gets the duration data.
	 * 
	 * @return the duration data
	 */
	public DurationData getDurationData()
	{
		return durationData;
	}

	/**
	 * Gets the party data.
	 * 
	 * @return the party data
	 */
	public PartyData getPartyData()
	{
		return partyData;
	}

	/**
	 * Gets the brand market data.
	 * 
	 * @return the brand market data
	 */
	public BrandMarketData getBrandMarketData()
	{
		return brandMarketData;
	}

	/**
	 * Gets the sort pagination data.
	 * 
	 * @return the sort pagination data
	 */
	public SortPaginationData getSortPaginationData()
	{
		return sortPaginationData;
	}

	/**
	 * Gets the miscellaneous attributes map.
	 * 
	 * @return the miscellaneous attributes map
	 */
	public Map<String, String> getMiscellaneousAttributesMap()
	{
		return miscellaneousAttributesMap;
	}

	public RemovedFacetPriceData getFacetRemoval()
	{
		return facetRemoval;
	}

	public void setFacetRemoval(final RemovedFacetPriceData facetRemoval)
	{
		this.facetRemoval = facetRemoval;
	}

}
