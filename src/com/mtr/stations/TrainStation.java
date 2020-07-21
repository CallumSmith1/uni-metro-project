package com.mtr.stations;

public class TrainStation extends StationType {
	

	public TrainStation(String stationName, String lineName) {
		super(stationName, lineName);
	}

	/*
	 * @Override protected void makeJourney() { if(doesContain(lineName,
	 * "Paid Connection")) { //Do nothing } else { UserInfo.deductJourney(); } }
	 */
}
