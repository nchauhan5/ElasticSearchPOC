//$Rev: 494 $
package com.endeca.tui.anite;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * The Interface ResponseBuffer.
 */
public interface ResponseBuffer
{

	/**
	 * Gets the buffer output stream.
	 * 
	 * @return the buffer output stream
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	OutputStream getBufferOutputStream() throws IOException;

	/**
	 * Gets the buffer input stream.
	 * 
	 * @return the buffer input stream
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	InputStream getBufferInputStream() throws IOException;

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *           the new name
	 */
	void setName(String name);

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * Persist.
	 * 
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	void persist() throws IOException;

	/**
	 * Dispose.
	 */
	void dispose();
}
