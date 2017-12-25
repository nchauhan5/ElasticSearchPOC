package com.endeca.search.api.adapters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.endeca.search.api.constants.SearchRequestConstant;


/**
 * @author shurde
 * 
 */
public class BrandAdapter
{

	/** The delimiter. */
	private String delimiter;

	/** The app name. */
	private String appName;

	/**
	 * Gets the accomodation brand code.
	 * 
	 * @param code
	 *           the code
	 * @return the accomodation brand code
	 */
	public String getAccomodationBrandCode(final String code)
	{

		final List<String> elements = new ArrayList<String>();
		elements.add(code);
		if (StringUtils.isNotEmpty(getBrand(getAppName())))
		{
			elements.add(getBrand(getAppName()));
		}
		return StringUtils.join(elements, getDelimiter());

	}

	/**
	 * Gets the brand.
	 * 
	 * @param appName
	 *           the app name
	 * @return the brand
	 */
	private String getBrand(final String appName)
	{
		String brandName = null;
		if (SearchRequestConstant.SEBLUESV.equalsIgnoreCase(appName))
		{
			brandName = SearchRequestConstant.S;
		}
		else if (SearchRequestConstant.FLBLUEFI.equalsIgnoreCase(appName))
		{
			brandName = SearchRequestConstant.F;
		}
		else if (SearchRequestConstant.NOBLUENO.equalsIgnoreCase(appName))
		{
			brandName = SearchRequestConstant.N;
		}
		else if (SearchRequestConstant.DKBLUEDA.equalsIgnoreCase(appName))
		{
			brandName = SearchRequestConstant.D;
		}
		return brandName;
	}

	/**
	 * Gets the delimiter.
	 * 
	 * @return the delimiter
	 */
	public String getDelimiter()
	{
		return delimiter;
	}

	/**
	 * Sets the delimiter.
	 * 
	 * @param delimiter
	 *           the delimiter
	 */
	public void setDelimiter(final String delimiter)
	{
		this.delimiter = delimiter;
	}

	/**
	 * Gets the app name.
	 * 
	 * @return the app name
	 */
	public String getAppName()
	{
		return appName;
	}

	/**
	 * Sets the app name.
	 * 
	 * @param appName
	 *           the app name
	 */
	public void setAppName(final String appName)
	{
		this.appName = appName;
	}
}
