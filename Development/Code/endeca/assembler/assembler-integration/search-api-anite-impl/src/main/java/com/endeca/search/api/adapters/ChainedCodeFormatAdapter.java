package com.endeca.search.api.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.ListUtils;

import com.endeca.search.api.adapter.CodeFormatAdapter;


/**
 * The Class ChainedCodeFormatAdapter.
 */
public class ChainedCodeFormatAdapter implements CodeFormatAdapter
{

	/** The reverse adapters. */
	@SuppressWarnings("unchecked")
	List<CodeFormatAdapter> adapters = ListUtils.EMPTY_LIST, reverseAdapters = ListUtils.EMPTY_LIST;


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.infront.navigation.request.support.CodeFormatAdapter#searchAPI2endeca(java.lang.String)
	 */
	@Override
	public String searchAPI2endeca(final String code)
	{
		String tempCode = code;
		for (final CodeFormatAdapter codeFormatAdapter : getAdapters())
		{
			tempCode = codeFormatAdapter.searchAPI2endeca(tempCode);
		}
		return tempCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.infront.navigation.request.support.CodeFormatAdapter#endeca2SearchAPI(java.lang.String)
	 */
	@Override
	public String endeca2SearchAPI(final String code)
	{
		String tempCode = code;
		for (final CodeFormatAdapter codeFormatAdapter : getAdapters())
		{
			tempCode = codeFormatAdapter.endeca2SearchAPI(tempCode);
		}
		return tempCode;
	}

	/**
	 * Gets the adapters.
	 * 
	 * @return the adapters
	 */
	@SuppressWarnings("unchecked")
	public List<CodeFormatAdapter> getAdapters()
	{
		return ListUtils.unmodifiableList(adapters);
	}

	/**
	 * Sets the adapters.
	 * 
	 * @param newAdapters
	 *           the new adapters
	 */
	public void setAdapters(final List<CodeFormatAdapter> newAdapters)
	{
		adapters = new ArrayList<CodeFormatAdapter>(newAdapters);
		reverseAdapters = new ArrayList<CodeFormatAdapter>(newAdapters);
		Collections.reverse(reverseAdapters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.infront.navigation.request.support.CodeFormatAdapter#accommCodeConverter(java.lang.String)
	 */
	@Override
	public String accommCodeConverter(String code)
	{
		String tempCode = code;
		for (final CodeFormatAdapter codeFormatAdapter : getAdapters())
		{
			tempCode = codeFormatAdapter.accommCodeConverter(tempCode);
		}
		return tempCode;
	}
}
