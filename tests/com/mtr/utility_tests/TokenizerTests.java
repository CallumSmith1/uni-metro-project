package com.mtr.utility_tests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.mtr.utilities.Tokenizer;

/* Used to test the implementation of the Tokenizer class.
 * I have tried to use descriptive names to avoid overly 
 * commented code
 */
public class TokenizerTests {
	
	@Test
	void callSplitMethodWithJustPattern() { 
		Tokenizer tokenizer = new Tokenizer("Te tst st Ctst omple tst te");
		String[] test = tokenizer.splitString("tst[a-z]*");
		String i = "";
		for(String word : test) { 
			i += word;
		}
		
		if(i.replaceAll(" ", "").equals("TestComplete")) { 
			assertTrue(true);
		}
		else { 
			assertTrue("Split incorrectly: " + i, false);
		}
	}
	
	@Test
	void callSplitMethodWithStringAndPattern() { 
		Tokenizer tokenizer = new Tokenizer("Not This");
		String[] test = tokenizer.splitString("Te tst st Ctst omple tst te", "tst[a-z]*");
		String i = "";
		for(String word : test) { 
			i += word;
		}
		
		if(i.replaceAll(" ", "").equals("TestComplete")) { 
			assertTrue(true);
		}
		else { 
			assertTrue("Split incorrectly: " + i, false);
		}
	}
	
	@Test
	void callReplaceMethodAndReturnResult() { 
		Tokenizer tokenizer = new Tokenizer("Callum Smith");
		if(tokenizer.replace(" ", "").equals("CallumSmith")) { 
			assertTrue(true);
		}
		else { 
			assertTrue(false);
		}
	}
	
	@Test
	void callSplitMethodWithANullStringAndPattern() { 
		Tokenizer tokenizer = new Tokenizer("Not This");
		try {
		tokenizer.splitString(null, null);
		} catch (IllegalArgumentException iae) { 
			if(iae.getMessage().equals("String passed is null")) { 
				assertTrue(true);
			} 
			else { 
				assertTrue("Expected message was not thrown", false);
			}
		}
	}
	
	@Test
	void passNullStringAndThrowException() { 
		Tokenizer tokenizer = new Tokenizer(null);
		try { 
		tokenizer.splitString("umS[a-z]*");
		} catch (IllegalArgumentException iae) { 
			if(iae.getMessage().equals("String passed to the constructor is null")) { 
				assertTrue(true);
			} 
			else { 
				assertTrue("Expected message was not thrown", false);
			}
		}
	}
	
	@Test
	void passNullPatternAndThrowException() { 
		Tokenizer tokenizer = new Tokenizer("CallumSmith");
		try { 
		tokenizer.splitString(null);
		} catch (IllegalArgumentException iae) { 
			if(iae.getMessage().equals("Pattern passed is null")) { 
				assertTrue(true);
			} 
			else { 
				assertTrue("Expected message was not thrown", false);
			}
		}
	}
	
	@Test
	void callReplaceMethodWithNullReplacement() { 
		Tokenizer tokenizer = new Tokenizer("Callum Smith");
		try { 
		tokenizer.replace(" ", null);
		} catch (IllegalArgumentException iae) { 
			if(iae.getMessage().equals("Replacement passed is null")) { 
				assertTrue(true);
			} 
			else { 
				assertTrue("Expected message was not thrown", false);
			}
		}
	}

}
