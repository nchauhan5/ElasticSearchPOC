package com.endeca.search.api.request.dtos;

/**
 * The Class BrandMarketInfo. DTO having brand and market information.
 */
public class BrandMarketData
{

	/** The market and channel type information. */
	String market;

	/**
	 * Gets the market.
	 * 
	 * @return the market
	 */
	public String getMarket()
	{
		return market;
	}

	/**
	 * Sets the market.
	 * 
	 * @param market
	 *           the new market
	 */
	public void setMarket(final String market)
	{
		this.market = market;
	}

}
