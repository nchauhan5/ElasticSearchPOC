package com.elastic.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elastic.constants.ElasticWebConstants;
import com.endeca.search.api.request.dtos.SortPaginationData;



/**
 * @author
 * 
 */
public class Esclient
{
	/**
	 * 
	 */

	private static final Logger LOGGER = LoggerFactory.getLogger(Esclient.class);
	private static String Brand = "sebluesv";
	private static String Type = "accommodation";

	private Client getClient() throws UnknownHostException
	{

		Client client = TransportClient.builder().build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		return client;
	}

	/**
	 * Gets the all apc.
	 * 
	 * @param facets
	 *           the facets
	 * @param sortPaginationData
	 *           the sort pagination data
	 * @return the all apc
	 * @throws UnknownHostException
	 *            the unknown host exception
	 */
	public SearchResponse getAllAPC(Map<String, Object> filterMap, final SortPaginationData sortPaginationData)
			throws UnknownHostException
	{
		List<String> brandFacets = new ArrayList<String>();
		Map<String, List<String>> destString = new HashMap<String, List<String>>();
		//final List<String> filterItems = Arrays.asList(StringUtils.split(filterMap.get("), ";"));
		QueryBuilder brandQuery = null;
		if (null != filterMap.get("brand"))
		{
			for (Map.Entry<String, Object> entry : filterMap.entrySet())
			{
				if (entry.getKey().equals("brand"))
				{
					String brandValues = entry.getValue().toString();
					String[] facetVals = brandValues.substring(1, brandValues.length() - 1).split(",");
					for (int i = 0; i < facetVals.length; i++)
					{
						String[] values = facetVals[i].split(":");
						brandFacets.add(values[1]);
					}
				}
			}
		}
		if (null != filterMap.get("destinations"))
		{
			destString = getDestinations((String) filterMap.get("destinations"));
		}


		if (CollectionUtils.isNotEmpty(brandFacets))
		{
			brandQuery = QueryBuilders.termsQuery(ElasticWebConstants.BRANDS_TERM, brandFacets);
		}
		QueryBuilder destquery = null;
		if (MapUtils.isNotEmpty(destString))
		{
			destquery = getBoolQueryForDestination(destString);
		}
		final Client client = getClient();

		int i = 1;
		int scrollSize = 1000;
		final SearchRequestBuilder searchRequestBuilder;
		if (MapUtils.isNotEmpty(destString))
		{
			searchRequestBuilder = client
					.prepareSearch(Brand)
					.setTypes(Type)
					.setQuery(destquery)
					.setSize(scrollSize)
					.setFrom(0)
					.addAggregation(
							AggregationBuilders.terms(ElasticWebConstants.BRANDS_TERM).size(30).field(ElasticWebConstants.BRANDS_TERM))
					.setPostFilter(brandQuery).setSize(10000);
		}
		else
		{
			searchRequestBuilder = client
					.prepareSearch(Brand)
					.setTypes(Type)
					.setQuery(QueryBuilders.matchAllQuery())
					.setSize(scrollSize)
					.setFrom(0)
					.addAggregation(
							AggregationBuilders.terms(ElasticWebConstants.BRANDS_TERM).size(30).field(ElasticWebConstants.BRANDS_TERM))
					.setPostFilter(brandQuery).setSize(10000);
		}

		// Add pagination to searchRequestBuilder

		if (sortPaginationData.getSortProperty().equals("commPriority"))
		{
			//	 Add sorting from elastic for the only property Added in
			FieldSortBuilder sortBuilder = SortBuilders.fieldSort("commercialPriority")
					.order(sortPaginationData.getSortOrder().equals("ASCENDING") ? SortOrder.ASC : SortOrder.DESC).sortMode("min");
			searchRequestBuilder.addSort(sortBuilder);
		}

		SearchResponse response = searchRequestBuilder.execute().actionGet();

		return response;
	}


	/**
	 * @param destString
	 * @return
	 */
	private QueryBuilder getBoolQueryForDestination(Map<String, List<String>> destMap)
	{
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		for (String destStr : destMap.keySet())
		{
			List<String> destString = destMap.get(destStr);
			boolQuery.should(QueryBuilders.matchQuery(getDestType(destString.get(1)), destString.get(0)));
		}
		return boolQuery;
	}

	/**
	 * @param parameter
	 */
	private Map<String, List<String>> getDestinations(String destinationParam)
	{
		Map<String, List<String>> destarr = new HashMap<String, List<String>>();

		if (null != destinationParam)
		{
			final List<String> destinations = Arrays.asList(StringUtils.split(destinationParam, ","));
			int i = 1;
			for (final String destination : destinations)
			{
				List<String> destString = new ArrayList<String>();
				destString.add(StringUtils.split(destination, "|")[0]);
				destString.add(StringUtils.split(destination, "|")[1]);
				destarr.put("dest" + i, destString);
				i++;
			}
		}
		return destarr;
	}

	/**
	 * @param string
	 * @return
	 */
	private String getDestType(String type)
	{
		String destinationType = null;
		if ("COUNTRY".equalsIgnoreCase(type))
		{
			destinationType = "countryCode";
		}
		else if ("DESTINATION".equalsIgnoreCase(type))
		{
			destinationType = "destinationCode";
		}
		else if ("RESORT".equalsIgnoreCase(type))
		{
			destinationType = "resortCode";

		}
		else if ("ACCOMMODATION".equalsIgnoreCase(type))
		{
			destinationType = "baseProductCode";
		}
		else
		{
			destinationType = "conceptCode";
		}
		return destinationType;
	}
}
