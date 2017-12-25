package com.endeca.search.api.response.dtos;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


// TODO: Auto-generated Javadoc
/**
 * The Class RoomsInformation. Response DTO to store unit level information.
 */
public class RoomData
{

	/** The room type name. */
	private String roomTypeName;

	/** The room code. */
	private String roomCode;

	/** The availability. */
	private int availability;

	/** The price total. */
	private BigDecimal unitDiscountedPrice;

	/** The price per person. */
	private BigDecimal unitDisPricePerPerson;

	/** The discount total. */
	private BigDecimal unitDiscount;

	/** The discount per person. */
	private BigDecimal unitDiscountPPP;

	/** The board type. */
	private String defaultBoard;

	/** The party. */
	private PartyData partyData;

	/** The room info. */
	private String roomInfo;

	/** The category description. */
	private String categoryDescription;

	/** The room type description. */
	private String roomTypeDescription;

	/** The room type category description. */
	private String roomTypeCategoryDescription;

	/** The additional text. */
	private String additionalText;

	/** The category image url. */
	private String categoryImageUrl;

	/** The available boards. */
	private List<BoardData> availableBoards;

	/** The crs error code. */
	private String crsErrorCode;

	/** The crs error message. */
	private String crsErrorMessage;

	/** The src info. */
	private ExternalSourceInfo externalSourceInfo;

	/**
	 * Gets the room type name.
	 *
	 * @return the room type name
	 */
	public String getRoomTypeName()
	{
		return roomTypeName;
	}

	/**
	 * Sets the room type name.
	 *
	 * @param roomTypeName
	 *           the new room type name
	 */
	public void setRoomTypeName(final String roomTypeName)
	{
		this.roomTypeName = roomTypeName;
	}

	/**
	 * Gets the room code.
	 *
	 * @return the room code
	 */
	public String getRoomCode()
	{
		return roomCode;
	}

	/**
	 * Sets the room code.
	 *
	 * @param roomCode
	 *           the new room code
	 */
	public void setRoomCode(final String roomCode)
	{
		this.roomCode = roomCode;
	}

	/**
	 * Checks if is availability.
	 *
	 * @return true, if is availability
	 */
	public int getAvailability()
	{
		return availability;
	}

	/**
	 * Sets the availability.
	 *
	 * @param availability
	 *           the new availability
	 */
	public void setAvailability(final int availability)
	{
		this.availability = availability;
	}




	/**
	 * Gets the board type.
	 *
	 * @return the board type
	 */
	public String getDefaultBoard()
	{
		return defaultBoard;
	}

	/**
	 * Sets the board type.
	 *
	 * @param boardType
	 *           the new board type
	 */
	public void setDefaultBoard(final String boardType)
	{
		this.defaultBoard = boardType;
	}

	/**
	 * Gets the party.
	 *
	 * @return the party
	 */
	public PartyData getPartyData()
	{
		return partyData;
	}

	/**
	 * Sets the party.
	 *
	 * @param partyData
	 *           the new party data
	 */
	public void setPartyData(final PartyData partyData)
	{
		this.partyData = partyData;
	}

	/**
	 * Gets the unit discounted price.
	 *
	 * @return the unit discounted price
	 */
	public BigDecimal getUnitDiscountedPrice()
	{
		return unitDiscountedPrice;
	}

	/**
	 * Sets the unit discounted price.
	 *
	 * @param unitDiscountedPrice
	 *           the new unit discounted price
	 */
	public void setUnitDiscountedPrice(final BigDecimal unitDiscountedPrice)
	{
		this.unitDiscountedPrice = unitDiscountedPrice;
	}

	/**
	 * Gets the unit dis price per person.
	 *
	 * @return the unit dis price per person
	 */
	public BigDecimal getUnitDisPricePerPerson()
	{
		return unitDisPricePerPerson;
	}

	/**
	 * Sets the unit dis price per person.
	 *
	 * @param unitDisPricePerPerson
	 *           the new unit dis price per person
	 */
	public void setUnitDisPricePerPerson(final BigDecimal unitDisPricePerPerson)
	{
		this.unitDisPricePerPerson = unitDisPricePerPerson;
	}

	/**
	 * Gets the unit discount.
	 *
	 * @return the unit discount
	 */
	public BigDecimal getUnitDiscount()
	{
		return unitDiscount;
	}

	/**
	 * Sets the unit discount.
	 *
	 * @param unitDiscount
	 *           the new unit discount
	 */
	public void setUnitDiscount(final BigDecimal unitDiscount)
	{
		this.unitDiscount = unitDiscount;
	}

