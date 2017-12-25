package com.endeca.search.api.anite.converters;

import com.endeca.search.api.response.dtos.RoomData;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom.Unit;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom.Unit.Occ;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom.Unit.Priced;


/**
 * The Class RoomDataToUnitDataConverter.
 */
public class RoomDataToUnitDataConverter
{

	/**
	 * Instantiates a new room data to unit data converter.
	 */
	private RoomDataToUnitDataConverter()
	{
		// Added to avoid instantiation since this is a static utility class.
	}

	/**
	 * Convert.
	 * 
	 * @param roomData
	 *           the room data
	 * @return the unit
	 */
	public static Unit convert(final RoomData roomData)
	{
		final Unit unit = new Unit();

		final Occ occ = new Occ();
		occ.setAd((short) roomData.getPartyData().getAdults());
		occ.setCh((short) roomData.getPartyData().getChildren());
		occ.setIn((short) roomData.getPartyData().getInfants());
		unit.setOcc(occ);

		final Priced priced = new Priced();
		priced.setAd((short) roomData.getPartyData().getAdults());
		priced.setCh((short) roomData.getPartyData().getChildren());
		priced.setIn((short) roomData.getPartyData().getInfants());
		unit.setPriced(priced);

		unit.setBoard(roomData.getDefaultBoard());
		unit.setCode(roomData.getRoomCode());
		unit.setDisc(roomData.getUnitDiscount());
		unit.setDiscPP(roomData.getUnitDiscountPPP());
		unit.setName(roomData.getRoomTypeName());
		unit.setPrice(roomData.getUnitDiscountedPrice());
		unit.setPricePP(roomData.getUnitDisPricePerPerson());

		unit.setDc(null);
		unit.setAvail(roomData.getAvailability());
		unit.setQty(0);
		unit.setTracs(null);
		return unit;
	}
}
