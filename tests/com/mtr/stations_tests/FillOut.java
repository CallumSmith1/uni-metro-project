package com.mtr.stations_tests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.mtr.rail_maps.MapCreator;
import com.mtr.utilities.Tokenizer;

/* Used to test the implementation of the Map Creator class.
 * I have tried to use descriptive names to avoid overly 
 * commented code
 */
public class FillOut {

	@Test
	void trueTest() {
		MapCreator mr = new MapCreator("files/MTR_CitySaverNetwork.csv");
		mr.createMap();
		assertTrue(true);
	}
}