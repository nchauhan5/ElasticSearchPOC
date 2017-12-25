package com.endeca.tui.anite.parameters.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.enums.HolidayType;
import com.endeca.tui.anite.enums.PriceType;
import com.endeca.tui.anite.exceptions.AniteRuntimeException;
import com.endeca.tui.anite.parameters.AniteParameters;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;
import com.endeca.tui.anite.parameters.MutableAniteParameters;


/**
 * The Class DefaultAniteParameters.
 */
public class DefaultAniteParameters implements MutableAniteParameters, Cloneable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant PASSTHROUGH_PARAM_FACETING. */
	public static final String PASSTHROUGH_PARAM_FACETING = "f";


	/** The Constant DATE_FORMAT_PATTERN. */
	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

	/**
	 * The Class DateFormatPatterns.
	 */
	protected class DateFormatPatterns extends ThreadLocal<DateFormat>
	{

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.ThreadLocal#initialValue()
		 */
		@Override
		protected DateFormat initialValue()
		{
			return new SimpleDateFormat(getDateFormatPattern());
		}
	}

	/** The date format pattern. */
	protected String dateFormatPattern = DATE_FORMAT_PATTERN;

	/** The date formats. */
	protected transient DateFormatPatterns dateFormats = new DateFormatPatterns();

	/** The accommodations string. */
	protected String accommodationsString;

	/** The departure airports string. */
	protected String departureAirportsString = "";

	/** The durations string. */
	protected String durationsString = "";

	/** The latest departure date string. */
	protected String earliestDepartureDateString, latestDepartureDateString;

	/** The rating. */
	protected String rating;

	/** The adult pax. */
	protected int adultPax = 2;

	/** The room count. */
	protected int roomCount = 0;

	/** The child ages. */
	protected int[] childAges = new int[0];

	/** The passthrough map. */
	protected Map<String, String> passthroughMap = new HashMap<String, String>();

	/** The market. */
	protected String market;

	/** The holiday type. */
	protected HolidayType holidayType;

	/** The price type. */
	protected PriceType priceType;

	/** The promotions. */
	protected String[] promotions;

	/** The promotions string. */
	protected String promotionsString;

	/** The board basis. */
	protected String boardBasis;

	/** The dream liner. */
	protected String dreamLiner;

	/** The Constant Y_STRING. */
	protected static final String Y_STRING = "Y";

	/** The Constant N_STRING. */
	protected static final String N_STRING = "N";

	/** The Constant COMMA. */
	protected static final String COMMA = ",";

	/** The Constant EMPTY_STRING. */
	protected static final String EMPTY_STRING = "";

	/** The candidate durations string. */
	private String candidateDurationsString;

	/** The n string. */
	private String nString;

	private AniteQueryType aniteQueryType;


	/**
	 * Instantiates a new default anite parameters.
	 */
	public DefaultAniteParameters()
	{
	}

	/**
	 * Instantiates a new default anite parameters.
	 *
	 * @param proto
	 *           the proto
	 */
	public DefaultAniteParameters(final AniteRequiredParameters proto)
	{

		if (proto instanceof AniteParameters)
		{
			final AniteParameters p = (AniteParameters) proto;

			dateFormatPattern = p.getDateFormatPattern();
			this.dateFormats = new DateFormatPatterns();
			rating = p.getRating();
		}

		accommodationsString = proto.getAccommodationsString();
		adultPax = proto.getAdultPax();
		childAges = Arrays.copyOf(proto.getChildAges().clone(), childAges.length);
		departureAirportsString = proto.getDepartureAirportsString();
		durationsString = proto.getDurationsString();
		earliestDepartureDateString = proto.getEarliestDepartureDateString();
		latestDepartureDateString = proto.getLatestDepartureDateString();
		roomCount = proto.getRoomCount();

	}


	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof DefaultAniteParameters))
		{
			return false;
		}
		final DefaultAniteParameters o = (DefaultAniteParameters) obj;
		return new EqualsBuilder().append(getDateFormatPattern(), o.getDateFormatPattern())
				.append(getAccommodationsString(), o.getAccommodationsString())
				.append(getDepartureAirportsString(), o.getDepartureAirportsString())
				.append(getDurationsString(), o.getDurationsString())
				.append(getEarliestDepartureDateString(), o.getEarliestDepartureDateString())
				.append(getLatestDepartureDateString(), o.getLatestDepartureDateString()).append(getRating(), o.getRating())
				.append(getPassthroughMap(), o.getPassthroughMap()).append(getAdultPax(), o.getAdultPax())
				.append(getRoomCount(), o.getRoomCount()).append(getChildAges(), o.getChildAges())
				.append(getPriceType(), o.getPriceType()).append(getHolidayType(), o.getHolidayType())
				.append(getPromotionsString(), o.getPromotionsString()).append(getBoardBasis(), o.getBoardBasis())
				.append(getDreamLiner(), o.getDreamLiner()).isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(12367263, 1421).append(getDateFormatPattern()).append(getAccommodationsString())
				.append(getDepartureAirportsString()).append(getDurationsString()).append(getEarliestDepartureDateString())
				.append(getLatestDepartureDateString()).append(getRating()).append(getAdultPax()).append(getRoomCount())
				.append(getChildAges()).append(getPassthroughMap()).append(getHolidayType()).append(getPriceType())
				.append(getPromotionsString()).append(getBoardBasis()).append(getDreamLiner()).toHashCode();
	}



	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public DefaultAniteParameters clone()
	{
		DefaultAniteParameters clone = null;
		try
		{
			clone = (DefaultAniteParameters) super.clone();
		}
		catch (final CloneNotSupportedException e)
		{
			throw new AniteRuntimeException(e);
		}
		clone.dateFormats = new DateFormatPatterns();
		return clone;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getAccommodationsString()
	 */
	@Override
	public String getAccommodationsString()
	{
		return accommodationsString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getAdultPax()
	 */
	@Override
	public int getAdultPax()
	{
		return adultPax;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getChildAges()
	 */
	@Override
	public int[] getChildAges()
	{
		return childAges.clone();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getDepartureAirportsString()
	 */
	@Override
	public String getDepartureAirportsString()
	{
		return departureAirportsString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getDurationsString()
	 */
	@Override
	public String getDurationsString()
	{
		return durationsString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getEarliestDepartureDateString()
	 */
	@Override
	public String getEarliestDepartureDateString()
	{
		if (null == earliestDepartureDateString)
		{
			earliestDepartureDateString = getDateFormat().format(new Date());
		}
		return earliestDepartureDateString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getLatestDepartureDateString()
	 */
	@Override
	public String getLatestDepartureDateString()
	{
		if (null == latestDepartureDateString)
		{
			latestDepartureDateString = getEarliestDepartureDateString();
		}
		return latestDepartureDateString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteParameters#getRating()
	 */
	@Override
	public String getRating()
	{
		return rating;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getRoomCount()
	 */
	@Override
	public int getRoomCount()
	{
		return roomCount;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteParameters#getEarliestDepartureDate()
	 */
	@Override
	public Date getEarliestDepartureDate() throws ParseException
	{
		return getDateFormat().parse(getEarliestDepartureDateString());
	}

	/**
	 * Gets the date format.
	 *
	 * @return the date format
	 */
	public DateFormat getDateFormat()
	{
		return dateFormats.get();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteParameters#getDateFormatPattern()
	 */
	public String getDateFormatPattern()
	{
		return dateFormatPattern;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteParameters#getLatestDepartureDate()
	 */
	@Override
	public Date getLatestDepartureDate() throws ParseException
	{
		return getDateFormat().parse(getLatestDepartureDateString());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getAccommodationsCount()
	 */
	@Override
	public int getAccommodationsCount()
	{
		final String strings = getAccommodationsString();
		if (null == strings)
		{
			return 0;
		}
		else
		{
			return strings.split(",").length;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setDateFormatPattern(java.lang.String)
	 */
	@Override
	public void setDateFormatPattern(final String dateFormatPattern)
	{
		this.dateFormatPattern = dateFormatPattern;
		this.dateFormats = new DateFormatPatterns();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setAccommodationsString(java.lang.String)
	 */
	@Override
	public void setAccommodationsString(final String accommodationsString)
	{
		this.accommodationsString = accommodationsString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setAccommodations(java.lang.String[])
	 */
	@Override
	public void setAccommodations(final String[] accommodations)
	{
		setAccommodationsString(StringUtils.join(accommodations, ','));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setAccommodations(java.util.Collection)
	 */
	@Override
	public void setAccommodations(final Collection<String> accommodations)
	{
		final List<String> sortList = new ArrayList<String>(accommodations);
		Collections.sort(sortList);
		setAccommodationsString(StringUtils.join(sortList, ','));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setAdultPax(int)
	 */
	@Override
	public void setAdultPax(final int adultPax)
	{
		this.adultPax = adultPax;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setChildAges(int[])
	 */
	@Override
	public void setChildAges(final int[] childAges)
	{
		final int[] tmp = Arrays.copyOf(childAges, childAges.length);

		this.childAges = tmp;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setDepartureAirportsString(java.lang.String)
	 */
	@Override
	public void setDepartureAirportsString(final String departureAirportsString)
	{
		this.departureAirportsString = departureAirportsString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setDurationsString(java.lang.String)
	 */
	@Override
	public void setDurationsString(final String durationsString)
	{
		this.durationsString = durationsString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setEarliestDepartureDateString(java.lang.String)
	 */
	@Override
	public void setEarliestDepartureDateString(final String earliestDepartureDateString)
	{
		this.earliestDepartureDateString = earliestDepartureDateString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setLatestDepartureDateString(java.lang.String)
	 */
	@Override
	public void setLatestDepartureDateString(final String latestDepartureDateString)
	{
		this.latestDepartureDateString = latestDepartureDateString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setRating(java.lang.String)
	 */
	@Override
	public void setRating(final String rating)
	{
		this.rating = rating;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setRoomCount(int)
	 */
	@Override
	public void setRoomCount(final int roomCount)
	{
		this.roomCount = roomCount;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(getAdultPax() + " adults");
		final String childAged = " aged ";
		final String childlLengthString = "and " + getChildAges().length + "child";
		if (null != getChildAges())
		{
			sb.append(childlLengthString + (getChildAges().length > 1 ? "ren" : "") + childAged);
			int i = 0;
			for (final int age : getChildAges())
			{
				if (i++ > 0)
				{
					sb.append(", ");
				}
				sb.append(age);
			}
		}
		sb.append(getRoomCount() > 0 ? " in " + getRoomCount() + " room" + (getRoomCount() > 1 ? "s" : "")
				: " in any number of rooms");
		sb.append(" departing between " + getEarliestDepartureDateString() + " and " + getLatestDepartureDateString());
		sb.append(null == getDepartureAirportsString() ? " from any airport" : " from any of (" + getDepartureAirportsString()
				+ ")");
		sb.append(null == getDurationsString() ? " for any duration" : " for " + getDurationsString() + " days");
		sb.append(" in accommodation codes: [" + getAccommodationsString() + "]");
		if (null != getPassthroughMap())
		{
			sb.append(" - passthroughs: " + getPassthroughMap().toString());
		}
		return sb.toString();
	}

	/**
	 * Sets the child age strings.
	 *
	 * @param ageStrings
	 *           the new child age strings
	 */
	public void setChildAgeStrings(final String[] ageStrings)
	{
		if (ageStrings != null)
		{
			final int[] newAges = new int[ageStrings.length];
			for (int i = 0; i < ageStrings.length; i++)
			{
				newAges[i] = Integer.parseInt(ageStrings[i]);
			}

			setChildAges(newAges);
		}
	}

	/**
	 * Sets the durations.
	 *
	 * @param durations
	 *           the new durations
	 */
	public void setDurations(final String[] durations)
	{
		setDurationsString(StringUtils.join(durations, ','));
	}

	/**
	 * Sets the departure airports string.
	 *
	 * @param airports
	 *           the new departure airports string
	 */
	public void setDepartureAirportsString(final String[] airports)
	{
		setDepartureAirportsString(StringUtils.join(airports, ','));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteParameters#getPassthroughMap()
	 */
	@Override
	public Map<String, String> getPassthroughMap()
	{
		return passthroughMap;
	}

	/**
	 * Sets the passthrough map.
	 *
	 * @param passthroughMap
	 *           the passthrough map
	 */
	public void setPassthroughMap(final Map<String, String> passthroughMap)
	{
		this.passthroughMap = passthroughMap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteParameters#isFaceting()
	 */
	@Override
	public boolean isFaceting()
	{
		return StringUtils.equals(getPassthroughMap().get(PASSTHROUGH_PARAM_FACETING), "Y");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setFaceting(boolean)
	 */
	@Override
	public void setFaceting(final boolean facetingActive)
	{
		getPassthroughMap().put(PASSTHROUGH_PARAM_FACETING, facetingActive ? "Y" : "N");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getPromotionsString()
	 */
	@Override
	public synchronized String getPromotionsString()
	{
		if (null == promotionsString)
		{
			promotionsString = StringUtils.join(promotions, ',');

		}
		return promotionsString;
	}

	/**
	 * Gets the promotions.
	 *
	 * @return the promotions
	 */
	public synchronized String[] getPromotions()
	{
		if (null == promotions)
		{
			promotions = StringUtils.split(promotionsString, ',');
		}
		return promotions.clone();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getPromotionsCount()
	 */
	@Override
	public int getPromotionsCount()
	{
		return getPromotions().length;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setPromotionsString(java.lang.String)
	 */
	@Override
	public synchronized void setPromotionsString(final String promotionsString)
	{
		this.promotionsString = promotionsString;
		this.promotions = null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setPromotions(java.lang.String[])
	 */
	@Override
	public synchronized void setPromotions(final String... promotionStrings)
	{
		this.promotions = promotionStrings;
		this.promotionsString = null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getMarket()
	 */
	public String getMarket()
	{
		return market;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setMarket(java.lang.String)
	 */
	public void setMarket(final String market)
	{
		this.market = market;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getHolidayType()
	 */
	public HolidayType getHolidayType()
	{
		return holidayType;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setHolidayType(com.endeca.tui.anite.HolidayType)
	 */
	public void setHolidayType(final HolidayType holidayType)
	{
		this.holidayType = holidayType;
	}

	/**
	 * Sets the holiday type string.
	 *
	 * @param holidayType
	 *           the new holiday type string
	 */
	public void setHolidayTypeString(final String holidayType)
	{
		setHolidayType(HolidayType.valueOf(holidayType));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteRequiredParameters#getPriceType()
	 */
	public PriceType getPriceType()
	{
		return priceType;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setPriceType(com.endeca.tui.anite.PriceType)
	 */
	public void setPriceType(final PriceType priceType)
	{
		this.priceType = priceType;
	}

	/**
	 * Sets the price type string.
	 *
	 * @param priceType
	 *           the new price type string
	 */
	public void setPriceTypeString(final String priceType)
	{
		setPriceType(PriceType.valueOf(priceType));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteParameters#getBoardBasis()
	 */
	public String getBoardBasis()
	{
		return boardBasis;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setBoardBasis(java.lang.String)
	 */
	public void setBoardBasis(final String boardBasis)
	{
		this.boardBasis = boardBasis;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteParameters#getDreamLiner()
	 */
	public String getDreamLiner()
	{
		return "true".equalsIgnoreCase(dreamLiner) ? Y_STRING : EMPTY_STRING;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setDreamLiner(java.lang.String)
	 */
	public void setDreamLiner(final String dreamLiner)
	{
		this.dreamLiner = dreamLiner;
	}

	/**
	 * Gets the candidate durations string.
	 *
	 * @return the candidate durations string
	 */
	public String getCandidateDurationsString()
	{
		return candidateDurationsString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setCandidateDurationsString(java.lang.String)
	 */
	public void setCandidateDurationsString(final String candidateDurationsString)
	{
		this.candidateDurationsString = candidateDurationsString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.AniteParameters#getnString()
	 */
	public String getnString()
	{
		return nString;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.tui.anite.MutableAniteParameters#setnString(java.lang.String)
	 */
	public void setnString(final String nString)
	{
		this.nString = nString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.parameters.AniteRequiredParameters#getAniteQueryType()
	 */
	@Override
	public AniteQueryType getAniteQueryType()
	{
		return this.aniteQueryType;
	}

	/**
	 * @param aniteQueryType
	 *           the aniteQueryType to set
	 */
	public void setAniteQueryType(final AniteQueryType aniteQueryType)
	{
		this.aniteQueryType = aniteQueryType;
	}

}
