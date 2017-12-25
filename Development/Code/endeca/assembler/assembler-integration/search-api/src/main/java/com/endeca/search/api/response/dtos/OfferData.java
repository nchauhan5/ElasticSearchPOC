package com.endeca.search.api.response.dtos;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


// TODO: Auto-generated Javadoc
/**
 * The Class OfferInfo. DTO to store offer level information.
 */
public class OfferData
{

	/** The date. */
	private Date date;

	/** The stay. */
	private int stay;

	/** The price total. */
	private BigDecimal discountedPrice;

	/** The price per person. */
	private BigDecimal discountedPricePerson;

	/** The deposit. */
	private BigDecimal deposit;

	/** The coach transfer. */
	private boolean coachTransfer;

	/** The car hire. */
	private boolean carHire;

	/** The world care. */
	private boolean worldCare;

	/** The package id. */
	private String packageID;


	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate()
	{
		return (Date) date.clone();
	}



	/**
	 * Sets the date.
	 *
	 * @param date
	 *           the new date
	 */
	public void setDate(final Date date)
	{
		this.date = new Date(date.getTime());
	}



	/**
	 * Gets the stay.
	 *
	 * @return the stay
	 */
	public int getStay()
	{
		return stay;
	}



	/**
	 * Sets the stay.
	 *
	 * @param stay
	 *           the new stay
	 */
	public void setStay(final int stay)
	{
		this.stay = stay;
	}





	/**
	 * Gets the deposit.
	 *
	 * @return the deposit
	 */
	public BigDecimal getDeposit()
	{
		return deposit;
	}



	/**
	 * Sets the deposit.
	 *
	 * @param deposit
	 *           the new deposit
	 */
	public void setDeposit(final BigDecimal deposit)
	{
		this.deposit = deposit;
	}



	/**
	 * Checks if is coach transfer.
	 *
	 * @return true, if is coach transfer
	 */
	public boolean isCoachTransfer()
	{
		return coachTransfer;
	}



	/**
	 * Sets the coach transfer.
	 *
	 * @param coachTransfer
	 *           the new coach transfer
	 */
	public void setCoachTransfer(final boolean coachTransfer)
	{
		this.coachTransfer = coachTransfer;
	}



	/**
	 * Checks if is car hire.
	 *
	 * @return true, if is car hire
	 */
	public boolean isCarHire()
	{
		return carHire;
	}



	/**
	 * Sets the car hire.
	 *
	 * @param carHire
	 *           the new car hire
	 */
	public void setCarHire(final boolean carHire)
	{
		this.carHire = carHire;
	}



	/**
	 * Checks if is world care.
	 *
	 * @return true, if is world care
	 */
	public boolean isWorldCare()
	{
		return worldCare;
	}



	/**
	 * Sets the world care.
	 *
	 * @param worldCare
	 *           the new world care
	 */
	public void setWorldCare(final boolean worldCare)
	{
		this.worldCare = worldCare;
	}



	/**
	 * Gets the discounted price.
	 *
	 * @return the discounted price
	 */
	public BigDecimal getDiscountedPrice()
	{
		return discountedPrice;
	}



	/**
	 * Sets the discounted price.
	 *
	 * @param discountedPrice
	 *           the new discounted price
	 */
	public void setDiscountedPrice(final BigDecimal discountedPrice)
	{
		this.discountedPrice = discountedPrice;
	}



	/**
	 * Gets the discounted price person.
	 *
	 * @return the discounted price person
	 */
	public BigDecimal getDiscountedPricePerson()
	{
		return discountedPricePerson;
	}



	/**
	 * Sets the discounted price person.
	 *
	 * @param discountedPricePerson
	 *           the new discounted price person
	 */
	public void setDiscountedPricePerson(final BigDecimal discountedPricePerson)
	{
		this.discountedPricePerson = discountedPricePerson;
	}

	/**
	 * Gets the package id.
	 *
	 * @return the package id
	 */
	public String getPackageID()
	{
		return packageID;
	}

	/**
	 * Sets the package id.
	 *
	 * @param packageID
	 *           the new package id
	 */
	public void setPackageID(final String packageID)
	{
		this.packageID = packageID;
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
