package com.endeca.search.api.response.dtos;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * The Class PartyInfo. Response DTO to store party size information.
 */
public class PartyData
{

	/** The adults. */
	private int adults;

	/** The children. */
	private int children;

	/** The infants. */
	private int infants;

	/** The senior citizens. */
	private int seniorCitizens;

	/** The youth. */
	private int youth;

	/**
	 * Gets the adults.
	 *
	 * @return the adults
	 */
	public int getAdults()
	{
		return adults;
	}

	/**
	 * Sets the adults.
	 *
	 * @param adults
	 *           the new adults
	 */
	public void setAdults(final int adults)
	{
		this.adults = adults;
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public int getChildren()
	{
		return children;
	}

	/**
	 * Sets the children.
	 *
	 * @param children
	 *           the new children
	 */
	public void setChildren(final int children)
	{
		this.children = children;
	}

	/**
	 * Gets the infants.
	 *
	 * @return the infants
	 */
	public int getInfants()
	{
		return infants;
	}

	/**
	 * Sets the infants.
	 *
	 * @param infants
	 *           the new infants
	 */
	public void setInfants(final int infants)
	{
		this.infants = infants;
	}

	/**
	 * Gets the senior citizens.
	 *
	 * @return the senior citizens
	 */
	public int getSeniorCitizens()
	{
		return seniorCitizens;
	}

	/**
	 * Sets the senior citizens.
	 *
	 * @param seniorCitizens
	 *           the new senior citizens
	 */
	public void setSeniorCitizens(final int seniorCitizens)
	{
		this.seniorCitizens = seniorCitizens;
	}

	/**
	 * Gets the youth.
	 *
	 * @return the youth
	 */
	public int getYouth()
	{
		return youth;
	}

	/**
	 * Sets the youth.
	 *
	 * @param youth
	 *           the new youth
	 */
	public void setYouth(final int youth)
	{
		this.youth = youth;
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
