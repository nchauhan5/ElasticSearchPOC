package com.endeca.search.api.response.dtos;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * The Class BookingClass.
 */
public class BookingClass implements Comparable<BookingClass>
{

	/** The pkg id. */
	private String pkgId;

	/** The code. */
	private String code;

	/** The total price. */
	private TotalPriceData totalPrice;

	/** The discounted price data. */
	private DiscountedPriceData discountedPriceData;

	/** The upgrade amount. */
	private BigDecimal upgradeAmount;

	/** The upgrade amunt pp. */
	private BigDecimal upgradeAmountPP;


	/**
	 * Gets the pkg id.
	 *
	 * @return the pkg id
	 */
	public String getPkgId()
	{
		return pkgId;
	}


	/**
	 * Sets the pkg id.
	 *
	 * @param pkgId
	 *           the new pkg id
	 */
	public void setPkgId(final String pkgId)
	{
		this.pkgId = pkgId;
	}


	/**
	 * Sets the code.
	 *
	 * @param code
	 *           the new code
	 */
	public void setCode(final String code)
	{
		this.code = code;
	}


	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}


	/**
	 * Gets the total price.
	 *
	 * @return the total price
	 */
	public TotalPriceData getTotalPrice()
	{
		return totalPrice;
	}


	/**
	 * Sets the total price.
	 *
	 * @param totalPrice
	 *           the new total price
	 */
	public void setTotalPrice(final TotalPriceData totalPrice)
	{
		this.totalPrice = totalPrice;
	}


	/**
	 * Gets the discounted price data.
	 *
	 * @return the discounted price data
	 */
	public DiscountedPriceData getDiscountedPriceData()
	{
		return discountedPriceData;
	}


	/**
	 * Sets the discounted price data.
	 *
	 * @param discountedPriceData
	 *           the new discounted price data
	 */
	public void setDiscountedPriceData(final DiscountedPriceData discountedPriceData)
	{
		this.discountedPriceData = discountedPriceData;
	}


	/**
	 * Gets the upgrade amount.
	 *
	 * @return the upgrade amount
	 */
	public BigDecimal getUpgradeAmount()
	{
		return upgradeAmount;
	}


	/**
	 * Sets the upgrade amount.
	 *
	 * @param upgradeAmount
	 *           the new upgrade amount
	 */
	public void setUpgradeAmount(final BigDecimal upgradeAmount)
	{
		this.upgradeAmount = upgradeAmount;
	}

	/**
	 * Gets the upgrade amount pp.
	 *
	 * @return the upgrade amount pp
	 */
	public BigDecimal getUpgradeAmountPP()
	{
		return upgradeAmountPP;
	}


	/**
	 * Sets the upgrade amount pp.
	 *
	 * @param upgradeAmountPP
	 *           the new upgrade amount pp
	 */
	public void setUpgradeAmountPP(final BigDecimal upgradeAmountPP)
	{
		this.upgradeAmountPP = upgradeAmountPP;
	}


	@Override
	public boolean equals(final Object o)
	{
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}


	@Override
	public int compareTo(final BookingClass nextOne)
	{
		if ((null != this.upgradeAmount) && (null != nextOne.upgradeAmount))
		{
			return (this.upgradeAmount).compareTo(nextOne.upgradeAmount);
		}
		else
		{
			return (this.upgradeAmountPP).compareTo(nextOne.upgradeAmountPP);
		}
	}

}
