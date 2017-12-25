package com.endeca.tui.anite.io.http;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteCommunicationException;
import com.endeca.tui.anite.parameters.impl.DefaultAniteParameters;


/**
 * Test class for {@link AniteHttpResponseStreamBuilder}.
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class AniteHttpResponseStreamBuilderTest
{

	/** The http client. */
	@Mock
	private HttpClient httpClient;

	/** The anite params http params converter. */
	@Mock
	private AniteParamsHttpParamsConverter aniteParamsHttpParamsConverter;

	/** The anite http response stream builder. */
	@InjectMocks
	private final AniteHttpResponseStreamBuilder aniteHttpResponseStreamBuilder = new AniteHttpResponseStreamBuilder();
	@Mock
	private HttpResponse httpResponse;
	@Mock
	private HttpEntity httpEntity;

	/**
	 * Test for not null parameters Http request is executed with not null request.
	 * 
	 * @throws AniteCommunicationException
	 *            the anite communication exception
	 * @throws ClientProtocolException
	 *            the client protocol exception
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	@Test
	public void httpRequestExecutedNotNullRequestOKResponse() throws AniteCommunicationException, ClientProtocolException,
			IOException
	{
		
		final ParamHelper helper = new ParamHelper().add("sdate","2016-03-22")
				.add("edate", "2016-03-26");
		List<NameValuePair> params =  helper.getPairs();
		
		final DefaultAniteParameters aniteParameters = new DefaultAniteParameters();
		aniteParameters.setAccommodationsString("123|S");

		when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("Mock Input Stream".getBytes()));

		final StatusLine statusLine = mock(StatusLine.class);
		when(statusLine.getStatusCode()).thenReturn(200);

		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(httpResponse.getEntity()).thenReturn(httpEntity);

		when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);
		when(aniteParamsHttpParamsConverter.convert(aniteParameters)).thenReturn(params);
		aniteHttpResponseStreamBuilder.buildQueryInputStream(AniteQueryType.CALENDAR, aniteParameters);
		verify(httpClient).execute(any(HttpUriRequest.class));
		Assert.assertThat(httpClient.execute(any(HttpUriRequest.class)).getStatusLine().getStatusCode(), Matchers.is(200));

	}

	/**
	 * Test for not null parameters Http request is executed with not null request and 404 response.
	 * 
	 * @throws ClientProtocolException
	 *            the client protocol exception
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	@Test
	public void httpRequestExecutedNotNullRequestNotFoundResponse() throws ClientProtocolException, IOException
	{

		final DefaultAniteParameters aniteParameters = new DefaultAniteParameters();
		aniteParameters.setAccommodationsString("123|S");

		when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("Mock Input Stream".getBytes()));

		final StatusLine statusLine = mock(StatusLine.class);
		when(statusLine.getStatusCode()).thenReturn(404);

		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(httpResponse.getEntity()).thenReturn(httpEntity);

		when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);
		try
		{
			aniteHttpResponseStreamBuilder.buildQueryInputStream(AniteQueryType.CALENDAR, aniteParameters);
		}
		catch (final AniteCommunicationException ae)
		{
			ae.printStackTrace();
		}
		verify(httpClient).execute(any(HttpUriRequest.class));
		Assert.assertThat(httpClient.execute(any(HttpUriRequest.class)).getStatusLine().getStatusCode(), Matchers.is(404));
	}

	/**
	 * Test Anite params converter is executed to convert anite params to http request.
	 * 
	 * @throws IllegalStateException
	 *            the illegal state exception
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 * @throws AniteCommunicationException
	 *            the anite communication exception
	 */
	@Test
	public void aniteParamsConverterExecuted() throws IllegalStateException, IOException, AniteCommunicationException
	{
		final DefaultAniteParameters aniteParameters = new DefaultAniteParameters();
		aniteParameters.setAccommodationsString("123|S");

		when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("Mock Input Stream".getBytes()));

		final StatusLine statusLine = mock(StatusLine.class);
		when(statusLine.getStatusCode()).thenReturn(200);

		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(httpResponse.getEntity()).thenReturn(httpEntity);

		when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);
		aniteHttpResponseStreamBuilder.buildQueryInputStream(AniteQueryType.CALENDAR, aniteParameters);
		verify(aniteParamsHttpParamsConverter).convert(aniteParameters);
	}

	/**
	 * Test if IO Exception is thrown while reading response its properly captured and wrapped in anite communication
	 * exception.
	 * 
	 * @throws IllegalStateException
	 *            the illegal state exception
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 * @throws AniteCommunicationException
	 *            the anite communication exception
	 */
	@Test(expected = AniteCommunicationException.class)
	public void aniteCommunicationExceptionIfIOExceptionInResponse() throws IllegalStateException, IOException,
			AniteCommunicationException
	{
		final DefaultAniteParameters aniteParameters = new DefaultAniteParameters();
		aniteParameters.setAccommodationsString("123|S");

		when(httpEntity.getContent()).thenThrow(new IOException());

		final StatusLine statusLine = mock(StatusLine.class);
		when(statusLine.getStatusCode()).thenReturn(200);

		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(httpResponse.getEntity()).thenReturn(httpEntity);

		when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);
		aniteHttpResponseStreamBuilder.buildQueryInputStream(AniteQueryType.CALENDAR, aniteParameters);
	}

	@Test(expected = AniteCommunicationException.class)
	public void printResponseIfResponse500() throws IllegalStateException, IOException, AniteCommunicationException
	{
		final DefaultAniteParameters aniteParameters = new DefaultAniteParameters();
		aniteParameters.setAccommodationsString("123|S");
		when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("Mock Input Stream".getBytes()));

		final StatusLine statusLine = mock(StatusLine.class);
		when(statusLine.getStatusCode()).thenReturn(500);

		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);
		aniteHttpResponseStreamBuilder.buildQueryInputStream(AniteQueryType.CALENDAR, aniteParameters);
	}

	@Test(expected = AniteCommunicationException.class)
	public void printResponseIfNullIS() throws IllegalStateException, IOException, AniteCommunicationException
	{
		final DefaultAniteParameters aniteParameters = new DefaultAniteParameters();
		aniteParameters.setAccommodationsString("123|S");
		when(httpEntity.getContent()).thenReturn(new ByteArrayInputStream("Mock Input Stream".getBytes()));

		final StatusLine statusLine = mock(StatusLine.class);
		when(statusLine.getStatusCode()).thenReturn(500);

		when(httpResponse.getStatusLine()).thenReturn(statusLine);
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		when(httpEntity.getContent()).thenReturn(null);
		when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);
		aniteHttpResponseStreamBuilder.buildQueryInputStream(AniteQueryType.CALENDAR, aniteParameters);
		Assert.assertThat(httpClient.execute(any(HttpUriRequest.class)).getStatusLine().getStatusCode(), Matchers.is(500));
	}
}
