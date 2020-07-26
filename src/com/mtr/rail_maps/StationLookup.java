package com.mtr.rail_maps;

import java.util.ArrayList;
import java.util.Collections;
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
	 * This is a Breadth First Search method to find the shortest path between
	 * stations. I will not look for the cheapest option, but the
	 * StationType.startNewJourney() method will keep a track of how many tickets
	 * will be used for the journey in question
	 */
	public List<String> stationTraversal(String originStation, String destinationStation) {
		// I am using a set for visited because it cannot contain duplicates and is
		// generally O(n)
		Set<StationType> visited = new LinkedHashSet<StationType>();
		Queue<StationType> queue = new LinkedList<StationType>();
		Map<StationType, StationType> previous = new HashMap<StationType, StationType>();
		List<String> stationsToDestination = new ArrayList<String>();

		StationType currentNode = null;
		StationType destination = null;

		/*
		 * This is checking the user values to make sure that they exist and are not
		 * null
		 */
		try {
			currentNode = handleStationCheck(originStation);
		} catch (IllegalArgumentException iae) {
			System.out.println(iae.getMessage());
			return null;
		}
		try {
			destination = handleStationCheck(destinationStation);
		} catch (IllegalArgumentException iae) {
			System.out.println(iae.getMessage());
			return null;
		}

		queue.add(currentNode);
		visited.add(currentNode);
		while (!queue.isEmpty() && map != null) {
			currentNode = queue.poll();

			if (currentNode.getStationName().equals(destinationStation)) {
				break;
			} else {

				for (StationNodes node : map.getAdjacentNodes(map.getMap(), currentNode)) {
					if (!visited.contains(node.getStation())) {
						queue.add(node.getStation());
						visited.add(node.getStation());
						previous.put(node.getStation(), currentNode);
					}
				}
			}
		}
		for (StationType dest = destination; dest != null; dest = previous.get(dest)) {
			stationsToDestination.add(dest.getStationName());
		}
		return stationsToDestination;
	}

	/*
	 * Drawing this out of the BFS method to make for easier debugging. Having these
	 * will drag the method over 30 lines which is not ideal.
	 */
	private StationType handleStationCheck(String stationToCheck) {
		if (stationToCheck == null || stationToCheck.replaceAll(" ", "").equals("")) {
			throw new IllegalArgumentException(
					"The station name cannot be blank, please input a valid station and try again. Please refer to a station Map for assistance");
		} else {
			try {
				return map.returnStationFromName(stationToCheck).getStation();
			} catch (NullPointerException npe) {
				throw new IllegalArgumentException("Cannot find: " + stationToCheck
						+ ", please enter a valid Hong Kong station. Refer to a station map for assistance");
			}
		}
	}

	// For this I do not need to use any sort of BFS implementation, because I can
	// reuse the returnStationFrom name function to get and examine the station
	// object
	@Override
	public String lookupStation(String station) {
		station = station.trim().toLowerCase();

		// check if the station name is "" or null, and if not, return the object
		// details as a formatted string
		try {
			if (station != null && !station.replaceAll(" ", "").equals("")) {
				StationType targetStation = map.returnStationFromName(station).getStation();
				String stationDetails = System.lineSeparator() + "Station: " + targetStation.getStationName() + System.lineSeparator()
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

		/*
		 * Start the BFS method and return a list of Strings. I am making a new variable
		 * on purpose so that it is easier to read in the string processing
		 */
		List<String> fastestPathList = stationTraversal(stationA, stationB);
		//I am reversing the string because they will be added in the order of last -> first
		try { 
		Collections.reverse(fastestPathList);
		String formattedString = System.lineSeparator() + "The quickest route from " + stationA + " to " + stationB + " is to follow: ";
		// I am using the if statement because I will return null from null pointers in the BFS
		if (fastestPathList == null) {
			formattedString = "";
		} else {
			// Format the return string
			for (String station : fastestPathList) {
				formattedString += System.lineSeparator() + "* " +  station;
			}
		}

		return formattedString;
		}
		catch(Exception any) { 
			return "";
		}

	}

}
