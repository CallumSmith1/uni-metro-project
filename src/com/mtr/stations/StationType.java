package com.mtr.stations;

import java.util.ArrayList;
import java.util.List;

public abstract class StationType {

	protected String lineName;
	// All lines, maybe temporary, lists all of the lines a station is part of
	protected List<String> allLines = new ArrayList<String>();
	protected String stationName;

	// Boolean value to check whether the station is a terminal of the line
	protected boolean isTerminus = false;;

	public StationType(String stationName, String lineName) {
		this.stationName = stationName;
		this.lineName = lineName;
		// Make sure the line isn't already attached to the station
		if (!allLines.contains(stationName)) {
			allLines.add(lineName);
		}
	}

	protected abstract void makeJourney();

	public void startNewJourney() {
		makeJourney();
	}

	public void showStationLines() {
		System.out.println(this.allLines.toString());
	}

	public void checkIfIsTerminus() {
		if (isTerminus) {
			System.out.printf("%s is a terminus of %n", stationName);
		}
	}
	
	public void setAsTerminus() {
		isTerminus = true;
	}

	public String getStationName() {
		return stationName;
	}
	
	@Override
	public String toString() {
		return "Current Line: " + lineName + System.lineSeparator() + 
				"Lines: " + allLines.toString() + System.lineSeparator() + 
				"Station: " + stationName + System.lineSeparator() + 
				"Is Terminus: " + isTerminus;
	}

	// I am overriding the equals and hashcode since these are immutable objects
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allLines == null) ? 0 : allLines.hashCode());
		result = prime * result + (isTerminus ? 1231 : 1237);
		result = prime * result + ((stationName == null) ? 0 : stationName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StationType other = (StationType) obj;
		if (allLines == null) {
			if (other.allLines != null)
				return false;
		} else if (!allLines.equals(other.allLines))
			return false;
		if (isTerminus != other.isTerminus)
			return false;
		if (stationName == null) {
			if (other.stationName != null)
				return false;
		} else if (!stationName.equals(other.stationName))
			return false;
		return true;
	}
}
