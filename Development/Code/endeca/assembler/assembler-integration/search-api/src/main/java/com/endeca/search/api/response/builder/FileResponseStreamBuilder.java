package com.endeca.search.api.response.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class ResponseStreamBuilderFromFile. This class is used to build response stream from file resource.
 */
public class FileResponseStreamBuilder implements ResponseStreamBuilder
{

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FileResponseStreamBuilder.class);

	/** The mock response xml. */
	private File responseXML;

	/**
	 * Read file.
	 * 
	 * @param file
	 *           the file
	 * @return the input stream
	 */
	private InputStream readFile(final File file)
	{

		FileInputStream fileInputStream = null;
		try
		{

			fileInputStream = new FileInputStream(file);
		}
		catch (final FileNotFoundException e)
		{
			LOG.error("Mock XML File Not Found" + e.getMessage(), e);
		}
		return fileInputStream;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.search.api.response.unmarshall.ResponseStreamBuilder#buildQueryInputStream()
	 */
	@Override
	public InputStream buildQueryInputStream()
	{
		return readFile(getResponseXML());

	}

	/**
	 * Sets the response xml.
	 * 
	 * @param responseXML
	 *           the new response xml
	 */
	public void setResponseXML(final File responseXML)
	{
		this.responseXML = responseXML;
	}

	/**
	 * Gets the response xml.
	 * 
	 * @return the response xml
	 */
	public File getResponseXML()
	{
		return responseXML;
	}
}
