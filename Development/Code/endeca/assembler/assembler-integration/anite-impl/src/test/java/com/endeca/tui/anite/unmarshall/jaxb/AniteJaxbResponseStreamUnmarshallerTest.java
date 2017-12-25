package com.endeca.tui.anite.unmarshall.jaxb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteUnmarshallingException;
import com.endeca.tui.anite.responses.AniteCalendarResponse;
import com.endeca.tui.anite.responses.AniteCalendarResponse.AniteCalendarResponseDateAndAvailability;
import com.endeca.tui.anite.responses.AniteDurationsResponse;
import com.endeca.tui.anite.unmarshall.impl.AniteJaxbResponseStreamUnmarshaller;


/**
 * Test class for {@link AniteJaxbResponseStreamUnmarshaller}.
 */
public class AniteJaxbResponseStreamUnmarshallerTest
{

	/** The Constant DATE_FORMAT_PATTERN. */
	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

	/**
	 * Test to assert calendar response is parsed correctly.
	 * 
	 * @throws AniteUnmarshallingException
	 *            the anite unmarshalling exception
	 * @throws JAXBException
	 *            the JAXB exception
	 * @throws ParseException
	 *            the parse exception
	 */
	@Test
	public void verifyCalendarResponseParsedCorrectly() throws AniteUnmarshallingException, JAXBException, ParseException
	{
		final AniteJaxbResponseStreamUnmarshaller u = new AniteJaxbResponseStreamUnmarshaller(DATE_FORMAT_PATTERN);
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		final AniteCalendarResponse aniteCalendarResponse = u.unmarshall(AniteQueryType.CALENDAR,
				cl.getResourceAsStream("aniteResponses/aniteCalendarResponse.xml"));
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		final List<String> dateslist = Arrays.asList("2015-03-01", "2015-03-02", "2015-03-03", "2015-03-04", "2015-03-05");
		final Map<Date, Boolean> responseMap = new HashMap<Date, Boolean>();
		for (final AniteCalendarResponseDateAndAvailability dateAndAvailability : aniteCalendarResponse
				.getDateAndAvailabilityList())
		{
			responseMap.put(dateAndAvailability.getDate().getTime(), dateAndAvailability.isAvailable());
		}
		Assert.assertTrue(responseMap.get(dateFormat.parseObject(dateslist.get(0))).equals(true));
		Assert.assertTrue(responseMap.get(dateFormat.parseObject(dateslist.get(1))).equals(false));
		Assert.assertTrue(responseMap.get(dateFormat.parseObject(dateslist.get(2))).equals(true));
		Assert.assertTrue(responseMap.get(dateFormat.parseObject(dateslist.get(3))).equals(false));
		Assert.assertTrue(responseMap.get(dateFormat.parseObject(dateslist.get(4))).equals(false));

	}

	/**
	 * Test durations response is parsed correctly.
	 * 
	 * @throws AniteUnmarshallingException
	 *            the anite unmarshalling exception
	 * @throws JAXBException
	 *            the JAXB exception
	 */
	@Test
	public void verifyDurationResponseParsedCorrectly() throws AniteUnmarshallingException, JAXBException
	{
		final AniteJaxbResponseStreamUnmarshaller u = new AniteJaxbResponseStreamUnmarshaller(DATE_FORMAT_PATTERN);
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		final AniteDurationsResponse durations = u.unmarshall(AniteQueryType.DURATIONS,
				cl.getResourceAsStream("aniteResponses/aniteDurationsResponse.xml"));
		Assert.assertTrue(CollectionUtils.isEqualCollection(durations.getStays(),
				Arrays.asList(Short.valueOf("8"), Short.valueOf("15"), Short.valueOf("22"))));
	}

	/**
	 * Verify unmarshalling exception for null implementation class.
	 * 
	 * @throws AniteUnmarshallingException
	 */
	@Test(expected = AniteUnmarshallingException.class)
	public void nullImplementationClassUnmarshallingException() throws AniteUnmarshallingException
	{
		final AniteJaxbResponseStreamUnmarshaller u = new AniteJaxbResponseStreamUnmarshaller(DATE_FORMAT_PATTERN);
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		u.unmarshall(AniteQueryType.LIST_BY_ACCOMMODATION, cl.getResourceAsStream("aniteResponses/aniteDurationsResponse.xml"));
	}

}
