package com.endeca.search.api.jaxb.converters;

import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;
import com.endeca.search.api.response.dtos.PackagesData;


/**
 * The Interface PackagesResponseConverter the implementation of this interface will be used to convert the jaxb
 * response into {@code PackagesData}.
 */
public interface PackagesResponseConverter extends ResponseConverter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.search.api.jaxb.converters.ResponseConverter#convertJaxbResponse(java.lang.Object)
	 */
	@Override
	PackagesData convertJaxbResponse(Object jaxbElement) throws SearchAPIUnmarshallingException;

}
