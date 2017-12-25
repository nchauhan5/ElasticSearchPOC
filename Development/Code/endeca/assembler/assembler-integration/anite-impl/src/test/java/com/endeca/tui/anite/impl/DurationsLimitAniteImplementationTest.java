/**
 * 
 */
package com.endeca.tui.anite.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteException;
import com.endeca.tui.anite.parameters.impl.DefaultAniteParameters;
import com.endeca.tui.anite.response.calendar.AvCache;
import com.endeca.tui.anite.responses.AniteCalendarResponse;
import com.endeca.tui.anite.responses.AniteDurationsResponse;
import com.endeca.tui.anite.responses.AniteListByAccommodationResponse;


/**
 * Test class for {@link DurationsLimitAniteImplementation}.
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class DurationsLimitAniteImplementationTest
{

	/** The default anite implementation. */
	@Mock
	private DefaultAniteImplementation defaultAniteImplementation;

	/** The durations limit anite implementation. */
	@InjectMocks
	private final DurationsLimitAniteImplementation durationsLimitAniteImplementation = new DurationsLimitAniteImplementation();

	/**
	 * Test for Empty durations default implementation is invoked.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void emptyDurationsDefaultImplementationInvoked() throws AniteException
	{
		final DefaultAniteParameters defaultAniteParameters = new DefaultAniteParameters();
		defaultAniteParameters.setDurationsString(StringUtils.EMPTY);
		durationsLimitAniteImplementation.query(AniteQueryType.CALENDAR, defaultAniteParameters);
		verify(defaultAniteImplementation).query(AniteQueryType.CALENDAR, defaultAniteParameters);
	}

	/**
	 * Test for Non empty less than max duration default implementation invoked.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void lessThanMaxDurationDefaultImplementationInvoked() throws AniteException
	{
		final DefaultAniteParameters defaultAniteParameters = new DefaultAniteParameters();
		defaultAniteParameters.setDurationsString("1,2,3,4,5,6,7");
		durationsLimitAniteImplementation.query(AniteQueryType.CALENDAR, defaultAniteParameters);
		verify(defaultAniteImplementation).query(AniteQueryType.CALENDAR, defaultAniteParameters);
		verify(defaultAniteImplementation, times(1)).query(AniteQueryType.CALENDAR, defaultAniteParameters);
	}

	/**
	 * Test for Non empty less than max duration default implementation invoked.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void greaterThanMaxDurationDefaultImplementationInvokedNTimes() throws AniteException
	{
		final DefaultAniteParameters defaultAniteParameters = new DefaultAniteParameters();
		final String durationsString = "1,2,3,4,5,6,7,8,9,10";
		defaultAniteParameters.setDurationsString(durationsString);

		final AvCache avCache = new AvCache();
		avCache.setVersion("1.1");
		final AniteCalendarResponse aniteCalendarResponse = Mockito.mock(AniteCalendarResponse.class);
		Mockito.when(
				defaultAniteImplementation.query(Matchers.eq(AniteQueryType.CALENDAR), Matchers.any(DefaultAniteParameters.class)))
				.thenReturn(aniteCalendarResponse);

		durationsLimitAniteImplementation.query(AniteQueryType.CALENDAR, defaultAniteParameters);
		verify(defaultAniteImplementation, times((StringUtils.split(durationsString, ",").length / 7) + 1)).query(
				Mockito.eq(AniteQueryType.CALENDAR), Matchers.any(DefaultAniteParameters.class));
	}

	/**
	 * Test for case non empty Greater than max duration in which default implementation should invoke n(sub arrays by
	 * max duration) times with correct params.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void greaterThanMaxDurationDefaultImplementationInvokedNTimesCorrectParams() throws AniteException
	{
		final DefaultAniteParameters defaultAniteParameters = new DefaultAniteParameters();
		final String durationsString = "1,2,3,4,5,6,7,8,9,10";
		defaultAniteParameters.setDurationsString(durationsString);

		final AvCache avCache = new AvCache();
		avCache.setVersion("1.1");
		final AniteCalendarResponse aniteCalendarResponse = Mockito.mock(AniteCalendarResponse.class);
		Mockito.when(
				defaultAniteImplementation.query(Matchers.eq(AniteQueryType.CALENDAR), Matchers.any(DefaultAniteParameters.class)))
				.thenReturn(aniteCalendarResponse);

		durationsLimitAniteImplementation.query(AniteQueryType.CALENDAR, defaultAniteParameters);
		verify(defaultAniteImplementation, times((StringUtils.split(durationsString, ",").length / 7) + 1)).query(
				Mockito.eq(AniteQueryType.CALENDAR), Matchers.any(DefaultAniteParameters.class));
		final String[] durations = durationsString.split(",");

		for (int i = 0; i < durations.length; i += 7)
		{
			final int last = Math.min(i + 7, durations.length);
			final DefaultAniteParameters subQueryParams = new DefaultAniteParameters(defaultAniteParameters);
			final String[] subQueryDurations = Arrays.copyOfRange(durations, i, last);
			subQueryParams.setDurations(subQueryDurations);
			verify(defaultAniteImplementation).query(AniteQueryType.CALENDAR, subQueryParams);
		}
	}

	/**
	 * Test for durations query case non empty Greater than max duration in which default implementation should invoke
	 * n(sub arrays by max duration) times with correct params.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void durationsQueryGreaterThanMaxDurationDefaultImplementationInvokedNTimesCorrectParams() throws AniteException
	{
		final DefaultAniteParameters defaultAniteParameters = new DefaultAniteParameters();
		final String durationsString = "1,2,3,4,5,6,7,8,9,10";
		defaultAniteParameters.setDurationsString(durationsString);

		final AvCache avCache = new AvCache();
		avCache.setVersion("1.1");
		final AniteDurationsResponse aniteDurationsResponse = Mockito.mock(AniteDurationsResponse.class);
		Mockito.when(
				defaultAniteImplementation.query(Matchers.eq(AniteQueryType.DURATIONS), Matchers.any(DefaultAniteParameters.class)))
				.thenReturn(aniteDurationsResponse);

		durationsLimitAniteImplementation.query(AniteQueryType.DURATIONS, defaultAniteParameters);
		verify(defaultAniteImplementation, times((StringUtils.split(durationsString, ",").length / 7) + 1)).query(
				Mockito.eq(AniteQueryType.DURATIONS), Matchers.any(DefaultAniteParameters.class));
		final String[] durations = durationsString.split(",");

		for (int i = 0; i < durations.length; i += 7)
		{
			final int last = Math.min(i + 7, durations.length);
			final DefaultAniteParameters subQueryParams = new DefaultAniteParameters(defaultAniteParameters);
			final String[] subQueryDurations = Arrays.copyOfRange(durations, i, last);
			subQueryParams.setDurations(subQueryDurations);
			verify(defaultAniteImplementation).query(AniteQueryType.DURATIONS, subQueryParams);
		}
	}

	/**
	 * Test for package query case non empty Greater than max duration in which default implementation should invoke
	 * n(sub arrays by max duration) times with correct params.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void packageQueryGreaterThanMaxDurationDefaultImplementationInvokedNTimesCorrectParams() throws AniteException
	{
		final DefaultAniteParameters defaultAniteParameters = new DefaultAniteParameters();
		final String durationsString = "1,2,3,4,5,6,7,8,9,10";
		defaultAniteParameters.setDurationsString(durationsString);

		final AvCache avCache = new AvCache();
		avCache.setVersion("1.1");
		final AniteListByAccommodationResponse anitePackageResponse = Mockito.mock(AniteListByAccommodationResponse.class);
		Mockito.when(
				defaultAniteImplementation.query(Matchers.eq(AniteQueryType.LIST_BY_ACCOMMODATION),
						Matchers.any(DefaultAniteParameters.class))).thenReturn(anitePackageResponse);

		durationsLimitAniteImplementation.query(AniteQueryType.LIST_BY_ACCOMMODATION, defaultAniteParameters);
		verify(defaultAniteImplementation, times((StringUtils.split(durationsString, ",").length / 7) + 1)).query(
				Mockito.eq(AniteQueryType.LIST_BY_ACCOMMODATION), Matchers.any(DefaultAniteParameters.class));
		final String[] durations = durationsString.split(",");

		for (int i = 0; i < durations.length; i += 7)
		{
			final int last = Math.min(i + 7, durations.length);
			final DefaultAniteParameters subQueryParams = new DefaultAniteParameters(defaultAniteParameters);
			final String[] subQueryDurations = Arrays.copyOfRange(durations, i, last);
			subQueryParams.setDurations(subQueryDurations);
			verify(defaultAniteImplementation).query(AniteQueryType.LIST_BY_ACCOMMODATION, subQueryParams);
		}
	}
}
