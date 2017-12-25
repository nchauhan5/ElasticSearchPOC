//$Rev: 494 $
package com.endeca.search.api.adapter;

/**
 * The Interface CodeFormatAdapter.
 */
public interface CodeFormatAdapter
{


	/**
	 * Endeca2 search api.
	 *
	 * @param code
	 *           the code
	 * @return the string
	 */
	String endeca2SearchAPI(String code);


	/**
	 * Search ap i2endeca.
	 *
	 * @param code
	 *           the code
	 * @return the string
	 */
	String searchAPI2endeca(String code);

	/**
	 * Accommodation Code Converter.
	 *
	 * @param code
	 *           the code
	 * @return the string
	 */
	String accommCodeConverter(String code);
}
