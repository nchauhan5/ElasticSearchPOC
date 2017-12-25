package com.endeca.search.api.anite.converters;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;

import com.endeca.search.api.response.dtos.PartyData;
import com.endeca.search.api.response.dtos.RoomData;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom.Unit;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom.Unit.Occ;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer.Accom.Unit.Priced;

public class RoomDataToUnitDataConverterTest {

	/*This Test method is to test convert, whether values set to room data successfully converted to unit */
	@Test
	public void roomDataShouldConvertToUnitData() {
		RoomData roomData=new RoomData();
		PartyData partyData =new PartyData();
		roomData.setPartyData(partyData);
		partyData.setAdults(3);
		partyData.setChildren(2);
		partyData.setInfants(1);
		roomData.setDefaultBoard("Luxirious");
		roomData.setRoomCode("A-001");
		roomData.setUnitDiscount(new BigDecimal(2300));
		roomData.setUnitDiscountPPP(new BigDecimal(12));
		roomData.setRoomTypeName("5star");
		roomData.setUnitDiscountedPrice(new BigDecimal(234));
		roomData.setUnitDisPricePerPerson(new BigDecimal(230));
		roomData.setAvailability(6);
		 long expectedAvail=6;
	    Unit unit =RoomDataToUnitDataConverter.convert(roomData);
		assertThat(unit.getCode(),is("A-001"));
		assertThat(unit.getBoard(),is("Luxirious"));
		assertThat(unit.getDisc(),is(new BigDecimal(2300)));
		assertThat(unit.getDiscPP(),is(new BigDecimal(12)));
		assertThat(unit.getName(),is("5star"));
		assertNull(unit.getTracs());
		assertThat(unit.getAvail(),is(expectedAvail));
		assertNull(unit.getDc());
		assertThat(unit.getPrice(),is(new BigDecimal(234)));
		assertThat(unit.getPricePP(),is(new BigDecimal(230)));
		assertThat(unit.getPriced().getCh(),is((short)2));
		assertThat(unit.getPriced().getAd(),is((short)3));
		assertThat(unit.getPriced().getIn(),is((short)1));
	}
}
