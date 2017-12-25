package com.endeca.tui.anite.io.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.springframework.core.convert.converter.Converter;

import com.endeca.tui.anite.parameters.AniteParameters;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;


/**
 * The Class AniteParamsHttpParamsConverter.
 * 
 * @author dkapil
 */
public class AniteParamsHttpParamsConverter implements Converter<AniteRequiredParameters, List<NameValuePair>>
{
	/** The Constant PAX_IN. */
	private static final String PAX_IN = "pax_in";

	/** The Constant SEARCH_TYPE. */
	private static final String SEARCH_TYPE = "s_tp";

	/** The Constant ACCOMMODATIONS_COUNT. */
	private static final String ACCOMMODATIONS_COUNT = "accom_no";

	/** The Constant ACCOMODATION_CODES. */
	private static final String ACCOMODATION_CODES = "accom";

	/** The Constant DEPARTURE_AIRPORTS. */
	private static final String DEPARTURE_AIRPORTS = "dep";

	/** The Constant STAY. */
	private static final String STAY = "stay";

	/** The Constant CH_AGE. */
	private static final String CH_AGE = "ch_age";

	/** The Constant PAX_CH. */
	private static final String PAX_CH = "pax_ch";

	/** The Constant ROOMS. */
	private static final String ROOMS = "rooms";

	/** The Constant PAX_AD. */
	private static final String PAX_AD = "pax_ad";

	/** The Constant SDATE. */
	private static final String SDATE = "sdate";

	/** The Constant EDATE. */
	private static final String EDATE = "edate";

	/** The Constant BB. */
	private static final String BB = "bb";

	/** The Constant EQ_TP. */
	private static final String EQ_TP = "eq_tp";

	/** The Constant PROM. */
	private static final String PROM = "prom";

	/** The Constant PROM_NO. */
	private static final String PROM_NO = "prom_no";

	/** The Constant CALEND_DURATIONS_TYPE_REGEX. */
	private static final String CALEND_DURATIONS_TYPE_REGEX = "[1,2]";


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public List<NameValuePair> convert(final AniteRequiredParameters params)
	{
		final ParamHelper helper = new ParamHelper().add(SDATE, params.getEarliestDepartureDateString())
				.add(EDATE, params.getLatestDepartureDateString()).add(PAX_AD, params.getAdultPax())
				.add(SEARCH_TYPE, params.getAniteQueryType().getSearchType());
		final int rooms = params.getRoomCount();
		String departureAirports = null;
		if (rooms > 0)
		{
			helper.add(ROOMS, rooms);
		}
		if (params instanceof AniteParameters)
		{
			final AniteParameters ap = (AniteParameters) params;
			//Include Anite Override Parameters
			addAniteOverridesParameters(ap, helper);
			if (null != ap.getPassthroughMap())
			{
				addAniteFileteredParameters(ap, helper, params.getAniteQueryType().getSearchType());
			}

			if (StringUtils.isEmpty(ap.getPassthroughMap().get(DEPARTURE_AIRPORTS)))
			{
				departureAirports = params.getDepartureAirportsString();
			}
		}
		addCommonParameters(params.getAccommodationsString(), params.getAccommodationsCount(), params.getDurationsString(),
				params.getChildAges(), departureAirports, helper);
		return helper.getPairs();

	}

	/**
	 * This API is used to add additional anite override parameters e.g board basis, promotions, dreamliner indicator
	 * etc.
	 * 
	 * @param aniteParameters
	 *           the anite parameters
	 * @param helper
	 *           the helper
	 */
	protected void addAniteOverridesParameters(final AniteParameters aniteParameters, final ParamHelper helper)
	{
		// add board basis
		if (StringUtils.isNotBlank(aniteParameters.getBoardBasis()))
		{
			helper.add(BB, aniteParameters.getBoardBasis());
		}
		// add dream Liner
		if (StringUtils.isNotBlank(aniteParameters.getDreamLiner()))
		{
			helper.add(EQ_TP, aniteParameters.getDreamLiner());
		}
		// add promotions
		if (StringUtils.isNotBlank(aniteParameters.getPromotionsString()))
		{
			helper.add(PROM_NO, aniteParameters.getPromotionsCount());
			helper.add(PROM, aniteParameters.getPromotionsString());

		}
	}

	/**
	 * Adds the anite filetered parameters.
	 * 
	 * @param aniteParameters
	 *           the anite parameters
	 * @param helper
	 *           the helper
	 * @param searchType
	 *           the search type
	 */
	protected void addAniteFileteredParameters(final AniteParameters aniteParameters, final ParamHelper helper,
			final int searchType)
	{
		for (final Entry<String, String> entry : aniteParameters.getPassthroughMap().entrySet())
		{
			final String key = entry.getKey();
			if (String.valueOf(searchType).matches(CALEND_DURATIONS_TYPE_REGEX) && ("f").equals(key))
			{
				continue;
			}

			helper.add(key, entry.getValue());
		}
	}

	/**
	 * Adds the common parameters.
	 * 
	 * @param accommodations
	 *           the accommodations
	 * @param accommodationsCount
	 *           the accommodations count
	 * @param durations
	 *           the durations
	 * @param childrenAndInfantAges
	 *           the children and infant ages
	 * @param departureAirports
	 *           the departure airports
	 * @param helper
	 *           the helper
	 */
	protected void addCommonParameters(final String accommodations, final int accommodationsCount, final String durations,
			final int[] childrenAndInfantAges, final String departureAirports, final ParamHelper helper)
	{
		if (null != durations && StringUtils.isNotBlank(durations))
		{
			helper.add(STAY, durations);
		}
		if (null != departureAirports && StringUtils.isNotBlank(departureAirports))
		{
			helper.add(DEPARTURE_AIRPORTS, departureAirports);
		}
		if (null != childrenAndInfantAges && childrenAndInfantAges.length > 0)
		{
			final List<Integer> childAges = new ArrayList<Integer>(childrenAndInfantAges.length);
			final List<Integer> infantAges = new ArrayList<Integer>(childrenAndInfantAges.length);
			for (final int age : childrenAndInfantAges)
			{
				if (age < 2)
				{
					infantAges.add(age);
				}
				else
				{
					childAges.add(age);
				}
			}
			helper.add(PAX_CH, childAges.size());
			helper.add(PAX_IN, infantAges.size());
			helper.add(CH_AGE, StringUtils.join(childAges, ','));
		}
		else
		{
			helper.add(PAX_CH, 0);
			helper.add(PAX_IN, 0);
			helper.add(CH_AGE, "");
		}
		if (null != accommodations)
		{
			helper.add(ACCOMODATION_CODES, accommodations);
			helper.add(ACCOMMODATIONS_COUNT, accommodationsCount);
		}
	}
}
