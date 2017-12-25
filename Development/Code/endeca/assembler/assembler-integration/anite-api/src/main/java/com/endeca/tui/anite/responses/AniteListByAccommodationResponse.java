//$Rev: 789 $
package com.endeca.tui.anite.responses;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.endeca.tui.anite.BackedObject;

/**
 * The Interface AniteListByAccommodationResponse.
 */
public interface AniteListByAccommodationResponse
{

	/**
	 * The Interface AniteStatus.
	 */
	public interface AniteStatus
	{

		/**
		 * Gets the count.
		 * 
		 * @return the count
		 */
		public long getCount();
	}

	/**
	 * The Interface AniteOffer.
	 * 
	 * @param <T>
	 *           the generic type
	 */
	public interface AniteOffer<T> extends BackedObject<T>
	{

		/**
		 * Gets the brand.
		 * 
		 * @return the brand
		 */
		public String getBrand();

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.endeca.tui.anite.BackedObject#getBackingObject()
		 */
		public T getBackingObject();

		/**
		 * Gets the date.
		 * 
		 * @return the date
		 */
		public Date getDate();

		/**
		 * Gets the stay.
		 * 
		 * @return the stay
		 */
		public int getStay();

		/**
		 * Gets the price.
		 * 
		 * @return the price
		 */
		public Number getPrice();

		/**
		 * Gets the accommodation.
		 * 
		 * @return the accommodation
		 */
		public AniteAccommodation getAccommodation();

		/**
		 * Gets the routes.
		 * 
		 * @return the routes
		 */
		public Iterable<AniteRoute> getRoutes();

		/**
		 * The Interface AniteAccommodation.
		 */
		public interface AniteAccommodation
		{

			/**
			 * Gets the code.
			 * 
			 * @return the code
			 */
			public String getCode();

			/**
			 * Gets the rating.
			 * 
			 * @return the rating
			 */
			public String getRating();

			/**
			 * Gets the unit.
			 * 
			 * @return the unit
			 */
			public AniteUnit getUnit();

			/**
			 * Gets the commercial priority.
			 * 
			 * @return the commercial priority
			 */
			public String getCommercialPriority();

			/**
			 * The Interface AniteUnit.
			 */
			public interface AniteUnit
			{

				/**
				 * The Interface AniteOccupancy.
				 */
				public interface AniteOccupancy
				{

					/**
					 * Gets the adults.
					 * 
					 * @return the adults
					 */
					public short getAdults();

					/**
					 * Gets the children.
					 * 
					 * @return the children
					 */
					public short getChildren();

					/**
					 * Gets the infants.
					 * 
					 * @return the infants
					 */
					public short getInfants();
				}

				/**
				 * Gets the code.
				 * 
				 * @return the code
				 */
				public String getCode();

				/**
				 * Gets the quantity.
				 * 
				 * @return the quantity
				 */
				public int getQuantity();

				/**
				 * Gets the board.
				 * 
				 * @return the board
				 */
				public String getBoard();

				/**
				 * Gets the occupancy.
				 * 
				 * @return the occupancy
				 */
				public AniteOccupancy getOccupancy();
			}
		}

		/**
		 * The Interface AniteRoute.
		 */
		public interface AniteRoute
		{

			/**
			 * Gets the departure point.
			 * 
			 * @return the departure point
			 */
			public String getDeparturePoint();

			/**
			 * Gets the arrival point.
			 * 
			 * @return the arrival point
			 */
			public String getArrivalPoint();

			/**
			 * Gets the departure date.
			 * 
			 * @return the departure date
			 */
			public Date getDepartureDate();

			/**
			 * Gets the departure time.
			 * 
			 * @return the departure time
			 */
			public String getDepartureTime();
		}
	}

	/**
	 * Gets the offers.
	 * 
	 * @return the offers
	 */
	public Iterable<AniteOffer<? extends Object>> getOffers();

	/**
	 * Gets the offers list.
	 * 
	 * @return the offers list
	 */
	public List<AniteOffer<? extends Object>> getOffersList();

	/**
	 * Gets the offer count.
	 * 
	 * @return the offer count
	 */
	public int getOfferCount();

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public AniteStatus getStatus();

	/**
	 * The Interface AniteLists.
	 */
	public interface AniteLists
	{

		/**
		 * Gets the board.
		 * 
		 * @return the board
		 */
		public String getBoard();

		/**
		 * Gets the departure point.
		 * 
		 * @return the departure point
		 */
		public String getDeparturePoint();

		/**
		 * Gets the dreamliner.
		 * 
		 * @return the dreamliner
		 */
		public String getDreamliner();

		/**
		 * Gets the in slots.
		 * 
		 * @return the in slots
		 */
		public String getInSlots();

		/**
		 * Gets the out slots.
		 * 
		 * @return the out slots
		 */
		public String getOutSlots();
	}

	/**
	 * The Interface AniteFacetCategory.
	 */
	public interface AniteFacetCategory
	{

		/**
		 * Gets the code.
		 * 
		 * @return the code
		 */
		public String getCode();

		/**
		 * The Interface AniteFacetRefinement.
		 */
		public interface AniteFacetRefinement
		{

			/**
			 * Gets the code.
			 * 
			 * @return the code
			 */
			public String getCode();

			/**
			 * Gets the count.
			 * 
			 * @return the count
			 */
			public long getCount();

			/**
			 * Gets the min.
			 * 
			 * @return the min
			 */
			public BigDecimal getMin();

			/**
			 * Gets the max.
			 * 
			 * @return the max
			 */
			public BigDecimal getMax();
		}

		/**
		 * Gets the facet refinements.
		 * 
		 * @return the facet refinements
		 */
		public List<AniteFacetRefinement> getFacetRefinements();
	}

	/**
	 * Gets the facet categories.
	 * 
	 * @return the facet categories
	 */
	public List<AniteFacetCategory> getFacetCategories();

	/**
	 * Gets the facet categories by code.
	 * 
	 * @return the facet categories by code
	 */
	public Map<String, AniteFacetCategory> getFacetCategoriesByCode();

	/**
	 * Gets the board basis brand facet categories.
	 * 
	 * @return the board basis brand facet categories
	 */
	public Map<String, AniteFacetCategory> getBoardBasisBrandFacetCategories();

	/**
	 * Gets the lists.
	 * 
	 * @return the lists
	 */
	public AniteLists getLists();
}
