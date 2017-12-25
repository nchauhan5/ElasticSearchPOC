package com.endeca.search.api.response.dtos;

import java.util.List;


/**
 * The Class FacetCategoryData. DTO to store facet category such as name, code and set of facets within this category.
 */
public class FacetCategoryData
{

	/** The category code. */
	String categoryCode;

	/** The category name. */
	String categoryName;

	/** The facets. */
	List<FacetData> facets;

	/** The type. */
	private String type;

	/**
	 * Gets the facets.
	 * 
	 * @return the facets
	 */
	public List<FacetData> getFacets()
	{
		return facets;
	}

	/**
	 * Sets the facets.
	 * 
	 * @param facets
	 *           the new facets
	 */
	public void setFacets(final List<FacetData> facets)
	{
		this.facets = facets;
	}

	/**
	 * Gets the category code.
	 * 
	 * @return the category code
	 */
	public String getCategoryCode()
	{
		return categoryCode;
	}

	/**
	 * Sets the category code.
	 * 
	 * @param categoryCode
	 *           the new category code
	 */
	public void setCategoryCode(final String categoryCode)
	{
		this.categoryCode = categoryCode;
	}

	/**
	 * Gets the category name.
	 * 
	 * @return the category name
	 */
	public String getCategoryName()
	{
		return categoryName;
	}

	/**
	 * Sets the category name.
	 * 
	 * @param categoryName
	 *           the new category name
	 */
	public void setCategoryName(final String categoryName)
	{
		this.categoryName = categoryName;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *           the new type
	 */
	public void setType(final String type)
	{
		this.type = type;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

}
