package com.endeca.search.api.sort.parameter;

/**
 * @author shurde
 *
 */
public enum SortingOrder
{
	/**
	 * 
	 */
	ASCENDING("ascending"),
	/**
	 * 
	 */
	DESCENDING("descending"); 
	
	/** The order. */
	String order ;
	
	/**
	 * The Constructor.
	 *
	 * @param order the order
	 */	
	private SortingOrder(String order)
	{
		
	}
	
	/**
	 * Sets the order.
	 *
	 * @param order the order
	 */
	public void setOrder(String order) {
		this.order = order;
	}
}
