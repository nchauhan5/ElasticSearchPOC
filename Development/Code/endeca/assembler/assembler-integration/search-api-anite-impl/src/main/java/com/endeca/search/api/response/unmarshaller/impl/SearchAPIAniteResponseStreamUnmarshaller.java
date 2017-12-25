package com.endeca.search.api.response.unmarshaller.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.endeca.search.api.exceptions.SearchAPIException;
import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;
import com.endeca.search.api.jaxb.converters.ResponseConverter;
import com.endeca.search.api.jaxb.converters.impl.AniteCalendarResponseConverter;
import com.endeca.search.api.jaxb.converters.impl.AniteConfiguratorResponseConverter;
import com.endeca.search.api.jaxb.converters.impl.AniteDurationResponseConverter;
import com.endeca.search.api.jaxb.converters.impl.AnitePackagesResponseConverter;
import com.endeca.search.api.response.unmarshaller.AbstractResponseStreamUnmarshaller;
import com.endeca.search.api.response.unmarshaller.ResponseStreamUnmarshaller;
import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteUnmarshallingException;
import com.endeca.tui.anite.response.AvCache;
import com.endeca.tui.anite.unmarshaller.AniteResponseStreamUnmarshaller;


/**
 * The Class SearchAPIAniteResponseStreamUnmarshaller anite specific implementation of
 * {@link ResponseStreamUnmarshaller} .
 */
public class SearchAPIAniteResponseStreamUnmarshaller extends AbstractResponseStreamUnmarshaller implements
		AniteResponseStreamUnmarshaller

