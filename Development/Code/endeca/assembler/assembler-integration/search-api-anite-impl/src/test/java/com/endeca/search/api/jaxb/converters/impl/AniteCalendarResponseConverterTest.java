package com.endeca.search.api.jaxb.converters.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.Before;
import org.junit.Test;

import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;
import com.endeca.tui.anite.response.calendar.AvCache;
import com.endeca.tui.anite.response.calendar.AvCache.Result;
import com.endeca.tui.anite.response.calendar.AvCache.Result.Offers;
import com.endeca.tui.anite.response.calendar.AvCache.Result.Offers.Offer;
import com.endeca.tui.anite.response.calendar.YesNo;


public class AniteCalendarResponseConverterTest
{
	private final AniteCalendarResponseConverter aniteCalendarResponseConverter = new AniteCalendarResponseConverter();

	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	final AvCache avCache = new AvCache();

	final Offers offers = new Offers();


	@Before
	public void SetUp() throws ParseException, DatatypeConfigurationException
	{
		final Result result = new Result();
		result.setOffers(offers);
		avCache.setResult(result);
	}

	@Test
	public void getDateAvalibilityWithValidWhenOfferListIsPassedNotEmpty()
			throws ParseException, DatatypeConfigurationException, SearchAPIUnmarshallingException
	{

		final List<YesNo> availability = Arrays.asList(YesNo.Y, YesNo.N, YesNo.Y, YesNo.N, YesNo.Y, YesNo.N, YesNo.Y, YesNo.N,
				YesNo.Y, YesNo.N);
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		final List<String> dates = Arrays.asList("2015-01-01", "2015-01-02", "2015-01-03", "2015-01-04", "2015-01-05", "2015-01-06",
				"2015-01-07", "2015-01-08", "2015-01-09");
		int i = 0;
		while (i < dates.size())
		{
			final Offer offer = new Offer();
			offer.setAvail(availability.get(i));
			final GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(dateFormat.parse(dates.get(i)));
			offer.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
			offers.getOffer().add(offer);
			i++;
		}
		final List<String> dateslist = Arrays.asList("2015-01-01", "2015-01-03", "2015-01-05", "2015-01-07", "2015-01-09");
		final Map<Date, Boolean> convertedMap = aniteCalendarResponseConverter.convertJaxbResponse(avCache);
		assertTrue(convertedMap.containsKey(dateFormat.parse(dateslist.get(0))));
		int j = 0;
		while (j < dateslist.size())
		{
			assertThat(convertedMap.get(dateFormat.parse(dateslist.get(j))), is(true));
			j++;
		}
		assertThat(convertedMap.size(), is(5));
	}

	@Test
	public void getDateAvalibilityWithValidWhenOfferListIsPassedEmpty() throws ParseException, SearchAPIUnmarshallingException
	{
		final Map<Date, Boolean> convertedMap = aniteCalendarResponseConverter.convertJaxbResponse(avCache);
		assertTrue(convertedMap.isEmpty());
	}
}
