package com.endeca.search.api.enums;

/**
 * The Enum FacetType. Used to distinguish whether selected facet or facet generated is of type MDEX i.e internal facet
 * or external facet handled in search api.
 */
public enum FacetType
{

	/** The internal. */
	INTERNAL("internal"),

	/** The external. */
	EXTERNAL("external"),
	
	INTERNAL_LOCATION("internal_location");

	/** The facet type. */
	String facetType;

	/**
	 * Instantiates a new facet type.
	 * 
	 * @param facetType
	 *           the facet type
	 */
	private FacetType(final String facetType)
	{
		this.facetType = facetType;
	}

	/**
	 * Gets the facet type.
	 * 
	 * @return the facet type
	 */
	public String getFacetType()
	{
		return facetType;
	}

	/**
	 * Sets the facet type.
	 * 
	 * @param facetType
	 *           the new facet type
	 */
	public void setFacetType(final String facetType)
	{
		this.facetType = facetType;
	}
}
