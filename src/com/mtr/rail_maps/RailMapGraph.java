package com.mtr.rail_maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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

	// set the key to be a string representation of the station
	private Map<StationNodes, List<StationNodes>> adjacentNodes;
	private RailMapGraph railMap;

	public RailMapGraph() {
		// I might want to use a set for this??
		this.adjacentNodes = new HashMap<StationNodes, List<StationNodes>>();
	}

	/*
	 * Create singleton so that we don't have more than one subway roaming around
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

	public void addStation(StationType station) {
		// What if it's a duplicate -- TODO: Handle this
		if (checkIfDuplicate(station)) {

		} else {
			adjacentNodes.putIfAbsent(new StationNodes(station), new ArrayList<>());
		}
	}

	/*
	 * This method takes a station object and removes it from the graph TODO: Do I
	 * really need this????
	 */
	public void removeStation(StationType station) {
		StationNodes node = new StationNodes(station);
		// TODO: Add an enhanced for loop here using iterable

		adjacentNodes.values().stream().forEach(e -> e.remove(node));
		adjacentNodes.remove(new StationNodes(station));
	}

	/**
	 * This method takes two station objects (vertexes) and connects them on the
	 * graph
	 */
	public void addStationConnection(StationType station1, StationType station2) {

		StationNodes edgeA = new StationNodes(station1);
		StationNodes edgeB = new StationNodes(station2);

		if (adjacentNodes.get(edgeA) != null) {
			adjacentNodes.get(edgeA).add(edgeB);
		}
		if (adjacentNodes.get(edgeB) != null) {
			adjacentNodes.get(edgeB).add(edgeA);
		}
	}

	/*
	 * Return the nodes adjacent to the one that is passed to this method
	 */
	public List<StationNodes> getAdjacentNodes(StationType station) {
		return adjacentNodes.get(new StationNodes(station));
	}

	private boolean checkIfDuplicate(StationType station) {
		if(stationTraversal(station).contains(station)) { 
			System.out.println("##########################################");
		}
		return true;
	}

	//Move this out because the station object is null
	public Set<StationType> stationTraversal(StationType stationRoot) {

		Set<StationType> visited = new LinkedHashSet<StationType>();
		Queue<StationType> queue = new LinkedList<StationType>();
		queue.add(stationRoot);
		visited.add(stationRoot);
		while (!queue.isEmpty() && railMap != null) {
			StationType vertex = queue.poll();
			
			for (StationNodes node : railMap.getAdjacentNodes(vertex)) {
				if (!visited.contains(node.getStation())) {
					visited.add(node.getStation());
					queue.add(node.getStation());
				}
			}
		}
		return visited;
	}

}
