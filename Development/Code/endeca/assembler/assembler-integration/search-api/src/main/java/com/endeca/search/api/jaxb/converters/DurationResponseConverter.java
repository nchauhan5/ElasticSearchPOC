package com.endeca.search.api.jaxb.converters;

import java.util.List;

import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;


/**
 * The Interface DurationResponseConverter the implementation of this interface will be used to convert the jaxb
 * response into duration data.
 */
public interface DurationResponseConverter extends ResponseConverter
{

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.search.api.jaxb.converters.ResponseConverter#convertJaxbResponse(java.lang.Object)
	 */
	@Override
	List<Integer> convertJaxbResponse(Object jaxbElement) throws SearchAPIUnmarshallingException;

}
