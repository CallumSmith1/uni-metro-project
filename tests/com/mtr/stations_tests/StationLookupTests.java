package com.mtr.stations_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.mtr.rail_maps.MapCreator;
import com.mtr.rail_maps.RailMapGraph;
import com.mtr.rail_maps.StationLookup;

/* Used to test the implementation of the StationLookup class.
 * I have tried to use descriptive names to avoid overly 
 * commented code
 */
public class StationLookupTests {
	private MapCreator createMap = new MapCreator("files/MTR_CitySaverNetwork.csv");
	private StationLookup lookup = new StationLookup(createMap.getRailMap());

	@Test
	void lookupIndividualStation() {
		createMap.createMap();

		String result = lookup.lookupStation("Kowloon");
		assertEquals("Station: kowloon\r\n" + "Is a station on the following lines: \r\n" + "* Tung Chung Line\r\n"
				+ "* Walkable Unpaid Connection 1\r\n" + "Is a terminus on the following lines: \r\n"
				+ "* Walkable Unpaid Connection 1", result.trim());
	}

	@Test
	void AssertThatTheQuickestRouteCanBeFound() {
		createMap.createMap();

		String result = lookup.showPathBetween("Kowloon", "Central");
		// This should be the fastest route
		assertEquals("The quickest route from kowloon to central is to follow: \r\n" + "* kowloon\r\n"
				+ "* hong kong\r\n" + "* central", result.trim());
	}

	@Test
	void ensureThatNullInputIsHandledForIndividualLookup() {
		createMap.createMap();

		String result = lookup.lookupStation("");
		assertEquals("Please enter a station name and try again", result);
	}

	@Test
	void ensureThatWhiteSpaceInputIsHandledForIndividualLookup() {
		createMap.createMap();

		String result = lookup.lookupStation("              ");
		assertEquals("Please enter a station name and try again", result);
	}

	@Test
	void ensureThatInvalidInputIsHandledForIndividualLookup() {
		createMap.createMap();

		String result = lookup.lookupStation("!£$%^&&*()sdsdkfj");
		assertEquals("You entered: !£$%^&&*()sdsdkfj please ensure that you enter a valid Hong Kong station", result);
	}

	@Test
	void ensureThatNullInputIsHandledForFastestPathLookup() {
		createMap.createMap();

		try {
			lookup.showPathBetween("", "Kowloon");
		} catch (NullPointerException npe) {
			assertEquals(
					"The station name cannot be blank, please input a valid station and try again. Please refer to a station Map for assistance",
					npe.getMessage());
		}

	}

	@Test
	void ensureThatWhiteSpaceInputIsHandledForFastestPathLookup() {
		createMap.createMap();

		try {
			lookup.showPathBetween("Kowloon", "          ");
		} catch (NullPointerException npe) {
			assertEquals(
					"The station name cannot be blank, please input a valid station and try again. Please refer to a station Map for assistance",
					npe.getMessage());
		}

	}

	@Test
	void ensureThatInvalidInputIsHandledForFastestPathLookupOnFirstEntry() {
		createMap.createMap();

		try {
			lookup.showPathBetween("%^£($^£(*&HJHG@:~", "Kowloon");
		} catch (NullPointerException npe) {
			assertEquals("You entered: %^£($^£(*&HJHG@:~ please ensure that you enter a valid Hong Kong station",
					npe.getMessage());
		}

	}

	@Test
	void ensureThatInvalidInputIsHandledForFastestPathLookupOnSecondEntry() {
		createMap.createMap();

		try {
			lookup.showPathBetween("Kowloon", "%^£($^£(*&HJHG@:~");
		} catch (NullPointerException npe) {
			assertEquals("You entered: %^£($^£(*&HJHG@:~ please ensure that you enter a valid Hong Kong station",
					npe.getMessage());
		}

	}
}