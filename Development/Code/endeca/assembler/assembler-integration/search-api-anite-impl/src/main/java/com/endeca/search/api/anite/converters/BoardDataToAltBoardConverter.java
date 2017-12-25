package com.endeca.search.api.anite.converters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.endeca.search.api.response.dtos.BoardData;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.AltBoard;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.AltBoard.Board;


/**
 * The Class BoardDataToAltBoardConverter.
 */
public class BoardDataToAltBoardConverter
{

	/**
	 * Instantiates a new board data to alt board converter.
	 */
	private BoardDataToAltBoardConverter()
	{
		// Added to avoid instantiation since this is a static utility class.
	}

	/**
	 * Convert.
	 * 
	 * @param boardDatas
	 *           the board datas
	 * @return the alt board
	 */
	public static AltBoard convert(final List<BoardData> boardDatas)
	{
		AltBoard altBoard = null;

		if (CollectionUtils.isNotEmpty(boardDatas))
		{
			altBoard = new AltBoard();
			final List<Board> boards = new ArrayList<Board>();
			for (final BoardData boardData : boardDatas)
			{
				final Board board = new Board();
				board.setCode(boardData.getBoardCode());
				board.setPrice(boardData.getBoardPrice());
				boards.add(board);
			}
			altBoard.getBoard().addAll(boards);
		}

		return altBoard;
	}
}
