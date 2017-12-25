package com.endeca.search.api.response.dtos;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * The Class BoardInfo used to store board information such as board code and price.
 */
public class BoardData
{

	/** The board code. */
	private String boardCode;

	/** The src code. */
	private String srcCode;

	/** The board price. */
	private BigDecimal boardPrice;

	/**
	 * Gets the board code.
	 *
	 * @return the board code
	 */
	public String getBoardCode()
	{
		return boardCode;
	}

	/**
	 * Sets the board code.
	 *
	 * @param boardCode
	 *           the new board code
	 */
	public void setBoardCode(final String boardCode)
	{
		this.boardCode = boardCode;
	}

	/**
	 * Gets the board price.
	 *
	 * @return the board price
	 */
	public BigDecimal getBoardPrice()
	{
		return boardPrice;
	}

	/**
	 * Sets the board price.
	 *
	 * @param boardPrice
	 *           the new board price
	 */
	public void setBoardPrice(final BigDecimal boardPrice)
	{
		this.boardPrice = boardPrice;
	}

	/**
	 * Gets the src code.
	 *
	 * @return the src code
	 */
	public String getSrcCode()
	{
		return srcCode;
	}

	/**
	 * Sets the src code.
	 *
	 * @param srcCode
	 *           the new src code
	 */
	public void setSrcCode(String srcCode)
	{
		this.srcCode = srcCode;
	}

	@Override
	public boolean equals(final Object o)
	{
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
