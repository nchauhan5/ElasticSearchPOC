//$Rev: 494 $
package com.endeca.tui.anite;

/**
 * A factory for creating ResponseBuffer objects.
 */
public interface ResponseBufferFactory
{

	/**
	 * New response buffer.
	 * 
	 * @return the response buffer
	 */
	ResponseBuffer newResponseBuffer();

	/**
	 * New response buffer.
	 * 
	 * @param name
	 *           the name
	 * @return the response buffer
	 */
	ResponseBuffer newResponseBuffer(String name);
}
