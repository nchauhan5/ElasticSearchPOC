/**
 *
 */
package com.core.elastic.converters;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.core.elastic.data.ElasticAccommodationDTO;
import com.tui.group.core.daos.FeatureValueDao;
import com.tui.group.core.daos.HotelAttributeDao;
import com.tui.group.core.endeca.out.resolvers.util.ImageResolverUtil;
import com.tui.group.core.enums.LocationType;
import com.tui.group.core.model.AccommodationModel;
import com.tui.group.core.model.BaseAccommodationModel;
import com.tui.group.core.model.ConceptModel;
import com.tui.group.core.model.FeatureValueModel;
import com.tui.group.core.model.HotelAttributeModel;
import com.tui.group.core.model.HotelVersionInfoModel;
import com.tui.group.core.model.LabelModel;
import com.tui.group.core.model.LocationModel;
import com.tui.group.core.model.PeriodModel;
import com.tui.group.core.pim.helpers.TuiPimConstants;
import com.tui.group.core.pim.helpers.URLGenerator;
import com.tui.group.core.pim.services.LocationService;


/**
 * @author msin82
 * 
 */
public class ModelToDTOConverter implements Converter<List<AccommodationModel>, List<ElasticAccommodationDTO>>
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ModelToDTOConverter.class);

	/**
	 * Constant Declaration for Location.
	 */
	private static final String LOCATION_STR = "Location";

	@Autowired
	private URLGenerator urlGenerator;

	@Autowired
	private HotelAttributeDao hotelAttributeDao;

	@Autowired
	private FeatureValueDao featureValueDao;

	@Autowired
	private LocationService locationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public List<ElasticAccommodationDTO> convert(final List<AccommodationModel> source)
	{

		final List<ElasticAccommodationDTO> elasticAccommodations = new ArrayList<>();
		for (final AccommodationModel currentAccommodation : source)
		{


			final ProductModel productModel = currentAccommodation.getBaseProduct();

			final ElasticAccommodationDTO elasticAccommodationDTO = new ElasticAccommodationDTO();

			elasticAccommodationDTO.setCode(currentAccommodation.getCode() != null ? currentAccommodation.getCode()
					: StringUtils.EMPTY);

			String accommodationName = StringUtils.EMPTY;
			final HotelVersionInfoModel hotelVerInfoModel = currentAccommodation.getHotelVersionInfo();
			if (null != hotelVerInfoModel)
			{
				accommodationName = hotelVerInfoModel.getVersionName();
			}
			elasticAccommodationDTO.setName(accommodationName);

			elasticAccommodationDTO.setBaseProductCode(productModel.getCode() != null ? productModel.getCode() : StringUtils.EMPTY);

			elasticAccommodationDTO.setCommercialPriority(currentAccommodation.getCommPriority());

			final String accommodationPageURL = urlGenerator.generateURLForAccommodation(currentAccommodation, null);
			elasticAccommodationDTO.setPageURL(accommodationPageURL != null ? accommodationPageURL : StringUtils.EMPTY);


			final HotelAttributeModel hotelAttributeModel = hotelAttributeDao.getHotelAttribute(currentAccommodation, "USP");
			elasticAccommodationDTO.setAccommodationUSP(null != hotelAttributeModel ? hotelAttributeModel.getValue()
					: StringUtils.EMPTY);


			String isWIFIAvailable = StringUtils.EMPTY;
			final FeatureValueModel featureValueModel = featureValueDao.getFeatureValue((BaseAccommodationModel) productModel,
					TuiPimConstants.WIRELESS_INTERNET);
			if (featureValueModel != null)
			{
				if (StringUtils.isNotEmpty(featureValueModel.getValue(Locale.ENGLISH))
						&& "1".equals(featureValueModel.getValue(Locale.ENGLISH)))
				{
					isWIFIAvailable = featureValueModel.getValue(Locale.ENGLISH);
				}
			}
			elasticAccommodationDTO.setWifiAvailablity(isWIFIAvailable);


			final List<LocationModel> locationModels = new ArrayList<>();
			setLocations(currentAccommodation.getSupercategories(), locationModels);
			final LocationModel resortModel = getLocationForType(locationModels, LocationType.RESORT);
			final LocationModel destinationModel = getLocationForType(locationModels, LocationType.DESTINATION);
			final LocationModel countryModel = getLocationForType(locationModels, LocationType.COUNTRY);
			setLocationDetails(resortModel, LocationType.RESORT, elasticAccommodationDTO);
			setLocationDetails(destinationModel, LocationType.DESTINATION, elasticAccommodationDTO);
			setLocationDetails(countryModel, LocationType.COUNTRY, elasticAccommodationDTO);


			String rating = StringUtils.EMPTY;
			String accommodationrating = currentAccommodation.getClassification();
			if (StringUtils.isNotBlank(accommodationrating))
			{
				if (NumberUtils.isNumber(accommodationrating))
				{
					rating = accommodationrating;
				}
				else if (StringUtils.contains(accommodationrating, "+"))
				{
					accommodationrating = accommodationrating.replace("+", "");
					accommodationrating = accommodationrating + ".5";
					rating = accommodationrating;
				}
			}
			elasticAccommodationDTO.setAccommodationRating(rating);

			final Collection<MediaContainerModel> mediaContainerModels = currentAccommodation.getContentImages();
			final List<MediaContainerModel> mediaImages = new ArrayList<MediaContainerModel>();
			final Collection<String> list = new ArrayList<String>();
			if (CollectionUtils.isNotEmpty(mediaContainerModels))
			{
				mediaImages.addAll(mediaContainerModels);
				Collections.sort(mediaImages, new MediaComparator());
				ImageResolverUtil.populateAccomodationImages(mediaImages, list, "IMG_312x208");
			}
			String imageURL = StringUtils.EMPTY;
			final List<String> concatenatedMedias = new ArrayList<>(list);
			if (CollectionUtils.isNotEmpty(concatenatedMedias))
			{
				final String[] separateImageURLs = concatenatedMedias.get(0).split("@");
				imageURL = separateImageURLs[0];
			}
			elasticAccommodationDTO.setImageURL(imageURL);

			final Collection<CategoryModel> categoryModels = currentAccommodation.getSupercategories();
			updateBrandDetails(categoryModels, elasticAccommodationDTO);

			if (CollectionUtils.isNotEmpty(categoryModels))
			{
				for (final CategoryModel categoryModel : categoryModels)
				{
					if (categoryModel instanceof PeriodModel)
					{
						final String[] splittedPeriodCode = StringUtils.split(categoryModel.getCode(), "-");
						final Integer periodCode = Integer.parseInt(splittedPeriodCode[1]);
						elasticAccommodationDTO.setSeasonCode(periodCode != null ? periodCode.toString() : StringUtils.EMPTY);
						break;
					}
				}
			}

			elasticAccommodations.add(elasticAccommodationDTO);

			//	++counter;
		}
		return elasticAccommodations;
	}

	/**
	 * @param categoryModels
	 */
	private void updateBrandDetails(final Collection<CategoryModel> categoryModels,
			final ElasticAccommodationDTO elasticAccommodationDTO)
	{
		if (CollectionUtils.isNotEmpty(categoryModels))
		{
			for (final CategoryModel categoryModel : categoryModels)
			{
				if (categoryModel instanceof ConceptModel || categoryModel instanceof LabelModel)
				{
					elasticAccommodationDTO.setBrand(categoryModel.getName(new Locale("en")));
					elasticAccommodationDTO.setConceptCode(categoryModel.getCode());
				}
			}
		}

	}

	/**
	 * Sets the location details.
	 * 
	 * @param location
	 *           the location
	 * @param locationType
	 *           the location type
	 * @param elasticAccommodationDTO
	 *           the elastic accommodation dto
	 */
	private void setLocationDetails(final LocationModel location, final LocationType locationType,
			final ElasticAccommodationDTO elasticAccommodationDTO)
	{

		String locationCode = StringUtils.EMPTY;
		String locationName = StringUtils.EMPTY;
		if (null != location)
		{
			locationCode = location.getStaticLocationId();
			if (null != location.getLocationVersionInfo() && location.getIsVisible())
			{
				locationName = locationService.getLatestLocationVersionName(locationCode, location.getCatalogVersion());
			}
		}

		if (LocationType.RESORT.equals(locationType))
		{
			elasticAccommodationDTO.setResortCode(locationCode);
			elasticAccommodationDTO.setResortName(locationName);
		}

		if (LocationType.DESTINATION.equals(locationType))
		{
			elasticAccommodationDTO.setDestinationCode(locationCode);
			elasticAccommodationDTO.setDestinationName(locationName);
		}

		if (LocationType.COUNTRY.equals(locationType))
		{
			elasticAccommodationDTO.setCountryCode(locationCode);
			elasticAccommodationDTO.setCountryName(locationName);
		}

	}

	/**
	 * Returns the matching location model for a given categories of accommodation based on the location type.
	 * 
	 * @param locationModels
	 * @return LocationModel
	 */
	private LocationModel getLocationForType(final List<LocationModel> locationModels, final LocationType locationType)
	{
		LocationModel locModel = null;
		if (CollectionUtils.isNotEmpty(locationModels))
		{
			for (final LocationModel locationModel : locationModels)
			{
				if (null != locationModel.getType() && locationType.getCode().equals(locationModel.getType().getCode()))
				{
					locModel = locationModel;
					break;
				}
			}
		}
		return locModel;
	}

	/**
	 * Returns the matching location model for a given categories of accommodation based on the location type.
	 * 
	 * @param categories
	 * @param locationModels
	 *           List
	 * 
	 */
	private void setLocations(final Collection<CategoryModel> categories, final List<LocationModel> locationModels)
	{
		final Iterator<CategoryModel> categoriesIterator = categories.iterator();
		while (categoriesIterator.hasNext())
		{
			final CategoryModel model = categoriesIterator.next();
			if (model.getItemtype().equals(LOCATION_STR))
			{
				locationModels.add((LocationModel) model);
				setLocations(model.getSupercategories(), locationModels);
			}
		}
	}

	/**
	 * 
	 * 
	 * Comparator class to sort media container on basis of sort order
	 */
	private class MediaComparator implements Comparator<MediaContainerModel>
	{
		@Override
		public int compare(final MediaContainerModel arg0, final MediaContainerModel arg1)
		{
			return arg0.getSortOrder() - arg1.getSortOrder();
		}
	}
}
