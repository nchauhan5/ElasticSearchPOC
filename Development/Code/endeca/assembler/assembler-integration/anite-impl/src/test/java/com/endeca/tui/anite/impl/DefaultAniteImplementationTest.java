package com.endeca.tui.anite.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteException;
import com.endeca.tui.anite.io.file.AniteResponseStreamBuilderFromFile;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;
import com.endeca.tui.anite.responses.AniteCalendarResponse;
import com.endeca.tui.anite.responses.AniteDurationsResponse;
import com.endeca.tui.anite.unmarshall.impl.AniteJaxbResponseStreamUnmarshaller;


/**
 * Test class for {@link DefaultAniteImplementation}.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultAniteImplementationTest
{

	/** The anite response stream builder from file. */
	@Mock
	private AniteResponseStreamBuilderFromFile mockAniteResponseStreamBuilderFromFile;

	/** The anite jaxb response stream unmarshaller. */
	@Mock
	private AniteJaxbResponseStreamUnmarshaller mockAniteJaxbResponseStreamUnmarshaller;

	private final AniteResponseStreamBuilderFromFile aniteResponseStreamBuilderFromFile = new AniteResponseStreamBuilderFromFile();

	private final AniteJaxbResponseStreamUnmarshaller aniteJaxbResponseStreamUnmarshaller = new AniteJaxbResponseStreamUnmarshaller();

	/** The default anite implementation. */
	@InjectMocks
	private final static DefaultAniteImplementation defaultAniteImplementation = new DefaultAniteImplementation();

	/**
	 * Passing date anite query type to check return object is date response.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void aniteResponseBuilderIsCalledWithQueryTypeAndRequiredParams() throws AniteException
	{
		final AniteRequiredParameters mockAniteParameters = Mockito.mock(AniteRequiredParameters.class);
		defaultAniteImplementation.query(AniteQueryType.CALENDAR, mockAniteParameters);
		verify(mockAniteResponseStreamBuilderFromFile).buildQueryInputStream(AniteQueryType.CALENDAR, mockAniteParameters);
	}

	/**
	 * Unmarshaller is called with input stream from builder.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void unmarshallerIsCalledWithInputStreamFromBuilder() throws AniteException
	{
		final AniteRequiredParameters mockAniteParameters = Mockito.mock(AniteRequiredParameters.class);
		final InputStream mockInputStream = Mockito.mock(InputStream.class);
		when(mockAniteResponseStreamBuilderFromFile.buildQueryInputStream(AniteQueryType.CALENDAR, mockAniteParameters))
				.thenReturn(mockInputStream);
		defaultAniteImplementation.query(AniteQueryType.CALENDAR, mockAniteParameters);
		verify(mockAniteJaxbResponseStreamUnmarshaller).unmarshall(AniteQueryType.CALENDAR, mockInputStream);
	}

	/**
	 * Verify response type conforms with anite query type. i.e.
	 * <p>
	 * For {@code AniteQueryType.CALENDAR}response should be instance of {@code CalendarResponse}
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void verifyActualResponseTypeConformsWithAniteQueryType() throws AniteException
	{
		final Map<AniteQueryType, String> aniteQueryTypeToFileMap = new HashMap<AniteQueryType, String>();

		aniteQueryTypeToFileMap.put(AniteQueryType.CALENDAR, "aniteResponses/aniteCalendarResponse.xml");
		aniteQueryTypeToFileMap.put(AniteQueryType.DURATIONS, "aniteResponses/aniteDurationsResponse.xml");
		aniteQueryTypeToFileMap.put(AniteQueryType.LIST_BY_ACCOMMODATION, "aniteResponses/listByAccommodationCode.xml");
		final AniteRequiredParameters mockAniteParameters = Mockito.mock(AniteRequiredParameters.class);

		aniteResponseStreamBuilderFromFile.setResponseMap(aniteQueryTypeToFileMap);
		defaultAniteImplementation.setResponseStreamBuilder(aniteResponseStreamBuilderFromFile);
		defaultAniteImplementation.setUnmarshaller(aniteJaxbResponseStreamUnmarshaller);
		final Object calendarResponse = defaultAniteImplementation.query(AniteQueryType.CALENDAR, mockAniteParameters);
		Assert.assertThat(calendarResponse, Matchers.instanceOf(AniteCalendarResponse.class));
		final Object durationsResponse = defaultAniteImplementation.query(AniteQueryType.DURATIONS, mockAniteParameters);
		Assert.assertThat(durationsResponse, Matchers.instanceOf(AniteDurationsResponse.class));
	}
}
