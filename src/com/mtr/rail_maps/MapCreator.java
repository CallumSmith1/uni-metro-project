package com.mtr.rail_maps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

//import com.mtr.interfaces.ContainsChecker;
//import com.mtr.stations.PaidArea;
import com.mtr.stations.StationType;
import com.mtr.stations.TrainStation;
//import com.mtr.stations.UnpaidArea;
import com.mtr.utilities.FileReaderClass;
import com.mtr.utilities.Tokenizer;

//This class does not show very good coupling, but I like to have reusable utility classes 
//So that they can be re-used elsewhere on other projects
public class MapCreator {

	private String mapFileLocation;

	private RailMapGraph graph = new RailMapGraph();

	/**
	 * The constructor of this object takes the location of the file that is to be
	 * turned into a map
	 * 
	 * @param mapFileLocation
	 */
	public MapCreator(String mapFileLocation) {
		this.mapFileLocation = mapFileLocation;
	}

	/**
	 * This method reads the passed file and splits it based on the file. The
	 * elements are then added to a Doubly linked list (forward and reverse
	 * traversal over the list. This allows for: station t -> station q, and station
	 * t -> station v
	 * 
	 * This will return a graph of type RailMapGraph
	 */
	public RailMapGraph createMap() {
		try {
			graph.createNewMap();
			readLinesFromMap();
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		return graph;
	}

	/*
	 * This returns a copy of the graph created by the createMap() method
	 */
	public RailMapGraph getRailMap() {
		return graph;
	}

	/*
	 * Reads the file passed to the constructor and splits it based on the pattern
	 * passed. This can then be passed to a loop in another method
	 */
	private void readLinesFromMap() throws FileNotFoundException, IOException {
		FileReaderClass frc = new FileReaderClass(new File(mapFileLocation));
		List<String[]> fileLines = frc.readFileLinesOfCsv();
		for (String[] railLine : fileLines) {
			splitStations(railLine);
		}
	}

	/*
	 * This is probably not ideal, and the tokenizer class - which I designed early
	 * on - has little use, but it at least allows a check for null values. It does
	 * increase coupling though
	 * 
	 */
	private void splitStations(String[] railLine) {
		String lineName = String.join(",", railLine);
		String stations = String.join(",", railLine);

		// This means that I can keep the String format and don't need to iterate later
		lineName = lineName.split(",")[0];

		// Use the tokenizer class to create a set of station tokens
		Tokenizer t = new Tokenizer(stations);
		stations = t.replace("^(.+?),", "");
		String[] stationTokens = t.splitString(stations, ",");

		createStations(lineName, stationTokens);
	}

	/*
	 * This takes the information from the split stations method and creates
	 * objects. The objects are then added as vertices and connected with edges in
	 * the graph.
	 */
	private void createStations(String lineName, String[] stationTokens) {
		// This lets me link the last station to the current one
		StationType lastStation = null;
		StationType currentStation = null;
		int numStations = stationTokens.length;

		// This now becomes O(n2) since it is looping within a loop on a different
		// variable (I believe)
		for (int i = 0; i < numStations; i++) {
			String token = stationTokens[i].trim().toLowerCase();
			// Check to see if there is already a key for this station
			if (checkForDuplicateStation(token)) {
				currentStation = graph.returnStationFromName(token).getStation();

				if (lastStation != null) {
					graph.addExistingStationConnection(graph.returnStationFromName(currentStation.getStationName()),
							lastStation);

				}
				if (i == 0 || i == numStations - 1) {
					currentStation.setAsTerminus(lineName);
				}
				lastStation = currentStation;
				lastStation.addNewLine(lineName);
			} else {
				currentStation = new TrainStation(token, lineName);

				if (i == 0 || i == numStations - 1) {
					currentStation.setAsTerminus(lineName);
				}

				graph.addStation(currentStation);
				if (lastStation != null) {
					graph.addStationConnection(lastStation, currentStation);
				}
				lastStation = currentStation;
			}
		}
	}

	/*
	 * If I can return a key, that means that the station must already have been
	 * added for another line
	 */
	private boolean checkForDuplicateStation(String token) {
		try {
			return graph.checkIfDuplicateStation(graph.returnStationFromName(token).getStation());
		} catch (NullPointerException npe) {
			return false;
		}
	}

	// Creates a station object of either paid area, unpaid area, or generic station
	// I think that this may become defunct if the objects are created last and
	// exist
	// --------------------------------------------------------------------------
	// This is a relic of the original code, where I would have a way of creating
	// different station objects based on an abstract class and an interface with a
	// pattern matcher method
	/*
	 * private StationType buildStationObject(String lineName, String token) {
	 * StationType stationType; if (doesContain(lineName.toLowerCase(),
	 * "Paid Connection")) { stationType = new PaidArea(token, lineName); } else if
	 * (doesContain(lineName.toLowerCase(), "Unpaid Connection")) { stationType =
	 * new UnpaidArea(token, lineName); } else { stationType = new
	 * TrainStation(token, lineName); } return stationType; }
	 * 
	 * @Override public boolean doesContain(String stringToCheck, String
	 * valueToCheckFor) { String s = "\\b" + valueToCheckFor + "\\b"; Pattern p =
	 * Pattern.compile(s); Matcher m = p.matcher(stringToCheck); return m.find(); }
	 */

}
