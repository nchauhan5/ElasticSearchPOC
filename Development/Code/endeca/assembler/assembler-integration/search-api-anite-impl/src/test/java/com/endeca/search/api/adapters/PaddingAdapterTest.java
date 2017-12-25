package com.endeca.search.api.adapters;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;


/**
 * Test class for {@link PaddingAdapter}.
 */
public class PaddingAdapterTest
{

	/** The padding adapter. */
	private final PaddingAdapter paddingAdapter = new PaddingAdapter();

	/**
	 * Test endeca2search api with default params i.e. padding character= 0.
	 */
	@Test
	public void endeca2searchApiDefaultParams()
	{
		final String response = paddingAdapter.endeca2SearchAPI("00000000045");
		assertThat(response, is("45"));
	}


	/**
	 * Test endeca2searchapi method with different padding character.
	 */
	@Test
	public void endeca2searchApiDifferentPadCharacter()
	{
		paddingAdapter.setPadCharacter("#");
		final String response = paddingAdapter.endeca2SearchAPI("######45");
		assertThat(response, is("45"));
	}

	/**
	 * Test accomm converter method with default params this is padding character 0.
	 */
	@Test
	public void accommConverterDefaultParams()
	{
		final String response = paddingAdapter.accommCodeConverter("00000000045");
		assertThat(response, is("45"));
	}


	/**
	 * Test accomm converter method with default params this is padding character 0.
	 */
	@Test
	public void accommConverterDifferentPadCharacter()
	{
		paddingAdapter.setPadCharacter2("#");
		final String response = paddingAdapter.accommCodeConverter("######45");
		assertThat(response, is("45"));
	}


	/**
	 * Test Padding adapter with different pad characters. Pad character 1 will be used for endeca2searchapi method and
	 * pad character 2 will be used for accom converter method.
	 */
	@Test
	public void paddingAdapterWithDifferentPadCharacters()
	{
		paddingAdapter.setPadCharacter("0");
		paddingAdapter.setPadCharacter2("#");
		final String response1 = paddingAdapter.endeca2SearchAPI("00000068");
		assertThat(response1, is("68"));
		final String response2 = paddingAdapter.accommCodeConverter("######45");
		assertThat(response2, is("45"));
	}


	/**
	 * Test that Search api2 endeca method returns the input unmodified.
	 */
	@Test
	public void searchApi2EndecaReturnsInputUnmodified()
	{
		final String code = "00000000045";
		final String response = paddingAdapter.searchAPI2endeca(code);
		assertThat(response, is(code));
	}
}
