package com.endeca.tui.anite;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteException;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;
import com.endeca.tui.anite.responses.AniteDurationsResponse;


// TODO: Auto-generated Javadoc
/**
 * The Class DurationsPreprocessor.
 */
public class DurationsPreprocessor
{

	/** The Constant AUTOSELECTED_DURATION_IMPOSSIBLE. */
	public static final short AUTOSELECTED_DURATION_IMPOSSIBLE = -1;

	/** The anite interface. */
	AniteInterface aniteInterface;

	/**
	 * Select available duration.
	 * 
	 * @param params
	 *           the params
	 * @param durations
	 *           the durations
	 * @return the short
	 * @throws AniteException
	 *            the anite exception
	 */
	public short selectAvailableDuration(final AniteRequiredParameters params, final short... durations) throws AniteException
	{
		return selectAvailableDuration(getAniteInterface(), params, durations);
	}

	/**
	 * Select available duration.
	 * 
	 * @param aniteInterface
	 *           the anite interface
	 * @param params
	 *           the params
	 * @param durations
	 *           the durations
	 * @return the short
	 * @throws AniteException
	 *            the anite exception
	 */
	public static short selectAvailableDuration(final AniteInterface aniteInterface, final AniteRequiredParameters params,
			final short... durations) throws AniteException
	{
		//final AniteDurationsResponse durationsResponse = aniteInterface.query(AniteQueryType.DURATIONS, params);
		List<Short> durationList = aniteInterface.query(AniteQueryType.DURATIONS, params);
		final Set<Short> availableStays = new HashSet<Short>(durationList);
		for (final short duration : durations)
		{
			if (availableStays.contains(duration))
			{
				return duration;
			}
		}
		return AUTOSELECTED_DURATION_IMPOSSIBLE;
	}

	/**
	 * Gets the anite interface.
	 * 
	 * @return the anite interface
	 */
	public AniteInterface getAniteInterface()
	{
		return aniteInterface;
	}

	/**
	 * Sets the anite interface.
	 * 
	 * @param aniteInterface
	 *           the new anite interface
	 */
	public void setAniteInterface(final AniteInterface aniteInterface)
	{
		this.aniteInterface = aniteInterface;
	}
}
