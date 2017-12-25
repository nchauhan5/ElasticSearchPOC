package com.endeca.search.api.populators;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.endeca.search.api.request.dtos.PartyData;


/**
 * Test class for {@link PartyDataPopulator}.
 */
public class PartyDataPopulatorTest
{

	/** The party data populator. */
	private final PartyDataPopulator partyDataPopulator = new PartyDataPopulator();

	/**
	 * Test Adults param converted correctly.
	 */
	@Test
	public void adultsParamConverted()
	{

		final PartyData response = partyDataPopulator.populate("2", null, null);
		assertThat(response.getNoOfAdults(), is(2));
		assertThat(response.getChildrenAges(), nullValue());
		assertThat(response.getNoOfChildren(), is(0));
		assertThat(response.getNoOfRooms(), is(0));
	}

	/**
	 * Test Child param converted correctly and child ages and count is mapped correctly.
	 */
	@Test
	public void childParamConverted()
	{

		final PartyData response = partyDataPopulator.populate(null, "10,17", null);
		assertThat(response.getNoOfChildren(), is(2));
		assertThat(response.getChildrenAges(), is(Arrays.asList(10, 17)));
		assertThat(response.getNoOfAdults(), is(0));
		assertThat(response.getNoOfRooms(), is(0));

	}

	/**
	 * Test Room param converted correctly.
	 */
	@Test
	public void roomParamConverted()
	{

		final PartyData response = partyDataPopulator.populate(null, null, "2");
		assertThat(response.getNoOfRooms(), is(2));
		assertThat(response.getNoOfAdults(), is(0));
		assertThat(response.getChildrenAges(), nullValue());
		assertThat(response.getNoOfChildren(), is(0));

	}
}
