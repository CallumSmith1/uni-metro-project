/**
 * 
 */
package com.mtr.interfaces;

/**
 * A controller for the MTR City Saver Route Finder application.
 * This controller includes the 2 features that the intended
 * prototype route finder application is expected to have.
 * (See coursework specification for details.)
 * 
 * @author Sylvia Wong
 * @version 28/05/2020
 */
public interface Controller {
	
	/**
	 * Return a String representation of the required information about the specified station,
	 * i.e. the name of all lines to which the station belongs and whether the station is a 
	 * terminus in each of the listed lines.
	 * 
	 * @param station	the name of a station
	 * @return a String representation of the line information about the specified station
	 */
	String lookupStation(String station);
	
	/**
	 * Lists a path between the two specified stations.
	 * The path is represented as a sequence of the name of the stations, and 
	 * the lines to which these stations belongs between the specified stations. 
	 * 
	 * @param stationA	the name of a station
	 * @param stationB	the name of another station
	 * @return	a String representation of a path between the specified stations
	 */
	String showPathBetween(String stationA, String stationB);
}
