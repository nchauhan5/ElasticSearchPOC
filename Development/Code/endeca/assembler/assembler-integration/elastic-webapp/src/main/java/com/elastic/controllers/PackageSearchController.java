package com.elastic.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elastic.client.Esclient;
import com.elastic.comparators.GenericRecordComparator;
import com.elastic.constants.ElasticWebConstants;
import com.elastic.response.AssemblerResponse;
import com.endeca.search.api.adapter.CodeFormatAdapter;
import com.endeca.search.api.adapters.BrandAdapter;
import com.endeca.search.api.constants.SearchRequestParams;
import com.endeca.search.api.exceptions.SearchAPIException;
import com.endeca.search.api.populators.FacetsDataPopulator;
import com.endeca.search.api.populators.SortPaginationDataPopulator;
import com.endeca.search.api.request.dtos.SearchAPIRequestData;
import com.endeca.search.api.request.dtos.SortPaginationData;
import com.endeca.search.api.response.dtos.FacetCategoryData;
import com.endeca.search.api.response.dtos.FacetData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.PackagesData;
import com.endeca.search.api.services.SearchAPIService;



/**
 * @author nchau9
 * 
 */
@Controller
public class PackageSearchController
{
	/**
	 *
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PackageSearchController.class);

	/** The Constant dateFormat. */
	private static final String DATEFORMAT = "yyyy-MM-dd";

	/** The code format adapter. */
	@Autowired
	private CodeFormatAdapter codeFormatAdapter;

	/** The search brand adapter. */
	@Autowired
	private BrandAdapter searchBrandAdapter;

	/** The search api service. */
	@Autowired
	private SearchAPIService searchAPIService;

	/** The facets data populator. */
	@Autowired
	private FacetsDataPopulator facetsDataPopulator;

	/** The sort pagination data populator. */
	@Autowired
	private SortPaginationDataPopulator sortPaginationDataPopulator;

	/** The sorting key map. */
	@Resource
	private Map<String, String> sortingKeyMap;

