package com.endeca.tui.anite.parameters;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;


// TODO: Auto-generated Javadoc
/**
 * The Interface AniteParameters.
 */
public interface AniteParameters extends AniteRequiredParameters
{

	/**
	 * Gets the rating.
	 * 
	 * @return the rating
	 */
	public String getRating();

	/**
	 * Gets the earliest departure date.
	 * 
	 * @return the earliest departure date
	 * @throws ParseException
	 *            the parse exception
	 */
	public Date getEarliestDepartureDate() throws ParseException;

	/**
	 * Gets the latest departure date.
	 * 
	 * @return the latest departure date
	 * @throws ParseException
	 *            the parse exception
	 */
	public Date getLatestDepartureDate() throws ParseException;

	/**
	 * Clone.
	 * 
	 * @return the anite parameters
	 */
	public AniteParameters clone();

	/**
	 * Gets the date format pattern.
	 * 
	 * @return the date format pattern
	 */
	public String getDateFormatPattern();

	/**
	 * Gets the passthrough map.
	 * 
	 * @return the passthrough map
	 */
	public Map<String, String> getPassthroughMap();

	/**
	 * Checks if is faceting.
	 * 
	 * @return true, if is faceting
	 */
	public boolean isFaceting();

	/**
	 * Gets the board basis.
	 * 
	 * @return the board basis
	 */
	public String getBoardBasis();

	/**
	 * Gets the dream liner.
	 * 
	 * @return the dream liner
	 */
	public String getDreamLiner();

	/**
	 * Gets the n string.
	 * 
	 * @return the n string
	 */
	public String getnString();
}
