package com.mtr.stations;

import java.util.ArrayList;
import java.util.List;

//This can now be non-abstract/concrete
public class StationType {

	protected String lineName;
	// All lines, maybe temporary, lists all of the lines a station is part of
	protected List<String> allLines = new ArrayList<String>();
	protected String stationName;

	// Boolean value to check whether the station is a terminal of the line
	protected List<String> isTerminusOnLine = new ArrayList<String>();

	public StationType(String stationName, String lineName) {
		this.stationName = stationName;
		this.lineName = lineName;
		// Make sure the line isn't already attached to the station
		if (!allLines.contains(stationName)) {
			allLines.add(lineName);
		}
	}

	/*
	 * 
	 * I was going to implement these methods across the different objects, but I
	 * have had to remove them to an issue with the MapCreator build method
	 * protected abstract void makeJourney();
	 * 
	 * public void startNewJourney() { makeJourney(); }
	 */

	/**
	 * @return A formatted string representation of the Lines that this station
	 *         is a part of.
	 */
	public String returnStationLines() {
		String stationLines = "Is a station on the following lines: ";
		for (String line : allLines) {
			stationLines += System.lineSeparator() + "* " + line.trim();
		}
		return stationLines;
	}

	/**
	 * @return A formatted string representation of the terminuses that this station
	 *         is a part of.
	 */
	public String returnStationTerminuses() {
		String stationTerminuses = "Is a terminus on the following lines: ";
		for (String terminus : isTerminusOnLine) {
			stationTerminuses += System.lineSeparator() + "* " + terminus.trim();
		}
		if (stationTerminuses.equals("Is a terminus on the following lines: ")) {
			stationTerminuses = "This station is not a terminus on any line";
		}
		return stationTerminuses;
	}

	public void addNewLine(String line) {
		allLines.add(line);
	}

	public void setAsTerminus(String lineName) {
		isTerminusOnLine.add(lineName);
	}

	public String getStationName() {
		return stationName;
	}

	@Override
	public String toString() {
		String allLines = "";
		String terminusLines = "";
		for (String s : this.allLines) {
			allLines += s + " | ";
		}
		for (String s : this.isTerminusOnLine) {
			terminusLines += s + " | ";
		}

		return "Lines: " + allLines + System.lineSeparator() + "Station: " + stationName + System.lineSeparator()
				+ "Is Terminus on: " + terminusLines;
	}
}
