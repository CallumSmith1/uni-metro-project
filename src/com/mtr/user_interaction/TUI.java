/**
 * 
 */
package com.mtr.user_interaction;

import java.util.Scanner;
import com.mtr.interfaces.Controller;

/**
 * A simple text-based user interface for the intended MTR City Saver Route Finder application.
 * 
 * The constructor contains a 'forever' loop which displays three menu options 
 * and waits for the user's menu option choice. Depending on the menu option chosen, 
 * the user may be prompted to enter more information. For example, menu option 1 
 * will prompt the user to enter the name of the required station. 
 * 
 * Each menu option display the result of the operation on the console 
 * (i.e.~standard output stream) in plain text format. 
 * 
 * (See coursework specification for more detail.)
 * 
 * @author S H S Wong
 * @version 28/05/2020
 */
public class TUI {

	private Controller controller;  
	private Scanner stdIn;
	
	public TUI(Controller controller) {
		
		this.controller = controller;
		
		// Creates a Scanner object for obtaining user input
		stdIn = new Scanner(System.in);
		
		while (true) {
			displayMenu();
			getAndProcessUserOption();
		}
	}

	/**
	 * Displays the header of this application and a summary of menu options.
	 */
	private void displayMenu() {
		display(header());
		display(menu());
	}
	
	/**
	 * Obtains an user option and processes it.
	 */
	private void getAndProcessUserOption() {
		String command = stdIn.nextLine().trim();
		switch (command) {
		case "1" : // Looks up a station in the CitySaver network
			display("Looks up a station in the CitySaver network...");
			display("Enter the name of the required station: ");
			String station = stdIn.nextLine().trim();
			display(controller.lookupStation(station));
			break;
		case "2" : // Finds a path between two stations
			display("Finds a path between two stations...");
			display("Enter the name of the start station: ");
			String stationA = stdIn.nextLine().trim();
			display("Enter the name of the end station: ");
			String stationB = stdIn.nextLine().trim();
			display(controller.showPathBetween(stationA, stationB));
			break;
		case "3" : // Exits the application
			display("Goodbye!");
			System.exit(0);
			break;
		default : // Not a known command option
			display(unrecogniseCommandErrorMsg(command));
		}
	}
	
	/*
	 * Returns a string representation of a brief title for this application as the header.
	 * @return	a header
	 */
	private static String header() {
		return "\nMTR City Saver Route Finder\n";
	}
	
	/*
	 * Returns a string representation of the user menu.
	 * @return	the user menu
	 */
	private static String menu() {
		return "Enter the number associated with your chosen menu option.\n" +
			   "1: Looks up a station in the CitySaver network\n" +
			   "2: Find a path between two stations\n" +
			   "3: Exit this application\n";
	}
	
	/*
	 * Displays the specified info for the user to view.
	 * @param info	info to be displayed on the screen
	 */
	private void display(String info) {
		System.out.println(info);
	}
	
    /*
     * Returns an error message for an unrecognised command.
     * 
     * @param error the unrecognised command
     * @return      an error message
     */
    private static String unrecogniseCommandErrorMsg(String error) {
            return String.format("Cannot recognise the given command: %s.%n", error);
    }

}
