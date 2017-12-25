package com.endeca.search.api.services;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.endeca.search.api.exceptions.SearchAPIException;
import com.endeca.search.api.request.dtos.SearchAPIRequestData;
import com.endeca.search.api.response.dtos.ConfiguratorResponseData;
import com.endeca.search.api.response.dtos.FacetCategoryData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.PackagesData;
import com.endeca.tui.anite.enums.AniteQueryType;


/**
 * The Interface SearchAPIService used to fetch data from third party providers for package search, duration or date
 * search. Interface that contains methods to interacts with the Search API and fetch the {@code PackagesData}. The
 * request parameter required to fetch the response is {@code SearchAPIRequestData}.
 * <p>
 * The implementation of this class will be written as per third party providers using their apis.
 *
 */
public interface SearchAPIService
{

	/**
	 * Gets the packages results. Basic and main packages search. Returns the {@link PackageData} wrapper dto having list
	 * of {@link FacetCategoryData} facets and {@link PackageData}
	 *
	 * @param searchAPIRequestData
	 *           the search api request data
	 * @return the packages results
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	PackagesData getPackageAndFacetData(SearchAPIRequestData searchAPIRequestData) throws SearchAPIException;

	/**
	 * Gets the calendar data. Map of Date vs boolean calendar storing information of date wise availability.
	 *
	 * @param searchAPIRequestData
	 *           the search api request data
	 * @return the calendar data
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	Map<Date, Boolean> getCalendarData(SearchAPIRequestData searchAPIRequestData) throws SearchAPIException;

	/**
	 * Gets the durations data. List of available durations.
	 *
	 * @param searchAPIRequestData
	 *           the search api request data
	 * @return the durations data
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	List<Integer> getDurationsData(SearchAPIRequestData searchAPIRequestData) throws SearchAPIException;

	/**
	 * Gets the facets data. Complete list of facet categories from provider with list of facets within each category.
	 *
	 * @param searchAPIRequestData
	 *           the search api request data
	 * @return the facets data
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	List<FacetCategoryData> getFacetsData(SearchAPIRequestData searchAPIRequestData) throws SearchAPIException;

	/**
	 * <<<<<<< HEAD Gets the configurator matrix entire through a combination of search types 2 & 5.
	 *
	 * @param searchAPIRequestData
	 *           the search api request data
	 * @param queryType
	 *           the query type
	 * @return the configurator matrix
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	ConfiguratorResponseData getConfiguratorResponse(final SearchAPIRequestData searchAPIRequestData,
			final AniteQueryType queryType) throws SearchAPIException;

	/**
	 * Gets the configurator full response.
	 *
	 * @param searchAPIRequestData
	 *           the search api request data
	 * @param queryType
	 *           the query type
	 * @param apcIDActualIDMap
	 *           the apc id actual id map
	 * @return the configurator full response
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	public ConfiguratorResponseData getConfiguratorFullResponse(final SearchAPIRequestData searchAPIRequestData,
			final AniteQueryType queryType, final Map<String, String> apcIDActualIDMap) throws SearchAPIException;

	/**
	 * Gets the package data for ppc.
	 *
	 * @param searchAPIRequestData
	 *           the search api request data
	 * @return the package data for ppc
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	PackagesData getPackageDataForPPC(SearchAPIRequestData searchAPIRequestData) throws SearchAPIException;

}
