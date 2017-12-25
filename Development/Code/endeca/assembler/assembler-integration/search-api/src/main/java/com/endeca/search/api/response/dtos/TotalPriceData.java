package com.endeca.search.api.response.dtos;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


// TODO: Auto-generated Javadoc
/**
 * The Class TotalPriceData.
 */
public class TotalPriceData
{

	/** The discount price. */
	private BigDecimal totalPrice;

	/** The discount price per person. */
	private BigDecimal totalPricePerPerson;

	/**
	 * Gets the total price.
	 *
	 * @return the total price
	 */
	public BigDecimal getTotalPrice()
	{
		return totalPrice;
	}

	/**
	 * Sets the total price.
	 *
	 * @param totalPrice
	 *           the new total price
	 */
	public void setTotalPrice(final BigDecimal totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	/**
	 * Gets the total price per person.
	 *
	 * @return the total price per person
	 */
	public BigDecimal getTotalPricePerPerson()
	{
		return totalPricePerPerson;
	}

	/**
	 * Sets the total price per person.
	 *
	 * @param totalPricePerPerson
	 *           the new total price per person
	 */
	public void setTotalPricePerPerson(final BigDecimal totalPricePerPerson)
	{
		this.totalPricePerPerson = totalPricePerPerson;
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


}
