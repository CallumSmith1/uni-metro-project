package com.mtr.interfaces;

public interface ContainsChecker {

	/*
	 * Doing a .contains will not always get me the exact contains i.e. paid
	 * connection and unpaid connection both contain paid connection. Therefore, I
	 * want to create this so that I can utilise different contains checks across
	 * classes with some abstraction and flexibility
	 */
	public boolean doesContain(String stringToCheck, String valueToCheckFor);

}
