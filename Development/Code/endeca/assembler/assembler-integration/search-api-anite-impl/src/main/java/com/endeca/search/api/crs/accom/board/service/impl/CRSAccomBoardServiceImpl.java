package com.endeca.search.api.crs.accom.board.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.endeca.infront.shaded.org.apache.commons.lang.StringUtils;
import com.endeca.search.api.constants.ConfiguratorConstants;
import com.endeca.search.api.crs.accom.board.service.CRSAccomBoardService;
import com.endeca.search.api.response.dtos.BoardData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.PartyData;
import com.endeca.search.api.response.dtos.RoomData;
import com.saxml09i.schemas.saxml09iinterface.COXML09;
import com.saxml09i.schemas.saxml09iinterface.Coxml09Crsheaderinfo;
import com.saxml09i.schemas.saxml09iinterface.Coxml09CrsheaderinfoIdentificationinfo;
import com.saxml09i.schemas.saxml09iinterface.Coxml09Crsuserdata;
import com.saxml09i.schemas.saxml09iinterface.Coxml09CrsuserdataSearchmealrq;
import com.saxml09i.schemas.saxml09iinterface.Coxml09CrsuserdataSearchmealrqSearchages;
import com.saxml09i.schemas.saxml09iinterface.ObjectFactory;
import com.saxml09o.schemas.saxml09ointerface.Coxml09CrsuserdataSearchmealrs;
import com.saxml09o.schemas.saxml09ointerface.Coxml09CrsuserdataSearchmealrsAvailableboardAllinclusive;
import com.saxml09o.schemas.saxml09ointerface.Coxml09CrsuserdataSearchmealrsAvailableboardBreakfast;
import com.saxml09o.schemas.saxml09ointerface.Coxml09CrsuserdataSearchmealrsAvailableboardFullboard;
import com.saxml09o.schemas.saxml09ointerface.Coxml09CrsuserdataSearchmealrsAvailableboardHalfboard;


/**
 * The Class CRSSoapServiceImpl that will hit the CRS endPoint and get all available boards with this roomtype group.
 */
