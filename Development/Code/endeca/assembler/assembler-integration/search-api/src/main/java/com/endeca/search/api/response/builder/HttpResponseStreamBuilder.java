package com.endeca.search.api.response.builder;

import java.io.InputStream;


/**
 * The Class HttpResponseStreamBuilder. This class is used to build response stream via data fetched over http.
 */
public class HttpResponseStreamBuilder implements ResponseStreamBuilder
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.search.api.response.unmarshall.ResponseStreamBuilder#buildQueryInputStream()
	 */
	@Override
	public InputStream buildQueryInputStream()
	{
		return null;
	}
}
