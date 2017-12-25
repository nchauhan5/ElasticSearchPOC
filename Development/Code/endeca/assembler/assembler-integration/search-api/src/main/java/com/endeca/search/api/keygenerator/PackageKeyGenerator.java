package com.endeca.search.api.keygenerator;

import com.endeca.search.api.response.dtos.PackageData;


public interface PackageKeyGenerator
{
	public String generatePackageKey(PackageData offer);
}
