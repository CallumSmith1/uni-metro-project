package com.mtr.rail_maps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		StationType lastStation = null;
		int numStations = stationTokens.length;

		// This now becomes O(n2) since it is looping within a loop on a different variable (I believe)
		for (int i = 0; i < numStations; i++) {
			StationType stationType;
			String token = stationTokens[i].trim();
			stationType = buildStationObject(lineName, token);
			
			if(i == 0 || i == numStations - 1) { 
				stationType.setAsTerminus();
			}
			if(lastStation != null) { 
				graph.addStationConnection(lastStation, stationType);
			}
			
			graph.addStation(stationType);
			lastStation = stationType;
		}
	}

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

	@Override
	public boolean doesContain(String stringToCheck, String valueToCheckFor) {
		String s = "\\b" + valueToCheckFor + "\\b";
		Pattern p = Pattern.compile(s);
		Matcher m = p.matcher(stringToCheck);
		return m.find();
	}

}
