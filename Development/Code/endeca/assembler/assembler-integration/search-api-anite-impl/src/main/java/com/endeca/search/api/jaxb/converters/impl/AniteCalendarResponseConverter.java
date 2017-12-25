package com.endeca.search.api.jaxb.converters.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;
import com.endeca.search.api.jaxb.converters.CalendarResponseConverter;
import com.endeca.tui.anite.response.calendar.AvCache;
import com.endeca.tui.anite.response.calendar.AvCache.Result;
import com.endeca.tui.anite.response.calendar.AvCache.Result.Offers;
import com.endeca.tui.anite.response.calendar.AvCache.Result.Offers.Offer;
import com.endeca.tui.anite.response.calendar.YesNo;


/**
 * The Class AniteCalendarResponseConverter this is the anite specific implementation of
 * {@link CalendarResponseConverter} used to convert anite specific jaxb response to calendar data.
 */
public class AniteCalendarResponseConverter implements CalendarResponseConverter
{

	@Override
	public Map<Date, Boolean> convertJaxbResponse(final Object jaxbElement) throws SearchAPIUnmarshallingException
	{
		final Map<Date, Boolean> dateAvailability = new HashMap<Date, Boolean>();
		final AvCache avCache = (AvCache) jaxbElement;

		if (null != avCache.getError())
		{
			final AvCache.Error aniteError = avCache.getError();
			throw new SearchAPIUnmarshallingException(
					" Received error code : " + aniteError.getCode() + " with text : " + aniteError.getText(), new Throwable());
		}

		else
		{
			final Result result = avCache.getResult();
			final Offers offers = result.getOffers();
			for (final Offer offer : offers.getOffer())
			{
				if (offer.getAvail().equals(YesNo.Y))
				{
					dateAvailability.put(offer.getDate().toGregorianCalendar().getTime(),
							offer.getAvail().equals(YesNo.Y) ? true : false);
				}
			}
			return dateAvailability;
		}
	}
}
