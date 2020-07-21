package com.mtr.rail_maps;

import java.util.Objects;

import com.mtr.stations.StationType;

public class StationNodes {
	
	private StationType station; 
	
	public StationNodes(StationType station) { 
		this.station = station;
	}
	
	public StationType getStation() { 
		return station;
	}
	
	@Override
	public String toString() { 
		return station.getStationName();
	}

	@Override
	public int hashCode() {
		//I have to base it off the station name, since this is an immutable field.
		//Figuring out why it wouln't add stations to the hashmap, when it had already added them, was a harrowing experience
		return Objects.hashCode(station.getStationName());
	}

	/*
	 * @Override public boolean equals(Object obj) { return
	 * station.equals(((StationNodes) obj).station); }
	 */
	
	@Override
	public boolean equals(Object n) { 
		if(n == null) { 
			return false;
		}
		StationNodes node = (StationNodes) n;
		return station.getStationName().equals(node.getStation().getStationName());
		
	}

}
