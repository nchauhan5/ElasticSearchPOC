package com.endeca.search.api.services;

import java.util.List;

import com.endeca.search.api.keygenerator.PackageKeyGenerator;
import com.endeca.search.api.response.dtos.PackageData;


public interface ClubPackagesOnFlightClassService
{
	public List<PackageData> reduceAndFillPackagesWithFlightClass(final List<PackageData> offers,
			final PackageKeyGenerator keyGenerator);
}
