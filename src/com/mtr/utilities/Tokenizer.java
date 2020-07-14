package com.mtr.utilities;

/* I want this class to be as reusable as possible. This is a very generic 
 * operation that can be used in other contexts and applications. Therefore, I 
 * have placed it in a non-specific package
 * 
 * It can be used to split a given string, using a given pattern. 
 * It will return an array. 
 */
public class Tokenizer {

	private String stringToSplit;

	// I am only passing the string to the constructor in case a user wants to pass
	// many patterns
	public Tokenizer(String stringToSplit) {
		this.stringToSplit = stringToSplit;
	}

	/*
	 * Takes a string regex to split the constructor string into an array returns a
	 * string array
	 */
	public String[] splitString(String pattern) {
		try {
			checkIfNull(pattern);
		} catch (IllegalArgumentException iae) {
			System.out.println(iae.getMessage());
			return null;
		}
		return stringToSplit.split(pattern);
	}

	/*
	 * Takes a String to be split and a string regex to split the string into an
	 * array returns a string array
	 */
	public String[] splitString(String stringToSplit, String pattern) {
		try {
			if (stringToSplit == null) {
				throw new IllegalArgumentException("String passed is null");
			}
			checkIfNull(pattern);
		} catch (IllegalArgumentException iae) {
			System.out.println(iae.getMessage());
			return null;
		}
		return stringToSplit.split(pattern);
	}

	/*
	 * Replaces elements of the constructor string based on a given pattern
	 * I may not use this, but it may be useful
	 */
	public String replace(String pattern, String replacement) {
		try {
			if (replacement == null) {
				throw new IllegalArgumentException("Replacement passed is null");
			}
			checkIfNull(pattern);
		} catch (IllegalArgumentException iae) {
			System.out.println(iae.getMessage());
			return null;
		}
		return stringToSplit.replaceAll(pattern, replacement);
	}

	// Use this for override methods etc
	private void checkIfNull(String pattern) {
		if (stringToSplit == null) {
			throw new IllegalArgumentException("String passed to the constructor is null");
		} else if (pattern == null) {
			throw new IllegalArgumentException("Pattern passed is null");
		}
	}
}
