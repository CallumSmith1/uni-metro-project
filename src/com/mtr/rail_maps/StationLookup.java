package com.mtr.rail_maps;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.mtr.interfaces.Controller;
import com.mtr.stations.StationType;

public class StationLookup implements Controller {

	private RailMapGraph map;
	
	public StationLookup(RailMapGraph map) {
		this.map = map;
	}

	/*
	 * public void stationTraversal(String originStation, String destinationStation)
	 * { //public Set<StationType> stationTraversal(String originStation, String
	 * destinationStation) { Set<StationType> visited = new
	 * LinkedHashSet<StationType>(); Queue<StationType> queue = new
	 * LinkedList<StationType>();
	 * 
	 * Map<StationType, StationType> associatedNodes = new HashMap<StationType,
	 * StationType>(); boolean isShortestPath = false;
	 * 
	 * try { queue.add(map.returnStationFromName(originStation).getStation());
	 * visited.add(map.returnStationFromName(originStation).getStation()); } catch
	 * (NullPointerException npe) { System.out.println("Cannot find: " +
	 * originStation +
	 * ", please enter a valid Hong Kong station. Refer to a station map for assistance"
	 * ); //return null; }
	 * 
	 * while (!queue.isEmpty() && map != null) { StationType nextStation =
	 * queue.peek();
	 * 
	 * if(nextStation.getStationName().equals(destinationStation)) { isShortestPath
	 * = true; break; }
	 * 
	 * visited.add(nextStation);
	 * 
	 * // System.out.println(graph.getAdjacentNodes(vertex).toString()); if
	 * (map.getAdjacentNodes(map.getMap(), vertex) != null) { for (StationNodes node
	 * : map.getAdjacentNodes(map.getMap(), vertex)) { if
	 * (!visited.contains(node.getStation())) { visited.add(node.getStation());
	 * queue.add(node.getStation()); } }
	 * 
	 * } } //return visited; }
	 */
	
	
	
	public void stationTraversal(String originStation, String destinationStation) {
	//I am using a set for visited because it cannot contain duplicates and is generally O(n)
		Set<StationType > visited = new LinkedHashSet<StationType >();
		Queue<StationType > queue = new LinkedList<StationType >();
		Map<StationType , StationType > previous = new HashMap<StationType , StationType >();
		
		StationType currentNode = map.returnStationFromName(originStation).getStation();
		
		try {
			queue.add(currentNode);
			visited.add(currentNode);
		} catch (NullPointerException npe) {
			System.out.println("Cannot find: " + originStation
					+ ", please enter a valid Hong Kong station. Refer to a station map for assistance");
			//return null;
		}

		while (!queue.isEmpty() && map != null) {
			currentNode = queue.poll();
			
			if(currentNode.getStationName().equals(destinationStation)) { 
				break;
			}
			
			else { 
				
				//When a junction is reached, the queue will be set to an empty value
				
				for(StationNodes node : map.getAdjacentNodes(map.getMap(), currentNode)) {
					if(!visited.contains(node.getStation())) { 
						queue.add(node.getStation());
						visited.add(node.getStation());
						previous.put(node.getStation(), currentNode);
					}
				}
			}
			/*
			 * if(!currentNode.getStationName().equals(destinationStation)) {
			 * System.out.printf("%nWas unable to reach %s from %s%n", destinationStation,
			 * originStation); }
			 */
		}
	}
	

	// For this I do not need to use any sort of BFS implementation, because I can
	// reuse the returnStationFrom name function to get and examine the station
	// object
	@Override
	public String lookupStation(String station) {
		station = station.trim().toLowerCase();
		
		
		/*
		 * List<StationNodes> test = map.getAdjacentNodes(map.getMap(),
		 * map.returnStationFromName(station).getStation());
		 * System.out.println(test.toString());
		 */
		//check if the station name is "" or null, and if not, return the object details as a formatted string
		try {
			if (station != null && !station.replaceAll(" ", "").equals("")) {
				StationType targetStation = map.returnStationFromName(station).getStation();
				String stationDetails = "Station: " + targetStation.getStationName() + System.lineSeparator()
						+ targetStation.returnStationLines() + System.lineSeparator()
						+ targetStation.returnStationTerminuses() + System.lineSeparator();
				return stationDetails;
			}
		} catch (NullPointerException npe) {
			return "You entered: " + station + " please ensure that you enter a valid Hong Kong station";
		}
		return "Please enter a station name and try again";
	}

	
	
	@Override
	public String showPathBetween(String stationA, String stationB) {
		stationA = stationA.trim().toLowerCase();
		stationB = stationB.trim().toLowerCase();
		
		stationTraversal(stationA, stationB);
		return null;
	}

}
