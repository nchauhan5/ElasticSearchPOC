package com.endeca.tui.anite.enums;

// TODO: Auto-generated Javadoc
/**
 * The Enum TimeUnits.
 */
public enum TimeUnits
{

	/** The d. */
	D(1),
	/** The w. */
	W(7),
	/** The m. */
	M(30);

	/** The multiplier. */
	int multiplier;

	/**
	 * Instantiates a new time units.
	 * 
	 * @param factor
	 *           the factor
	 */
	private TimeUnits(final int factor)
	{
		this.multiplier = factor;
	}

	/**
	 * Gets the multiplier.
	 * 
	 * @return the multiplier
	 */
	public int getMultiplier()
	{
		return multiplier;
	}
}
