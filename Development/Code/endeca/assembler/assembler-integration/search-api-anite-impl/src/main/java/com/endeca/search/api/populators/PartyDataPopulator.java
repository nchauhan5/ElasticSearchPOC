package com.endeca.search.api.populators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.endeca.search.api.request.dtos.PartyData;


/**
 * The Class PartyDataPopulator. Used to populate {@code PartyData} contains party information such as no of adults,
 * children, room no etc.
 */
public class PartyDataPopulator
{

	/**
	 * Populate.
	 * 
	 * @param adults
	 * @param childAgeString
	 * @param rooms
	 * 
	 * @return the party data
	 */
	public PartyData populate(String adults, String childAgeString, String rooms)
	{
		final PartyData partyData = new PartyData();
		if (null != adults)
		{
			partyData.setNoOfAdults(Integer.parseInt(adults));
		}
		if (null != childAgeString)
		{
			final List<Integer> childrenAges = new ArrayList<Integer>();
			for (final String childAge : StringUtils.split(childAgeString, ","))
			{
				childrenAges.add(Integer.parseInt(childAge));
			}
			partyData.setChildrenAges(childrenAges);
			partyData.setNoOfChildren(childrenAges.size());

		}
		if (null != rooms)
		{
			partyData.setNoOfRooms(Integer.parseInt(rooms));
		}

		return partyData;
	}
}