public class CRSAccomBoardServiceImpl implements CRSAccomBoardService
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CRSAccomBoardServiceImpl.class);

	/** The brand specific request map. */
	private Map<String, Map<String, String>> brandSpecificRequestMap;

	/** The configured board basis. */
	private Map<String, String> configuredBoardBasis;

	/** The web service template. */
	private WebServiceTemplate webServiceTemplate;

	@Override
	public List<BoardData> getAvailableBoardBasis(final PackageData packageData, final RoomData currentRoom)
	{
		List<BoardData> boards = new ArrayList<BoardData>();
		final COXML09 coxml09Part = new ObjectFactory().createCOXML09();
		final Coxml09Crsheaderinfo crsHeaderInfo = new Coxml09Crsheaderinfo();

		Coxml09CrsheaderinfoIdentificationinfo identificationInfo;
		identificationInfo = getBrandSpecificRequest(brandSpecificRequestMap.get(packageData.getHotelData().getBrand()));
		crsHeaderInfo.setIdentificationInfo(identificationInfo);

		final Coxml09Crsuserdata crsUserData = new Coxml09Crsuserdata();
		final Coxml09CrsuserdataSearchmealrq searchMealRQ = new Coxml09CrsuserdataSearchmealrq();

		searchMealRQ.setInternalFlightInfo(packageData.getOutboundFlight().get(0).getFlightInfo().trim());
		searchMealRQ.setInternalHotelInfo(packageData.getHotelData().getSysInfo());

		final PartyData party = currentRoom.getPartyData();
		searchMealRQ.setSearchAdult((short) party.getAdults());
		searchMealRQ.setSearchChild((short) party.getChildren());
		searchMealRQ.setSearchInfant((short) party.getInfants());
		searchMealRQ.setSearchYouth((short) party.getYouth());

		final Coxml09CrsuserdataSearchmealrqSearchages ages = new Coxml09CrsuserdataSearchmealrqSearchages();
		searchMealRQ.setSearchAges(ages);

		crsUserData.setSearchMealRQ(searchMealRQ);
		coxml09Part.setCRSHeaderInfo(crsHeaderInfo);
		coxml09Part.setCRSUserData(crsUserData);

		try
		{
			final com.saxml09o.schemas.saxml09ointerface.COXML09 responseFromCRS = (com.saxml09o.schemas.saxml09ointerface.COXML09) webServiceTemplate
					.marshalSendAndReceive(coxml09Part);

			final Coxml09CrsuserdataSearchmealrs mealResponse = responseFromCRS.getCRSUserData().getSearchMealRS();
			final String crsErrorCode = mealResponse.getErrorCode();
			if (StringUtils.isBlank(crsErrorCode))
			{
				boards = getBoardsForRoom(mealResponse.getAvailableBoard(), currentRoom);
				currentRoom.setAvailableBoards(boards);
			}
			else
			{
				currentRoom.setCrsErrorCode(crsErrorCode);
				currentRoom.setCrsErrorMessage(mealResponse.getErrorMessage());
			}
		}
		catch (final WebServiceClientException e)
		{
			LOGGER.error("WebServiceException occured while fetching alternate boards list from CRS : " + e.getMessage(), e);
		}

		catch (IllegalStateException e)
		{
			LOGGER.error("IllegalStateException occured while fetching alternate boards list from CRS : " + e.getMessage(), e);
		}

		return boards;
	}

	final Coxml09CrsheaderinfoIdentificationinfo getBrandSpecificRequest(final Map<String, String> brandMap)
	{
		final Coxml09CrsheaderinfoIdentificationinfo indentificationRequestInfo = new Coxml09CrsheaderinfoIdentificationinfo();

		indentificationRequestInfo.setTypeOfAction("SearchMealRQ");
		indentificationRequestInfo.setCodePage(brandMap.get("codePage"));
		indentificationRequestInfo.setAgentNo(brandMap.get("agentNo"));
		indentificationRequestInfo.setTerminalId("MATS");
		indentificationRequestInfo.setCurrencyCode(brandMap.get("currencyCode"));
		indentificationRequestInfo.setTextLanguage(brandMap.get("textLanguage"));

		indentificationRequestInfo.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		indentificationRequestInfo.setCurrentTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
		indentificationRequestInfo.setDefaultMarket(brandMap.get("defaultMarket"));
		indentificationRequestInfo.setCurrentMarket(brandMap.get("currentMarket"));
		indentificationRequestInfo.setSystemEnvironment(brandMap.get("systemEnvironment"));

		return indentificationRequestInfo;
	}


	/**
	 * Update room with boards.
	 *
	 * @param availableBoards
	 *           the available boards
	 * @param room
	 *           the room
	 * @return the list
	 */
	public List<BoardData> getBoardsForRoom(
			final com.saxml09o.schemas.saxml09ointerface.Coxml09CrsuserdataSearchmealrsAvailableboard availableBoards,
			final RoomData room)
	{
		final List<BoardData> boardsList = new ArrayList<BoardData>();
		if (null != availableBoards)
		{
			final Coxml09CrsuserdataSearchmealrsAvailableboardAllinclusive allInclusiveCRS = availableBoards.getAllInclusive();
			if (ConfiguratorConstants.CRS_BOARD_AVAILABILITY_TOKEN.equals(allInclusiveCRS.getAllInclAvailable()))
			{
				final BoardData allInclusiveBoard = new BoardData();
				allInclusiveBoard.setBoardCode(configuredBoardBasis.get("AllInclusive"));
				allInclusiveBoard.setBoardPrice(new BigDecimal(Integer.valueOf(allInclusiveCRS.getSumOfBoardAI().trim()).toString()));
				boardsList.add(allInclusiveBoard);
			}
			final Coxml09CrsuserdataSearchmealrsAvailableboardBreakfast breakFastCRS = availableBoards.getBreakfast();
			if (ConfiguratorConstants.CRS_BOARD_AVAILABILITY_TOKEN.equals(breakFastCRS.getBreakfastAvailable()))
			{
				final BoardData breakFast = new BoardData();
				breakFast.setBoardCode(configuredBoardBasis.get("Breakfast"));
				breakFast.setBoardPrice(new BigDecimal(Integer.valueOf(breakFastCRS.getSumOfBoardBB().trim()).toString()));
				boardsList.add(breakFast);
			}
			final Coxml09CrsuserdataSearchmealrsAvailableboardFullboard fullBoardCRS = availableBoards.getFullBoard();
			if (ConfiguratorConstants.CRS_BOARD_AVAILABILITY_TOKEN.equals(fullBoardCRS.getFullBoardAvailable()))
			{
				final BoardData fullBoard = new BoardData();
				fullBoard.setBoardCode(configuredBoardBasis.get("FullBoard"));
				fullBoard.setBoardPrice(new BigDecimal(Integer.valueOf(fullBoardCRS.getSumOfBoardFB().trim()).toString()));
				boardsList.add(fullBoard);
			}
			final Coxml09CrsuserdataSearchmealrsAvailableboardHalfboard halfBoardCRS = availableBoards.getHalfBoard();
			if (ConfiguratorConstants.CRS_BOARD_AVAILABILITY_TOKEN.equals(halfBoardCRS.getHalfBoardAvailable()))
			{
				final BoardData halfBoard = new BoardData();
				halfBoard.setBoardCode(configuredBoardBasis.get("HalfBoard"));
				halfBoard.setBoardPrice(new BigDecimal(Integer.valueOf(halfBoardCRS.getSumOfBoardHB().trim()).toString()));
				boardsList.add(halfBoard);
			}
		}
		return boardsList;
	}


	/**
	 * Gets the brand specific request map.
	 *
	 * @return the brand specific request map
	 */
	public Map<String, Map<String, String>> getBrandSpecificRequestMap()
	{
		return brandSpecificRequestMap;
	}

	/**
	 * Sets the brand specific request map.
	 *
	 * @param brandSpecificRequestMap
	 *           the brand specific request map
	 */
	public void setBrandSpecificRequestMap(final Map<String, Map<String, String>> brandSpecificRequestMap)
	{
		this.brandSpecificRequestMap = brandSpecificRequestMap;
	}

	/**
	 * Gets the configured board basis.
	 *
	 * @return the configured board basis
	 */
	public Map<String, String> getConfiguredBoardBasis()
	{
		return configuredBoardBasis;
	}

	/**
	 * Sets the configured board basis.
	 *
	 * @param configuredBoardBasis
	 *           the configured board basis
	 */
	public void setConfiguredBoardBasis(final Map<String, String> configuredBoardBasis)
	{
		this.configuredBoardBasis = configuredBoardBasis;
	}

	/**
	 * Gets the web service template.
	 *
	 * @return the web service template
	 */
	public WebServiceTemplate getWebServiceTemplate()
	{
		return webServiceTemplate;
	}

	/**
	 * Sets the web service template.
	 *
	 * @param webServiceTemplate
	 *           the new web service template
	 */
	public void setWebServiceTemplate(final WebServiceTemplate webServiceTemplate)
	{
		this.webServiceTemplate = webServiceTemplate;
	}

}