{

	/** The Constant LOG_MSG_CLASS_INSTANTIATION. */
	private static final String LOG_MSG_CLASS_INSTANTIATION = "Exception Occurred in creating instance of response implementation class";


	/** The Constant RESPONSE_CLASS_BY_QUERY_TYPE. */
	private static final Map<AniteQueryType, Class<? extends Object>> RESPONSE_CLASS_BY_QUERY_TYPE = new HashMap<AniteQueryType, Class<? extends Object>>();

	/** The Constant IMPLEMENTATIONS. */
	private static final Map<AniteQueryType, Class<? extends Object>> IMPLEMENTATIONS = new HashMap<AniteQueryType, Class<? extends Object>>();

	static
	{
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.LIST_BY_ACCOMMODATION, AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.SINGLE_ACCOMM_ALTERNATIVE_FLIGHTS, AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.LIST_BY_PACKAGEPRODUCTS, AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.SINGLE_ACCOMMODATION, AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.VILLA_AVAILABILITY, AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.SINGLE_ACCOMM_CHEAPEST_PER_DAY, AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.SINGLE_ACCOMM_ALL_ROOMS, AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.DURATIONS, com.endeca.tui.anite.response.durations.AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.CALENDAR, com.endeca.tui.anite.response.calendar.AvCache.class);
		IMPLEMENTATIONS.put(AniteQueryType.LIST_BY_ACCOMMODATION, AnitePackagesResponseConverter.class);
		IMPLEMENTATIONS.put(AniteQueryType.LIST_BY_PACKAGEPRODUCTS, AnitePackagesResponseConverter.class);
		IMPLEMENTATIONS.put(AniteQueryType.DURATIONS, AniteDurationResponseConverter.class);
		IMPLEMENTATIONS.put(AniteQueryType.CALENDAR, AniteCalendarResponseConverter.class);
		IMPLEMENTATIONS.put(AniteQueryType.SINGLE_ACCOMM_ALTERNATIVE_FLIGHTS, AniteConfiguratorResponseConverter.class);
		IMPLEMENTATIONS.put(AniteQueryType.SINGLE_ACCOMM_CHEAPEST_PER_DAY, AniteConfiguratorResponseConverter.class);
		IMPLEMENTATIONS.put(AniteQueryType.SINGLE_ACCOMM_ALL_ROOMS, AniteConfiguratorResponseConverter.class);
	}

	/** The implementation key. */
	private AniteQueryType implementationKey = null;

	/** The jaxb return type. */
	private Class<? extends Object> jaxbReturnType = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.search.api.response.unmarshaller.ResponseStreamUnmarshaller#unmarshall(java.io.InputStream)
	 */
	/**
	 * Unmarshall.
	 * 
	 * @param inputStream
	 *           the input stream
	 * @return the object
	 * @throws SearchAPIUnmarshallingException
	 *            the search api unmarshalling exception
	 */
	@Override
	public Object unmarshall(final InputStream inputStream) throws SearchAPIUnmarshallingException
	{
		final Class<? extends Object> responseImplementationClass = getImplementations().get(getImplementationKey());
		Object response = null;
		try
		{
			synchronized (this)
			{
				final ResponseConverter responseConverter = (ResponseConverter) responseImplementationClass.newInstance();
				response = responseConverter.convertJaxbResponse(unmarshall(inputStream, getJaxbReturnType()));
				
			}
		}
		catch (final InstantiationException e)
		{
			throw new SearchAPIUnmarshallingException(LOG_MSG_CLASS_INSTANTIATION + e.getMessage(), e);
		}
		catch (final IllegalAccessException e)
		{
			throw new SearchAPIUnmarshallingException(LOG_MSG_CLASS_INSTANTIATION + e.getMessage(), e);
		}
		finally
		{
			IOUtils.closeQuietly(inputStream);

		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.unmarshaller.AniteResponseStreamUnmarshaller#unmarshall(com.endeca.tui.anite.enums.
	 * AniteQueryType , java.io.InputStream)
	 */
	/**
	 * Unmarshall.
	 * 
	 * @param <T>
	 *           the generic type
	 * @param type
	 *           the type
	 * @param in
	 *           the in
	 * @return the t
	 * @throws AniteUnmarshallingException
	 *            the anite unmarshalling exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T unmarshall(final AniteQueryType type, final InputStream in) throws AniteUnmarshallingException
	{
		T response = null;
		synchronized (this)
		{
			setImplementationKey(type);
			setJaxbReturnType(getResponseClassByQueryType().get(type));
			try
			{
				response = (T) unmarshall(in);
			}
			catch (final SearchAPIException e)
			{
				throw new AniteUnmarshallingException("Exception Occured in unmarshalling anite repsonse" + e.getMessage(), e);
			}
		}
		return response;
	}



	/**
	 * Gets the response class by query type.
	 * 
	 * @return the response class by query type
	 */
	public static Map<AniteQueryType, Class<? extends Object>> getResponseClassByQueryType()
	{
		return RESPONSE_CLASS_BY_QUERY_TYPE;
	}

	/**
	 * Gets the implementations.
	 * 
	 * @return the implementations
	 */
	public static Map<AniteQueryType, Class<? extends Object>> getImplementations()
	{
		return IMPLEMENTATIONS;
	}

	/**
	 * Gets the implementation key.
	 * 
	 * @return the implementation key
	 */
	public AniteQueryType getImplementationKey()
	{
		return implementationKey;
	}


	/**
	 * Sets the implementation key.
	 * 
	 * @param type
	 *           the new implementation key
	 */
	public void setImplementationKey(final AniteQueryType type)
	{
		this.implementationKey = type;
	}

	/**
	 * Gets the jaxb return type.
	 * 
	 * @return the jaxb return type
	 */
	public Class<? extends Object> getJaxbReturnType()
	{
		return jaxbReturnType;
	}

	/**
	 * Sets the jaxb return type.
	 * 
	 * @param jaxbReturnType
	 *           the new jaxb return type
	 */
	public void setJaxbReturnType(final Class<? extends Object> jaxbReturnType)
	{
		synchronized (this)
		{
			this.jaxbReturnType = jaxbReturnType;
		}
	}

}
