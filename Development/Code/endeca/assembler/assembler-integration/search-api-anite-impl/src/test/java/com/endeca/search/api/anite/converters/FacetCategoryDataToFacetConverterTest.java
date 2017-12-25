package com.endeca.search.api.anite.converters;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import static org.hamcrest.Matchers.is;
import org.junit.Assert;
import org.junit.Test;

import com.endeca.search.api.response.dtos.FacetCategoryData;
import com.endeca.search.api.response.dtos.FacetData;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Facets.Cat;
import com.endeca.tui.anite.response.AvCache.Result.Offers.Facets.Cat.Facet;

public class FacetCategoryDataToFacetConverterTest {
	
	/*This method is to test ,whether facet category data including code, count, max, min should successfully set to Facet*/
	@Test
	public void facetCategoryDataShouldBeConvertedToFacet() {
		float max=(float) 23.4;
		float min=(float) 12.2;
		long value=5;
		FacetCategoryData facetCategoryData=new FacetCategoryData();
		List<FacetData> facets=new ArrayList<FacetData>();
		FacetData facetData=new FacetData();
		facetCategoryData.setCategoryCode("favourite");
		facetData.setCode("A1");
		facetData.setMax(max);
		facetData.setMin(min);
		facets.add(facetData);
		facetData.setCount(5);
		facetCategoryData.setFacets(facets);
		Cat cat=FacetCategoryDataToFacetConverter.convert(facetCategoryData);
		assertThat(cat.getFacet().size(),is(1));
		assertThat(cat.getFacet().get(0).getCode(),is("A1"));
		assertThat(cat.getFacet().get(0).getCount(),is(value));
		assertThat(cat.getFacet().get(0).getMax(),is(BigDecimal.valueOf(max)));
		assertThat(cat.getFacet().get(0).getMin(),is(BigDecimal.valueOf(min)));
	}
	
	/*This method is to test ,whether facet category data passed Empty*/
	@Test
	public void facetShouldBeNullWhenfacetlistIsPassedEmpty() {
		float max=(float) 23.4;
		float min=(float) 12.2;
		long value=5;
		FacetCategoryData facetCategoryData=new FacetCategoryData();
		List<FacetData> facets=new ArrayList<FacetData>();
		FacetData facetData=new FacetData();
		facetCategoryData.setCategoryCode("favourite");
		Cat cat=FacetCategoryDataToFacetConverter.convert(facetCategoryData);
		assertThat(cat.getFacet().size(),is(0));
		
	}
}
