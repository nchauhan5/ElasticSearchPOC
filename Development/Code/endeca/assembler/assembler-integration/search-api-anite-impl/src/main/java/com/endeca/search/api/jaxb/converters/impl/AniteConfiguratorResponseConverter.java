package com.endeca.search.api.jaxb.converters.impl;

import java.util.ArrayList;
import java.util.List;

import com.endeca.search.api.exceptions.SearchAPIUnmarshallingException;
import com.endeca.search.api.response.dtos.BoardData;
import com.endeca.search.api.response.dtos.ExternalSourceInfo;
import com.endeca.search.api.response.dtos.HotelData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.PackagesData;
import com.endeca.search.api.response.dtos.RoomData;
import com.endeca.tui.anite.response.AvCache;
import com.endeca.tui.anite.response.AvCache.Result;
import com.endeca.tui.anite.response.AvCache.Result.Offers;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom.Unit;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom.Unit.SrcInfo;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.AltBoard.Board;


/**
 * The Class AniteConfiguratorResponseConverter.
 */
public class AniteConfiguratorResponseConverter extends AnitePackagesResponseConverter
{

	@Override
	public PackagesData convertJaxbResponse(final Object jaxbElement) throws SearchAPIUnmarshallingException
	{
		final PackagesData packagesData = new PackagesData();
		final List<PackageData> packages = new ArrayList<PackageData>();

		final AvCache avCache = (AvCache) jaxbElement;

		if (null != avCache.getError())
		{
			final AvCache.Error aniteError = avCache.getError();
			throw new SearchAPIUnmarshallingException(
					" Received error code : " + aniteError.getCode() + " with text : " + aniteError.getText(), new Throwable());
		}

		else
		{
			final Result result = avCache.getResult();
			final Offers offers = result.getOffers();

			convertOffersToPackages(offers, packages);

			packagesData.setCount(packages.size());
			packagesData.setPackages(packages);
			return packagesData;
		}

	}


	@Override
	public void convertOffersToPackages(final Offers offers, final List<PackageData> packages)
			throws SearchAPIUnmarshallingException
	{
		for (final Offer offer : offers.getOffer())
		{
			final PackageData packageData = new PackageData();

			// DEV-29121 & DEV-29729 || Changes to support Bed banks || See how this method takes different parameter compared to getRoomData in the super class
			packageData.setRoomsData(getRoomData(offer));

			setDataInPackage(offer, packageData);
			packageData.getOfferData().setPackageID(offer.getPkg());
			packages.add(packageData);
		}
	}

	/**
	 * Gets the hotel data.
	 *
	 * @param accom
	 *           the accom
	 * @return the hotel data
	 */
	@Override
	public HotelData getHotelData(final Accom accom)
	{
		final HotelData hotelData = super.getHotelData(accom);

		if ("EXTERNAL".equals(hotelData.getAccomType()))
		{
			hotelData.setExternalAccommodationCode(accom.getUnit().get(0).getSrcInfo().getAccom());
			hotelData.setExternalSystem(accom.getUnit().get(0).getSrcInfo().getSystem());
		}
		return hotelData;
	}

	/**
	 * Gets the room data.
	 *
	 * @param offer
	 *           the offer
	 * @return the room data
	 */
	public List<RoomData> getRoomData(final Offer offer)
	{
		final Accom accom = offer.getAccom();
		final List<RoomData> rooms = new ArrayList<RoomData>();
		for (final Unit unit : accom.getUnit())
		{
			RoomData room = populateRoomData(unit);
			setBoardsData(offer, room);
			setExternalSourceInfoInRoomData(room, unit.getSrcInfo());
			rooms.add(room);
		}
		return rooms;
	}

	/**
	 * Sets the boards data.
	 *
	 * @param offer
	 *           the offer
	 * @param roomData
	 *           the room data
	 */
	public void setBoardsData(final Offer offer, final RoomData roomData)
	{
		if (offer.getAltBoard() != null)
		{
			final List<BoardData> boards = new ArrayList<BoardData>();
			for (final Board board : offer.getAltBoard().getBoard())
			{
				final BoardData boardData = new BoardData();
				boardData.setBoardCode(board.getCode());
				boardData.setBoardPrice(board.getPrice());
				boardData.setSrcCode(board.getSrcInfo().getCode());
				boards.add(boardData);
			}
			roomData.setAvailableBoards(boards);
		}
	}


	/**
	 * Sets the src info in room data.
	 *
	 * @param room
	 *           the room
	 * @param srcInfo
	 *           the src info
	 */
	public void setExternalSourceInfoInRoomData(final RoomData room, final SrcInfo srcInfo)
	{
		if (null != srcInfo)
		{
			final ExternalSourceInfo unitSrcInfo = new ExternalSourceInfo();
			//		unitSrcInfo.setAccom(srcInfo.getAccom());
			unitSrcInfo.setExternalBoardBasis(srcInfo.getBoard());
			//		unitSrcInfo.setSystem(srcInfo.getSystem());
			unitSrcInfo.setExternalRoomCode(srcInfo.getUnit());
			room.setExternalSourceInfo(unitSrcInfo);
		}
	}

}
