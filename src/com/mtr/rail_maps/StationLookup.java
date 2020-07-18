package com.mtr.rail_maps;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.mtr.interfaces.Controller;
import com.mtr.stations.StationType;

public class StationLookup implements Controller {

	private RailMapGraph map;

	public StationLookup(RailMapGraph map) {
		this.map = map;
	}

	// Move this out because the station object is null
	public Set<StationType> stationTraversal(String stationName) {
		
		Set<StationType> visited = new LinkedHashSet<StationType>();
		Queue<StationType> queue = new LinkedList<StationType>();
		try { 
		queue.add(map.returnStationFromName(stationName).getStation());
		visited.add(map.returnStationFromName(stationName).getStation());
		}
		catch(NullPointerException npe) { 
			System.out.println("Cannot find: " + stationName + ", please enter a valid Hong Kong station. Refer to a station map for assistance");
			return null;
		}
		
		while (!queue.isEmpty() && map != null) {
			StationType vertex = queue.poll();
			// System.out.println(graph.getAdjacentNodes(vertex).toString());
			if (map.getAdjacentNodes(map.getMap(), vertex) != null) {
				for (StationNodes node : map.getAdjacentNodes(map.getMap(), vertex)) {
					if (!visited.contains(node.getStation())) {
						visited.add(node.getStation());
						queue.add(node.getStation());
					}
				}

			}
		}
		return visited;
	}

	@Override
	public String lookupStation(String station) {
		if (station != null) { 
			return stationTraversal(station.trim().toLowerCase()).stream()
			.filter(e -> e.getStationName().equals(station.trim().toLowerCase()))
			.findFirst().toString().replaceAll("Optional", "");
			
		}
			return "Please make sure you have entered a station and try again";
		
	}

	@Override
	public String showPathBetween(String stationA, String stationB) {
		// TODO Auto-generated method stub
		return null;
	}

}
