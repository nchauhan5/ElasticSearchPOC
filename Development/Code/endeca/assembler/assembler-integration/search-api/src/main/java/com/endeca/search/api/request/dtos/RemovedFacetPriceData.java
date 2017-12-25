package com.endeca.search.api.request.dtos;

import java.math.BigDecimal;

//added for DE32941
public class RemovedFacetPriceData {
	
	private String removedFacet;
	
	private BigDecimal id;
	
	private String type;

	public String getRemovedFacet() {
		return removedFacet;
	}

	public void setRemovedFacet(String removedFacet) {
		this.removedFacet = removedFacet;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

}
