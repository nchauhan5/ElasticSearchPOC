package com.endeca.tui.anite.enums;


/**
 * The Enum AniteQueryType.
 */
public enum AniteQueryType
{

	/** The list by accommodation. */
	LIST_BY_ACCOMMODATION(3),
	/** The calendar. */
	CALENDAR(1),
	/** The durations. */
	DURATIONS(2),
	SINGLE_ACCOMM_ALTERNATIVE_FLIGHTS(4),
	/** The single accommodation. */
	SINGLE_ACCOMMODATION(4),
	/** The Package products. */
	LIST_BY_PACKAGEPRODUCTS(4),
	/** The villa availability. */
	VILLA_AVAILABILITY(5),
	/** The single accomm cheapest per day. */
	SINGLE_ACCOMM_CHEAPEST_PER_DAY(5),
	/** The single accomm all rooms. */
	SINGLE_ACCOMM_ALL_ROOMS(6);

	/** The search type. */
	private int searchType;

	/**
	 * Instantiates a new anite query type.
	 *
	 * @param searchType
	 *           the search type
	 */
	private AniteQueryType(final int searchType)
	{
		this.searchType = searchType;
	}

	/**
	 * Gets the search type.
	 *
	 * @return the search type
	 */
	public int getSearchType()
	{
		return searchType;
	}

	/**
	 * Sets the search type.
	 *
	 * @param searchType
	 *           the search type
	 */
	public void setSearchType(final int searchType)
	{
		this.searchType = searchType;
	}

}
