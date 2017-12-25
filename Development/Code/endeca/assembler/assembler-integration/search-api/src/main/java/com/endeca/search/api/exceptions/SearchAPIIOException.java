package com.endeca.search.api.exceptions;


/**
 * The Class SearchAPIIOException is used to indicate exceptions and errors that occurs while communicating with third
 * party providers to fetch data via search api.
 */
public class SearchAPIIOException extends SearchAPIException
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5446198493752390789L;

	/**
	 * Instantiates a new search api unmarshalling exception.
	 * 
	 * @param paramString
	 *           the param string
	 * @param paramThrowable
	 *           the param throwable
	 */
	public SearchAPIIOException(final String paramString, final Throwable paramThrowable)
	{
		super(paramString, paramThrowable);
	}

}
