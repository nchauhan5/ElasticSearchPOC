//$Rev: 577 $
package com.endeca.tui.anite.io;

import java.io.InputStream;

import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteCommunicationException;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;


// TODO: Auto-generated Javadoc
/**
 * The Interface AniteResponseStreamBuilder.
 */
public interface AniteResponseStreamBuilder
{

	/**
	 * Builds the query input stream.
	 * 
	 * @param <T>
	 *           the generic type
	 * @param type
	 *           the type
	 * @param params
	 *           the params
	 * @return the input stream
	 * @throws AniteCommunicationException
	 *            the anite communication exception
	 */
	<T> InputStream buildQueryInputStream(AniteQueryType type, AniteRequiredParameters params) throws AniteCommunicationException;
}
