//$Rev: 577 $
package com.endeca.tui.anite.unmarshaller;

import java.io.InputStream;

import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteUnmarshallingException;


// TODO: Auto-generated Javadoc
/**
 * The Interface AniteResponseStreamUnmarshaller.
 */
public interface AniteResponseStreamUnmarshaller
{

	/**
	 * Unmarshall.
	 * 
	 * @param <T>
	 *           the generic type
	 * @param type
	 *           the type
	 * @param in
	 *           the in
	 * @return the t
	 * @throws AniteUnmarshallingException
	 *            the anite unmarshalling exception
	 */
	public <T> T unmarshall(AniteQueryType type, InputStream in) throws AniteUnmarshallingException;
}
