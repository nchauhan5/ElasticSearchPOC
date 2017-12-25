package com.endeca.search.api.anite.paramteres.provider.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hamcrest.Matchers;
import org.junit.Test;

import com.endeca.search.api.request.dtos.BrandMarketData;
import com.endeca.search.api.request.dtos.DepartureData;
import com.endeca.search.api.request.dtos.DurationData;
import com.endeca.search.api.request.dtos.PartyData;
import com.endeca.search.api.request.dtos.SearchAPIRequestData;
import com.endeca.search.api.request.dtos.SortPaginationData;
import com.endeca.tui.anite.parameters.HolidayParameters;


/**
 * Test class for {@link DefaultAniteParametersProvider}.
 */
public class DefaultAniteParametersProviderTest
{

	/** The default anite parameters provider. */
	private static DefaultAniteParametersProvider defaultAniteParametersProvider = new DefaultAniteParametersProvider();

	/**
	 * Test Search request departure data is converted to holiday params.
	 */
	@Test
	public void departureDataToHolidayParams()
	{
		final DepartureData departureData = new DepartureData();
		departureData.setCandidateCodes(new HashSet<String>(Arrays.asList("184|S", "9723|S", "4562|S")));
		departureData.setDepartureAirports(Arrays.asList("CPH", "ARN", "MMI"));

		final DurationData durationData = new DurationData();
		durationData.setStartDate(new Date());
		durationData.setEndDate(new Date());
		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData(departureData, durationData, new PartyData(),
				new BrandMarketData(), new SortPaginationData());
		final HolidayParameters parameters = defaultAniteParametersProvider.buildAniteParameters(searchAPIRequestData);
		assertThat(parameters.getAccommodationsString(),
				is(StringUtils.join(Arrays.asList("184|S", "4562|S", "9723|S").toArray(), ",")));
		assertThat(parameters.getDepartureAirportsString(), is(StringUtils.join(Arrays.asList("CPH", "ARN", "MMI").toArray(), ",")));
	}

	/**
	 * Test Duration data is converted correctly to holiday params.
	 * 
	 * @throws ParseException
	 *            the parse exception
	 */
	@Test
	public void durationDataToHolidayParams() throws ParseException
	{
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		final DepartureData departureData = new DepartureData();
		departureData.setCandidateCodes(new HashSet<String>(Arrays.asList("184|S", "9723|S", "4562|S")));
		departureData.setDepartureAirports(Arrays.asList("CPH", "ARN", "MMI"));

		final DurationData durationData = new DurationData();
		durationData.setStartDate(new Date());
		durationData.setEndDate(DateUtils.addDays(durationData.getStartDate(), 7));
		durationData.setDuration("8");
		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData(departureData, durationData, new PartyData(),
				new BrandMarketData(), new SortPaginationData());
		final HolidayParameters parameters = defaultAniteParametersProvider.buildAniteParameters(searchAPIRequestData);
		assertThat(parameters.getEarliestDepartureDateString(), is(dateFormat.format(durationData.getStartDate())));
		assertThat(parameters.getLatestDepartureDateString(), is(dateFormat.format(durationData.getEndDate())));
		assertThat(parameters.getAutoSelectedDuration(), is(durationData.getDuration()));
	}

	/**
	 * Test Party data is converted correctly to holiday params.
	 * 
	 * @throws ParseException
	 *            the parse exception
	 */
	@Test
	public void partyDataToHolidayParams() throws ParseException
	{
		final DepartureData departureData = new DepartureData();
		departureData.setCandidateCodes(new HashSet<String>(Arrays.asList("184|S", "9723|S", "4562|S")));
		departureData.setDepartureAirports(Arrays.asList("CPH", "ARN", "MMI"));

		final DurationData durationData = new DurationData();
		durationData.setStartDate(new Date());
		durationData.setEndDate(DateUtils.addDays(durationData.getStartDate(), 7));
		durationData.setDuration("8");

		final PartyData partyData = new PartyData();
		partyData.setChildrenAges(Arrays.asList(7, 10));
		partyData.setNoOfAdults(2);
		partyData.setNoOfChildren(2);
		partyData.setNoOfInfants(1);
		partyData.setNoOfRooms(2);
		partyData.setNoOfSeniors(2);

		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData(departureData, durationData, partyData,
				new BrandMarketData(), new SortPaginationData());
		final HolidayParameters parameters = defaultAniteParametersProvider.buildAniteParameters(searchAPIRequestData);
		assertThat(parameters.getChildAges(), is(new int[]
		{ 7, 10 }));
		assertThat(parameters.getAdultPax(), is(2));
		assertThat(parameters.getRoomCount(), is(2));
	}

