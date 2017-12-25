package com.endeca.search.api.adapters;

import com.endeca.search.api.adapter.CodeFormatAdapter;


/**
 * The Class PaddingAdapter.
 */
public class PaddingAdapter implements CodeFormatAdapter
{

	/** The Constant DEFAULT_PAD_CHARACTER. */
	private static final String DEFAULT_PAD_CHARACTER = "0";

	/** The pad character. */
	private String padCharacter;

	/** The pad character2. */
	private String padCharacter2;

	/** The find regex. */
	private String regex;

	/** The find regex2. */
	private String regex2;

	/**
	 * Instantiates a new padding adapter.
	 */
	public PaddingAdapter()
	{
		this(DEFAULT_PAD_CHARACTER, DEFAULT_PAD_CHARACTER);
	}

	/**
	 * Instantiates a new padding adapter.
	 * 
	 * @param padCharacter
	 *           the pad character
	 * @param padCharacter2
	 *           the pad character2
	 */
	public PaddingAdapter(final String padCharacter, final String padCharacter2)
	{
		super();
		this.padCharacter = padCharacter;
		this.padCharacter2 = padCharacter2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.infront.navigation.request.support.CodeFormatAdapter#endeca2anite(java.lang.String)
	 */
	@Override
	public String endeca2SearchAPI(final String code)
	{
		this.setRegex(padCharacter);
		return code.replaceAll(this.getRegex(), "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.search.api.adapter.CodeFormatAdapter#accommCodeConverter(java.lang.String)
	 */
	@Override
	public String accommCodeConverter(final String code)
	{
		setRegex2(padCharacter2);
		return code.replaceAll(getRegex2(), "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.infront.navigation.request.support.CodeFormatAdapter#anite2endeca(java.lang.String)
	 */
	@Override
	public String searchAPI2endeca(final String code)
	{
		return code;
	}

	/**
	 * Gets the pad character.
	 * 
	 * @return the pad character
	 */
	public String getPadCharacter()
	{
		return padCharacter;
	}

	/**
	 * Sets the pad character.
	 * 
	 * @param padCharacter
	 *           the new pad character
	 */
	public final void setPadCharacter(final String padCharacter)
	{
		this.padCharacter = padCharacter;
	}

	/**
	 * Sets the regex.
	 * 
	 * @param regex
	 *           the new regex
	 */
	public void setRegex(final String regex)
	{
		this.regex = "^" + getPadCharacter() + "*";
	}

	/**
	 * Gets the regex.
	 * 
	 * @return the regex
	 */
	public String getRegex()
	{
		return this.regex;
	}

	/**
	 * Gets the pad character2.
	 * 
	 * @return the pad character2
	 */
	public String getPadCharacter2()
	{
		return padCharacter2;
	}

	/**
	 * Sets the pad character2.
	 * 
	 * @param padCharacter2
	 *           the new pad character2
	 */
	public void setPadCharacter2(final String padCharacter2)
	{
		this.padCharacter2 = padCharacter2;
	}

	/**
	 * Gets the regex2.
	 * 
	 * @return the regex2
	 */
	public String getRegex2()
	{
		return regex2;
	}

	/**
	 * Sets the regex2.
	 * 
	 * @param regex2
	 *           the new regex2
	 */
	public void setRegex2(final String regex2)
	{
		this.regex2 = "^" + getPadCharacter2() + "*";
	}
}
