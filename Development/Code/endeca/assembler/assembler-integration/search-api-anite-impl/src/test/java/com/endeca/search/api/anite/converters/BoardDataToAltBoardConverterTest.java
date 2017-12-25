package com.endeca.search.api.anite.converters;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import  static org.hamcrest.Matchers.is;
import org.junit.Assert;
import org.junit.Test;


import com.endeca.search.api.response.dtos.BoardData;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.AltBoard;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.AltBoard.Board;

public class BoardDataToAltBoardConverterTest {

	/*This method is to test ,whether board data converted to altdata when passed board data is not empty*/
	@Test
	public void boardDataShouldBeConvertedToAltBoardWhenBoardDataIsNotEmpty() {
		List<BoardData> boardDatas=new ArrayList<BoardData>();
		BoardData boardData= new BoardData();
		boardDatas.add(boardData);
		boardData.setBoardCode("B-0008");
		boardData.setBoardPrice(new BigDecimal(56));
		 AltBoard altBoard=BoardDataToAltBoardConverter.convert(boardDatas);
        assertThat(altBoard.getBoard().get(0).getPrice(),is(new BigDecimal(56)));
        assertThat(altBoard.getBoard().get(0).getCode(),is("B-0008"));
	}
	
	/*This method is to test ,altboard with null value should be returned when passed board data is empty*/
	@Test
	public void boardDataShouldNotBeSetToAltBoardWhenBoardDataIsEmpty() {
		List<BoardData> boardDatas=null;
		 AltBoard altBoard=BoardDataToAltBoardConverter.convert(boardDatas);
        Assert.assertNull(altBoard);
	}	
}
