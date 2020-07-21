package com.mtr.stations_tests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.mtr.rail_maps.MapCreator;
import com.mtr.rail_maps.RailMapGraph;
import com.mtr.rail_maps.StationLookup;
import com.mtr.stations.TrainStation;
import com.mtr.utilities.Tokenizer;

/* Used to test the implementation of the Map Creator class.
 * I have tried to use descriptive names to avoid overly 
 * commented code
 */
public class FillOut {

	@Test
	void trueTest() {
		MapCreator mr = new MapCreator("files/MTR_CitySaverNetwork.csv");
		RailMapGraph graph = new RailMapGraph(); 
		StationLookup lookup = new StationLookup(mr.createMap());
		//lookup.stationTraversal("East Tsim Sha Tsui");
		
		assertTrue(true);
	}
} 