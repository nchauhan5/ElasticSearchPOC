package com.endeca.search.api.populators;

import org.apache.commons.lang.StringUtils;

import com.endeca.search.api.constants.SearchRequestConstant;
import com.endeca.search.api.request.dtos.SortPaginationData;
import com.endeca.search.api.sort.parameter.SortingOrder;
import com.endeca.search.api.sort.parameter.SortingType;


/**
 * The Class SortPaginationDataPopulator used to populate Sort And Pagination information from request params.
 */
public class SortPaginationDataPopulator
{

	/**
	 * Method will populate the requestData into SortPaginationData.
	 * 
	 * @param paramMap
	 *           the arg0
	 * @return the sort pagination data
	 */
	public SortPaginationData populate(String sortParam, String paginationParam)
	{
		SortPaginationData paginationData = new SortPaginationData();
		if (null != sortParam)
		{
			String[] sort = StringUtils.split(sortParam, SearchRequestConstant.UNDERSCORE);
			if (sort.length > 0)
			{
				populateSortingOrder(paginationData, sort);

				paginationData.setSortProperty(sort[1]);

				populateSortingType(paginationData, sort);
			}
			else
			{
				populateNullSortData(paginationData);
			}
		}
		else
		{
			populateNullSortData(paginationData);
		}
		if (null != paginationParam)
		{
			String[] pagination = StringUtils.split(paginationParam, SearchRequestConstant.UNDERSCORE);
			if (pagination.length > 0)
			{
				populatePaginationData(paginationData, pagination);
			}
			else
			{
				populateNullPaginationData(paginationData);
			}

		}
		else
		{
			populateNullPaginationData(paginationData);
		}
		return paginationData;
	}

	/**
	 * Method will populate Pagination Data.
	 * 
	 * @param paginationData
	 *           the pagination data
	 * @param pagination
	 *           the pagination
	 */
	private void populatePaginationData(final SortPaginationData paginationData, final String[] pagination)
	{
		paginationData.setPageNumber(Integer.parseInt(pagination[0]));
		paginationData.setPageSize(Integer.parseInt(pagination[1]));

	}


	/**
	 * Populate sorting type.
	 * 
	 * @param paginationData
	 *           the pagination data
	 * @param sort
	 *           the sort
	 */
	private void populateSortingOrder(final SortPaginationData paginationData, final String[] sort)
	{
		if (null != sort[0] && sort[0].equalsIgnoreCase(SearchRequestConstant.ASCENDING))
		{
			paginationData.setSortOrder(SortingOrder.ASCENDING);
		}
		else if (null != sort[0] && sort[0].equalsIgnoreCase(SearchRequestConstant.DESCENDING))
		{
			paginationData.setSortOrder(SortingOrder.DESCENDING);
		}
		else
		{
			paginationData.setSortOrder(null);
		}


	}

	/**
	 * Populate sorting type.
	 * 
	 * @param paginationData
	 *           the pagination data
	 * @param sort
	 *           the sort
	 */
	private void populateSortingType(final SortPaginationData paginationData, final String[] sort)
	{
		if (null != sort[2] && sort[2].equalsIgnoreCase(SearchRequestConstant.ALPHA))
		{
			paginationData.setSortType(SortingType.ALPHA);
		}
		else if (null != sort[2] && sort[2].equalsIgnoreCase(SearchRequestConstant.INTEGER))
		{
			paginationData.setSortType(SortingType.INTEGER);
		}
		else if (null != sort[2] && sort[2].equalsIgnoreCase(SearchRequestConstant.FLOAT))
		{
			paginationData.setSortType(SortingType.FLOAT);
		}
		else
		{
			paginationData.setSortType(null);
		}

	}

	/**
	 * Method will set null value for all sorting property , this is the default values for sorting.
	 * 
	 * @param paginationData
	 *           the pagination data
	 */
	private void populateNullSortData(final SortPaginationData paginationData)
	{
		paginationData.setSortOrder(null);
		paginationData.setSortType(null);
		paginationData.setSortProperty(null);
	}

	/**
	 * Method will set null value for all pagination property, this is the default values for pagination.
	 * 
	 * @param paginationData
	 *           the pagination data
	 */
	private void populateNullPaginationData(final SortPaginationData paginationData)
	{
		paginationData.setPageNumber(0);
		paginationData.setPageSize(0);
	}

}
