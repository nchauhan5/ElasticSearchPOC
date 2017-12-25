package com.elastic.comparators;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.Comparator;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elastic.response.AssemblerResponse;
import com.endeca.search.api.response.dtos.FlightData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.sort.parameter.SortingOrder;
import com.endeca.search.api.sort.parameter.SortingType;


/**
 * @author shurde
 *
 * @param <T>
 */
public class GenericRecordComparator<T> implements Comparator<T>
{

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(GenericRecordComparator.class);

	/** The sort type. */
	private final SortingType sortType;

	/** The sort order. */
	private final SortingOrder sortOrder;

	/** The property to sort. */
	private final String propertyToSort;

	/**
	 * Instantiates a new generic record comparator.
	 *
	 * @param sortType
	 *           the sort type
	 * @param sortOrder
	 *           the sort order
	 * @param propertyToSort
	 *           the property to sort
	 */
	public GenericRecordComparator(final SortingType sortType, final SortingOrder sortOrder, final String propertyToSort)
	{
		this.propertyToSort = propertyToSort;
		this.sortOrder = sortOrder;
		this.sortType = sortType;
	}

	/**
	 * Method will compare the two item
	 */
	@Override
	public int compare(final T firstItem, final T secondItem)
	{
		int result;
		if (sortOrder == SortingOrder.ASCENDING)
		{
			result = compareForAscending(firstItem, secondItem);
		}
		else
		{
			result = compareForDescending(firstItem, secondItem);
		}
		return result;
	}

	/**
	 * Compare for ascending.
	 *
	 * @param valueA
	 *           the value a
	 * @param valueB
	 *           the value b
	 * @return the int
	 */
	private int compareForAscending(final T valueA, final T valueB)
	{
		String propertyValueA = null;
		String propertyValueB = null;
		int result = 0;
		try
		{
			final PackageData p1 = (PackageData) getPackageFromRecord(valueA);
			final PackageData p2 = (PackageData) getPackageFromRecord(valueB);
			if (propertyToSort.contains("p_"))
			{
				propertyValueA = getValue(valueA, propertyToSort);
				propertyValueB = getValue(valueB, propertyToSort);
			}
			else if (propertyToSort.equals("RoomData.unitDiscount"))
			{
				propertyValueA = getPackageRoomData(p1);
				propertyValueB = getPackageRoomData(p2);
			}

			else if (propertyToSort.equals("FlightData.departureDateTime"))
			{
				propertyValueA = getFlightTimeMillisAsString(p1.getOutboundFlight().get(0), "SORT_ON_DEPARTURE_DATETIME");
				propertyValueB = getFlightTimeMillisAsString(p2.getOutboundFlight().get(0), "SORT_ON_DEPARTURE_DATETIME");
			}
			else if (propertyToSort.equals("FlightData.arrivalDateTime"))
			{
				propertyValueA = getFlightTimeMillisAsString(p1.getOutboundFlight().get(p1.getOutboundFlight().size() - 1),
						"SORT_ON_ARRIVAL_DATETIME");
				propertyValueB = getFlightTimeMillisAsString(p2.getOutboundFlight().get(p2.getOutboundFlight().size() - 1),
						"SORT_ON_ARRIVAL_DATETIME");
			}
			else
			{
				propertyValueA = getPackageDiscountedPrice(p1);
				propertyValueB = getPackageDiscountedPrice(p2);
			}
			final int nullCheck = nullSafeCheck(propertyValueA, propertyValueB);
			if (nullCheck != -2)
			{
				result = nullCheck;
			}
			else
			{
				switch (sortType)
				{
					case ALPHA:
						final Collator collator = Collator.getInstance();
						result = collator.compare(propertyValueA, propertyValueB);
						break;
					case INTEGER:
						result = Integer.parseInt(propertyValueA) - Integer.parseInt(propertyValueB);
						//code added for US11002:To consider price as secondary sorting attribute
						if (result == 0 && propertyToSort.equals("p_commercialPriority"))
						{
							propertyValueA = getPackageDiscountedPrice(p1);
							propertyValueB = getPackageDiscountedPrice(p2);
							result = Double.compare(Double.parseDouble(propertyValueA), Double.parseDouble(propertyValueB));
						}
						break;
					case FLOAT:
						result = Double.compare(Double.parseDouble(propertyValueA), Double.parseDouble(propertyValueB));
						if (result == 0 && propertyToSort.equals("RoomData.unitDiscount"))
						{
							result = nextLevelSortingOnPriorityAndDiscountPrice(valueA, valueB);
						}
						break;
					case LONG:
						result = Long.compare(Long.parseLong(propertyValueA), Long.parseLong(propertyValueB));
						if ((result == 0) && ((propertyToSort.equals("FlightData.departureDateTime"))
								|| (propertyToSort.equals("FlightData.arrivalDateTime"))))
						{
							result = nextLevelSortingOnDiscountPrice(valueA, valueB);
						}
						break;
					default:
						result = 0;
						break;
				}
			}
		}
		catch (final IllegalArgumentException e)
		{
			LOG.error("error in parsing values", e);
		}
		return result;
	}

