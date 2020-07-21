package com.mtr.user_interaction;

import com.mtr.rail_maps.MapCreator;
import com.mtr.rail_maps.StationLookup;

public class WelcomePage {

	public static void main(String[] args) {
		MapCreator createMap = new MapCreator("files/MTR_CitySaverNetwork.csv");
		createMap.createMap();
		StationLookup controller = new StationLookup(createMap.getRailMap());
		
		TUI ui = new TUI(controller);
	}

}
