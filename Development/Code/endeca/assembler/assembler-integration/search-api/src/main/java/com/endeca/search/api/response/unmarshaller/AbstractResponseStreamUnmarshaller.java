package com.endeca.search.api.response.unmarshaller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;


/**
 * The Class AbstractResponseStreamUnmarshaller used to add common methods which will be needed by all the unmarshaller
 * to convert jaxb response into data objects.
 * <p>
 * The common methods are written to retrieve unmarshaller and jax b content.
 */
public abstract class AbstractResponseStreamUnmarshaller implements ResponseStreamUnmarshaller
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResponseStreamUnmarshaller.class);

	/** The context by class. */
	private final Map<Class<? extends Object>, JAXBContext> contextByClass = new HashMap<Class<? extends Object>, JAXBContext>();

	/** The unmarshallers by return type. */
	private final Map<Class<? extends Object>, ThreadLocal<Unmarshaller>> unmarshallersByReturnType = new HashMap<Class<? extends Object>, ThreadLocal<Unmarshaller>>();



	/**
	 * Gets the unmarshaller.
	 * 
	 * @param jaxbReturnType
	 *           the jaxb return type
	 * @return the unmarshaller
	 * @throws SearchAPIUnmarshallingException
	 *            the search api unmarshalling exception
	 */
	public Unmarshaller getUnmarshaller(final Class<? extends Object> jaxbReturnType) throws SearchAPIUnmarshallingException
	{
		ThreadLocal<Unmarshaller> threadLocal = unmarshallersByReturnType.get(jaxbReturnType);
		Unmarshaller unmarshaller;
		try
		{
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
			unmarshaller = threadLocal.get();
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
			unmarshaller = getJaxbContext(jaxbReturnType).createUnmarshaller();
		}
		catch (final JAXBException jaxbException)
		{
			throw new SearchAPIUnmarshallingException("Exception Occurred in creating unmarshaller for class"
					+ jaxbReturnType.getName() + jaxbException.getMessage(), jaxbException);
		}
		return unmarshaller;

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
	private JAXBContext getJaxbContext(final Class<? extends Object> responseClass) throws JAXBException
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

	/**
	 * Unmarshall.
	 * 
	 * @param inputStream
	 *           the input stream
	 * @param jaxbReturnType
	 *           the jaxb return type
	 * @return the object
	 * @throws SearchAPIUnmarshallingException
	 *            the search api unmarshalling exception
	 */
	public Object unmarshall(final InputStream inputStream, final Class<? extends Object> jaxbReturnType)
			throws SearchAPIUnmarshallingException
	{
		Object response = null;
		try
		{
			response = getUnmarshaller(jaxbReturnType).unmarshal(inputStream);
		}
		catch (final JAXBException e)
		{
			throw new SearchAPIUnmarshallingException("Exception Occurred In Unmarshalling for" + jaxbReturnType.getName()
					+ e.getMessage(), e);
		}
		return response;
	}
}
