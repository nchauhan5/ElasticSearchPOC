package com.endeca.search.api.response.dtos;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class FacetData. DTO to store information about a single facet such as
 * code name min max etc.
 */
public class FacetData {

	/** The code. */
	String code;
	
	String id;

	/** The name. */
	String name;

	/** The count. */
	int count;

	/** The min. */
	float min;

	/** The max. */
	float max;

	/** The sub facets. */
	List<FacetData> subFacets;
	
	boolean checkFacetRemoval;

	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 * 
	 * @param code
	 *            the new code
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the count.
	 * 
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 * 
	 * @param count
	 *            the new count
	 */
	public void setCount(final int count) {
		this.count = count;
	}

	/**
	 * Gets the min.
	 * 
	 * @return the min
	 */
	public float getMin() {
		return min;
	}

	/**
	 * Sets the min.
	 * 
	 * @param min
	 *            the new min
	 */
	public void setMin(final float min) {
		this.min = min;
	}

	/**
	 * Gets the max.
	 * 
	 * @return the max
	 */
	public float getMax() {
		return max;
	}

	/**
	 * Sets the max.
	 * 
	 * @param max
	 *            the new max
	 */
	public void setMax(final float max) {
		this.max = max;
	}

	/**
	 * Sets the sub facets.
	 * 
	 * @param subFacets
	 *            the new sub facets
	 */
	public void setSubFacets(List<FacetData> subFacets) {
		this.subFacets = subFacets;
	}

	/**
	 * Gets the sub facets.
	 * 
	 * @return the sub facets
	 */
	public List<FacetData> getSubFacets() {
		return subFacets;
	}

	public boolean isCheckFacetRemoval()
	{
		return checkFacetRemoval;
	}

	public void setCheckFacetRemoval(boolean checkFacetRemoval)
	{
		this.checkFacetRemoval = checkFacetRemoval;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

}