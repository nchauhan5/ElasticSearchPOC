package com.endeca.search.api.request.dtos;

import java.util.List;
import java.util.Set;


/**
 * The Class DepartureInformation. DTO having departure data information.
 */
public class DepartureData {

	/**
	 * The departure airports. For e.g. LGW,LHR,DEL
	 */
	private List<String> departureAirports;
	
	/**
	 *  The arrival airports. For e.g. LGW,LHR,DEL
	 */
	private List<String> arrivalAirports;

	
	/**
	 * The corporate accommodation codes. For e.g. 32456|F,12345|F
	 */
	private Set<String> candidateCodes;
	
	
	/**
	 * The corporate packageproduct codes. For e.g. 32456|F,12345|F
	 */
	private Set<String> packageProductCodes;

	
	/** The accommodation count. */
	private int accommodationCount;

	/**
	 * Gets the departure airports.
	 * 
	 * @return the departure airports
	 */
	public List<String> getDepartureAirports() {
		return departureAirports;
	}

	/**
	 * Sets the departure airports.
	 * 
	 * @param departureAirports
	 *            the new departure airports
	 */
	public void setDepartureAirports(final List<String> departureAirports) {
		this.departureAirports = departureAirports;
	}


	/**
	 * Gets the candidate codes.
	 *
	 * @return the candidate codes
	 */
	public Set<String> getCandidateCodes() {
		return candidateCodes;
	}

	/**
	 * Sets the candidate codes.
	 *
	 * @param candidateCodes the new candidate codes
	 */
	public void setCandidateCodes(Set<String> candidateCodes) {
		this.candidateCodes = candidateCodes;
	}
	
	
	/**
	 * Gets the accommodation count.
	 * 
	 * @return the accommodation count
	 */
	public int getAccommodationCount() {
		return accommodationCount;
	}

	/**
	 * Sets the accommodation count.
	 * 
	 * @param accommodationCount
	 *            the new accommodation count
	 */
	public void setAccommodationCount(final int accommodationCount) {
		this.accommodationCount = accommodationCount;
	}
	
	/**
	 * Gets the package product codes.
	 *
	 * @return the package product codes
	 */
	public Set<String> getPackageProductCodes() {
		return packageProductCodes;
	}

	/**
	 * Sets the package product codes.
	 *
	 * @param packageProductCodes the new package product codes
	 */
	public void setPackageProductCodes(Set<String> packageProductCodes) {
		this.packageProductCodes = packageProductCodes;
	}

	/**
	 * @return the arrivalAirports
	 */
	public List<String> getArrivalAirports() {
		return arrivalAirports;
	}

	/**
	 * @param arrivalAirports the arrivalAirports to set
	 */
	public void setArrivalAirports(List<String> arrivalAirports) {
		this.arrivalAirports = arrivalAirports;
	}

}
