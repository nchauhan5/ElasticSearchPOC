package com.endeca.search.api.adapters;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;


/**
 * Test class for {@link BrandAdapterTest}.
 */
public class BrandAdapterTest
{

	/** The brand adapter. */
	private final BrandAdapter brandAdapter = new BrandAdapter();

	/**
	 * Test Null app name and delimiter returns code itself without modifying.
	 */
	@Test
	public void nullAppNameAndDelimiterReturnsCodeUnmodified()
	{
		brandAdapter.setAppName(null);
		brandAdapter.setDelimiter(null);
		final String response1 = brandAdapter.getAccomodationBrandCode("1468");
		assertThat(response1, is("1468"));

		brandAdapter.setAppName("");
		brandAdapter.setDelimiter("");
		final String response2 = brandAdapter.getAccomodationBrandCode("1468");
		assertThat(response2, is("1468"));
	}

	/**
	 * Test for null delimiter code is directly appended with app name.
	 */
	@Test
	public void nullDelimiterCodeAppendedWithAppname()
	{
		brandAdapter.setAppName("SEBluesv");
		brandAdapter.setDelimiter(null);
		final String response = brandAdapter.getAccomodationBrandCode("1468");
		assertThat(response, is("1468S"));
	}

	/**
	 * Test for null app name code is returned unmodified.
	 */
	@Test
	public void nullAppNameReturnCodeUnmodifed()
	{
		brandAdapter.setAppName(null);
		brandAdapter.setDelimiter("|");
		final String response = brandAdapter.getAccomodationBrandCode("1468");
		assertThat(response, is("1468"));
	}


	/**
	 * Test for valid app names SEBluesv,FLBluefi,NOBlueno,DKBlueda code is returned appended with brand name by a
	 * delimiter.
	 */
	@Test
	public void validAppNameAndDelimiterCodeModified()
	{
		final Map<String, String> appNameBrandNameMap = new HashMap<String, String>();
		appNameBrandNameMap.put("SEBluesv", "S");
		appNameBrandNameMap.put("FLBluefi", "F");
		appNameBrandNameMap.put("NOBlueno", "N");
		appNameBrandNameMap.put("DKBlueda", "D");
		Arrays.asList("SEBluesv", "FLBluefi", "NOBlueno", "DKBlueda");
		brandAdapter.setDelimiter("|");
		for (final Entry<String, String> appNameBrandNameEntry : appNameBrandNameMap.entrySet())
		{
			final String code = "1468";
			brandAdapter.setAppName(appNameBrandNameEntry.getKey());
			final String response = brandAdapter.getAccomodationBrandCode(code);
			assertThat(response, is(StringUtils.join(new String[]
			{ code, appNameBrandNameEntry.getValue() }, "|")));
		}
	}

	/**
	 * Test that for Invalid app name code is returned unmodified.
	 */
	@Test
	public void invalidAppNameReturnsCodeUnmodified()
	{
		brandAdapter.setAppName("USBlueen");
		brandAdapter.setDelimiter("|");
		final String response = brandAdapter.getAccomodationBrandCode("1468");
		assertThat(response, is("1468"));
	}

}