	/**
	 * Compare descending.
	 *
	 * @param valueA
	 *           the value a
	 * @param valueB
	 *           the value b
	 * @return the int
	 */
	private int compareForDescending(final T valueA, final T valueB)
	{
		return compareForAscending(valueB, valueA);
	}

	/**
	 * Next level sorting.
	 *
	 * @param valueA
	 * @param valueB
	 * @return the int
	 */
	private int nextLevelSortingOnPriorityAndDiscountPrice(final T valueA, final T valueB)
	{
		int result = 0;

		//final Collator unitCollator = Collator.getInstance();
		final String propertyValueA = getValue(valueA, "p_commercialPriority");
		final String propertyValueB = getValue(valueB, "p_commercialPriority");
		if (StringUtils.isNotEmpty(propertyValueA) && StringUtils.isNotEmpty(propertyValueB))
		{
			result = Integer.compare(Integer.parseInt(propertyValueB), Integer.parseInt(propertyValueA));
		}

		if (result == 0)
		{
			result = nextLevelSortingOnDiscountPrice(valueA, valueB);
		}
		return result;
	}

	/**
	 * Next level sorting on discount price.
	 *
	 * @param valueA
	 *           the value a
	 * @param valueB
	 *           the value b
	 * @return the int
	 */
	private int nextLevelSortingOnDiscountPrice(final T valueA, final T valueB)
	{
		final PackageData p1 = (PackageData) getPackageFromRecord(valueA);
		final String propertyValueA = getPackageDiscountedPrice(p1);
		final PackageData p2 = (PackageData) getPackageFromRecord(valueB);
		final String propertyValueB = getPackageDiscountedPrice(p2);

		if (sortOrder == SortingOrder.ASCENDING)
		{
			return Double.compare(Double.parseDouble(propertyValueA), Double.parseDouble(propertyValueB));
		}
		else
		{
			return Double.compare(Double.parseDouble(propertyValueB), Double.parseDouble(propertyValueA));
		}
	}

	/**
	 * Gets the flight date time millis as string.
	 *
	 * @param flight
	 *           the flight
	 * @return the flight date time millis as string
	 */
	private String getFlightTimeMillisAsString(final FlightData flight, final String flightType)
	{
		DateTime flightTime = null;
		if ("SORT_ON_DEPARTURE_DATETIME".equals(flightType))
		{
			flightTime = new DateTime(flight.getDepartureTime());
		}

		else
		{
			flightTime = new DateTime(flight.getArrivalTime());
		}

		final Long departureDateTimeInMillis = flightTime.getMillis();
		return departureDateTimeInMillis.toString();
	}

	/**
	 * Gets the package discounted price.
	 *
	 * @param p1
	 *           the p1
	 * @return the package discounted price
	 */
	private String getPackageDiscountedPrice(PackageData p1)
	{
		return p1.getOfferData().getDiscountedPrice().toString();
	}

	/**
	 * Gets the indirect package room data property.
	 *
	 * @param v1
	 *           the v1
	 * @return the indirect package room data property
	 */
	private String getPackageRoomData(PackageData p1)
	{
		return BigDecimal.valueOf(Math.abs((p1.getRoomsData().get(0).getUnitDiscount().doubleValue()))).toString();
	}

	/**
	 * Method will be use to compare some additional property that are not the part of package and are directly link to
	 * Record like p_accomodation,p_wifiAvailability.
	 *
	 * @param valueA
	 *           the a
	 * @return the value
	 */
	private String getValue(final T valueA, String property)
	{
		String str = null;
		final Object obj = ((Map<String, Object>) ((AssemblerResponse) valueA).getAttributes().get("ESResponse"))
				.get(property.substring(property.indexOf("_") + 1));
		if (null != obj)
		{
			str = ObjectUtils.toString(obj).trim();
			return str;
		}
		return str;
	}

	/**
	 * Gets the package from record .
	 *
	 * @param v1
	 *           the v1
	 * @return the package from record
	 */
	private Object getPackageFromRecord(final Object v1)
	{
		final PackageData currentPackage = (PackageData) ((AssemblerResponse) v1).getAttributes().get("package");
		return currentPackage;
	}

	/**
	 * Null safe check.
	 *
	 * @param valueA
	 *           the value a
	 * @param valueB
	 *           the value b
	 * @return the int
	 */
	private int nullSafeCheck(final String valueA, final String valueB)
	{
		int result = -2;


		if (null == valueA && null == valueB)
		{
			result = 0;
		}
		else if (null == valueA && null != valueB)
		{
			if (SortingOrder.ASCENDING == sortOrder)
			{
				result = 1;
			}
			else
			{
				result = -1;
			}

		}
		else if (null != valueA && null == valueB)
		{
			if (SortingOrder.ASCENDING == sortOrder)
			{
				result = -1;
			}
			else
			{
				result = 1;
			}
		}

		return result;
	}

}
