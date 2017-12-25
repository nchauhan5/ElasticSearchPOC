package com.endeca.search.api.constants;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchRequestParams. Request Parameters constants used to sent to Search API to fetch SearchResults.
 */
public class SearchRequestParams
{

	/** The Constant DEPARTURE_AIRPORTS_PARAM. */
	public static final String DEPARTURE_AIRPORTS_PARAM = "from";
	
	/** The Constant AARIVAL_AIRPORTS_PARAM. */
	public static final String ARRIVAL_AIRPORTS_PARAM = "to";

	/** The Constant DURATION_PARAM. */
	public static final String DURATIONS_PARAM = "durations";

	/** The Constant EARLIEST_DEPARTURE_DATE_PARAM. */
	public static final String EARLIEST_DEPARTURE_DATE_PARAM = "sdate";

	/** The Constant LATEST_DEPARTURE_DATE_PARAM. */
	public static final String LATEST_DEPARTURE_DATE_PARAM = "edate";

	/** The Constant STAY_PARAM. */
	public static final String STAY_PARAM = "stay";

	/** The Constant CHILD_AGE_PARAM. */
	public static final String CHILD_AGE_PARAM = "childAge";

	/** The Constant ADULT_PAX_PARAM. */
	public static final String ADULT_PAX_PARAM = "adults";

	/** The Constant ROOM_DATA. */
	public static final String ROOM_DATA = "rooms";

	/** The Constant ROOM_DATA. */
	public static final String ROOM_ALLOCATION1 = "rm_1";

	/** The Constant ROOM_DATA. */
	public static final String ROOM_ALLOCATION2 = "rm_2";

	/** The Constant ROOM_DATA. */
	public static final String ROOM_ALLOCATION3 = "rm_3";

	/** The Constant MARKET_PARAMETER. */
	public static final String MARKET_PARAMETER = "mkt";

	/** The Constant SORT_PARAMETER. */
	public static final String SORT_PARAMETER = "sort";

	/** The Constant PAGINATION_PARAMETER. */
	public static final String PAGINATION_PARAMETER = "page";

	/** The Constant SYS_INFO. */
	public static final String SYS_INFO = "sysInfo";

	/** The Constant APC_ID. */
	public static final String APC_ID = "apcId";

	/** The Constant SELECTED_FACETS. */
	public static final String SELECTED_FACETS = "facets";

	/** The Constant DAYS_FOR_ALTERNATIVE_SEARCH. */
	public static final String DAYS_FOR_ALTERNATIVE_SEARCH = "altSearchDays";

	/** The Constant IS_USE_CONFIGURED_DURATION. */
	public static final String IS_USE_CONFIGURED_DURATION = "useConfDuration";

	/** The Constant DURATION_FOR_ALTERNATIVE_SEARCH. */
	public static final String DURATION_FOR_ALTERNATIVE_SEARCH = "altSearchDurations";

	//added for DE32941
	public static final String REMOVED_FACET = "removedFacet";

	//added for DE32941
	public static final String PRICE_TOTAL = "priceIdTotal";

	//added for DE32941
	public static final String PRICE_PERPERSON = "priceIdPP";

	/** The Constant season. */
	public static final String SELECTED_SEASON = "season";

	/** The Constant pkg_id. */
	public static final String PKG_ID = "pkg";

	public static final String TYPE_PARAMETER = "h_tp";
	
	public static final String SEARCH_TYPE = "searchType";
	
	/**
	 * This attribute specifies whether user choose one way flight or round trip.
	 */
	public static final String FLIGHT_SEARCH_TYPE = "flightSearchType";
	
	/**
	 * Instantiates a new search request params.
	 */
	private SearchRequestParams()
	{
		// To Avoid instantiation.
	}

}
