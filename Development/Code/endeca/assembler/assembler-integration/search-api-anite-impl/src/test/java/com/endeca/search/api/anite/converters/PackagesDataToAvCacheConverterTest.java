package com.endeca.search.api.anite.converters;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.endeca.search.api.response.dtos.FacetCategoryData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.PackagesData;
import com.endeca.tui.anite.response.AvCache;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Facets.Cat;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Offer;

@RunWith(PowerMockRunner.class)
@PrepareForTest(
{PackageDataToOfferConverter.class,FacetCategoryDataToFacetConverter.class})

public class PackagesDataToAvCacheConverterTest {
	List<PackageData> packagesList;
	List<FacetCategoryData> facetCategoryDatas;
	PackagesData packagesData;
	FacetCategoryData facetCategory;
	PackageData packages;
	Offer offer;
	Cat cat;
 
	@Before
	public void setUp()
	{
	  packagesList=new ArrayList<PackageData>();
      facetCategoryDatas =new ArrayList<FacetCategoryData>();
      facetCategory=new FacetCategoryData();
	  packagesData=new PackagesData();
      packages=new PackageData();
	  offer=new Offer();
      cat=new Cat();
	}
	
	/*This method is to test ,whether facets data set to target avcache when facetcategory data is  not empty*/
	@Test
	public void  facetsShouldBeAddedWhenFacetCategoryDataIsNotEmpty(){
		packagesData.setPackages(packagesList);
		facetCategoryDatas.add(facetCategory);
		packagesData.setFacetCategoryDatas(facetCategoryDatas);
		PowerMockito.mockStatic(PackageDataToOfferConverter.class);
		PowerMockito.when(PackageDataToOfferConverter.convert(packages)).thenReturn(offer);
		PowerMockito.mockStatic(FacetCategoryDataToFacetConverter.class);
		PowerMockito.when(FacetCategoryDataToFacetConverter.convert(facetCategory)).thenReturn(cat);
		packagesList.add(packages);
		
		AvCache target = PackagesDataToAvCacheConverter.convert(packagesData);
		PowerMockito.verifyStatic();
		FacetCategoryDataToFacetConverter.convert(facetCategory);
		PackageDataToOfferConverter.convert(packages);
		assertThat(target.getResult().getOffers().getFacets().getCat().size(),Matchers.is(1));
		
	}

	/*This method is to test ,whether facets data set to target avcache when facetcategory data is empty*/
	@Test
	public void  facetsShouldNotBeAddedWhenFacetCategoryDataIsEmpty(){
		packagesData.setPackages(packagesList);
		packagesData.setFacetCategoryDatas(null);
		PowerMockito.mockStatic(PackageDataToOfferConverter.class);
		PowerMockito.when(PackageDataToOfferConverter.convert(packages)).thenReturn(offer);
		PowerMockito.mockStatic(FacetCategoryDataToFacetConverter.class);
		PowerMockito.when(FacetCategoryDataToFacetConverter.convert(facetCategory)).thenReturn(cat);
		packagesList.add(packages);
		
		AvCache target = PackagesDataToAvCacheConverter.convert(packagesData);
		PowerMockito.verifyStatic(Mockito.never());
		FacetCategoryDataToFacetConverter.convert(facetCategory);
		PowerMockito.verifyStatic();
		PackageDataToOfferConverter.convert(packages);
		assertNull(target.getResult().getOffers().getFacets());
	}
	
	/*This method is to test ,whether facets data set to target avcache when facetcategory data is empty*/
	@Test
	public void  offerDataShouldNotBeAddedWhenPackageDataIsEmpty(){
		packagesData.setPackages(packagesList);
		packagesData.setFacetCategoryDatas(null);
		PowerMockito.mockStatic(PackageDataToOfferConverter.class);
		PowerMockito.when(PackageDataToOfferConverter.convert(packages)).thenReturn(offer);
		PowerMockito.mockStatic(FacetCategoryDataToFacetConverter.class);
		PowerMockito.when(FacetCategoryDataToFacetConverter.convert(facetCategory)).thenReturn(cat);
		
		AvCache target = PackagesDataToAvCacheConverter.convert(packagesData);
		PowerMockito.verifyStatic(Mockito.never());
		FacetCategoryDataToFacetConverter.convert(facetCategory);
		PowerMockito.verifyStatic(Mockito.never());
		PackageDataToOfferConverter.convert(packages);
		assertTrue(target.getResult().getOffers().getOffer().isEmpty());
	}
}
