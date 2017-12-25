package com.endeca.tui.anite.unmarshall.impl;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteUnmarshallingException;
import com.endeca.tui.anite.response.AvCache;
import com.endeca.tui.anite.response.impl.jaxb.JaxbCalendarResponse;
import com.endeca.tui.anite.response.impl.jaxb.JaxbDurationsResponse;
import com.endeca.tui.anite.unmarshaller.AniteResponseStreamUnmarshaller;


/**
 * The Class AniteJaxbResponseStreamUnmarshaller.
 */
public class AniteJaxbResponseStreamUnmarshaller implements AniteResponseStreamUnmarshaller
{

	private static final String GENERATED_UNMARSHALLING_EXCEPTION = "Generated Unmarshalling Exception";

	/** The date format pattern. */
	protected String dateFormatPattern;

	/** The Constant DATE_FORMAT_PATTERN. */
	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

	/** The context by class. */
	protected Map<Class<? extends Object>, JAXBContext> contextByClass = new HashMap<Class<? extends Object>, JAXBContext>();

	/** The unmarshallers by return type. */
	protected Map<Class<? extends Object>, ThreadLocal<Unmarshaller>> unmarshallersByReturnType = new HashMap<Class<? extends Object>, ThreadLocal<Unmarshaller>>();

	/** The Constant RESPONSE_CLASS_BY_QUERY_TYPE. */
	protected static final Map<AniteQueryType, Class<? extends Object>> RESPONSE_CLASS_BY_QUERY_TYPE = new HashMap<AniteQueryType, Class<? extends Object>>();

	/** The Constant IMPLEMENTATIONS. */
	protected static final Map<AniteQueryType, Class<? extends Object>> IMPLEMENTATIONS = new HashMap<AniteQueryType, Class<? extends Object>>();

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AniteJaxbResponseStreamUnmarshaller.class);
	static
	{
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.LIST_BY_ACCOMMODATION, AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.SINGLE_ACCOMM_ALTERNATIVE_FLIGHTS, AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.VILLA_AVAILABILITY, AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.SINGLE_ACCOMM_CHEAPEST_PER_DAY, AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.DURATIONS, com.endeca.tui.anite.response.durations.AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.CALENDAR, com.endeca.tui.anite.response.calendar.AvCache.class);
		RESPONSE_CLASS_BY_QUERY_TYPE.put(AniteQueryType.LIST_BY_PACKAGEPRODUCTS,
				com.endeca.tui.anite.response.calendar.AvCache.class);
		IMPLEMENTATIONS.put(AniteQueryType.CALENDAR, JaxbCalendarResponse.class);
		IMPLEMENTATIONS.put(AniteQueryType.DURATIONS, JaxbDurationsResponse.class);
	}

	/**
	 * Instantiates a new anite jaxb response stream unmarshaller.
	 */
	public AniteJaxbResponseStreamUnmarshaller()
	{
		this(DATE_FORMAT_PATTERN);
	}

	/**
	 * Instantiates a new anite jaxb response stream unmarshaller.
	 * 
	 * @param dateFormatPattern
	 *           the date format pattern
	 */
	public AniteJaxbResponseStreamUnmarshaller(final String dateFormatPattern)
	{
		this.dateFormatPattern = dateFormatPattern;
	}

	/**
	 * Gets the date format pattern.
	 * 
	 * @return the date format pattern
	 */
	public String getDateFormatPattern()
	{
		return dateFormatPattern;
	}

	/**
	 * Sets the date format pattern.
	 * 
	 * @param dateFormatPattern
	 *           the new date format pattern
	 */
	public void setDateFormatPattern(final String dateFormatPattern)
	{
		this.dateFormatPattern = dateFormatPattern;
	}

	/**
	 * Gets the unmarshaller.
	 * 
	 * @param jaxbReturnType
	 *           the jaxb return type
	 * @return the unmarshaller
	 * @throws JAXBException
	 *            the JAXB exception
	 */
	protected Unmarshaller getUnmarshaller(final Class<? extends Object> jaxbReturnType) throws JAXBException
	{
		ThreadLocal<Unmarshaller> threadLocal = unmarshallersByReturnType.get(jaxbReturnType);
		if (null == threadLocal)
		{
			synchronized (unmarshallersByReturnType)
			{
				threadLocal = unmarshallersByReturnType.get(jaxbReturnType);
				if (null == threadLocal)
				{
					threadLocal = new ThreadLocal<Unmarshaller>();
					unmarshallersByReturnType.put(jaxbReturnType, threadLocal);
				}
			}
		}
		Unmarshaller unmarshaller = threadLocal.get();
		if (null == unmarshaller)
		{
			synchronized (threadLocal)
			{
				if (null == threadLocal.get())
				{
					unmarshaller = getJaxbContext(jaxbReturnType).createUnmarshaller();
					threadLocal.set(unmarshaller);
				}
			}
		}
		return getJaxbContext(jaxbReturnType).createUnmarshaller();
	}

	/**
	 * Gets the jaxb context.
	 * 
	 * @param responseClass
	 *           the response class
	 * @return the jaxb context
	 * @throws JAXBException
	 *            the JAXB exception
	 */
	public JAXBContext getJaxbContext(final Class<? extends Object> responseClass) throws JAXBException
	{
		JAXBContext ctx = contextByClass.get(responseClass);
		if (null == ctx)
		{
			synchronized (contextByClass)
			{
				if (!contextByClass.containsKey(responseClass))
				{
					ctx = JAXBContext.newInstance(responseClass);
					contextByClass.put(responseClass, ctx);
				}
			}
		}
		return ctx;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.endeca.tui.anite.unmarshall.AniteResponseStreamUnmarshaller#unmarshall(com.endeca.tui.anite.AniteQueryType,
	 * java.io.InputStream)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T unmarshall(final AniteQueryType type, final InputStream in) throws AniteUnmarshallingException
	{
		try
		{
			final Unmarshaller unmarshaller = getUnmarshaller(RESPONSE_CLASS_BY_QUERY_TYPE.get(type));
			final Object o = unmarshaller.unmarshal(in);
			final Class<? extends Object> responseImplementationClass = IMPLEMENTATIONS.get(type);
			if (null == responseImplementationClass)
			{
				throw new AniteUnmarshallingException("No implementation class configured for response class " + type.getSearchType());
			}
			return (T) responseImplementationClass.getConstructor(o.getClass()).newInstance(o);
		}
		catch (final JAXBException e)
		{

			final String msg = GENERATED_UNMARSHALLING_EXCEPTION;
			LOGGER.error(msg + e);
			throw new AniteUnmarshallingException(e);
		}
		catch (final NoSuchMethodException e)
		{
			final String msg = GENERATED_UNMARSHALLING_EXCEPTION;
			LOGGER.error(msg + e);
			throw new AniteUnmarshallingException(e);
		}
		catch (final InvocationTargetException e)
		{
			final String msg = GENERATED_UNMARSHALLING_EXCEPTION;
			LOGGER.error(msg + e);
			throw new AniteUnmarshallingException(e);
		}
		catch (final IllegalAccessException e)
		{
			final String msg = GENERATED_UNMARSHALLING_EXCEPTION;
			LOGGER.error(msg + e);
			throw new AniteUnmarshallingException(e);
		}
		catch (final InstantiationException e)
		{
			final String msg = GENERATED_UNMARSHALLING_EXCEPTION;
			LOGGER.error(msg + e);
			throw new AniteUnmarshallingException(e);
		}
		finally
		{
			IOUtils.closeQuietly(in);
		}

	}
}
