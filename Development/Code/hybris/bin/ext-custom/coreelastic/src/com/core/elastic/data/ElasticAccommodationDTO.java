/**
 *
 */
package com.core.elastic.data;

import java.io.Serializable;


/**
 * @author msin82
 * 
 */
public class ElasticAccommodationDTO implements Serializable
{

	private Integer commercialPriority;

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/** The base product code. */
	private String baseProductCode;

	/** The country code. */
	private String countryCode;

	/** The country name. */
	private String countryName;

	/** The destination code. */
	private String destinationCode;

	/** The destination name. */
	private String destinationName;

	/** The resort code. */
	private String resortCode;

	/** The resort name. */
	private String resortName;

	/** The page url. */
	private String pageURL;

	/** The accommodation usp. */
	private String accommodationUSP;

	/** The wifi availablity. */
	private String wifiAvailablity;

	/** The customer rating. */
	private String accommodationRating;

	private String seasonCode;

	private String imageURL;

	private String brand;
	private String conceptCode;




	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *           the name to set
	 */
	public void setName(final String name)
	{
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code
	 *           the code to set
	 */
	public void setCode(final String code)
	{
		this.code = code;
	}

	/**
	 * @return the baseProductCode
	 */
	public String getBaseProductCode()
	{
		return baseProductCode;
	}

	/**
	 * @param baseProductCode
	 *           the baseProductCode to set
	 */
	public void setBaseProductCode(final String baseProductCode)
	{
		this.baseProductCode = baseProductCode;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode()
	{
		return countryCode;
	}

	/**
	 * @param countryCode
	 *           the countryCode to set
	 */
	public void setCountryCode(final String countryCode)
	{
		this.countryCode = countryCode;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName()
	{
		return countryName;
	}

	/**
	 * @param countryName
	 *           the countryName to set
	 */
	public void setCountryName(final String countryName)
	{
		this.countryName = countryName;
	}

	/**
	 * @return the destinationCode
	 */
	public String getDestinationCode()
	{
		return destinationCode;
	}

	/**
	 * @param destinationCode
	 *           the destinationCode to set
	 */
	public void setDestinationCode(final String destinationCode)
	{
		this.destinationCode = destinationCode;
	}

	/**
	 * @return the destinationName
	 */
	public String getDestinationName()
	{
		return destinationName;
	}

	/**
	 * @param destinationName
	 *           the destinationName to set
	 */
	public void setDestinationName(final String destinationName)
	{
		this.destinationName = destinationName;
	}

	/**
	 * @return the resortCode
	 */
	public String getResortCode()
	{
		return resortCode;
	}

	/**
	 * @param resortCode
	 *           the resortCode to set
	 */
	public void setResortCode(final String resortCode)
	{
		this.resortCode = resortCode;
	}

	/**
	 * @return the resortName
	 */
	public String getResortName()
	{
		return resortName;
	}

	/**
	 * @param resortName
	 *           the resortName to set
	 */
	public void setResortName(final String resortName)
	{
		this.resortName = resortName;
	}

	/**
	 * @return the pageURL
	 */
	public String getPageURL()
	{
		return pageURL;
	}

	/**
	 * @param pageURL
	 *           the pageURL to set
	 */
	public void setPageURL(final String pageURL)
	{
		this.pageURL = pageURL;
	}

	/**
	 * @return the accommodationUSP
	 */
	public String getAccommodationUSP()
	{
		return accommodationUSP;
	}

	/**
	 * @param accommodationUSP
	 *           the accommodationUSP to set
	 */
	public void setAccommodationUSP(final String accommodationUSP)
	{
		this.accommodationUSP = accommodationUSP;
	}

	/**
	 * @return the wifiAvailablity
	 */
	public String getWifiAvailablity()
	{
		return wifiAvailablity;
	}

	/**
	 * @param wifiAvailablity
	 *           the wifiAvailablity to set
	 */
	public void setWifiAvailablity(final String wifiAvailablity)
	{
		this.wifiAvailablity = wifiAvailablity;
	}

	/**
	 * @return the accommodationRating
	 */
	public String getAccommodationRating()
	{
		return accommodationRating;
	}

	/**
	 * @param accommodationRating
	 *           the accommodationRating to set
	 */
	public void setAccommodationRating(final String accommodationRating)
	{
		this.accommodationRating = accommodationRating;
	}

	/**
	 * @return the seasonCode
	 */
	public String getSeasonCode()
	{
		return seasonCode;
	}

	/**
	 * @param seasonCode
	 *           the seasonCode to set
	 */
	public void setSeasonCode(final String seasonCode)
	{
		this.seasonCode = seasonCode;
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL()
	{
		return imageURL;
	}

	/**
	 * @param imageURL
	 *           the imageURL to set
	 */
	public void setImageURL(final String imageURL)
	{
		this.imageURL = imageURL;
	}

	/**
	 * @return the commercialPriority
	 */
	public Integer getCommercialPriority()
	{
		return commercialPriority;
	}

	/**
	 * @param commercialPriority
	 *           the commercialPriority to set
	 */
	public void setCommercialPriority(final Integer commercialPriority)
	{
		this.commercialPriority = commercialPriority;
	}

	/**
	 * @return the conceptCode
	 */
	public String getConceptCode()
	{
		return conceptCode;
	}

	/**
	 * @param conceptCode the conceptCode to set
	 */
	public void setConceptCode(String conceptCode)
	{
		this.conceptCode = conceptCode;
	}

	/**
	 * @return the brand
	 */
	public String getBrand()
	{
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand)
	{
		this.brand = brand;
	}

}