	/**
	 * Gets the all search results.
	 * 
	 * @param model
	 *           the model
	 * @param request
	 *           the request
	 * @return the all search results
	 * @throws JsonGenerationException
	 *            the json generation exception
	 * @throws JsonMappingException
	 *            the json mapping exception
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	@ResponseBody
	@RequestMapping(value = "/pages/search", method = RequestMethod.GET)
	public String getAllSearchResults(Model model, HttpServletRequest request) throws JsonGenerationException,
			JsonMappingException, IOException
	{
		Map<String, Object> response = new HashMap<String, Object>();
		SearchResponse results = null;
		Map<String, Object> facetMap = getFacetData(request.getParameter("facets"));

		final Set<String> candiateAccomodationList = new HashSet<String>();
		final Map<String, Object> apcs = new HashMap<String, Object>();
		final DateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
		final Map<String, PackageData> packageDataMapForEs = new HashMap<String, PackageData>();
		final Map<String, String> actualIDTOSubIDMap = new HashMap<String, String>();
		final Esclient esclient = new Esclient();
		final List<Map<String, Object>> esData = new ArrayList<Map<String, Object>>();
		Map<String, Object> filterMap = new HashMap<String, Object>();
		if (null != facetMap.get("brand"))
		{
			filterMap.put("brand", facetMap.get("brand"));
		}
		if (null != request.getParameter("destinations"))
		{
			filterMap.put("destinations", request.getParameter("destinations"));
		}

		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData();
		final SortPaginationData sortPaginationData = sortPaginationDataPopulator.populate(
				request.getParameter(SearchRequestParams.SORT_PARAMETER),
				request.getParameter(SearchRequestParams.PAGINATION_PARAMETER));

		results = esclient.getAllAPC(filterMap, sortPaginationData);
		StringBuilder productsCode = new StringBuilder();
		for (SearchHit hit : results.getHits())
		{
			esData.add(hit.getSource());
		}
		for (Map<String, Object> esda : esData)
		{
			productsCode.append(esda.get("baseProductCode")).append(",");
			apcs.put((String) esda.get("baseProductCode"), esda);
		}
		//LOG.info("BaseProducts:{}", productsCode);

		for (final String code : apcs.keySet())
		{
			final String baseCode = codeFormatAdapter.endeca2SearchAPI(code);

			actualIDTOSubIDMap.put(baseCode, code);
			candiateAccomodationList.add(searchBrandAdapter.getAccomodationBrandCode(baseCode));
		}

		searchAPIRequestData.getDepartureData().setCandidateCodes(candiateAccomodationList);
		List<String> departureAirports = Arrays.asList(request.getParameter("from").split("\\s*,\\s*"));
		if (facetMap.get("DEPARTUREAIRPORTS") != null)
		{
			searchAPIRequestData.getDepartureData().setDepartureAirports((List<String>) facetMap.get("DEPARTUREAIRPORTS"));
		}
		else
		{
			searchAPIRequestData.getDepartureData().setDepartureAirports(departureAirports);
		}

		final Map<String, String> paramsMap = facetsDataPopulator.populate(request.getParameter("facets"));
		searchAPIRequestData.getMiscellaneousAttributesMap().putAll(paramsMap);
		searchAPIRequestData.getDurationData().setDuration(request.getParameter("stay"));
		try
		{
			searchAPIRequestData.getDurationData().setStartDate(simpleDateFormat.parse(request.getParameter("sdate")));
			searchAPIRequestData.getDurationData().setEndDate(simpleDateFormat.parse(request.getParameter("edate")));
		}
		catch (ParseException pe)
		{
			LOG.error("exception occureed while parsing date:", pe);
		}

		searchAPIRequestData.getPartyData().setNoOfAdults(Integer.parseInt(request.getParameter("adults")));

		PackagesData anitepackages = null;
		try
		{
			anitepackages = searchAPIService.getPackageAndFacetData(searchAPIRequestData);
		}
		catch (SearchAPIException spe)
		{
			LOG.error("exception occureed while getting data from API:", spe);
		}
		final List<PackageData> packageList = anitepackages.getPackages();
		final List<FacetCategoryData> facetCategoryDatas = anitepackages.getFacetCategoryDatas();
		for (FacetCategoryData facetCategoryData : facetCategoryDatas)
		{
			facetCategoryData.setType("external");
		}
		FacetCategoryData elasticfacet = new FacetCategoryData();
		List<FacetData> facetDatas = new ArrayList<FacetData>();



		for (PackageData packageData : packageList)
		{
			packageDataMapForEs.put(packageData.getHotelData().getId(), packageData);
		}
		LOG.info("BaseProducts in Elastic:{}", apcs.size());
		LOG.info("Anite Packages size is :{}", packageList.size());
		List<AssemblerResponse> asseblerResponse = new ArrayList<AssemblerResponse>();
		Map<String, Integer> brandCountMap = new HashMap<String, Integer>();
		for (String key : packageDataMapForEs.keySet())
		{
			final AssemblerResponse currentRecord = new AssemblerResponse();

			final PackageData attribute1 = packageDataMapForEs.get(key);

			final String keyCode = actualIDTOSubIDMap.get(key);
			final Object attribute2 = apcs.get(keyCode);
			Map<String, String> attributeMap = (Map<String, String>) attribute2;
			Integer brandcount = brandCountMap.get(attributeMap.get("brand"));
			if (null != brandcount)
			{
				brandCountMap.put(attributeMap.get("brand"), ++brandcount);
			}
			else
			{
				brandCountMap.put(attributeMap.get("brand"), 1);
			}
			currentRecord.getAttributes().put("package", attribute1);
			currentRecord.getAttributes().put("ESResponse", attribute2);
			asseblerResponse.add(currentRecord);
		}
		Terms terms = results.getAggregations().get(ElasticWebConstants.BRANDS_TERM);
		List<Bucket> buckets = terms.getBuckets();
		for (Bucket bucket : buckets)
		{
			FacetData facet = new FacetData();
			facet.setCount(brandCountMap.get(bucket.getKeyAsString()) != null ? brandCountMap.get(bucket.getKeyAsString()) : 0);
			facet.setName(bucket.getKeyAsString());
			facet.setCode("brand:" + bucket.getKeyAsString());
			if (Integer.valueOf(facet.getCount()) > 0)
			{
				facetDatas.add(facet);
			}
		}
		elasticfacet.setCategoryCode("brand");
		elasticfacet.setCategoryName("brand");
		elasticfacet.setType("internal");
		elasticfacet.setFacets(facetDatas);
		facetCategoryDatas.add(elasticfacet);
		final Integer totalActualRecords = asseblerResponse.size();
		if (!sortPaginationData.getSortProperty().equals("commPriority"))
		{
			try
			{
				sortResultList(asseblerResponse, sortPaginationData);
			}
			catch (SearchAPIException e)
			{
				LOG.error("exception occureed while Sorting:", e);
			}
		}

		final List<AssemblerResponse> paginatedRecordsValidatedWithAnite = paginateResultList(asseblerResponse, sortPaginationData);
		response.put("packages", paginatedRecordsValidatedWithAnite);
		response.put("totalRecords", totalActualRecords);
		response.put("facets", anitepackages.getFacetCategoryDatas());

		return new ObjectMapper().writeValueAsString(response);
	}

	/**
	 * @param parameter
	 * @return
	 */
	private Map<String, Object> getFacetData(String selectedFacet)
	{
		final List<String> facetTypeList = new ArrayList<String>();
		Map<String, Object> facetMap = new HashMap<String, Object>();
		try
		{
			facetTypeList.addAll(Arrays.asList(StringUtils.split(new String((selectedFacet).getBytes("iso-8859-1"), "UTF-8"), ",")));
		}
		catch (final UnsupportedEncodingException e)
		{
			LOG.error("Exception occured while encoding ", e);
		}
		for (String facetType : facetTypeList)
		{
			List<String> selectedFacetList = Arrays.asList(facetType.split(Pattern.quote("|")));
			if (selectedFacetList.get(1).equalsIgnoreCase("DEPARTUREAIRPORTS"))
			{
				facetMap.put("DEPARTUREAIRPORTS", selectedFacetList.subList(2, selectedFacetList.size()));
			}
			else if (selectedFacetList.get(1).equalsIgnoreCase("brand"))
			{
				facetMap.put("brand", selectedFacetList.subList(2, selectedFacetList.size()));
			}

		}
		return facetMap;
	}

