package com.endeca.search.api.response.dtos;

import java.util.List;



/**
 * The Class ConfiguratorMatrixData.
 */
public class ConfiguratorResponseData
{

	/** The response offers. */
	private List<PackageData> responseOffers;

	/** The count. */
	private int totalOffers;

	/**
	 * Gets the total offers.
	 * 
	 * @return the total offers
	 */
	public int getTotalOffers()
	{
		return totalOffers;
	}

	/**
	 * Sets the total offers.
	 * 
	 * @param totalOffers
	 *           the new total offers
	 */
	public void setTotalOffers(final int totalOffers)
	{
		this.totalOffers = totalOffers;
	}

	/**
	 * Gets the response offers.
	 * 
	 * @return the response offers
	 */
	public List<PackageData> getResponseOffers()
	{
		return responseOffers;
	}

	/**
	 * Sets the response offers.
	 * 
	 * @param responseOffers
	 *           the new response offers
	 */
	public void setResponseOffers(final List<PackageData> responseOffers)
	{
		this.responseOffers = responseOffers;
	}

}
