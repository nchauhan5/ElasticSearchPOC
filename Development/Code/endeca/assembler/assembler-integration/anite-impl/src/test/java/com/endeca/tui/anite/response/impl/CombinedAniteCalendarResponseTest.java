package com.endeca.tui.anite.response.impl;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.Test;

import com.endeca.tui.anite.response.calendar.AvCache;
import com.endeca.tui.anite.response.calendar.AvCache.Result;
import com.endeca.tui.anite.response.calendar.AvCache.Result.Offers;
import com.endeca.tui.anite.response.calendar.AvCache.Result.Offers.Offer;
import com.endeca.tui.anite.response.calendar.YesNo;
import com.endeca.tui.anite.response.impl.jaxb.JaxbCalendarResponse;
import com.endeca.tui.anite.responses.AniteCalendarResponse;
import com.endeca.tui.anite.responses.AniteCalendarResponse.AniteCalendarResponseDateAndAvailability;


/**
 * Test class for {@link CombinedAniteCalendarResponse}.
 * 
 */
public class CombinedAniteCalendarResponseTest
{

	/**
	 * Test case Responses joined correctly.
	 * 
	 * @throws ParseException
	 * @throws DatatypeConfigurationException
	 */
	@Test
	public void responsesJoinedCorrectly() throws DatatypeConfigurationException, ParseException
	{
		final List<Object> responses = createTestData();
		final CombinedAniteCalendarResponse combinedAniteCalendarResponse = new CombinedAniteCalendarResponse(responses);

		final AniteCalendarResponseDateAndAvailability[] datesAndAvailablity1 = ((AniteCalendarResponse) responses.get(0))
				.getDateAndAvailabilityList().toArray(new AniteCalendarResponseDateAndAvailability[5]);

		final AniteCalendarResponseDateAndAvailability[] datesAndAvailablity2 = ((AniteCalendarResponse) responses.get(1))
				.getDateAndAvailabilityList().toArray(new AniteCalendarResponseDateAndAvailability[5]);

		assertThat(combinedAniteCalendarResponse.getDateAndAvailabilityList().size(), is(10));
		assertThat(combinedAniteCalendarResponse.getDateAndAvailabilityList(), hasItems(datesAndAvailablity1));
		assertThat(combinedAniteCalendarResponse.getDateAndAvailabilityList(), hasItems(datesAndAvailablity2));
	}

	/**
	 * Creates the test data.
	 * 
	 * @return the list
	 * @throws DatatypeConfigurationException
	 * @throws ParseException
	 */
	private List<Object> createTestData() throws DatatypeConfigurationException, ParseException
	{
		final List<YesNo> availability = Arrays.asList(YesNo.Y, YesNo.N, YesNo.Y, YesNo.N, YesNo.Y, YesNo.N, YesNo.Y, YesNo.N,
				YesNo.Y, YesNo.N);
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		final List<String> dates = Arrays.asList("2015-01-01", "2015-01-02", "2015-01-03", "2015-01-04", "2015-01-05",
				"2015-01-06", "2015-01-07", "2015-01-08", "2015-01-09", "2015-01-10");
		final Offers offers = new Offers();
		int i = 0;
		while (i < 5)
		{
			final Offer offer = new Offer();
			offer.setAvail(availability.get(i));
			final GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(dateFormat.parse(dates.get(i)));
			offer.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
			offers.getOffer().add(offer);
			i++;
		}
		final Result result = new Result();
		result.setOffers(offers);
		final AvCache avCache = new AvCache();
		avCache.setResult(result);

		final Offers offers2 = new Offers();
		while (i < 10)
		{
			final Offer offer = new Offer();
			offer.setAvail(availability.get(i));
			final GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(dateFormat.parse(dates.get(i)));
			offer.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
			offers2.getOffer().add(offer);
			i++;
		}
		final Result result2 = new Result();
		result2.setOffers(offers2);
		final AvCache avCache2 = new AvCache();
		avCache2.setResult(result2);
		return Arrays.asList((Object) new JaxbCalendarResponse(avCache), (Object) new JaxbCalendarResponse(avCache2));
	}
}