	private void sortResultList(final List<AssemblerResponse> records, final SortPaginationData sortData)
			throws SearchAPIException
	{
		final List<Comparator<AssemblerResponse>> comparators = new ArrayList<Comparator<AssemblerResponse>>();
		if (null != sortData)
		{
			if (null != sortData.getSortOrder() && null != sortData.getSortProperty() && null != sortData.getSortType())
			{
				final String elasticBasedSortproperty = sortingKeyMap.get(sortData.getSortProperty());
				if (null != elasticBasedSortproperty)
				{
					LOG.info("Performing sorting for property:" + elasticBasedSortproperty);
					comparators.add(new GenericRecordComparator<AssemblerResponse>(sortData.getSortType(), sortData.getSortOrder(),
							elasticBasedSortproperty));
				}
			}
		}

		else
		{
			LOG.error("Sorting won't be performed as the sortProperty is not a valid one :");
		}

		if (!comparators.isEmpty())
		{
			final ComparatorChain c = new ComparatorChain(comparators);
			Collections.sort(records, c);
		}
	}

	/**
	 * Function will perform pagination on recordsList based on offset and page no.
	 * 
	 * @param list
	 *           the list
	 * @param sortData
	 *           the sort data
	 * @return the list
	 */
	private List<AssemblerResponse> paginateResultList(final List<AssemblerResponse> list, final SortPaginationData sortData)
	{
		int pageSize = sortData.getPageSize();
		final int page = sortData.getPageNumber();
		int firstRecNum = (pageSize * (page - 1));
		if (firstRecNum < 0)
		{
			firstRecNum = 0;
		}
		final int recordCount = list.size();
		final int lastRecNum = Math.min(pageSize * page, recordCount);
		LOG.info("Paginated Records first:" + firstRecNum + " last:" + lastRecNum);
		return (list.subList(firstRecNum, lastRecNum));
	}

}
