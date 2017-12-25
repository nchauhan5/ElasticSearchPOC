package com.endeca.tui.anite.parameters.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.endeca.tui.anite.enums.AniteJoinPolicy;
import com.endeca.tui.anite.enums.TimeUnits;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;
import com.endeca.tui.anite.parameters.HolidayParameters;


/**
 * The Class DefaultHolidayParameters.
 */
public class DefaultHolidayParameters extends DefaultAniteParameters implements HolidayParameters
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant NUMBER_AND_MULTIPLIER. */
	protected static final Pattern NUMBER_AND_MULTIPLIER = Pattern.compile("^\\s*(\\d+)\\s*(\\w*)\\s*$");

	/** The anite join policy. */
	protected AniteJoinPolicy aniteJoinPolicy;

	/** The auto selected duration. */
	private String autoSelectedDuration;

	/**
	 * Instantiates a new default holiday parameters.
	 */
	public DefaultHolidayParameters()
	{
		super();
	}

	/**
	 * Instantiates a new default holiday parameters.
	 * 
	 * @param proto
	 *           the proto
	 */
	public DefaultHolidayParameters(final AniteRequiredParameters proto)
	{
		super(proto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.DefaultAniteParameters#clone()
	 */
	@Override
	public DefaultHolidayParameters clone()
	{
		final DefaultHolidayParameters clone = (DefaultHolidayParameters) super.clone();
		clone.setAniteJoinPolicy(getAniteJoinPolicy());
		return clone;
	}

	/**
	 * Sets the dates by start date and date range length.
	 * 
	 * @param startDate
	 *           the start date
	 * @param length
	 *           the length
	 */
	public void setDatesByStartDateAndDateRangeLength(final Date startDate, final int length)
	{
		setEarliestDepartureDate(startDate);
		final Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.DATE, length);
		setLatestDepartureDate(c.getTime());
	}

	/**
	 * Sets the dates by start date and date range length.
	 * 
	 * @param startDate
	 *           the start date
	 * @param length
	 *           the length
	 * @throws ParseException
	 *            the parse exception
	 */
	public void setDatesByStartDateAndDateRangeLength(final String startDate, final int length) throws ParseException
	{
		setDatesByStartDateAndDateRangeLength(getDateFormat().parse(startDate), length);
	}

	/**
	 * Sets the dates by days from today.
	 * 
	 * @param daysFromToday
	 *           the new dates by days from today
	 */
	public void setDatesByDaysFromToday(final int daysFromToday)
	{
		setDatesByStartDateAndDateRangeLength(new Date(), daysFromToday);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.request.data.HolidayParameters#ignoreAnite()
	 */
	@Override
	public boolean ignoreAnite()
	{
		return aniteJoinPolicy == AniteJoinPolicy.NONE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.request.data.HolidayParameters#getAniteJoinPolicy()
	 */
	public AniteJoinPolicy getAniteJoinPolicy()
	{
		return aniteJoinPolicy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.request.data.HolidayParameters#setAniteJoinPolicy(com.endeca.tui.anite.AniteJoinPolicy)
	 */
	public void setAniteJoinPolicy(final AniteJoinPolicy aniteJoinPolicy)
	{
		this.aniteJoinPolicy = aniteJoinPolicy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.request.data.HolidayParameters#setEarliestDepartureDate(java.util.Date)
	 */
	public void setEarliestDepartureDate(final Date date)
	{
		setEarliestDepartureDateString(getDateFormat().format(date));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.request.data.HolidayParameters#setLatestDepartureDate(java.util.Date)
	 */
	public void setLatestDepartureDate(final Date date)
	{
		setLatestDepartureDateString(getDateFormat().format(date));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.request.data.HolidayParameters#setDatesByDaysFromToday(java.lang.String)
	 */
	public void setDatesByDaysFromToday(final String daysFromToday)
	{
		final Matcher matcher = NUMBER_AND_MULTIPLIER.matcher(daysFromToday);
		if (matcher.matches())
		{
			int number = Integer.parseInt(matcher.group(1));
			final String unitsString = matcher.group(2);
			if (StringUtils.isNotBlank(unitsString))
			{
				final TimeUnits units = TimeUnits.valueOf(unitsString.toUpperCase().substring(0, 1));
				number *= units.getMultiplier();
			}
			setDatesByDaysFromToday(number);
		}
		else
		{
			throw new IllegalArgumentException("Invalid date range specification (should match \\d+\\w?): " + daysFromToday);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.request.data.HolidayParameters#setAutoSelectedDuration(int)
	 */
	@Override
	public void setAutoSelectedDuration(final String selectedAvailableDuration)
	{
		autoSelectedDuration = selectedAvailableDuration;
		setDurations(new String[]{ autoSelectedDuration });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.request.data.HolidayParameters#getAutoSelectedDuration()
	 */
	@Override
	public String getAutoSelectedDuration()
	{
		return autoSelectedDuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.DefaultAniteParameters#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (null == obj)
		{
			return false;
		}
		if (!(obj instanceof DefaultHolidayParameters))
		{
			return false;
		}
		final DefaultHolidayParameters other = (DefaultHolidayParameters) obj;
		return new EqualsBuilder().appendSuper(super.equals(other)).append(aniteJoinPolicy, other.aniteJoinPolicy)
				.append(autoSelectedDuration, other.autoSelectedDuration).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.DefaultAniteParameters#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(3749517, 986334561).appendSuper(super.hashCode()).append(aniteJoinPolicy)
				.append(autoSelectedDuration).toHashCode();
	}
}
