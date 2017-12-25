package com.endeca.tui.anite.response.impl.jaxb;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.collections.list.UnmodifiableList;

import com.endeca.tui.anite.BackedObject;
import com.endeca.tui.anite.response.calendar.AvCache;
import com.endeca.tui.anite.response.calendar.AvCache.Result.Offers.Offer;
import com.endeca.tui.anite.response.calendar.YesNo;
import com.endeca.tui.anite.responses.AniteCalendarResponse;


// TODO: Auto-generated Javadoc
/**
 * The Class JaxbCalendarResponse.
 */
public class JaxbCalendarResponse implements AniteCalendarResponse, BackedObject<AvCache>
{

	/**
	 * The Class JaxbAniteResponseDateAndAvailability.
	 */
	public static class JaxbAniteResponseDateAndAvailability implements AniteCalendarResponseDateAndAvailability
	{

		/** The available. */
		protected boolean available;

		/** The date. */
		protected GregorianCalendar date;

		/**
		 * Instantiates a new jaxb anite response date and availability.
		 * 
		 * @param date
		 *           the date
		 * @param available
		 *           the available
		 */
		public JaxbAniteResponseDateAndAvailability(final GregorianCalendar date, final boolean available)
		{
			super();
			this.available = available;
			this.date = date;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.endeca.tui.anite.AniteCalendarResponse.AniteCalendarResponseDateAndAvailability#getDate()
		 */
		@Override
		public GregorianCalendar getDate()
		{
			return date;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.endeca.tui.anite.AniteCalendarResponse.AniteCalendarResponseDateAndAvailability#isAvailable()
		 */
		@Override
		public boolean isAvailable()
		{
			return available;
		}

		/**
		 * Sets the available.
		 * 
		 * @param available
		 *           the new available
		 */
		public void setAvailable(final boolean available)
		{
			this.available = available;
		}
	}

	/** The response. */
	protected final AvCache response;

	/** The date and availability list. */
	protected List<AniteCalendarResponseDateAndAvailability> dateAndAvailabilityList;

	/** The date and availability list lock. */
	private final Object dateAndAvailabilityListLock = new Object();

	/**
	 * Instantiates a new jaxb calendar response.
	 * 
	 * @param response
	 *           the response
	 */
	public JaxbCalendarResponse(final AvCache response)
	{
		this.response = response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteCalendarResponse#getDateAndAvailabilityList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AniteCalendarResponseDateAndAvailability> getDateAndAvailabilityList()
	{
		synchronized (dateAndAvailabilityListLock)
		{
			if (null == dateAndAvailabilityList)
			{
				final List<Offer> offersList = response.getResult().getOffers().getOffer();
				dateAndAvailabilityList = new ArrayList<AniteCalendarResponse.AniteCalendarResponseDateAndAvailability>(
						offersList.size());
				for (final Offer offer : offersList)
				{
					dateAndAvailabilityList.add(new JaxbAniteResponseDateAndAvailability(offer.getDate().toGregorianCalendar(),
							YesNo.Y == offer.getAvail()));
				}
				dateAndAvailabilityList = UnmodifiableList.decorate(dateAndAvailabilityList);
			}
		}

		return dateAndAvailabilityList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteCalendarResponse#getResponseVersion()
	 */
	@Override
	public String getResponseVersion()
	{
		return response.getVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.BackedObject#getBackingObject()
	 */
	@Override
	public AvCache getBackingObject()
	{
		return this.response;
	}
}
