package com.mtr.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* I want this class to be as reusable as possible. This is a very generic 
 * operation that can be used in other contexts and applications. Therefore, I 
 * have placed it in a non-specific package
 */
public class FileReaderClass {

	private File file;

	// Construct with given file - don't hard-code the file-path here
	public FileReaderClass(File file) {
		this.file = file;
	}

	/*
	 * New implementation to return an array of all of the lines of data. This
	 * version is splitting on the comma of a csv
	 * 
	 * I will return an arraylist of the .split array elements, which can then be
	 * Reconstructed by more specialised methods
	 */
	public List<String[]> readFileLinesOfCsv() throws IOException {
		checkIfFileIsNull();
		BufferedReader br = new BufferedReader(new FileReader(file));
		List<String[]> fileLines = new ArrayList<String[]>();
		String line;
		while ((line = br.readLine()) != null) {
			fileLines.add(line.split(","));
		}
		return fileLines;
	}

	/**
	 * This method will return a block of text. This can be useful elsewhere, but
	 * I'll probably remove it here if it makes sense to do so
	 */
	@Deprecated
	public String readFile() throws IOException, FileNotFoundException {
		checkIfFileIsNull();

		BufferedReader br = new BufferedReader(new FileReader(file));
		try {
			if (br.ready()) {
				// Return the lines of the file, with separator, using a lambda
				return br.lines().collect(Collectors.joining(System.lineSeparator()));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			// Making sure to close the reader
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return null;
	}

	// This just checks to see if the constructor file is not null because there are
	// multiple methods here
	private void checkIfFileIsNull() throws FileNotFoundException {
		if (file == null) {
			throw new FileNotFoundException("Null File Passed");
		}
	}

}
