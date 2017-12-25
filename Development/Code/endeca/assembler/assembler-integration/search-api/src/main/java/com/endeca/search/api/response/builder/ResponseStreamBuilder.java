package com.endeca.search.api.response.builder;

import java.io.InputStream;

import com.endeca.search.api.response.unmarshaller.ResponseStreamUnmarshaller;


/**
 * The Interface ResponseStreamBuilder used to build input stream which contains the response fetched from third party
 * provider. The classes implementing this will provide their mechanism to build the input stream. This input stream
 * will be fetched to {@link ResponseStreamUnmarshaller} to un-marshall the response and convert it into desirable
 * response DTOs.
 * <p>
 * Directly known implementations.
 * <p>
 * {@link HttpResponseStreamBuilder}, {@link FileResponseStreamBuilder}
 * 
 */
public interface ResponseStreamBuilder
{

	/**
	 * Builds the query input stream.
	 * 
	 * @return the input stream
	 */
	InputStream buildQueryInputStream();
}
