package com.endeca.tui.anite.io.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteCommunicationException;
import com.endeca.tui.anite.io.AniteResponseStreamBuilder;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;


/**
 * The Class AniteResponseStreamBuilderFromFile.
 */
public class AniteResponseStreamBuilderFromFile implements AniteResponseStreamBuilder
{

	/** The query type to file map. */
	protected Map<AniteQueryType, String> queryTypeToFileMap = new HashMap<AniteQueryType, String>();

	/**
	 * Read file.
	 * 
	 * @param file
	 *           the file
	 * @return the input stream
	 * @throws AniteCommunicationException
	 *            the anite communication exception
	 */
	protected InputStream readFile(final String fileName) throws AniteCommunicationException
	{
		try
		{
			return AniteResponseStreamBuilderFromFile.class.getClassLoader().getResource(fileName).openStream();
		}
		catch (final IOException e)
		{
			throw new AniteCommunicationException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.endeca.tui.anite.io.AniteResponseStreamBuilder#buildQueryInputStream(com.endeca.tui.anite.enums.AniteQueryType
	 * , com.endeca.tui.anite.parameters.AniteRequiredParameters)
	 */
	@Override
	public <T> InputStream buildQueryInputStream(final AniteQueryType type, final AniteRequiredParameters params)
			throws AniteCommunicationException
	{
		return readFile(getQueryTypeToFileMap().get(type));
	}

	/**
	 * Gets the query type to file map.
	 * 
	 * @return the query type to file map
	 */
	protected Map<AniteQueryType, String> getQueryTypeToFileMap()
	{
		return queryTypeToFileMap;
	}

	/**
	 * Sets the response map.
	 * 
	 * @param searchTypeToFileMap
	 *           the search type to file map
	 */
	public void setResponseMap(final Map<AniteQueryType, String> searchTypeToFileMap)
	{
		this.queryTypeToFileMap = searchTypeToFileMap;
	}
}
