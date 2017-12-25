package com.endeca.search.api.request.dtos;

import java.util.List;


/**
 * The Class PartyInformation. DTO class having party size information.
 */
public class PartyData
{

	/** The no of infants. */
	private int noOfInfants;

	/** The no of children. */
	private int noOfChildren;

	/** The children ages. */
	private List<Integer> childrenAges;

	/** The no of adults. */
	private int noOfAdults;

	/** The no of seniors. */
	private int noOfSeniors;

	/** The no of rooms. */
	private int noOfRooms;

	/**
	 * Gets the children ages.
	 * 
	 * @return the children ages
	 */
	public List<Integer> getChildrenAges()
	{
		return childrenAges;
	}

	/**
	 * Sets the children ages.
	 * 
	 * @param childrenAges
	 *           the new children ages
	 */
	public void setChildrenAges(final List<Integer> childrenAges)
	{
		this.childrenAges = childrenAges;
	}

	/**
	 * Gets the no of infants.
	 * 
	 * @return the no of infants
	 */
	public int getNoOfInfants()
	{
		return noOfInfants;
	}

	/**
	 * Sets the no of infants.
	 * 
	 * @param noOfInfants
	 *           the new no of infants
	 */
	public void setNoOfInfants(final int noOfInfants)
	{
		this.noOfInfants = noOfInfants;
	}

	/**
	 * Gets the no of children.
	 * 
	 * @return the no of children
	 */
	public int getNoOfChildren()
	{
		return noOfChildren;
	}

	/**
	 * Sets the no of children.
	 * 
	 * @param noOfChildren
	 *           the new no of children
	 */
	public void setNoOfChildren(final int noOfChildren)
	{
		this.noOfChildren = noOfChildren;
	}

	/**
	 * Gets the no of adults.
	 * 
	 * @return the no of adults
	 */
	public int getNoOfAdults()
	{
		return noOfAdults;
	}

	/**
	 * Sets the no of adults.
	 * 
	 * @param noOfAdults
	 *           the new no of adults
	 */
	public void setNoOfAdults(final int noOfAdults)
	{
		this.noOfAdults = noOfAdults;
	}

	/**
	 * Gets the no of seniors.
	 * 
	 * @return the no of seniors
	 */
	public int getNoOfSeniors()
	{
		return noOfSeniors;
	}

	/**
	 * Sets the no of seniors.
	 * 
	 * @param noOfSeniors
	 *           the new no of seniors
	 */
	public void setNoOfSeniors(final int noOfSeniors)
	{
		this.noOfSeniors = noOfSeniors;
	}

	/**
	 * Gets the no of rooms.
	 * 
	 * @return the no of rooms
	 */
	public int getNoOfRooms()
	{
		return noOfRooms;
	}

	/**
	 * Sets the no of rooms.
	 * 
	 * @param noOfRooms
	 *           the new no of rooms
	 */
	public void setNoOfRooms(final int noOfRooms)
	{
		this.noOfRooms = noOfRooms;
	}
}
