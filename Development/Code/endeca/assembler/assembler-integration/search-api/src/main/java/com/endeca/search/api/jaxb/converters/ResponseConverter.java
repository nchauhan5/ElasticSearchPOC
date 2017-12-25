package com.endeca.search.api.jaxb.converters;

import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;


/**
 * The Interface ResponseConverter used to convert response fetched into desired data objects. The subclasses of this
 * interface extends the method {@link ResponseConverter#convertJaxbResponse(Object)} and converts the return object
 * into desired data objects.
 * <p>
 * Directly known subclasses
 * <p>
 * {@link CalendarResponseConverter} , {@link DurationResponseConverter}, {@link PackagesResponseConverter}
 */
public interface ResponseConverter
{

	/**
	 * Convert jaxb response into response object of desired form.
	 * 
	 * @param jaxbElement
	 *           the jaxb response fetched from response stream builder.
	 * @return the object response data.
	 * @throws SearchAPIUnmarshallingException
	 *            the search api unmarshalling exception to indicate unmarshalling exception
	 */
	Object convertJaxbResponse(Object jaxbElement) throws SearchAPIUnmarshallingException;
}
