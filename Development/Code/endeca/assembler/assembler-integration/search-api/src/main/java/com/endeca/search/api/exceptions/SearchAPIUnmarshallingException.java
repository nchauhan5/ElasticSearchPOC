package com.endeca.search.api.exceptions;

/**
 * The Class SearchAPIUnmarshallingException is used to indicate exceptions and errors that occurs while un-marshalling
 * or parsing data fetched from search api from third party providers.
 */
public class SearchAPIUnmarshallingException extends SearchAPIException
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2798800860016195796L;

	/**
	 * Instantiates a new search api unmarshalling exception.
	 * 
	 * @param paramString
	 *           the param string
	 * @param paramThrowable
	 *           the param throwable
	 */
	public SearchAPIUnmarshallingException(final String paramString, final Throwable paramThrowable)
	{
		super(paramString, paramThrowable);
	}

}
