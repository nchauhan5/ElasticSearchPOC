package com.endeca.search.api.anite.paramteres.provider.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.endeca.search.api.anite.parameters.provider.AniteParametersProvider;
import com.endeca.search.api.request.dtos.SearchAPIRequestData;
import com.endeca.tui.anite.parameters.HolidayParameters;
import com.endeca.tui.anite.parameters.impl.DefaultHolidayParameters;


/**
 * The Class DefaultAniteParametersProvider default implementation of {@link AniteParametersProvider} used to build
 * anite parameters.
 */
public class DefaultAniteParametersProvider implements AniteParametersProvider
{

	/** The Constant PRICE_TYPE_PARAM. */
	private static final String PRICE_TYPE_PARAM = "p_tp";

	/** The Constant HOLIDAY_TYPE_PARAM. */
	private static final String HOLIDAY_TYPE_PARAM = "h_tp";

	/** The Constant BOARD_BASIS_PARAMETER. */
	private static final String BOARD_BASIS_PARAMETER = "bb";

	/** The Constant N_PARAM. */
	private static final String N_PARAM = "N";

	/** The Constant dateFormat. */
	private static final String DATEFORMAT = "yyyy-MM-dd";


	/**
	 * The allowed pass through keys. List of parameters names that pass straight through to Anite
	 */
	private Set<String> allowedPassthroughKeys = Collections.emptySet();

	/**
	 * The allowed pass through patterns. List of parameters name patterns that pass straight through to Anite e.g.
	 * "rm_1", "rm_2", "rm_12345" e.g. "f_1", "f_2", "f_12345"
	 */
	private Set<Pattern> allowedPassthroughPatterns = Collections.emptySet();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.endeca.tui.anite.request.populator.AniteParametersProvider#buildAniteParameters(com.endeca.search.api.request
	 * .dtos.SearchAPIRequestData)
	 */
	@Override
	public HolidayParameters buildAniteParameters(final SearchAPIRequestData requestData)

	{
		final DateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);

		final DefaultHolidayParameters searchParameters = new DefaultHolidayParameters();

		searchParameters.setAccommodations(requestData.getDepartureData().getCandidateCodes());
		searchParameters.setEarliestDepartureDateString(simpleDateFormat.format(requestData.getDurationData().getStartDate()));

		searchParameters.setLatestDepartureDateString(simpleDateFormat.format(requestData.getDurationData().getEndDate()));

		searchParameters.setAutoSelectedDuration(requestData.getDurationData().getDuration());
		searchParameters.setAdultPax(requestData.getPartyData().getNoOfAdults());

		searchParameters.setRoomCount(requestData.getPartyData().getNoOfRooms());

		searchParameters.setChildAgeStrings(StringUtils.split(StringUtils.join(requestData.getPartyData().getChildrenAges(), ","),
				","));

		searchParameters.setDepartureAirportsString(StringUtils.join(requestData.getDepartureData().getDepartureAirports(), ","));

		if (requestData.getMiscellaneousAttributesMap() != null && !(requestData.getMiscellaneousAttributesMap().isEmpty()))
		{
			populateMiscellaneousAttributes(requestData, searchParameters);

			populatePassthroughMap(requestData, searchParameters);
		}

		return searchParameters;
	}


	/**
	 * Populate passthrough map.
	 * 
	 * @param request
	 *           the request
	 * @param aniteParameters
	 *           the anite parameters
	 */
	private void populatePassthroughMap(final SearchAPIRequestData request, final DefaultHolidayParameters aniteParameters)
	{
		final Set<String> paramKeys = request.getMiscellaneousAttributesMap().keySet();
		final Map<String, String> map = new HashMap<String, String>();
		for (final String param : paramKeys)
		{
			if (CollectionUtils.isNotEmpty(getAllowedPassthroughKeys()) && getAllowedPassthroughKeys().contains(param))
			{
				aniteParameters.getPassthroughMap().put(param, request.getMiscellaneousAttributesMap().get(param));
			}
			else if (CollectionUtils.isNotEmpty(getAllowedPassthroughPatterns()))
			{
				aniteParameters.getPassthroughMap().putAll(populatePassThroughMapForPattern(param, request, map));
			}

		}
	}


	/**
	 * Populate miscellaneous attributes.
	 * 
	 * @param request
	 *           the request
	 * @param aniteParameters
	 *           the anite parameters
	 * @return the sets the
	 */
	private void populateMiscellaneousAttributes(final SearchAPIRequestData request, final DefaultHolidayParameters aniteParameters)
	{
		final Map<String, String> paramMap = request.getMiscellaneousAttributesMap();
		if (null != paramMap.get(N_PARAM))
		{
			aniteParameters.setnString(paramMap.get(N_PARAM));
		}
		if (null != paramMap.get(HOLIDAY_TYPE_PARAM))
		{
			aniteParameters.setHolidayTypeString(request.getMiscellaneousAttributesMap().get(HOLIDAY_TYPE_PARAM));
		}
		if (null != paramMap.get(PRICE_TYPE_PARAM))
		{
			aniteParameters.setPriceTypeString(request.getMiscellaneousAttributesMap().get(PRICE_TYPE_PARAM));
		}
		if (null != paramMap.get(BOARD_BASIS_PARAMETER))
		{
			aniteParameters.setBoardBasis(request.getMiscellaneousAttributesMap().get(BOARD_BASIS_PARAMETER));
		}
	}


	/**
	 * Populate pass through map for pattern.
	 * 
	 * @param param
	 *           the param
	 * @param request
	 *           the request
	 * @return the map
	 */
	private Map<String, String> populatePassThroughMapForPattern(final String param, final SearchAPIRequestData request,
			final Map<String, String> map)
	{

		for (final Pattern pattern : getAllowedPassthroughPatterns())
		{
			if (pattern.matcher(param).matches())
			{
				map.put(param, request.getMiscellaneousAttributesMap().get(param));
			}
		}
		return map;
	}

	/**
	 * Gets the allowed passthrough keys.
	 * 
	 * @return the allowed passthrough keys
	 */
	public Set<String> getAllowedPassthroughKeys()
	{
		return allowedPassthroughKeys;
	}

	/**
	 * Sets the allowed passthrough keys.
	 * 
	 * @param allowedPassthroughKeys
	 *           the new allowed passthrough keys
	 */
	public void setAllowedPassthroughKeys(final Set<String> allowedPassthroughKeys)
	{
		this.allowedPassthroughKeys = allowedPassthroughKeys;
	}

	/**
	 * Gets the allowed passthrough patterns.
	 * 
	 * @return the allowed passthrough patterns
	 */
	public Set<Pattern> getAllowedPassthroughPatterns()
	{
		return allowedPassthroughPatterns;
	}

	/**
	 * Sets the allowed passthrough patterns.
	 * 
	 * @param allowedPassthroughPatterns
	 *           the new allowed passthrough patterns
	 */
	public void setAllowedPassthroughPatterns(final Set<Pattern> allowedPassthroughPatterns)
	{
		this.allowedPassthroughPatterns = allowedPassthroughPatterns;
	}
}
