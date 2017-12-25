//$Rev: 707 $
package com.endeca.tui.anite.io.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteCommunicationException;
import com.endeca.tui.anite.exceptions.AniteRuntimeException;
import com.endeca.tui.anite.io.AniteResponseStreamBuilder;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;
import com.endeca.tui.anite.parameters.impl.DefaultAniteParameters;


/**
 * The Class AniteHttpResponseStreamBuilder.
 */
public class AniteHttpResponseStreamBuilder implements AniteResponseStreamBuilder
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AniteHttpResponseStreamBuilder.class);

	/** The ipaddress. */
	private URI aniteUri;

	/** The http client. */
	private HttpClient httpClient;

	/** The anite params http params converter. */
	private AniteParamsHttpParamsConverter aniteParamsHttpParamsConverter;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.io.AniteResponseStreamBuilder#buildQueryInputStream(com.endeca.tui.anite.AniteQueryType,
	 * com.endeca.tui.anite.AniteRequiredParameters)
	 */
	@Override
	public <T> InputStream buildQueryInputStream(final AniteQueryType type, final AniteRequiredParameters params)
			throws AniteCommunicationException
	{
		LOGGER.info("Fetching response from Anite for Query:{}", type.toString());
		final HttpUriRequest req = buildHttpRequest(params, type);
		HttpResponse response = null;
		HttpEntity httpEntity = null;
		InputStream inputStream = null;
		try
		{
			final HttpClient c = getHttpClient();
			response = c.execute(req);
			if (null != response)
			{
				httpEntity = response.getEntity();
				if (null != httpEntity)
				{
					inputStream = httpEntity.getContent();
					if (200 == response.getStatusLine().getStatusCode())
					{
						LOGGER.info("Anite Status:{}", response.getStatusLine());
					}
					else
					{
						throw new AniteCommunicationException(
								response.getStatusLine().getStatusCode() + " Response received from Anite for URI " + getAniteUri());
					}
					if (LOGGER.isTraceEnabled())
					{
						for (final Header header : response.getAllHeaders())
						{
							LOGGER.trace("Anite response header\t" + header);
						}
					}
				}
			}
			return inputStream;
		}
		catch (final IOException e)
		{
			if (null != response)
			{
				try
				{
					EntityUtils.consume(httpEntity);
				}
				catch (final IOException e1)
				{
					LOGGER.error("Unable to close Stream ", e);
				}
			}
			LOGGER.error("Connection to anite refused ", e);
			throw new AniteCommunicationException(
					"Exception occured while communicating from Anite with URI: " + getAniteUri() + e.getMessage(), e);
		}
	}

	private String printAniteResponse(final InputStream is)
	{
		String reader = null;
		BufferedReader br = null;
		try
		{
			if (null != is)
			{
				br = getBufferedReader(is);
				String line = "";
				final StringBuilder response = new StringBuilder();
				while ((line = br.readLine()) != null)
				{
					response.append(line);
					response.append('\r');
				}
				reader = response.toString();
			}
		}
		catch (final IOException iOE)
		{
			LOGGER.error("Exception occured while reading stream from Anite ", iOE);
		}
		finally
		{
			IOUtils.closeQuietly(br);
		}
		return reader;
	}

	protected BufferedReader getBufferedReader(final InputStream is) throws UnsupportedEncodingException
	{
		return new BufferedReader(new InputStreamReader(is, "utf-8"));
	}

	/**
	 * Builds the http request.
	 *
	 * @param params
	 *           the params
	 * @param aniteQueryType
	 *           the search type
	 * @return the http uri request
	 * @throws AniteCommunicationException
	 *            the anite communication exception
	 */
	protected HttpUriRequest buildHttpRequest(final AniteRequiredParameters params, final AniteQueryType aniteQueryType)
			throws AniteCommunicationException
	{
		final HttpPost post = new HttpPost(getAniteUri());
		((DefaultAniteParameters) params).setAniteQueryType(aniteQueryType);
		final List<NameValuePair> valuePairs = aniteParamsHttpParamsConverter.convert(params);

		StringBuilder aniteParams = new StringBuilder();
		if (CollectionUtils.isNotEmpty(valuePairs))
		{
			for (int i = 0; i < valuePairs.size(); i++)
			{
				aniteParams = aniteParams.append(valuePairs.get(i)).append("&");
			}
			if (aniteParams.length() > 0)
			{
				LOGGER.info("Anite URI: {} And Query Parameters: {}", post.getURI(),
						aniteParams.deleteCharAt(aniteParams.length() - 1));
			}
		}

		try
		{
			post.setEntity(new UrlEncodedFormEntity(valuePairs));
		}
		catch (final UnsupportedEncodingException e)
		{
			LOGGER.error("Error setting UrlEncodedFormEntity", e);
			throw new AniteRuntimeException(e);
		}
		return post;
	}

	/**
	 * Gets the http client.
	 *
	 * @return the http client
	 */
	public HttpClient getHttpClient()
	{
		return httpClient;
	}

	/**
	 * Sets the http client.
	 *
	 * @param httpClient
	 *           the new http client
	 */
	public void setHttpClient(final HttpClient httpClient)
	{
		this.httpClient = httpClient;
	}

	/**
	 * Gets the anite uri.
	 *
	 * @return the anite uri
	 */
	public URI getAniteUri()
	{
		return aniteUri;
	}

	/**
	 * Sets the anite uri.
	 *
	 * @param aniteUri
	 *           the new anite uri
	 */
	public void setAniteUri(final URI aniteUri)
	{
		this.aniteUri = aniteUri;
	}

	/**
	 * Sets the anite params http params converter.
	 *
	 * @param aniteParamsHttpParamsConverter
	 *           the aniteParamsHttpParamsConverter to set
	 */
	public void setAniteParamsHttpParamsConverter(final AniteParamsHttpParamsConverter aniteParamsHttpParamsConverter)
	{
		this.aniteParamsHttpParamsConverter = aniteParamsHttpParamsConverter;
	}

	/**
	 * Gets the anite params http params converter.
	 *
	 * @return the aniteParamsHttpParamsConverter
	 */
	public AniteParamsHttpParamsConverter getAniteParamsHttpParamsConverter()
	{
		return aniteParamsHttpParamsConverter;
	}
}
