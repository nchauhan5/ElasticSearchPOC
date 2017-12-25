package com.endeca.tui.anite;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class SearchAndBrowseAniteInterfaceProvider.
 */
public class SearchAndBrowseAniteInterfaceProvider
{

	/** The Constant SEARCH_ANITE_INTERFACE. */
	public static final String SEARCH_ANITE_INTERFACE = "search";

	/** The Constant ANITE_PARAM_NAME. */
	public static final String ANITE_PARAM_NAME = "anite";

	/** The search anite interface. */
	protected AniteInterface browseAniteInterface, searchAniteInterface;

	/**
	 * Gets the anite interface.
	 * 
	 * @param request
	 *           the request
	 * @return the anite interface
	 */
	public AniteInterface getAniteInterface(final HttpServletRequest request)
	{
		return getSearchAniteInterface();
	}

	/**
	 * Gets the browse anite interface.
	 * 
	 * @return the browse anite interface
	 */
	public AniteInterface getBrowseAniteInterface()
	{
		return browseAniteInterface;
	}

	/**
	 * Sets the browse anite interface.
	 * 
	 * @param browseAniteInterface
	 *           the new browse anite interface
	 */
	public void setBrowseAniteInterface(final AniteInterface browseAniteInterface)
	{
		this.browseAniteInterface = browseAniteInterface;
	}

	/**
	 * Gets the search anite interface.
	 * 
	 * @return the search anite interface
	 */
	public AniteInterface getSearchAniteInterface()
	{
		return searchAniteInterface;
	}

	/**
	 * Sets the search anite interface.
	 * 
	 * @param searchAniteInterface
	 *           the new search anite interface
	 */
	public void setSearchAniteInterface(final AniteInterface searchAniteInterface)
	{
		this.searchAniteInterface = searchAniteInterface;
	}
}
