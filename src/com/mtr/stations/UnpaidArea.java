package com.mtr.stations;

import com.mtr.user_interaction.UserInfo;

public class UnpaidArea extends StationType {

	public UnpaidArea(String stationName, String lineName) {
		super(stationName, lineName);
	}

	@Override
	protected void makeJourney() {
		UserInfo.deductJourney();
		System.out.printf("You have %d journeys remaining", UserInfo.getSeasonTicketInfo());
	}
}
