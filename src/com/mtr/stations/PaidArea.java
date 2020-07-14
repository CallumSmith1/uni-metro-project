package com.mtr.stations;

import com.mtr.user_interaction.UserInfo;

public class PaidArea extends StationType {


	public PaidArea(String stationName, String lineName) {
		super(stationName, lineName);
	}

	@Override
	protected void makeJourney() {
		System.out.printf("You have %d journeys remaining", UserInfo.getSeasonTicketInfo());
	}
}
