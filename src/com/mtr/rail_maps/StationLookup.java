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
		//queue.add(stationRoot);
		//visited.add(stationRoot);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String showPathBetween(String stationA, String stationB) {
		// TODO Auto-generated method stub
		return null;
	}

}
