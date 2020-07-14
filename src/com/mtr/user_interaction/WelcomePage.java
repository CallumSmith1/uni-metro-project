package com.mtr.user_interaction;

import com.mtr.rail_maps.MapCreator;
import com.mtr.rail_maps.StationLookup;

public class WelcomePage {

	public static void main(String[] args) {
		UserInfo.resetSeasonTicket();
		
		MapCreator createMap = new MapCreator("files/MTR_CitySaverNetwork.csv");
		StationLookup controller = new StationLookup(createMap.createMap());
		
		TUI ui = new TUI(controller);
	}

}
