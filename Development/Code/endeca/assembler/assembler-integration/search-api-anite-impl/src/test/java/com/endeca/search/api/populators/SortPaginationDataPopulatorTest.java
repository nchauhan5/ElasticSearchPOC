package com.endeca.search.api.populators;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.endeca.search.api.request.dtos.SortPaginationData;
import com.endeca.search.api.sort.parameter.SortingOrder;
import com.endeca.search.api.sort.parameter.SortingType;


/**
 * Test class for {@link SortPaginationDataPopulator}.
 */
public class SortPaginationDataPopulatorTest
{

	/** The sort pagination data populator. */
	private final SortPaginationDataPopulator sortPaginationDataPopulator = new SortPaginationDataPopulator();

	/**
	 * Test Sort parameters with ascending sort order are converted correctly.
	 */
	@Test
	public void sortParamsAscendingSortOrderConverted()
	{
		final SortPaginationData response = sortPaginationDataPopulator.populate("ASCENDING_commPriority_INTEGER", null);
		assertThat(response.getSortOrder(), is(SortingOrder.ASCENDING));
		assertThat(response.getSortProperty(), is("commPriority"));
		assertThat(response.getSortType(), is(SortingType.INTEGER));
		assertThat(response.getPageNumber(), is(0));
		assertThat(response.getPageSize(), is(0));
	}

	/**
	 * Test Sort params with descending sort order is converted correctly.
	 */
	@Test
	public void sortParamsDescendingSortOrderConverted()
	{
		final SortPaginationData response = sortPaginationDataPopulator.populate("DESCENDING_commPriority_INTEGER", null);
		assertThat(response.getSortOrder(), is(SortingOrder.DESCENDING));
		assertThat(response.getSortProperty(), is("commPriority"));
		assertThat(response.getSortType(), is(SortingType.INTEGER));
		assertThat(response.getPageNumber(), is(0));
		assertThat(response.getPageSize(), is(0));
	}

	/**
	 * Test Sort params with invalid sort order parameter results into setting null.
	 */
	@Test
	public void sortParamsInvalidSortOrderSetNull()
	{

		final SortPaginationData response = sortPaginationDataPopulator.populate("BOGUS_commPriority_INTEGER", null);
		assertThat(response.getSortOrder(), nullValue());
		assertThat(response.getSortProperty(), is("commPriority"));
		assertThat(response.getSortType(), is(SortingType.INTEGER));
		assertThat(response.getPageNumber(), is(0));
		assertThat(response.getPageSize(), is(0));
	}

	/**
	 * Test Sort parameters with integer sort type are converted correctly.
	 */
	@Test
	public void sortParamsIntegerSortTypeConverted()
	{

		final SortPaginationData response = sortPaginationDataPopulator.populate("ASCENDING_commPriority_INTEGER", null);
		assertThat(response.getSortType(), is(SortingType.INTEGER));
		assertThat(response.getSortOrder(), is(SortingOrder.ASCENDING));
		assertThat(response.getSortProperty(), is("commPriority"));
		assertThat(response.getPageNumber(), is(0));
		assertThat(response.getPageSize(), is(0));
	}

	/**
	 * Test Sort parameters with float sort type are converted correctly.
	 */
	@Test
	public void sortParamsFloatSortTypeConverted()
	{
		final Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("sort", new String[] {});
		final SortPaginationData response = sortPaginationDataPopulator.populate("ASCENDING_commPriority_FLOAT", null);
		assertThat(response.getSortType(), is(SortingType.FLOAT));
		assertThat(response.getSortOrder(), is(SortingOrder.ASCENDING));
		assertThat(response.getSortProperty(), is("commPriority"));
		assertThat(response.getPageNumber(), is(0));
		assertThat(response.getPageSize(), is(0));
	}

	/**
	 * Test Sort parameters with alpha sort type are converted correctly.
	 */
	@Test
	public void sortParamsAlphaSortTypeConverted()
	{

		final SortPaginationData response = sortPaginationDataPopulator.populate("ASCENDING_commPriority_ALPHA", null);
		assertThat(response.getSortType(), is(SortingType.ALPHA));
		assertThat(response.getSortOrder(), is(SortingOrder.ASCENDING));
		assertThat(response.getSortProperty(), is("commPriority"));
		assertThat(response.getPageNumber(), is(0));
		assertThat(response.getPageSize(), is(0));
	}

	/**
	 * Test Sort parameters with invalid sort type results into setting null.
	 */
	@Test
	public void sortParamsInvalidSortTypeSetNull()
	{

		final SortPaginationData response = sortPaginationDataPopulator.populate("ASCENDING_commPriority_BIGDECIMAL", null);
		assertThat(response.getSortType(), nullValue());
		assertThat(response.getSortOrder(), is(SortingOrder.ASCENDING));
		assertThat(response.getSortProperty(), is("commPriority"));
		assertThat(response.getPageNumber(), is(0));
		assertThat(response.getPageSize(), is(0));
	}

	/**
	 * Test Sort parameters sort property converted correctly.
	 */
	@Test
	public void sortParamsSortPropertyConverted()
	{
		final Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("sort", new String[] {});
		final SortPaginationData response = sortPaginationDataPopulator.populate("ASCENDING_commPriority_FLOAT", null);
		assertThat(response.getSortProperty(), is("commPriority"));
		assertThat(response.getSortType(), is(SortingType.FLOAT));
		assertThat(response.getSortOrder(), is(SortingOrder.ASCENDING));
		assertThat(response.getPageNumber(), is(0));
		assertThat(response.getPageSize(), is(0));
	}

	/**
	 * Test For empty Sort parameters null sort order, property and type is set.
	 */
	@Test
	public void emptySortParameterNullSortParamsSet()
	{

		final SortPaginationData response = sortPaginationDataPopulator.populate(null, null);
		assertThat(response.getSortOrder(), nullValue());
		assertThat(response.getSortProperty(), nullValue());
		assertThat(response.getSortType(), nullValue());
		assertThat(response.getPageNumber(), is(0));
		assertThat(response.getPageSize(), is(0));
	}

	/**
	 * Test Pagination parameters are converted correctly.
	 */
	@Test
	public void paginationParamsConverted()
	{

		final SortPaginationData response = sortPaginationDataPopulator.populate(null, "1_50");
		assertThat(response.getPageNumber(), is(1));
		assertThat(response.getPageSize(), is(50));
		assertThat(response.getSortOrder(), nullValue());
		assertThat(response.getSortProperty(), nullValue());
		assertThat(response.getSortType(), nullValue());
	}

	/**
	 * Test Empty pagination params results into setting null.
	 */
	@Test
	public void emptyPaginationParamsSetNull()
	{

		final SortPaginationData response = sortPaginationDataPopulator.populate(null, null);
		assertThat(response.getPageNumber(), is(0));
		assertThat(response.getPageSize(), is(0));
		assertThat(response.getSortOrder(), nullValue());
		assertThat(response.getSortProperty(), nullValue());
		assertThat(response.getSortType(), nullValue());
	}



}
