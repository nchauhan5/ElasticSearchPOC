package com.endeca.search.api.keygenerator.impl;

import org.springframework.util.CollectionUtils;

import com.endeca.search.api.keygenerator.PackageKeyGenerator;
import com.endeca.search.api.response.dtos.FlightData;
import com.endeca.search.api.response.dtos.PackageData;


/**
 * The Class FlightPackageKeyGenerator.
 */
public class FlightPackageKeyGenerator implements PackageKeyGenerator
{

	@Override
	public String generatePackageKey(final PackageData offer)
	{
		validateOffer(offer);
		final StringBuilder builder = new StringBuilder();
		for (final FlightData flight : offer.getOutboundFlight())
		{
			builder.append(createRouteKey(flight, builder));
		}
		for (final FlightData flight : offer.getInboundFlight())
		{
			builder.append(createRouteKey(flight, builder));
		}
		return builder.toString();
	}

	private void validateOffer(final PackageData offer)
	{
		if (CollectionUtils.isEmpty(offer.getInboundFlight()) || CollectionUtils.isEmpty(offer.getOutboundFlight()))
		{
			throw new IllegalArgumentException("Invalid offer: At least 1 inbound and 1 outbound flight needed for flight offers!");
		}
	}

	private String createRouteKey(final FlightData flight, final StringBuilder builder)
	{
		return builder.append(flight.getFlightNo()).append(flight.getArrivalPort()).append(flight.getDeparturePort())
				.append(flight.getArrivalDate()).append(flight.getArrivalTime()).append(flight.getDepartureDate())
				.append(flight.getDepartureTime()).toString();
	}

}
