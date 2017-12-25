package com.endeca.search.api.adapters;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;


/**
 * Test class for {@link HybrisCodeAdapter}.
 */
public class HybrisCodeAdapterTest
{

	/** The hybris code adapter. */
	private final HybrisCodeAdapter hybrisCodeAdapter = new HybrisCodeAdapter();

	/**
	 * Test {@link HybrisCodeAdapter#endeca2SearchAPI(String)} method removes prefix "P-".
	 */
	@Test
	public void endeca2SearchAPIRemovesPrefixP()
	{
		final String response = hybrisCodeAdapter.endeca2SearchAPI("P-00004568");
		assertThat(response, is("00004568"));
	}

	/**
	 * Test {@link HybrisCodeAdapter#accommCodeConverter(String)} method removes prefix "PC-".
	 */
	@Test
	public void accommodationConvertersRemovesPrefixPC()
	{
		final String response = hybrisCodeAdapter.accommCodeConverter("PC-009271");
		assertThat(response, is("009271"));
	}

	/**
	 * Test {@link HybrisCodeAdapter#searchAPI2endeca(String)} returns code unmodified.
	 */
	@Test
	public void searchapi2EndecaReturnsCodeUnmodified()
	{
		final String response = hybrisCodeAdapter.searchAPI2endeca("1432");
		assertThat(response, is("1432"));
	}
}
