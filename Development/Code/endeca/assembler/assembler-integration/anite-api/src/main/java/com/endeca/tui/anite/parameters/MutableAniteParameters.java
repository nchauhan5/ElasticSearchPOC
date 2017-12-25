//$Rev: 792 $
package com.endeca.tui.anite.parameters;

import java.util.Collection;

import com.endeca.tui.anite.enums.HolidayType;
import com.endeca.tui.anite.enums.PriceType;


// TODO: Auto-generated Javadoc
/**
 * The Interface MutableAniteParameters.
 */
public interface MutableAniteParameters extends AniteParameters
{

	/**
	 * Sets the room count.
	 *
	 * @param roomCount
	 *           the new room count
	 */
	public void setRoomCount(int roomCount);

	/**
	 * Sets the rating.
	 *
	 * @param rating
	 *           the new rating
	 */
	public void setRating(String rating);

	/**
	 * Sets the latest departure date string.
	 *
	 * @param latestDepartureDateString
	 *           the new latest departure date string
	 */
	public void setLatestDepartureDateString(String latestDepartureDateString);

	/**
	 * Sets the earliest departure date string.
	 *
	 * @param earliestDepartureDateString
	 *           the new earliest departure date string
	 */
	public void setEarliestDepartureDateString(String earliestDepartureDateString);

	/**
	 * Sets the durations string.
	 *
	 * @param durationsString
	 *           the new durations string
	 */
	public void setDurationsString(String durationsString);

	/**
	 * Sets the departure airports string.
	 *
	 * @param departureAirportsString
	 *           the new departure airports string
	 */
	public void setDepartureAirportsString(String departureAirportsString);

	/**
	 * Sets the child ages.
	 *
	 * @param childAges
	 *           the new child ages
	 */
	public void setChildAges(int[] childAges);

	/**
	 * Sets the adult pax.
	 *
	 * @param adultPax
	 *           the new adult pax
	 */
	public void setAdultPax(int adultPax);

	/**
	 * Sets the accommodations string.
	 *
	 * @param accommodationsString
	 *           the new accommodations string
	 */
	public void setAccommodationsString(String accommodationsString);

	/**
	 * Sets the date format pattern.
	 *
	 * @param dateFormatPattern
	 *           the new date format pattern
	 */
	public void setDateFormatPattern(String dateFormatPattern);

	/**
	 * Sets the accommodations.
	 *
	 * @param accommodations
	 *           the new accommodations
	 */
	public void setAccommodations(String[] accommodations);

	/**
	 * Sets the accommodations.
	 *
	 * @param accommodations
	 *           the new accommodations
	 */
	public void setAccommodations(Collection<String> accommodations);

	/**
	 * Sets the faceting.
	 *
	 * @param facetingActive
	 *           the new faceting
	 */
	public void setFaceting(boolean facetingActive);

	/**
	 * Sets the market.
	 *
	 * @param market
	 *           the new market
	 */
	public void setMarket(String market);

	/**
	 * Sets the promotions string.
	 *
	 * @param promotionsString
	 *           the new promotions string
	 */
	public void setPromotionsString(String promotionsString);

	/**
	 * Sets the promotions.
	 *
	 * @param promotionStrings
	 *           the new promotions
	 */
	public void setPromotions(String... promotionStrings);

	/**
	 * Sets the holiday type.
	 *
	 * @param holidayType
	 *           the new holiday type
	 */
	public void setHolidayType(HolidayType holidayType);

	/**
	 * Sets the price type.
	 *
	 * @param priceType
	 *           the new price type
	 */
	public void setPriceType(PriceType priceType);

	/**
	 * Sets the board basis.
	 *
	 * @param boardBasis
	 *           the new board basis
	 */
	public void setBoardBasis(String boardBasis);

	/**
	 * Sets the dream liner.
	 *
	 * @param dreamLiner
	 *           the new dream liner
	 */
	public void setDreamLiner(String dreamLiner);

	/**
	 * Sets the candidate durations string.
	 *
	 * @param durations
	 *           the new candidate durations string
	 */
	public void setCandidateDurationsString(String durations);

	/**
	 * Sets the n string.
	 *
	 * @param nString
	 *           the new n string
	 */
	public void setnString(String nString);

}
