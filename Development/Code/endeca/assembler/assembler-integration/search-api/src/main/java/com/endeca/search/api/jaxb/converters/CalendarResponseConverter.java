package com.endeca.search.api.jaxb.converters;

import java.util.Date;
import java.util.Map;

import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;


/**
 * The Interface CalendarResponseConverter the implementation of this interface will be used to convert the jaxb
 * response into Calendar Response Data.
 */
public interface CalendarResponseConverter extends ResponseConverter
{

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.search.api.jaxb.converters.ResponseConverter#convertJaxbResponse(java.lang.Object)
	 */
	@Override
	Map<Date, Boolean> convertJaxbResponse(Object jaxbElement) throws SearchAPIUnmarshallingException;

}
