package com.endeca.search.api.anite.paramteres.provider.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.endeca.search.api.request.dtos.BrandMarketData;
import com.endeca.search.api.request.dtos.DepartureData;
import com.endeca.search.api.request.dtos.DurationData;
import com.endeca.search.api.request.dtos.PartyData;
import com.endeca.search.api.request.dtos.SearchAPIRequestData;
import com.endeca.search.api.request.dtos.SortPaginationData;
import com.endeca.tui.anite.parameters.HolidayParameters;



// TODO: Auto-generated Javadoc
/**
 * Test class for {@link DurationAutoSelectProvider}.
 */
public class DurationAutoSelectProviderTest
{

	/** The duration auto select provider. */
	private final DurationAutoSelectProvider durationAutoSelectProvider = new DurationAutoSelectProvider();

	/**
	 * Test for Empty duration priority list duration in holiday parameters remain unchanged.
	 */
	@Test
	public void emptyDurationPriorityListDurationEqualsSingleDuration()
	{
		final DepartureData departureData = new DepartureData();
		departureData.setCandidateCodes(new HashSet<String>(Arrays.asList("184|S", "9723|S", "4562|S")));
		departureData.setDepartureAirports(Arrays.asList("CPH", "ARN", "MMI"));

		final DurationData durationData = new DurationData();
		durationData.setStartDate(new Date());
		durationData.setEndDate(DateUtils.addDays(durationData.getStartDate(), 7));
		durationData.setDuration("8");

		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData(departureData, durationData, new PartyData(),
				new BrandMarketData(), new SortPaginationData());
		final HolidayParameters parameters = durationAutoSelectProvider.buildAniteParameters(searchAPIRequestData);
		assertThat(parameters.getDurationsString(), is(Integer.toString(8)));
		assertThat(parameters.getAccommodationsString(), is("184|S,4562|S,9723|S"));
		assertThat(parameters.getDepartureAirportsString(), is("CPH,ARN,MMI"));
	}

	/**
	 * Test for Non Empty duration priority list duration in holiday parameters changes to duration priority list.
	 */
	@Test
	public void nonEmptyDurationPriorityListDurationEqualsDurationPriorityList()
	{
		final DepartureData departureData = new DepartureData();
		departureData.setCandidateCodes(new HashSet<String>(Arrays.asList("184|S", "9723|S", "4562|S")));
		departureData.setDepartureAirports(Arrays.asList("CPH", "ARN", "MMI"));

		final DurationData durationData = new DurationData();
		durationData.setStartDate(new Date());
		durationData.setEndDate(DateUtils.addDays(durationData.getStartDate(), 7));
		durationData.setDuration("8");
		durationData.setDurationsPriorityList(Arrays.asList(8, 10, 11, 17));

		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData(departureData, durationData, new PartyData(),
				new BrandMarketData(), new SortPaginationData());
		final HolidayParameters parameters = durationAutoSelectProvider.buildAniteParameters(searchAPIRequestData);
		assertThat(parameters.getDurationsString(), is(StringUtils.join(Arrays.asList(8, 10, 11, 17), ",")));
	}

	/**
	 * Test for Single accommodation request accommodation parameter is updated with single accommodation code.
	 */
	@Test
	public void singleAccommodationRequestAccommodationParameterUpdated()
	{
		final Map<String, String> miscellaneousAttributesMap = new HashMap<String, String>();
		miscellaneousAttributesMap.put("accommodationCode", "8567|S");

		final DepartureData departureData = new DepartureData();
		departureData.setCandidateCodes(new HashSet<String>(Arrays.asList("184|S", "9723|S", "4562|S")));
		departureData.setDepartureAirports(Arrays.asList("CPH", "ARN", "MMI"));

		final DurationData durationData = new DurationData();
		durationData.setStartDate(new Date());
		durationData.setEndDate(DateUtils.addDays(durationData.getStartDate(), 7));
		durationData.setDuration("8");
		durationData.setDurationsPriorityList(Arrays.asList(8, 10, 11, 17));

		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData(departureData, durationData, new PartyData(),
				new BrandMarketData(), new SortPaginationData(), miscellaneousAttributesMap);

		final HolidayParameters parameters = durationAutoSelectProvider.buildAniteParameters(searchAPIRequestData);

		assertThat(parameters.getAccommodationsString(), is(miscellaneousAttributesMap.get("accommodationCode")));
	}

	/**
	 * Test for Non Single accommodation request accommodation parameter remains unchanged.
	 */
	@Test
	public void notSingleAccommodationRequestAccommodationParameterUnchanged()
	{
		final Map<String, String> miscellaneousAttributesMap = new HashMap<String, String>();

		final DepartureData departureData = new DepartureData();
		departureData.setCandidateCodes(new HashSet<String>(Arrays.asList("184|S", "9723|S", "4562|S")));
		departureData.setDepartureAirports(Arrays.asList("CPH", "ARN", "MMI"));

		final DurationData durationData = new DurationData();
		durationData.setStartDate(new Date());
		durationData.setEndDate(DateUtils.addDays(durationData.getStartDate(), 7));
		durationData.setDuration("8");
		durationData.setDurationsPriorityList(Arrays.asList(8, 10, 11, 17));

		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData(departureData, durationData, new PartyData(),
				new BrandMarketData(), new SortPaginationData(), miscellaneousAttributesMap);

		final HolidayParameters parameters = durationAutoSelectProvider.buildAniteParameters(searchAPIRequestData);

		assertThat(parameters.getAccommodationsString(), is("184|S,4562|S,9723|S"));
	}
}
