package com.endeca.search.api.response.dtos;


// TODO: Auto-generated Javadoc
/**
 * The Class DiscountData. to store discount related information
 */
public class DiscountData
{
	
	/** The discount price. */
	private Double discountPrice;
	
	/** The discount price per person. */
	private Double discountPricePerPerson;

	/**
	 * Gets the discount price.
	 *
	 * @return the discount price
	 */
	public Double getDiscountPrice()
	{
		return discountPrice;
	}

	/**
	 * Sets the discount price.
	 *
	 * @param discountPrice the new discount price
	 */
	public void setDiscountPrice(Double discountPrice)
	{
		this.discountPrice = discountPrice;
	}

	/**
	 * Gets the discount price per person.
	 *
	 * @return the discount price per person
	 */
	public Double getDiscountPricePerPerson()
	{
		return discountPricePerPerson;
	}

	/**
	 * Sets the discount price per person.
	 *
	 * @param discountPricePerPerson the new discount price per person
	 */
	public void setDiscountPricePerPerson(Double discountPricePerPerson)
	{
		this.discountPricePerPerson = discountPricePerPerson;
	}

	
	
	
}