	/**
	 * Test Miscellaneous attributes map are converted to params.
	 */
	@Test
	public void miscellaneousAttributesMapToParams()
	{

		final Map<String, String> miscellaneousAttributesMap = new HashMap<String, String>();
		miscellaneousAttributesMap.put("h_tp", "P");
		miscellaneousAttributesMap.put("p_tp", "PP");
		miscellaneousAttributesMap.put("bb", "AI");
		miscellaneousAttributesMap.put("N", "87165042");

		final DepartureData departureData = new DepartureData();
		departureData.setCandidateCodes(new HashSet<String>(Arrays.asList("184|S", "9723|S", "4562|S")));
		departureData.setDepartureAirports(Arrays.asList("CPH", "ARN", "MMI"));

		final DurationData durationData = new DurationData();
		durationData.setStartDate(new Date());
		durationData.setEndDate(DateUtils.addDays(durationData.getStartDate(), 7));
		durationData.setDuration("8");

		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData(departureData, durationData, new PartyData(),
				new BrandMarketData(), new SortPaginationData(), miscellaneousAttributesMap);

		final HolidayParameters parameters = defaultAniteParametersProvider.buildAniteParameters(searchAPIRequestData);

		assertThat(parameters.getHolidayType().toString(), is(miscellaneousAttributesMap.get("h_tp")));
		assertThat(parameters.getPriceType().toString(), is(miscellaneousAttributesMap.get("p_tp")));
		assertThat(parameters.getBoardBasis(), is(miscellaneousAttributesMap.get("bb")));
		assertThat(parameters.getnString(), is(miscellaneousAttributesMap.get("N")));
	}


	/**
	 * Test Miscellaneous attributes map allowed by passthrough patterns are converted to params and rest are rejected.
	 */
	@Test
	public void passthroughPatternsToParams()
	{
		/**
		 * The allowed pass through keys. List of parameters names that pass straight through to Anite
		 */
		final Set<String> allowedPassthroughKeys = new HashSet<String>(Arrays.asList("h_tp", "p_tp", "dep"));

		/**
		 * The allowed pass through patterns. List of parameters name patterns that pass straight through to Anite e.g.
		 * "rm_1", "rm_2", "rm_12345" e.g. "f_1", "f_2", "f_12345"
		 */
		final Set<Pattern> allowedPassthroughPatterns = new HashSet<Pattern>(Arrays.asList(Pattern.compile("rm_\\d+"),
				Pattern.compile("f_\\d+"), Pattern.compile("fa_\\d+"), Pattern.compile("fo_\\d+")));

		final Map<String, String> miscellaneousAttributesMap = new HashMap<String, String>();
		// Acceptable patterns
		miscellaneousAttributesMap.put("rm_1", "2,3");
		miscellaneousAttributesMap.put("f_1", "CR");
		miscellaneousAttributesMap.put("fa_1", "1,2");
		// Non-acceptable patterns
		miscellaneousAttributesMap.put("da_1", "1,2");


		final DepartureData departureData = new DepartureData();
		departureData.setCandidateCodes(new HashSet<String>(Arrays.asList("184|S", "9723|S", "4562|S")));
		departureData.setDepartureAirports(Arrays.asList("CPH", "ARN", "MMI"));

		final DurationData durationData = new DurationData();
		durationData.setStartDate(new Date());
		durationData.setEndDate(DateUtils.addDays(durationData.getStartDate(), 7));
		durationData.setDuration("8");

		final SearchAPIRequestData searchAPIRequestData = new SearchAPIRequestData(departureData, durationData, new PartyData(),
				new BrandMarketData(), new SortPaginationData(), miscellaneousAttributesMap);

		defaultAniteParametersProvider.setAllowedPassthroughKeys(allowedPassthroughKeys);
		defaultAniteParametersProvider.setAllowedPassthroughPatterns(allowedPassthroughPatterns);

		final HolidayParameters parameters = defaultAniteParametersProvider.buildAniteParameters(searchAPIRequestData);
		assertThat(parameters.getPassthroughMap(), Matchers.hasEntry("rm_1", "2,3"));
		assertThat(parameters.getPassthroughMap(), Matchers.hasEntry("f_1", "CR"));
		assertThat(parameters.getPassthroughMap(), Matchers.hasEntry("fa_1", "1,2"));
		Assert.assertFalse(parameters.getPassthroughMap().containsKey("da_1"));

	}
}
