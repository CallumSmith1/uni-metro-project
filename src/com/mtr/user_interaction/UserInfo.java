package com.mtr.user_interaction;

public class UserInfo {
	
	private static int seasonTicket = 40;
	
	public static void resetSeasonTicket() { 
		seasonTicket = 40;
	}
	
	public static int getSeasonTicketInfo() { 
		return seasonTicket;
	}
	
	public static void deductJourney() { 
		if(seasonTicket > 0 && seasonTicket <= 40) { 
			seasonTicket -= 1;
		}
		else { 
			throw new IllegalArgumentException("Not enough journeys on the season ticket: " + getSeasonTicketInfo()); 
		}
	}

}
