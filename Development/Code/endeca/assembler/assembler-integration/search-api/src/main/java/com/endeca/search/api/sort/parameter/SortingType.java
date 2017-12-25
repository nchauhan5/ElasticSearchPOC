
package com.endeca.search.api.sort.parameter;


/**
 * @author shurde
 *
 */
public enum SortingType
{

	/** The alpha. */
	ALPHA,
	/** The integer. */
	INTEGER,
	/** The float. */
	FLOAT,
	/** The long. */
	LONG;

	/** The type. */
	String type;

	/**
	 * Sets the type.
	 *
	 * @param type
	 *           the type
	 */
	public void setType(String type)
	{
		this.type = type;
	}


}
