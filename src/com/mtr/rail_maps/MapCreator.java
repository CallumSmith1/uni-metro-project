package com.mtr.rail_maps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mtr.interfaces.ContainsChecker;
import com.mtr.stations.PaidArea;
import com.mtr.stations.StationType;
import com.mtr.stations.TrainStation;
import com.mtr.stations.UnpaidArea;
import com.mtr.utilities.FileReaderClass;
import com.mtr.utilities.Tokenizer;

//This class does not show very good coupling, but I like to have reusable utility classes 
//So that they can be re-used elsewhere on other projects
public class MapCreator implements ContainsChecker {

	private String mapFileLocation;
	private int numberOfLines = 0;
	private boolean added = false;

	private boolean lastStationWasDuplicate = false;

	private RailMapGraph graph = new RailMapGraph();;

	public MapCreator(String mapFileLocation) {
		this.mapFileLocation = mapFileLocation;
	}

	/*
	 * This method reads the passed file and splits it based on the file. The
	 * elements are then added to a Doubly linked list (forward and reverse
	 * traversal over the list. This allows for: station t -> station q, and station
	 * t -> station v
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
			numberOfLines++;
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

	private void createStations(String lineName, String[] stationTokens) {
		//This lets me link the last station to the current one 
		StationType lastStation = null;
		int numStations = stationTokens.length;

		// This now becomes O(n2) since it is looping within a loop on a different variable (I believe)
		for (int i = 0; i < numStations; i++) {
			StationType stationType;
			String token = stationTokens[i].trim().toLowerCase();
			if(checkForDuplicateStation(token))
			{
				lastStation = duplicateStation(lineName, lastStation, numStations, i, token);
				lastStation.addNewLine(lineName);
			}
			else {
				lastStation = newStation(lineName, lastStation, numStations, i, token);
			}
		}
	}

	private boolean checkForDuplicateStation(String token) {
		try { 
		return graph.checkIfDuplicateStation(graph.returnStationFromName(token).getStation()); 
		} 
		catch(NullPointerException npe) {}
		return false;
	}

	private StationType newStation(String lineName, StationType lastStation, int numStations, int i, String token) {
		StationType stationType;
		stationType = buildStationObject(lineName, token);
		
		//handleDuplicateStations(stationType, lastStation);
		if(i == 0 || i == numStations - 1) { 
			stationType.setAsTerminus(lineName);
		}
		if(lastStation != null) { 
			graph.addStationConnection(lastStation, stationType);
		}
		
		//graph.getAdjacentNodes(stationType);
		//StationLookup.stationTraversal(graph, stationType);
		//graph.addStation(stationType);
		graph.addStation(stationType);
		graph.getAdjacentNodes(graph.getMap(), stationType);
		lastStation = stationType;
		return lastStation;
	}

	private StationType duplicateStation(String lineName, StationType lastStation, int numStations, int i,
			String token) {
		StationType station = graph.returnStationFromName(token).getStation();
		graph.addStationConnection(lastStation, station);
		if(i == 0 || i == numStations - 1) { 
			station.setAsTerminus(lineName);
		}
		lastStation = station;
		return lastStation;
	}

	// Creates a station object of either paid area, unpaid area, or generic station
	private StationType buildStationObject(String lineName, String token) {
		StationType stationType;
		if (doesContain(lineName.toLowerCase(), "paid connection")) {
			stationType = new PaidArea(token, lineName);
		} else if (doesContain(lineName.toLowerCase(), "unpaid connection")) {
			stationType = new UnpaidArea(token, lineName);
		} else {
			stationType = new PaidArea(token, lineName);
		}
		return stationType;
	}

	private void handleDuplicateStations(StationType currentStation, StationType previousStation) {
		// if the current graph is a duplicate but the last was not, link the last to
		// the initial instance
		if (graph.checkIfDuplicateStation(currentStation)) {
			graph.addStationConnection(previousStation,
					graph.returnStationFromName(currentStation.getStationName()).getStation());
			added = true;
		} else {
			graph.addStation(currentStation);
		}

	}

	@Override
	public boolean doesContain(String stringToCheck, String valueToCheckFor) {
		String s = "\\b" + valueToCheckFor + "\\b";
		Pattern p = Pattern.compile(s);
		Matcher m = p.matcher(stringToCheck);
		return m.find();
	}

}
