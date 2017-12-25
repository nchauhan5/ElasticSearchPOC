//$Rev: 537 $
package com.endeca.tui.anite.io.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


/**
 * The Class ParamHelper.
 */
public class ParamHelper
{

	/** The pairs. */
	List<NameValuePair> pairs = new ArrayList<NameValuePair>();

	/**
	 * Adds the.
	 * 
	 * @param key
	 *           the key
	 * @param value
	 *           the value
	 * @return the param helper
	 */
	public ParamHelper add(final String key, final Object value)
	{
		pairs.add(new BasicNameValuePair(key, value.toString()));
		return this;
	}

	/**
	 * Gets the pairs.
	 * 
	 * @return the pairs
	 */
	public List<NameValuePair> getPairs()
	{
		return pairs;
	}

}
