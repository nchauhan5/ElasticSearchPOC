//$Rev: 792 $
package com.endeca.tui.anite.impl;

import java.io.InputStream;

import com.endeca.tui.anite.AniteInterface;
import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteCommunicationException;
import com.endeca.tui.anite.exceptions.AniteException;
import com.endeca.tui.anite.exceptions.AniteUnmarshallingException;
import com.endeca.tui.anite.io.AniteResponseStreamBuilder;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;
import com.endeca.tui.anite.unmarshaller.AniteResponseStreamUnmarshaller;


/**
 * The Class DefaultAniteImplementation.
 */
public class DefaultAniteImplementation implements AniteInterface
{

	/** The response stream builder. */
	private AniteResponseStreamBuilder responseStreamBuilder;

	/** The unmarshaller. */
	private AniteResponseStreamUnmarshaller unmarshaller;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteInterface#query(com.endeca.tui.anite.AniteQueryType,
	 * com.endeca.tui.anite.AniteRequiredParameters)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T query(final AniteQueryType type, final AniteRequiredParameters params) throws AniteException
	{

		final InputStream in = buildQueryInputStream(type, params);
		return (T) unmarshall(type, in);
	}

	/**
	 * Builds the query input stream.
	 * 
	 * @param type
	 *           the type
	 * @param params
	 *           the params
	 * @return the input stream
	 * @throws AniteCommunicationException
	 *            the anite communication exception
	 */
	protected InputStream buildQueryInputStream(final AniteQueryType type, final AniteRequiredParameters params)
			throws AniteCommunicationException
	{
		return getResponseStreamBuilder().buildQueryInputStream(type, params);
	}

	/**
	 * Unmarshall.
	 * 
	 * @param type
	 *           the type
	 * @param in
	 *           the in
	 * @return the object
	 * @throws AniteUnmarshallingException
	 *            the anite unmarshalling exception
	 */
	protected Object unmarshall(final AniteQueryType type, final InputStream in) throws AniteUnmarshallingException
	{
		return getUnmarshaller().unmarshall(type, in);
	}

	/**
	 * Gets the response stream builder.
	 * 
	 * @return the response stream builder
	 */
	public AniteResponseStreamBuilder getResponseStreamBuilder()
	{
		return responseStreamBuilder;
	}

	/**
	 * Sets the response stream builder.
	 * 
	 * @param responseStreamBuilder
	 *           the new response stream builder
	 */
	public void setResponseStreamBuilder(final AniteResponseStreamBuilder responseStreamBuilder)
	{
		this.responseStreamBuilder = responseStreamBuilder;
	}

	/**
	 * Gets the unmarshaller.
	 * 
	 * @return the unmarshaller
	 */
	public AniteResponseStreamUnmarshaller getUnmarshaller()
	{
		return unmarshaller;
	}

	/**
	 * Sets the unmarshaller.
	 * 
	 * @param unmarshaller
	 *           the new unmarshaller
	 */
	public void setUnmarshaller(final AniteResponseStreamUnmarshaller unmarshaller)
	{
		this.unmarshaller = unmarshaller;
	}

}
