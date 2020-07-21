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
	
	public String toString() { 
		return station.getStationName();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(station);
	}

	@Override
	public boolean equals(Object obj) {
        return station.equals(((StationNodes) obj).station);
    }
	
	

}
