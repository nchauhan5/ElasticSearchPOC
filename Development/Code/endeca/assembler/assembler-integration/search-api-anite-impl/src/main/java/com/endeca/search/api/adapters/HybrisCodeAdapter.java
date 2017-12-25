package com.endeca.search.api.adapters;

/**
 * The Class HybrisCodeAdapter.
 */
public class HybrisCodeAdapter extends PaddingAdapter
{

	/** The Constant padCharacter. */
	private static final String PADCHARACTER = "P-";

	private static final String PADCHARACTER2 = "PC-";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.infront.navigation.request.support.PaddingAdapter#getPadCharacter()
	 */
	@Override
	public String getPadCharacter()
	{
		return PADCHARACTER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.infront.navigation.request.support.PaddingAdapter#getPadCharacter2()
	 */
	@Override
	public String getPadCharacter2()
	{
		return PADCHARACTER2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.infront.navigation.request.support.PaddingAdapter#getRegex()
	 */
	@Override
	public String getRegex()
	{
		return "^" + getPadCharacter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.infront.navigation.request.support.PaddingAdapter#getRegex2()
	 */
	@Override
	public String getRegex2()
	{
		return "^" + getPadCharacter2();
	}
}
