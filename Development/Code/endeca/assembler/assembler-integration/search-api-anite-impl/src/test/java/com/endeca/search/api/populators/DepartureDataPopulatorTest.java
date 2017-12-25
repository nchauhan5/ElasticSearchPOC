package com.endeca.search.api.populators;

import static org.hamcrest.Matchers.is;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.endeca.search.api.request.dtos.DepartureData;


public class DepartureDataPopulatorTest
{

	DepartureDataPopulator departureDataPopulator = new DepartureDataPopulator();

	@Test
	public void departureAirportsShouldPopulateWhenParamKeysContainsDepartureAirportParam() throws ParseException
	{
		String departurekey = "ARN,CPM,GHY";
		DepartureData result = departureDataPopulator.populate(departurekey, null);
		List<String> expected = Arrays.asList("ARN", "CPM", "GHY");
		Assert.assertThat(result.getDepartureAirports(), is(expected));
	}

	@Test
	public void departureAirportsShouldNotPopulateWhenParamKeysDoNoTContainsDepartureAirportParam() throws ParseException
	{
		String arrivalKey = "ARN,CPM,GHY";
		DepartureData result = departureDataPopulator.populate(null, arrivalKey);
		Assert.assertNull(result.getDepartureAirports());
	}

	@Test
	public void arrivalAirportsShouldPopulateWhenParamKeysContainsArrivalAirportParam() throws ParseException
	{
		String arrivalKey = "CPM,ARN,GHY";
		DepartureData result = departureDataPopulator.populate(null, arrivalKey);
		List<String> expected = Arrays.asList("CPM", "ARN", "GHY");
		Assert.assertThat(result.getArrivalAirports(), is(expected));
	}
}