	/**
	 * Gets the unit discount ppp.
	 *
	 * @return the unit discount ppp
	 */
	public BigDecimal getUnitDiscountPPP()
	{
		return unitDiscountPPP;
	}

	/**
	 * Sets the unit discount ppp.
	 *
	 * @param unitDiscountPPP
	 *           the new unit discount ppp
	 */
	public void setUnitDiscountPPP(final BigDecimal unitDiscountPPP)
	{
		this.unitDiscountPPP = unitDiscountPPP;
	}

	/**
	 * Gets the room info.
	 *
	 * @return the room info
	 */
	public String getRoomInfo()
	{
		return roomInfo;
	}

	/**
	 * Sets the room info.
	 *
	 * @param roomInfo
	 *           the new room info
	 */
	public void setRoomInfo(final String roomInfo)
	{
		this.roomInfo = roomInfo;
	}

	/**
	 * Gets the category description.
	 *
	 * @return the category description
	 */
	public String getCategoryDescription()
	{
		return categoryDescription;
	}

	/**
	 * Sets the category description.
	 *
	 * @param categoryDescription
	 *           the new category description
	 */
	public void setCategoryDescription(final String categoryDescription)
	{
		this.categoryDescription = categoryDescription;
	}

	/**
	 * Gets the room type description.
	 *
	 * @return the room type description
	 */
	public String getRoomTypeDescription()
	{
		return roomTypeDescription;
	}

	/**
	 * Sets the room type description.
	 *
	 * @param roomTypeDescription
	 *           the new room type description
	 */
	public void setRoomTypeDescription(final String roomTypeDescription)
	{
		this.roomTypeDescription = roomTypeDescription;
	}

	/**
	 * Gets the room type category description.
	 *
	 * @return the room type category description
	 */
	public String getRoomTypeCategoryDescription()
	{
		return roomTypeCategoryDescription;
	}

	/**
	 * Sets the room type category description.
	 *
	 * @param roomTypeCategoryDescription
	 *           the new room type category description
	 */
	public void setRoomTypeCategoryDescription(final String roomTypeCategoryDescription)
	{
		this.roomTypeCategoryDescription = roomTypeCategoryDescription;
	}

	/**
	 * Gets the additional text.
	 *
	 * @return the additional text
	 */
	public String getAdditionalText()
	{
		return additionalText;
	}

	/**
	 * Sets the additional text.
	 *
	 * @param additionalText
	 *           the new additional text
	 */
	public void setAdditionalText(final String additionalText)
	{
		this.additionalText = additionalText;
	}

	/**
	 * Gets the category image url.
	 *
	 * @return the category image url
	 */
	public String getCategoryImageUrl()
	{
		return categoryImageUrl;
	}

	/**
	 * Sets the category image url.
	 *
	 * @param categoryImageUrl
	 *           the new category image url
	 */
	public void setCategoryImageUrl(final String categoryImageUrl)
	{
		this.categoryImageUrl = categoryImageUrl;
	}

	/**
	 * Gets the available boards.
	 *
	 * @return the available boards
	 */
	public List<BoardData> getAvailableBoards()
	{
		return availableBoards;
	}

	/**
	 * Sets the available boards.
	 *
	 * @param availableBoards
	 *           the new available boards
	 */
	public void setAvailableBoards(final List<BoardData> availableBoards)
	{
		this.availableBoards = availableBoards;
	}

	/**
	 * Gets the crs error code.
	 *
	 * @return the crs error code
	 */
	public String getCrsErrorCode()
	{
		return crsErrorCode;
	}

	/**
	 * Sets the crs error code.
	 *
	 * @param crsErrorCode
	 *           the new crs error code
	 */
	public void setCrsErrorCode(final String crsErrorCode)
	{
		this.crsErrorCode = crsErrorCode;
	}

	/**
	 * Gets the crs error message.
	 *
	 * @return the crs error message
	 */
	public String getCrsErrorMessage()
	{
		return crsErrorMessage;
	}

	/**
	 * Sets the crs error message.
	 *
	 * @param crsErrorMessage
	 *           the new crs error message
	 */
	public void setCrsErrorMessage(final String crsErrorMessage)
	{
		this.crsErrorMessage = crsErrorMessage;
	}

	/**
	 * Gets the external source info.
	 *
	 * @return the external source info
	 */
	public ExternalSourceInfo getExternalSourceInfo()
	{
		return externalSourceInfo;
	}

	/**
	 * Sets the external source info.
	 *
	 * @param externalSourceInfo
	 *           the new external source info
	 */
	public void setExternalSourceInfo(ExternalSourceInfo externalSourceInfo)
	{
		this.externalSourceInfo = externalSourceInfo;
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
