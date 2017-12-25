package com.endeca.search.api.exceptions;


/**
 * The Class SearchAPIException and its subclasses are used to indicate exceptions and errors that occurs while fetching
 * results from Search API.
 */
public class SearchAPIException extends Exception
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8169498287644959825L;

	/**
	 * Instantiates a new search api exception.
	 * 
	 * @param paramString
	 *           the param string
	 * @param paramThrowable
	 *           the param throwable
	 */
	public SearchAPIException(final String paramString, final Throwable paramThrowable)
	{
		super(paramString, paramThrowable);
	}
}
