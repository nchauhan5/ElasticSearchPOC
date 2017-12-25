package com.endeca.tui.anite;

import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteException;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;


// TODO: Auto-generated Javadoc
/**
 * The Interface AniteInterface.
 */
public interface AniteInterface
{

	/**
	 * Query.
	 * 
	 * @param <T>
	 *           the generic type
	 * @param type
	 *           the type
	 * @param params
	 *           the params
	 * @return the t
	 * @throws AniteException
	 *            the anite exception
	 */
	public <T> T query(AniteQueryType type, AniteRequiredParameters params) throws AniteException;
}
