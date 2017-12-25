package com.endeca.search.api.crs.accom.board.service;

import java.util.List;

import com.endeca.search.api.response.dtos.BoardData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.RoomData;


/**
 * The Interface CRSSoapService to get the boardBasis list from CRS.
 */
public interface CRSAccomBoardService
{

	/**
	 * Gets the available board basis.
	 * 
	 * @param currentPackage
	 *           the current package
	 * @param room
	 *           the room
	 * @return the available board basis
	 */
	public List<BoardData> getAvailableBoardBasis(final PackageData currentPackage, final RoomData room);
}
