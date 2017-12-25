package com.endeca.search.api.response.dtos;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * The Class RatingReviewsInfo. Response DTO to store rating and reviews information.
 */
public class RatingReviewsData
{

	/** The rating. */
	private BigDecimal rating;

	/** The reviews. */
	private String reviews;

	/** The percentage. */
	private BigDecimal percentage;

	/** The category. */
	private String category;

	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public BigDecimal getRating()
	{
		return rating;
	}

	/**
	 * Sets the rating.
	 *
	 * @param rating
	 *           the new rating
	 */
	public void setRating(final BigDecimal rating)
	{
		this.rating = rating;
	}

	/**
	 * Gets the reviews.
	 *
	 * @return the reviews
	 */
	public String getReviews()
	{
		return reviews;
	}

	/**
	 * Sets the reviews.
	 *
	 * @param reviews
	 *           the new reviews
	 */
	public void setReviews(final String reviews)
	{
		this.reviews = reviews;
	}

	/**
	 * Gets the percentage.
	 *
	 * @return the percentage
	 */
	public BigDecimal getPercentage()
	{
		return percentage;
	}

	/**
	 * Sets the percentage.
	 *
	 * @param percentage
	 *           the new percentage
	 */
	public void setPercentage(final BigDecimal percentage)
	{
		this.percentage = percentage;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category
	 *           the new category
	 */
	public void setCategory(final String category)
	{
		this.category = category;
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
