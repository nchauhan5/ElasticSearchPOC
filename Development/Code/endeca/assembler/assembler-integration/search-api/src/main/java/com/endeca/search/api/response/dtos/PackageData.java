package com.endeca.search.api.response.dtos;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * The Class PackageData. Main response DTO representing a single package returned by third party provider. This
 * contains further sub dto to store segregated information.
 *
 * @see OfferData
 * @see HotelData
 * @see RatingReviewsData
 * @see FlightData
 * @see RoomData
 * @see BoardData
 * @see DurationCalendarData
 */
public class PackageData
{

	/** The offer data. */
	private OfferData offerData;

	/** The hotel data. */
	private HotelData hotelData;

	/** The rating reviews data. */
	private List<RatingReviewsData> ratingReviewsData;

	/** The outbound flight. */
	private List<FlightData> outboundFlight;

	/** The inbound flight. */
	private List<FlightData> inboundFlight;

	private String bookingFlightClass;

	/** The booking classes. */
	private List<BookingClass> bookingClasses;

	/** The rooms data. */
	private List<RoomData> roomsData;

	/** The duration calendar data. */
	private DurationCalendarData durationCalendarData;

	/** The discount data. */
	private TotalPriceData totalPriceData;

	/** The out bound extra flight details. */
	private ExtraFlightDetails outBoundExtraFlightDetails;

	/** The inbound extra flight details. */
	private ExtraFlightDetails inboundExtraFlightDetails;

	/**
	 * Gets the offer data.
	 *
	 * @return the offer data
	 */
	public OfferData getOfferData()
	{
		return offerData;
	}

	/**
	 * Sets the offer data.
	 *
	 * @param offerData
	 *           the new offer data
	 */
	public void setOfferData(final OfferData offerData)
	{
		this.offerData = offerData;
	}

	/**
	 * Gets the hotel data.
	 *
	 * @return the hotel data
	 */
	public HotelData getHotelData()
	{
		return hotelData;
	}

	/**
	 * Sets the hotel data.
	 *
	 * @param hotelData
	 *           the new hotel data
	 */
	public void setHotelData(final HotelData hotelData)
	{
		this.hotelData = hotelData;
	}

	/**
	 * Gets the rating reviews data.
	 *
	 * @return the rating reviews data
	 */
	public List<RatingReviewsData> getRatingReviewsData()
	{
		return ratingReviewsData;
	}

	/**
	 * Sets the rating reviews data.
	 *
	 * @param ratingReviewsData
	 *           the new rating reviews data
	 */
	public void setRatingReviewsData(final List<RatingReviewsData> ratingReviewsData)
	{
		this.ratingReviewsData = ratingReviewsData;
	}



	/**
	 * Gets the inbound flight.
	 *
	 * @return the inbound flight
	 */
	public List<FlightData> getInboundFlight()
	{
		return inboundFlight;
	}

	/**
	 * Sets the inbound flight.
	 *
	 * @param inboundFlight
	 *           the new inbound flight
	 */
	public void setInboundFlight(final List<FlightData> inboundFlight)
	{
		this.inboundFlight = inboundFlight;
	}

	/**
	 * Gets the outbound flight.
	 *
	 * @return the outbound flight
	 */
	public List<FlightData> getOutboundFlight()
	{
		return outboundFlight;
	}

	/**
	 * Sets the outbound flight.
	 *
	 * @param outboundFlight
	 *           the new outbound flight
	 */
	public void setOutboundFlight(final List<FlightData> outboundFlight)
	{
		this.outboundFlight = outboundFlight;
	}

	/**
	 * Gets the rooms data.
	 *
	 * @return the rooms data
	 */
	public List<RoomData> getRoomsData()
	{
		return roomsData;
	}

	/**
	 * Sets the rooms data.
	 *
	 * @param roomsData
	 *           the new rooms data
	 */
	public void setRoomsData(final List<RoomData> roomsData)
	{
		this.roomsData = roomsData;
	}

	/**
	 * Gets the duration calendar data.
	 *
	 * @return the duration calendar data
	 */
	public DurationCalendarData getDurationCalendarData()
	{
		return durationCalendarData;
	}

	/**
	 * Sets the duration calendar data.
	 *
	 * @param durationCalendarData
	 *           the new duration calendar data
	 */
	public void setDurationCalendarData(final DurationCalendarData durationCalendarData)
	{
		this.durationCalendarData = durationCalendarData;
	}

	/**
	 * Gets the total price data.
	 *
	 * @return the total price data
	 */
	public TotalPriceData getTotalPriceData()
	{
		return totalPriceData;
	}

	/**
	 * Sets the total price data.
	 *
	 * @param totalPriceData
	 *           the new total price data
	 */
	public void setTotalPriceData(final TotalPriceData totalPriceData)
	{
		this.totalPriceData = totalPriceData;
	}

	/**
	 * Gets the out bound extra flight details.
	 *
	 * @return the out bound extra flight details
	 */
	public ExtraFlightDetails getOutBoundExtraFlightDetails()
	{
		return outBoundExtraFlightDetails;
	}

	/**
	 * Sets the out bound extra flight details.
	 *
	 * @param outBoundExtraFlightDetails
	 *           the new out bound extra flight details
	 */
	public void setOutBoundExtraFlightDetails(final ExtraFlightDetails outBoundExtraFlightDetails)
	{
		this.outBoundExtraFlightDetails = outBoundExtraFlightDetails;
	}

	/**
	 * Gets the inbound extra flight details.
	 *
	 * @return the inbound extra flight details
	 */
	public ExtraFlightDetails getInboundExtraFlightDetails()
	{
		return inboundExtraFlightDetails;
	}

	/**
	 * Sets the inbound extra flight details.
	 *
	 * @param inboundExtraFlightDetails
	 *           the new inbound extra flight details
	 */
	public void setInboundExtraFlightDetails(final ExtraFlightDetails inboundExtraFlightDetails)
	{
		this.inboundExtraFlightDetails = inboundExtraFlightDetails;
	}

	/**
	 * Gets the booking flight class.
	 *
	 * @return the booking flight class
	 */
	public String getBookingFlightClass()
	{
		return bookingFlightClass;
	}

	/**
	 * Sets the booking flight class.
	 *
	 * @param bookingFlightClass
	 *           the new booking flight class
	 */
	public void setBookingFlightClass(final String bookingFlightClass)
	{
		this.bookingFlightClass = bookingFlightClass;
	}

	/**
	 * Gets the booking classes.
	 *
	 * @return the booking classes
	 */
	public List<BookingClass> getBookingClasses()
	{
		if (null == this.bookingClasses)
		{
			this.bookingClasses = new ArrayList<BookingClass>();
		}
		return bookingClasses;
	}

	/**
	 * Sets the booking classes.
	 *
	 * @param bookingClasses
	 *           the new booking classes
	 */
	public void setBookingClasses(final List<BookingClass> bookingClasses)
	{
		this.bookingClasses = bookingClasses;
	}

	@Override
	public boolean equals(final Object o)
	{
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
