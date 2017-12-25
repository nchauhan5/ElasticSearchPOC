package com.endeca.search.api.request.dtos;

import com.endeca.search.api.sort.parameter.SortingOrder;
import com.endeca.search.api.sort.parameter.SortingType;


// TODO: Auto-generated Javadoc
/**
 * The Class SortPaginationInfo. DTO class having sort and pagination information.
 */
public class SortPaginationData
{

	/** The page offset. */
	private int pageSize;

	/** The page size. */
	private int pageNumber;
	
	/** The sort order. */
	private SortingOrder sortOrder;

	/** The sort property. */
	private String sortProperty;
	
	/** The sort type. */
	private SortingType sortType;
	
	
	/**
	 * Instantiates a new sort pagination data.
	 *
	 * @param sortOrder the sort order
	 * @param sortType the sort type
	 * @param sortProperty the sort property
	 */
	public SortPaginationData(SortingOrder sortOrder,SortingType sortType, String sortProperty)
	{
		this.sortOrder=sortOrder;
		this.sortType=sortType;
		this.sortProperty=sortProperty;
	}

	
	/**
	 * Instantiates a new sort pagination data.
	 */
	public SortPaginationData()
	{
		
	}
	
	/**
	 * Gets the page size.
	 *
	 * @return the page size
	 */
	public int getPageSize()
	{
		return pageSize;
	}


	/**
	 * Sets the page size.
	 *
	 * @param pageSize the new page size
	 */
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}


	/**
	 * Gets the page number.
	 *
	 * @return the page number
	 */
	public int getPageNumber()
	{
		return pageNumber;
	}


	/**
	 * Sets the page number.
	 *
	 * @param pageNumber the new page number
	 */
	public void setPageNumber(int pageNumber)
	{
		this.pageNumber = pageNumber;
	}


	/**
	 * Gets the sort order.
	 *
	 * @return the sort order
	 */
	public SortingOrder getSortOrder()
	{
		return sortOrder;
	}

	/**
	 * Sets the sort order.
	 *
	 * @param sortOrder the sort order
	 */
	public void setSortOrder(SortingOrder sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	/**
	 * Gets the sort property.
	 *
	 * @return the sort property
	 */
	public String getSortProperty()
	{
		return sortProperty;
	}

	/**
	 * Sets the sort property.
	 *
	 * @param sortProperty the sort property
	 */
	public void setSortProperty(String sortProperty)
	{
		this.sortProperty = sortProperty;
	}

	/**
	 * Gets the sort type.
	 *
	 * @return the sort type
	 */
	public SortingType getSortType()
	{
		return sortType;
	}

	/**
	 * Sets the sort type.
	 *
	 * @param sortType the sort type
	 */
	public void setSortType(SortingType sortType)
	{
		this.sortType = sortType;
	}


}
