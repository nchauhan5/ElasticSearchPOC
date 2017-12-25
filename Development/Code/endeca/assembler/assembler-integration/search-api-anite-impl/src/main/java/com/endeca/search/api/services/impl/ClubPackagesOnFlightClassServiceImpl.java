package com.endeca.search.api.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.endeca.search.api.keygenerator.PackageKeyGenerator;
import com.endeca.search.api.response.dtos.BookingClass;
import com.endeca.search.api.response.dtos.DiscountedPriceData;
import com.endeca.search.api.response.dtos.PackageData;
import com.endeca.search.api.response.dtos.RoomData;
import com.endeca.search.api.services.ClubPackagesOnFlightClassService;


/**
 * The Class MassagePackagesServiceImpl.
 */
public class ClubPackagesOnFlightClassServiceImpl implements ClubPackagesOnFlightClassService
{

	/** The configured board basis. */
	private Map<String, String> configuredBoardBasis;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.endeca.search.api.services.MassagePackagesService#reduceAndFillPackagesWithFlightClass(java.util.List,
	 * com.endeca.search.api.keygenerator.PackageKeyGenerator)
	 */
	@Override
	public List<PackageData> reduceAndFillPackagesWithFlightClass(final List<PackageData> offers,
			final PackageKeyGenerator keyGenerator)
	{
		final Map<String, PackageData> cheapestUniqueOffers = reduceCheapestUniqueOffers(offers, keyGenerator);
		for (final PackageData offer : offers)
		{
			final String key = keyGenerator.generatePackageKey(offer);
			final PackageData cheapestOffer = cheapestUniqueOffers.get(key);
			if (!offer.equals(cheapestOffer))
			{
				final BigDecimal price1 = getPriceFromPackage(offer);
				final BigDecimal price2 = getPriceFromPackage(cheapestOffer);
				final BigDecimal upgradePrice = price1.subtract(price2);

				final BigDecimal price1PP = getPricePPFromPackage(offer);
				final BigDecimal price2PP = getPricePPFromPackage(cheapestOffer);
				final BigDecimal upgradePricePP = price1PP.subtract(price2PP);

				if (!upgradePrice.equals(BigDecimal.ZERO))
				{
					clubCurrentOfferUpgradeWithCheapest(offer, cheapestOffer, upgradePrice, upgradePricePP);
				}
			}
		}
		return getBackCondensedPackagesListWithSortedClasses(cheapestUniqueOffers);
	}

	/**
	 * Gets the discounted price from package.
	 *
	 * @param offer
	 *           the offer
	 * @return the discounted price from package
	 */
	private BigDecimal getPriceFromPackage(final PackageData offer)
	{
		return offer.getOfferData().getDiscountedPrice() != null ? offer.getOfferData().getDiscountedPrice()
				: offer.getTotalPriceData().getTotalPrice();
	}

	/**
	 * Gets the price per person from package.
	 *
	 * @param offer
	 *           the offer
	 * @return the price per person from package
	 */
	private BigDecimal getPricePPFromPackage(final PackageData offer)
	{
		return offer.getOfferData().getDiscountedPricePerson() != null ? offer.getOfferData().getDiscountedPricePerson()
				: offer.getTotalPriceData().getTotalPricePerPerson();
	}

	/**
	 * This method takes a list of offers and determines which offers are equal related to the given
	 * {@link PackageKeyGenerator} except the price and reduces them accordingly. The returned map contains the generated
	 * offer keys and the reduced offers as values.
	 *
	 * @param offers
	 *           The {@link PackageData} list that shall be reduced according to the given {@link PackageKeyGenerator}.
	 * @param offerKeyGenerator
	 *           The {@link PackageKeyGenerator} that creates a key using specific {@link PackageData} properties that
	 *           describe which offers are equal except the price.
	 * @return
	 */
	private Map<String, PackageData> reduceCheapestUniqueOffers(final List<PackageData> offers,
			final PackageKeyGenerator offerKeyGenerator)
	{
		final Map<String, PackageData> cheapestPricedOffers = new HashMap<String, PackageData>();
		for (final PackageData offer : offers)
		{
			final String key = offerKeyGenerator.generatePackageKey(offer);
			if (cheapestPricedOffers.containsKey(key))
			{
				final PackageData cheapestOffer = cheapestPricedOffers.get(key);
				final BigDecimal cheapestOfferPrice = cheapestOffer.getOfferData().getDiscountedPrice() != null
						? cheapestOffer.getOfferData().getDiscountedPrice() : cheapestOffer.getTotalPriceData().getTotalPrice();
				if (offer.getOfferData().getDiscountedPrice() != null
						&& offer.getOfferData().getDiscountedPrice().compareTo(cheapestOfferPrice) < 0)
				{
					cheapestPricedOffers.put(key, offer);
				}
				else if (offer.getTotalPriceData().getTotalPrice().compareTo(cheapestOfferPrice) < 0)
				{
					cheapestPricedOffers.put(key, offer);
				}
			}
			else
			{
				cheapestPricedOffers.put(key, offer);
			}
		}
		return cheapestPricedOffers;
	}

	/**
	 * Club current offer upgrade with cheapest.
	 *
	 * @param offer
	 *           the offer
	 * @param cheapestoffer
	 *           the cheapestoffer
	 * @param priceUpgrade
	 *           the price upgrade
	 * @param priceUpgradePP
	 *           the price upgrade pp
	 */
	private void clubCurrentOfferUpgradeWithCheapest(final PackageData offer, final PackageData cheapestoffer,
			final BigDecimal priceUpgrade, final BigDecimal priceUpgradePP)
	{
		final BookingClass currentOfferBookingClass = new BookingClass();

		currentOfferBookingClass.setCode(offer.getBookingFlightClass());

		final DiscountedPriceData discountedPriceData = new DiscountedPriceData();
		discountedPriceData.setDiscountPrice(offer.getOfferData().getDiscountedPrice());
		discountedPriceData.setDiscountPricePerPerson(offer.getOfferData().getDiscountedPricePerson());
		currentOfferBookingClass.setDiscountedPriceData(discountedPriceData);

		currentOfferBookingClass.setTotalPrice(offer.getTotalPriceData());
		currentOfferBookingClass.setUpgradeAmount(priceUpgrade);
		currentOfferBookingClass.setUpgradeAmountPP(priceUpgradePP);
		currentOfferBookingClass.setPkgId(populateConcatenatedPackageID(offer));

		cheapestoffer.getBookingClasses().add(currentOfferBookingClass);
	}

	/**
	 * Populate package id.
	 *
	 * @param singlePackage
	 *           the single package
	 * @return the string
	 */
	private String populateConcatenatedPackageID(final PackageData singlePackage)
	{
		final String pipeSeparator = "|";
		final StringBuilder packageIDBuilder = new StringBuilder();
		packageIDBuilder.append(singlePackage.getOutboundFlight().get(0).getFlightInfo()).append(pipeSeparator)
				.append(singlePackage.getHotelData().getSysInfo()).append(pipeSeparator)
				.append(singlePackage.getRoomsData().get(0).getRoomInfo());
		return packageIDBuilder.toString();
	}

	/**
	 * Gets the back condensed packages list with sorted classes.
	 *
	 * @param condensedPackagesMap
	 *           the condensed packages map
	 * @return the back condensed packages list with sorted classes
	 */
	private List<PackageData> getBackCondensedPackagesListWithSortedClasses(final Map<String, PackageData> condensedPackagesMap)
	{
		final List<PackageData> packages = new ArrayList<PackageData>();
		final Iterator<Map.Entry<String, PackageData>> entries = condensedPackagesMap.entrySet().iterator();
		while (entries.hasNext())
		{
			final Map.Entry<String, PackageData> entry = entries.next();
			final PackageData currentPackage = entry.getValue();
			if (!CollectionUtils.isEmpty(currentPackage.getBookingClasses()))
			{
				final List<BookingClass> bookingClassesForCurrentOffer = currentPackage.getBookingClasses();
				Collections.sort(bookingClassesForCurrentOffer);
			}
			changeBoardCodeToHybrisCode(currentPackage);
			packages.add(currentPackage);
		}
		return packages;
	}

	/**
	 * Change board code to hybris code.
	 *
	 * @param packageData
	 *           the package data
	 */
	private void changeBoardCodeToHybrisCode(final PackageData packageData)
	{
		for (final RoomData roomData : packageData.getRoomsData())
		{
			roomData.setDefaultBoard(configuredBoardBasis.get(roomData.getDefaultBoard()));
		}
	}

	/**
	 * Sets the configured board basis.
	 *
	 * @param configuredBoardBasis
	 *           the configured board basis
	 */
	public void setConfiguredBoardBasis(final Map<String, String> configuredBoardBasis)
	{
		this.configuredBoardBasis = configuredBoardBasis;
	}

}
