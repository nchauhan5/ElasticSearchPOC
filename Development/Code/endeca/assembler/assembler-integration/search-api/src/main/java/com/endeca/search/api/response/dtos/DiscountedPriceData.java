package com.endeca.search.api.response.dtos;

import java.math.BigDecimal;


/**
 * The Class DiscountedPriceData.
 */
public class DiscountedPriceData
{

	/** The discount price. */
	private BigDecimal discountPrice;

	/** The discount price per person. */
	private BigDecimal discountPricePerPerson;

	/**
	 * Gets the discount price.
	 * 
	 * @return the discount price
	 */
	public BigDecimal getDiscountPrice()
	{
		return discountPrice;
	}

	/**
	 * Sets the discount price.
	 * 
	 * @param discountPrice
	 *           the new discount price
	 */
	public void setDiscountPrice(final BigDecimal discountPrice)
	{
		this.discountPrice = discountPrice;
	}

	/**
	 * Gets the discount price per person.
	 * 
	 * @return the discount price per person
	 */
	public BigDecimal getDiscountPricePerPerson()
	{
		return discountPricePerPerson;
	}

	/**
	 * Sets the discount price per person.
	 * 
	 * @param discountPricePerPerson
	 *           the new discount price per person
	 */
	public void setDiscountPricePerPerson(final BigDecimal discountPricePerPerson)
	{
		this.discountPricePerPerson = discountPricePerPerson;
	}

}
