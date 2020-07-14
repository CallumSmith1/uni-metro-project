package com.mtr.stations;

import com.mtr.user_interaction.UserInfo;

public class TrainStation extends StationType {
	

	public TrainStation(String stationName, String lineName) {
		super(stationName, lineName);
	}

	@Override
	protected void makeJourney() {
		UserInfo.deductJourney();
		System.out.printf("You have %d journeys remaining", UserInfo.getSeasonTicketInfo());
	}
}
