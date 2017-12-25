package com.endeca.search.api.populators;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.endeca.search.api.constants.SearchRequestParams;
import com.endeca.search.api.exceptions.SearchAPIException;
import com.endeca.search.api.request.dtos.SearchAPIRequestData;


/**
 * Test class for {@link SearchAPIRequestDataPopulator}.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchAPIRequestDataPopulatorTest
{

	/** The departure data populator. */
	@Mock
	private DepartureDataPopulator departureDataPopulator;

	/** The duration data populator. */
	@Mock
	private DurationDataPopulator durationDataPopulator;

	/** The party data populator. */
	@Mock
	private PartyDataPopulator partyDataPopulator;

	/** The sort pagination data populator. */
	@Mock
	private SortPaginationDataPopulator sortPaginationDataPopulator;

	/** The sort pagination data populator. */
	@Mock
	private FacetsDataPopulator facetsDataPopulator;

	/** The removed facet price data populator. */
	@Mock
	private RemovedFacetPriceDataPopulator removedFacetPriceDataPopulator;

	/** The search api request data populator. */
	@InjectMocks
	private final SearchAPIRequestDataPopulator searchAPIRequestDataPopulator = new SearchAPIRequestDataPopulator();
	Map<String, String> paramsMap;

	String mockContextPath = "mock-contextpath";

	@Before
	public void setUp()
	{
		paramsMap = new HashMap<String, String>();
		paramsMap.put("h_tp", "P");
	}

	/**
	 * Test Individual sub populators are invoked to convert request params into respective sub dto.
	 * 
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void individualSubPopulatorsAreInvoked() throws SearchAPIException
	{
		searchAPIRequestDataPopulator.populate(paramsMap, mockContextPath);
		verify(departureDataPopulator, Mockito.times(1)).populate(Mockito.anyString(), Mockito.anyString());
		verify(durationDataPopulator, Mockito.times(1)).populate(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString());
		verify(partyDataPopulator, Mockito.times(1)).populate(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		verify(sortPaginationDataPopulator, Mockito.times(1)).populate(Mockito.anyString(), Mockito.anyString());
		verify(facetsDataPopulator, Mockito.times(1)).populate(Mockito.anyString());
	}

	/**
	 * Verify Room allocation parameters are converted correctly.
	 * 
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	@Test
	public void roomAllocationParametersConverted() throws SearchAPIException
	{
		final Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("rm_1", "1,2");
		paramsMap.put("rm_2", "3,4");
		paramsMap.put("rm_3", "5,6");
		final SearchAPIRequestData response = searchAPIRequestDataPopulator.populate(paramsMap, mockContextPath);
		assertThat(response.getMiscellaneousAttributesMap().get(SearchRequestParams.ROOM_ALLOCATION1), is("1,2"));
		assertThat(response.getMiscellaneousAttributesMap().get(SearchRequestParams.ROOM_ALLOCATION2), is("3,4"));
		assertThat(response.getMiscellaneousAttributesMap().get(SearchRequestParams.ROOM_ALLOCATION3), is("5,6"));
	}

	/**
	 * Verify Holiday Type parameter converted correctly.
	 * 
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	@Test
	public void holidayTypeParametersConverted() throws SearchAPIException
	{

		searchAPIRequestDataPopulator.setMandatoryAttributesMap(new HashMap<String, String>());
		final SearchAPIRequestData response = searchAPIRequestDataPopulator.populate(paramsMap, "mock-contextpath");
		assertThat(response.getMiscellaneousAttributesMap().get(SearchRequestParams.TYPE_PARAMETER), is("P"));
	}

	/**
	 * Test for Null room allocation and holiday type miscellaneous attributes map remains empty.
	 * 
	 * @throws SearchAPIException
	 */
	public void nullRoomAllocationAndHolidayTypeMiscAttributesMapEmpty() throws SearchAPIException
	{
		searchAPIRequestDataPopulator.setMandatoryAttributesMap(new HashMap<String, String>());
		final SearchAPIRequestData response = searchAPIRequestDataPopulator.populate(paramsMap, mockContextPath);
		assertThat(response.getMiscellaneousAttributesMap(), is(MapUtils.EMPTY_MAP));
	}

	/**
	 * Test {@link SearchAPIRequestDataPopulator#populateRemovalFacetattributes(HttpServletRequest)} invokes Removed
	 * facet price data populator.
	 * 
	 * @throws SearchAPIException
	 *            the search api exception
	 */
	@Test
	public void removedFacetPriceDataPopulatorInvoked() throws SearchAPIException
	{
		searchAPIRequestDataPopulator.populateRemovalFacetattributes(paramsMap);
		verify(removedFacetPriceDataPopulator, Mockito.times(1)).populate(paramsMap);
	}
}
