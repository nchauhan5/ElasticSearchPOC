package com.endeca.search.api.populators;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import com.endeca.search.api.exceptions.SearchAPIException;
import com.endeca.search.api.request.dtos.DurationData;


public class DurationDataPopulatorTest
{

	DurationDataPopulator durationDataPopulator = new DurationDataPopulator();


	final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	public void startDateAndEndDateShouldPopulateWhenParamkeysContainEarliestAndLatestDepartureDate() throws SearchAPIException,
			ParseException
	{
		String startdate = "2012-03-12";
		String enddate = "2012-05-22";
		DurationData result = durationDataPopulator.populate(startdate, enddate, null, null);
		Date expectedStartDate = dateFormat.parse("2012-03-12");
		Date expectedEndDate = dateFormat.parse("2012-05-22");
		assertThat(result.getStartDate(), is(expectedStartDate));
		assertThat(result.getEndDate(), is(expectedEndDate));
	}

	@Test(expected = SearchAPIException.class)
	public void parseExceptionShouldBeThrownWhen() throws SearchAPIException, ParseException
	{
		String startdate = "2012-03-12";
		String enddate = "null";
		durationDataPopulator.populate(startdate, enddate, null, null);
	}

	@Test
	public void durationPriorityListShouldPopulateWhenParamKeysContainsDurationParam() throws SearchAPIException
	{
		String duration = "7";
		String durationList = "7,9,12";
		DurationData result = durationDataPopulator.populate(null, null, durationList, duration);
		assertThat(result.getDurationsPriorityList(), is(Arrays.asList(7, 9, 12)));
	}

	@Test
	public void durationPriorityListShouldNotPopulateWhenParamKeysDoNotContainDurationParam() throws SearchAPIException
	{
		String dates = "2012-03-12";
		DurationData result = durationDataPopulator.populate(dates.toString(), dates.toString(), null, null);
		assertTrue(result.getDurationsPriorityList().isEmpty());
	}

	@Test
	public void durationShouldPopulateWhenParamKeysContainsStayParam() throws SearchAPIException
	{
		String dates = "2012-03-12";
		String durationList = "7,9,12";
		String duration = "7";
		DurationData result = durationDataPopulator.populate(dates.toString(), dates.toString(), durationList.toString(),
				duration.toString());
		assertThat(result.getDuration(), is("7"));
	}

	@Test
	public void durationShouldNotPopulateWhenParamKeysDoNotContainStayParam() throws SearchAPIException
	{
		String dates = "2012-03-12";
		String durationList = "7,9,12";
		DurationData result = durationDataPopulator.populate(dates.toString(), dates.toString(), durationList, null);
		assertNull(result.getDuration());
	}
}
