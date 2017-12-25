package com.endeca.search.api.response.dtos;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * The Class FlightInfo. DTO to store transport information.
 */
public class FlightData
{

	/** The flight type. */
	private String flightType;

	//Departure Information
	/** The departure port. */
	private String departurePort;

	/** The departure date. */
	private Date departureDate;

	/** The departure time. */
	private Date departureTime;

	//Arrival Information
	/** The arrival port. */
	private String arrivalPort;

	/** The arrival date. */
	private Date arrivalDate;

	/** The arrival time. */
	private Date arrivalTime;

	//Carrier Related Information
	/** The Availability. */
	private int availability;

	/** The flight no. */
	private String flightNo;

	/** The airplane code. */
	private String airplaneCode;

	//Extra Information
	/** The dreamliner. */
	private boolean dreamliner;

	/** The car. */
	private boolean car;

	/** The meals. */
	private boolean meals;

	/** The flight info. */
	private String flightInfo;

	/** The journey duration. */
	private ExtraFlightDetails journeyExtras;

	/**
	 * @return the flightInfo
	 */
	public String getFlightInfo()
	{
		return flightInfo;
	}

	/**
	 * @param flightInfo
	 *           the flightInfo to set
	 */
	public void setFlightInfo(final String flightInfo)
	{
		this.flightInfo = flightInfo;
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
	 * Gets the availability.
	 *
	 * @return the availability
	 */
	public int getAvailability()
	{
		return availability;
	}

	/**
	 * Gets the departure port.
	 *
	 * @return the departure port
	 */
	public String getDeparturePort()
	{
		return departurePort;
	}

	/**
	 * Sets the departure port.
	 *
	 * @param departurePort
	 *           the new departure port
	 */
	public void setDeparturePort(final String departurePort)
	{
		this.departurePort = departurePort;
	}

	/**
	 * Gets the departure date.
	 *
	 * @return the departure date
	 */
	public Date getDepartureDate()
	{
		return (Date) departureDate.clone();
	}

	/**
	 * Sets the departure date.
	 *
	 * @param departureDate
	 *           the new departure date
	 */
	public void setDepartureDate(final Date departureDate)
	{
		this.departureDate = new Date(departureDate.getTime());
	}

	/**
	 * Gets the departure time.
	 *
	 * @return the departure time
	 */
	public Date getDepartureTime()
	{
		return (Date) departureTime.clone();
	}

	/**
	 * Sets the departure time.
	 *
	 * @param departureTime
	 *           the new departure time
	 */
	public void setDepartureTime(final Date departureTime)
	{
		this.departureTime = new Date(departureTime.getTime());
	}

	/**
	 * Gets the arrival port.
	 *
	 * @return the arrival port
	 */
	public String getArrivalPort()
	{
		return arrivalPort;
	}

	/**
	 * Sets the arrival port.
	 *
	 * @param arrivalPort
	 *           the new arrival port
	 */
	public void setArrivalPort(final String arrivalPort)
	{
		this.arrivalPort = arrivalPort;
	}

	/**
	 * Gets the arrival date.
	 *
	 * @return the arrival date
	 */
	public Date getArrivalDate()
	{
		return (Date) arrivalDate.clone();
	}

	/**
	 * Sets the arrival date.
	 *
	 * @param arrivalDate
	 *           the new arrival date
	 */
	public void setArrivalDate(final Date arrivalDate)
	{
		this.arrivalDate = new Date(arrivalDate.getTime());
	}

	/**
	 * Gets the arrival time.
	 *
	 * @return the arrival time
	 */
	public Date getArrivalTime()
	{
		return (Date) arrivalTime.clone();
	}

	/**
	 * Sets the arrival time.
	 *
	 * @param arrivalTime
	 *           the new arrival time
	 */
	public void setArrivalTime(final Date arrivalTime)
	{
		this.arrivalTime = new Date(arrivalTime.getTime());
	}


	/**
	 * Gets the flight no.
	 *
	 * @return the flight no
	 */
	public String getFlightNo()
	{
		return flightNo;
	}

	/**
	 * Sets the flight no.
	 *
	 * @param flightNo
	 *           the new flight no
	 */
	public void setFlightNo(final String flightNo)
	{
		this.flightNo = flightNo;
	}

	/**
	 * Gets the airplane code.
	 *
	 * @return the airplane code
	 */
	public String getAirplaneCode()
	{
		return airplaneCode;
	}

	/**
	 * Sets the airplane code.
	 *
	 * @param airplaneCode
	 *           the new airplane code
	 */
	public void setAirplaneCode(final String airplaneCode)
	{
		this.airplaneCode = airplaneCode;
	}

	/**
	 * Checks if is dreamliner.
	 *
	 * @return true, if is dreamliner
	 */
	public boolean isDreamliner()
	{
		return dreamliner;
	}

	/**
	 * Sets the dreamliner.
	 *
	 * @param dreamliner
	 *           the new dreamliner
	 */
	public void setDreamliner(final boolean dreamliner)
	{
		this.dreamliner = dreamliner;
	}

	/**
	 * Checks if is car.
	 *
	 * @return true, if is car
	 */
	public boolean isCar()
	{
		return car;
	}

	/**
	 * Sets the car.
	 *
	 * @param car
	 *           the new car
	 */
	public void setCar(final boolean car)
	{
		this.car = car;
	}

	/**
	 * Checks if is meals.
	 *
	 * @return true, if is meals
	 */
	public boolean isMeals()
	{
		return meals;
	}

	/**
	 * Sets the meals.
	 *
	 * @param meals
	 *           the new meals
	 */
	public void setMeals(final boolean meals)
	{
		this.meals = meals;
	}

	/**
	 * Sets the flight type.
	 *
	 * @param flightType
	 *           the new flight type
	 */
	public void setFlightType(final String flightType)
	{
		this.flightType = flightType;
	}

	/**
	 * Gets the flight type.
	 *
	 * @return the flight type
	 */
	public String getFlightType()
	{
		return flightType;
	}

	/**
	 * Gets the journey extras.
	 *
	 * @return the journey extras
	 */
	public ExtraFlightDetails getJourneyExtras()
	{
		return journeyExtras;
	}

	/**
	 * Sets the journey extras.
	 *
	 * @param journeyExtras
	 *           the new journey extras
	 */
	public void setJourneyExtras(final ExtraFlightDetails journeyExtras)
	{
		this.journeyExtras = journeyExtras;
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
