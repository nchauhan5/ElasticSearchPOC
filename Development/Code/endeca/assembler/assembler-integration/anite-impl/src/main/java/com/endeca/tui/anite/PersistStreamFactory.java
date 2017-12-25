//$Rev: 494 $
package com.endeca.tui.anite;

import java.io.IOException;
import java.io.OutputStream;


/**
 * A factory for creating PersistStream objects.
 */
public interface PersistStreamFactory
{

	/**
	 * New persist output stream.
	 * 
	 * @return the output stream
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	OutputStream newPersistOutputStream() throws IOException;

	/**
	 * New persist output stream.
	 * 
	 * @param name
	 *           the name
	 * @return the output stream
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	OutputStream newPersistOutputStream(String name) throws IOException;
}
