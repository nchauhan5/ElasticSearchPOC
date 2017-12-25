package com.endeca.search.api.response.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * The Class PackagesData. Wrapper DTO containing list of {@code PackageData} and {@code FacetCategoryData}
 */

@XmlRootElement
public class PackagesData
{

	/** The packages. */
	private List<PackageData> packages;

	/** The facet category datas. */
	private List<FacetCategoryData> facetCategoryDatas;

	/** The count. */
	private int count;

	/**
	 * Gets the packages.
	 *
	 * @return the packages
	 */
	public List<PackageData> getPackages()
	{
		return packages;
	}

	/**
	 * Sets the packages.
	 *
	 * @param packages
	 *           the new packages
	 */
	public void setPackages(final List<PackageData> packages)
	{
		this.packages = packages;
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public int getCount()
	{
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count
	 *           the new count
	 */
	public void setCount(final int count)
	{
		this.count = count;
	}

	/**
	 * Gets the facet category datas.
	 *
	 * @return the facet category datas
	 */
	public List<FacetCategoryData> getFacetCategoryDatas()
	{
		return facetCategoryDatas;
	}

	/**
	 * Sets the facet category datas.
	 *
	 * @param facetCategoryDatas
	 *           the new facet category datas
	 */
	public void setFacetCategoryDatas(final List<FacetCategoryData> facetCategoryDatas)
	{
		this.facetCategoryDatas = facetCategoryDatas;
	}

}
