package com.mtr.rail_maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mtr.stations.StationType;

/* 
 * I have designated this class as an adjacency list graph. I want to model 
 * the subway as a graph of stations since it allows hopping between nodes 
 * and lookup functions. My initial idea was to have a doubly linked list 
 * where the nodes could reference elements in some sort of chained list, 
 * but when drawn on paper it became a Frankenstein's monster of a structure. 
 * This allows me to use a linked structure in a cleaner way. 
 * 
 * For this, I have had to research the implementation and intricacies of the 
 * adjacency graph. Much of this has been found from external sources. I have 
 * adapted the methods and implementation as much as possible, avoiding a straight 
 * copy paste. Resources used are: 
 * + https://stackoverflow.com/
 * + https://youtube.com -- (https://youtu.be/gXgEDyodOJU)
 * + https://www.baeldung.com/java-graphs
 * + https://www.geeksforgeeks.org/graph-and-its-representations/
 */
public class RailMapGraph {

	//set the key to be a string representation of the station
	private Map<StationNodes, List<StationNodes>> adjacentNodes;
	private RailMapGraph railMap;
	
	//Keeps track of how many stations are in the graph

	public RailMapGraph() {
		/*
		 * Create a HashMap of the station nodes. Each node will have a list of the
		 * stations that it connects to
		 */
		this.adjacentNodes = new HashMap<StationNodes, List<StationNodes>>();
	}

	/*
	 * Create singleton so that we don't have more than one subway loose and roaming around
	 * the code
	 * 
	 */
	public RailMapGraph createNewMap() {
		if (railMap == null) {
			railMap = new RailMapGraph();
			return railMap;
		}
		return railMap;
	}

	/*
	 * Not really sure if I'll use this method, but adding it here just in case I
	 * need it. It is used to delete the graph without need for iteration etc.
	 */
	public void clearMap() {
		if (railMap != null) {
			railMap = null;
		}
	}

	//Return the adjacent vertices (The graph)
	public Map<StationNodes, List<StationNodes>> getMap() {
		return adjacentNodes;
	}
	

	public void addStation(StationType station) {
		StationNodes stationToInsert = new StationNodes(station);
		adjacentNodes.putIfAbsent(stationToInsert, new ArrayList<>());
		//Maybe add a field to track the number of vertices 
	}

	/**
	 * This method takes two station objects (vertexes) and connects them on the
	 * graph
	 */
	public void addStationConnection(StationType lastStation, StationType newStation) {

		StationNodes edgeA = returnStationFromName(lastStation.getStationName());
		StationNodes edgeB = returnStationFromName(newStation.getStationName());

		if (adjacentNodes.get(edgeA) != null) {
			adjacentNodes.get(edgeA).add(edgeB);
		}
		if (adjacentNodes.get(edgeB) != null) {
			adjacentNodes.get(edgeB).add(edgeA);
		}
	}
	
	/*
	 * Method for helping debug the dropped edges bug. Same as "addStationConnection"
	 * remove when possible
	 */
	public void addExistingStationConnection(StationNodes duplicateStation, StationType lastStation) {
		
		StationNodes edgeA = new StationNodes(lastStation);

		if (adjacentNodes.get(duplicateStation) != null) {
			adjacentNodes.get(duplicateStation).add(edgeA);
		}
		if (adjacentNodes.get(edgeA) != null) {
			adjacentNodes.get(edgeA).add(duplicateStation);
		}

	}


	/*
	 * Return the nodes adjacent to the one that is passed to this method
	 */
	public List<StationNodes> getAdjacentNodes(Map<StationNodes, List<StationNodes>> adjacentNodes,
			StationType station) {
			return adjacentNodes.get(new StationNodes(station));
	}

	// Takes a string, the station's name, and returns an object or null if it does
	// not exist
	public StationNodes returnStationFromName(String stationName) {
		return adjacentNodes.entrySet().stream()
				.filter(a -> a.getKey().getStation().getStationName().equals(stationName)).map(Map.Entry::getKey).findAny()
				.orElse(null);

	}

	// For checking if a passed station is already in the graph
	// Takes a station object and, using a lambda, checks to see if it exists in the key set
	public boolean checkIfDuplicateStation(StationType station) {
		String stationName = station.getStationName();
		// This is checking if the station name already exists in the key set
		boolean stationExists = adjacentNodes.entrySet().stream()
				.anyMatch(e -> e.getKey().getStation().getStationName().equals(stationName));
		if (stationExists) {
			return true;
		}
		return false;
	}
}
