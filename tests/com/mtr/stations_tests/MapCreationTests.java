package com.mtr.stations_tests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.mtr.rail_maps.MapCreator;
import com.mtr.rail_maps.RailMapGraph;
import com.mtr.rail_maps.StationLookup;

/* Used to test the implementation of the Map Creator class.
 * I have tried to use descriptive names to avoid overly 
 * commented code
 */
public class MapCreationTests {
	private MapCreator mr = new MapCreator("files/MTR_CitySaverNetwork.csv");
	RailMapGraph graph = new RailMapGraph();

	@Test
	void createMapFromFile() {
		mr.createMap();
		RailMapGraph graph = new RailMapGraph();
		if (graph.getMap() != null) {
			assertTrue(true);
		} else {
			assertTrue("Failed to create map from file", false);
		}
		// StationLookup lookup = new StationLookup(mr.createMap());
	}
	
	@Test
	void assertThatThereCanBeNoDuplicateMaps() { 
		RailMapGraph graph1 = graph.createNewMap();
		RailMapGraph graph2 = graph.createNewMap();
		
		if(graph1 == graph2) { 
			assertTrue(true);
		}
		else { 
			assertTrue("Maps created are not the same", false);
		}
	}
	
}