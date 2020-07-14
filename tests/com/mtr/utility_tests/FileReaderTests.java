package com.mtr.utility_tests;

import static org.junit.Assert.assertTrue;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mtr.utilities.FileReaderClass;

/* Used to test the implementation of the File Reader class.
 * I have tried to use descriptive names to avoid overly 
 * commented code
 */
class FileReaderTests {

	@Test
	void passNonNullFileAndExecuteReadFileMethod() {
		FileReaderClass frc = new FileReaderClass(new File("files/MTR_CitySaverNetwork.csv"));
		try {
			frc.readFile();
		} catch (Exception e) {
			assertTrue("Could not construct with given file\n" + e, false);
		}
	}

	@Test
	void passANullFileToCheckErrorMessage() throws IOException {
		FileReaderClass frc = new FileReaderClass(null);
		try {
			frc.readFile();
		} catch (FileNotFoundException fnfe) {
			if (fnfe.getMessage().equals("Null File Passed")) {
				assertTrue(true);
			} else {
				assertTrue("Error message was not a match\n" + fnfe, false);
			}
		}
	}

	@Test
	void checkThatReturnedStringIsNotNull() throws FileNotFoundException, IOException {
		FileReaderClass frc = new FileReaderClass(new File("files/MTR_CitySaverNetwork.csv"));
		String returnedString = frc.readFile();
		if(returnedString != null) { 
			assertTrue(true);
		}
		else { 
			assertTrue("Returned string was null: " + returnedString, false);
		}
	}
	
	@Test
	void checkReadAllLinesMethodReadsFileCorrectly() { 
		FileReaderClass frc = new FileReaderClass(new File("files/MTR_CitySaverNetwork.csv"));
		try {
			frc.readFileLinesOfCsv();
			assertTrue(true);
		} catch (IOException ioe) {
			assertTrue("Error when reading the file" + ioe, false);
		}
	}

}
