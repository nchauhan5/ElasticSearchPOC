package com.elastic.response;

import java.util.HashMap;
import java.util.Map;


/**
 * The Class AssemblerResponse.
 */
public class AssemblerResponse
{
	private Map<String, Object> attributes = new HashMap<String, Object>();

	/**
	 * Gets the attributes.
	 *
	 * @return the attributes
	 */
	public Map<String, Object> getAttributes()
	{
		return attributes;
	}

	/**
	 * Sets the attributes.
	 *
	 * @param attributes
	 *           the attributes
	 */
	public void setAttributes(Map<String, Object> attributes)
	{
		this.attributes = attributes;
	}
}
