package com.endeca.tui.anite.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.tui.anite.ConfigurableResponseBufferFactory;
import com.endeca.tui.anite.ResponseBuffer;
import com.endeca.tui.anite.ResponseBufferFactory;
import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteCommunicationException;
import com.endeca.tui.anite.exceptions.AniteException;
import com.endeca.tui.anite.exceptions.AniteUnmarshallingException;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;


/**
 * The Class BufferingAniteImplementation.
 */
public class BufferingAniteImplementation extends DefaultAniteImplementation
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BufferingAniteImplementation.class);

	/** The response buffer factory. */
	private ResponseBufferFactory responseBufferFactory;

	/**
	 * The Enum BufferPolicy.
	 */
	public enum BufferPolicy
	{

		/** The dispose. */
		DISPOSE,
		/** The persist. */
		PERSIST
	}

	/** The on successful query buffer policy. */
	private BufferPolicy onSuccessfulQueryBufferPolicy = BufferPolicy.PERSIST;

	/**
	 * Persist quietly.
	 * 
	 * @param responseBuffer
	 *           the response buffer
	 */
	private void persistQuietly(final ResponseBuffer responseBuffer)
	{
		try
		{
			responseBuffer.persist();
		}
		catch (final IOException e1)
		{
			LOGGER.error("Error persisting response", e1);
		}
	}

	/**
	 * Gets the response buffer factory.
	 * 
	 * @return the response buffer factory
	 */
	public ResponseBufferFactory getResponseBufferFactory()
	{
		if (responseBufferFactory == null)
		{
			responseBufferFactory = new ConfigurableResponseBufferFactory();
			((ConfigurableResponseBufferFactory) responseBufferFactory).setMemoryBacked(true);
		}
		return responseBufferFactory;
	}

	/**
	 * Sets the response buffer factory.
	 * 
	 * @param responseBufferFactory
	 *           the new response buffer factory
	 */
	public void setResponseBufferFactory(final ResponseBufferFactory responseBufferFactory)
	{
		this.responseBufferFactory = responseBufferFactory;
	}

	/**
	 * Gets the on successful query buffer policy.
	 * 
	 * @return the on successful query buffer policy
	 */
	public BufferPolicy getOnSuccessfulQueryBufferPolicy()
	{
		return onSuccessfulQueryBufferPolicy;
	}

	/**
	 * Sets the on successful query buffer policy.
	 * 
	 * @param onSuccessfulQueryBufferPolicy
	 *           the new on successful query buffer policy
	 */
	public void setOnSuccessfulQueryBufferPolicy(final BufferPolicy onSuccessfulQueryBufferPolicy)
	{
		this.onSuccessfulQueryBufferPolicy = onSuccessfulQueryBufferPolicy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.impl.DefaultAniteImplementation#query(com.endeca.tui.anite.enums.AniteQueryType,
	 * com.endeca.tui.anite.parameters.AniteRequiredParameters)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T query(final AniteQueryType type, final AniteRequiredParameters params) throws AniteException
	{
		final StopWatch stopWatch = new StopWatch();
		try
		{
			stopWatch.start();
		}
		catch (final IllegalStateException e)
		{
			LOGGER.warn("Stop Watch Already Running" + e.getMessage(), e);
		}
		int responseSize;
		final ResponseBuffer responseBuffer = getResponseBufferFactory().newResponseBuffer(type.toString());
		final InputStream in = getResponseStreamBuilder().buildQueryInputStream(type, params);
		T response = null;
		if (in != null)
		{
		try
		{
			final OutputStream bufferOutputStream = responseBuffer.getBufferOutputStream();
			responseSize = IOUtils.copy(in, bufferOutputStream);
		}
		catch (final IOException e)
		{
			persistQuietly(responseBuffer);
			throw new AniteCommunicationException(e);
		}
		finally
		{
			IOUtils.closeQuietly(in);
		}

		stopWatch.stop();
		LOGGER.debug("Anite response: " + stopWatch.getTime() + "ms/" + responseSize + "bytes");
		stopWatch.reset();
		try
		{
			stopWatch.start();
			response = (T) getUnmarshaller().unmarshall(type, responseBuffer.getBufferInputStream());
			stopWatch.stop();
		}
			catch (final IOException e)
		{
			persistQuietly(responseBuffer);
			throw new AniteUnmarshallingException("Exception occurred while unmarshalling response from Anite for "
					+ type.toString() + " ", e);
		}
		LOGGER.debug("Anite response parsed in (ms): " + stopWatch.getTime());
		if (onSuccessfulQueryBufferPolicy == BufferPolicy.DISPOSE)
		{
			responseBuffer.dispose();
		}
		else if (onSuccessfulQueryBufferPolicy == BufferPolicy.PERSIST)
		{
			persistQuietly(responseBuffer);
		}
		}
		return response;
	}
}
