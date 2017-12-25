package com.endeca.search.api.anite.parameters.provider;

import com.endeca.search.api.anite.paramteres.provider.impl.DefaultAniteParametersProvider;
import com.endeca.search.api.anite.paramteres.provider.impl.DurationAutoSelectProvider;
import com.endeca.search.api.request.dtos.SearchAPIRequestData;
import com.endeca.tui.anite.parameters.HolidayParameters;


/**
 * The Interface AniteParametersProvider used to build anite parameters used to query anite to fetch results. All the
 * anite parameters are built by {@link SearchAPIRequestData}. The implementations of this class shall write their own
 * logic to generate anite parameters with the help of {@link SearchAPIRequestData}.
 * <p>
 * Directly know implementations. {@link DefaultAniteParametersProvider} , {@link DurationAutoSelectProvider}
 */
public interface AniteParametersProvider
{

	/**
	 * Builds the anite parameters.
	 *
	 * @param searchAPIRequestData
	 *           the search api request data
	 * @return the holiday parameters
	 */
	HolidayParameters buildAniteParameters(SearchAPIRequestData searchAPIRequestData);
}
