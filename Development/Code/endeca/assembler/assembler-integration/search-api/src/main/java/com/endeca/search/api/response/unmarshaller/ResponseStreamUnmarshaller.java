package com.endeca.search.api.response.unmarshaller;

import java.io.InputStream;

import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;
import com.endeca.search.api.jaxb.converters.ResponseConverter;


/**
 * The Interface ResponseStreamUnmarshaller used to unmarshall the input stream into jax b response. This response will
 * be converted by {@link ResponseConverter} to desired data objects.
 * <p>
 * Subclasses of this interface can add common methods to be used across all response stream unmarshaller. Directly
 * known subclass {@link AbstractResponseStreamUnmarshaller}
 */
public interface ResponseStreamUnmarshaller
{

	/**
	 * Unmarshall the input stream into response jaxb object.
	 * 
	 * @param inputStream
	 *           the inputStream having streaming data from third party providers
	 * @return the object jax b response
	 * @throws SearchAPIUnmarshallingException
	 *            the search api unmarshalling exception
	 */
	Object unmarshall(InputStream inputStream) throws SearchAPIUnmarshallingException;
}
