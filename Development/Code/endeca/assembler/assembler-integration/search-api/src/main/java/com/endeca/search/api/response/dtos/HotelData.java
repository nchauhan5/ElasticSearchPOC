package com.endeca.search.api.response.dtos;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * The Class HotelInfo. Response DTO to store information about a hotel.
 */
public class HotelData
{
	/** The accom type. */
	private String accomType;

	/** The id. */
	private String id;

	/** The code. */
	private String code;

	/** The brand. */
	private String brand;

	/** The commercial priority. */
	private int commercialPriority;

	/** The apcId. */
	private String apcId;

	/** The external accommodation code. */
	private String externalAccommodationCode;

	/** The external system. */
	private String externalSystem;

	/** The sysInfo. */
	private String sysInfo;

	/**
	 * Gets the accom type.
	 *
	 * @return the accom type
	 */
	public String getAccomType()
	{
		return accomType;
	}

	/**
	 * Sets the accom type.
	 *
	 * @param accomType
	 *           the new accom type
	 */
	public void setAccomType(String accomType)
	{
		this.accomType = accomType;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *           the new id
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code
	 *           the new code
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * Gets the brand.
	 *
	 * @return the brand
	 */
	public String getBrand()
	{
		return brand;
	}

	/**
	 * Sets the brand.
	 *
	 * @param brand
	 *           the new brand
	 */
	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	/**
	 * Gets the commercial priority.
	 *
	 * @return the commercial priority
	 */
	public int getCommercialPriority()
	{
		return commercialPriority;
	}

	/**
	 * Sets the commercial priority.
	 *
	 * @param commercialPriority
	 *           the new commercial priority
	 */
	public void setCommercialPriority(int commercialPriority)
	{
		this.commercialPriority = commercialPriority;
	}

	/**
	 * Gets the apc id.
	 *
	 * @return the apc id
	 */
	public String getApcId()
	{
		return apcId;
	}

	/**
	 * Sets the apc id.
	 *
	 * @param apcId
	 *           the new apc id
	 */
	public void setApcId(String apcId)
	{
		this.apcId = apcId;
	}

	/**
	 * Gets the external accommodation code.
	 *
	 * @return the external accommodation code
	 */
	public String getExternalAccommodationCode()
	{
		return externalAccommodationCode;
	}

	/**
	 * Sets the external accommodation code.
	 *
	 * @param externalAccommodationCode
	 *           the new external accommodation code
	 */
	public void setExternalAccommodationCode(String externalAccommodationCode)
	{
		this.externalAccommodationCode = externalAccommodationCode;
	}

	/**
	 * Gets the external system.
	 *
	 * @return the external system
	 */
	public String getExternalSystem()
	{
		return externalSystem;
	}

	/**
	 * Sets the external system.
	 *
	 * @param externalSystem
	 *           the new external system
	 */
	public void setExternalSystem(String externalSystem)
	{
		this.externalSystem = externalSystem;
	}

	/**
	 * Gets the sys info.
	 *
	 * @return the sys info
	 */
	public String getSysInfo()
	{
		return sysInfo;
	}

	/**
	 * Sets the sys info.
	 *
	 * @param sysInfo
	 *           the new sys info
	 */
	public void setSysInfo(String sysInfo)
	{
		this.sysInfo = sysInfo;
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
