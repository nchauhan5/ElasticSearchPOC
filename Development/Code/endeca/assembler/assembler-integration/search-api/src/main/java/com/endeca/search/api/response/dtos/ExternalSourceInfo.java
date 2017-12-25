/**
 *
 */
package com.endeca.search.api.response.dtos;

/**
 *
 */
public class ExternalSourceInfo
{

	/** The external room code. */
	private String externalRoomCode;

	/** The external board basis. */
	private String externalBoardBasis;

	/**
	 * Gets the external room code.
	 *
	 * @return the external room code
	 */
	public String getExternalRoomCode()
	{
		return externalRoomCode;
	}

	/**
	 * Sets the external room code.
	 *
	 * @param externalRoomCode
	 *           the new external room code
	 */
	public void setExternalRoomCode(String externalRoomCode)
	{
		this.externalRoomCode = externalRoomCode;
	}

	/**
	 * Gets the external board basis.
	 *
	 * @return the external board basis
	 */
	public String getExternalBoardBasis()
	{
		return externalBoardBasis;
	}

	/**
	 * Sets the external board basis.
	 *
	 * @param externalBoardBasis
	 *           the new external board basis
	 */
	public void setExternalBoardBasis(String externalBoardBasis)
	{
		this.externalBoardBasis = externalBoardBasis;
	}

}
