package assignment7;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalancedSymbolCheckerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests against the expected results of Class1.java
	 */
	@Test
	public void testClass1() {
		try {
			assertEquals(
					"ERROR: Unmatched symbol at line 6 and column 1. Expected ), but read } instead.",
					BalancedSymbolChecker.checkFile("Class1.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests against the expected results of Class2.java
	 */
	@Test
	public void testClass2() {
		try {
			assertEquals(
					"ERROR: Unmatched symbol at line 7 and column 1. Expected  , but read } instead.",
					BalancedSymbolChecker.checkFile("Class2.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests against the expected results of Class2.java
	 */
	@Test
	public void testClass3() {
		try {
			assertEquals(
					"No errors found. All symbols match.",
					BalancedSymbolChecker.checkFile("Class3.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests against the expected results of Class2.java
	 */
	@Test
	public void testClass4() {
		try {
			assertEquals(
					"ERROR: File ended before closing comment.",
					BalancedSymbolChecker.checkFile("Class4.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests against the expected results of Class2.java
	 */
	@Test
	public void testClass5() {
		try {
			assertEquals(
					"ERROR: Unmatched symbol at line 3 and column 18. Expected ], but read } instead.",
					BalancedSymbolChecker.checkFile("Class5.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests against the expected results of Class2.java
	 */
	@Test
	public void testClass6() {
		try {			
			assertEquals(
					"No errors found. All symbols match.",
					BalancedSymbolChecker.checkFile("Class6.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests against the expected results of Class2.java
	 */
	@Test
	public void testClass7() {
		try {
			assertEquals(
					"ERROR: Unmatched symbol at line 3 and column 33. Expected ], but read ) instead.",
					BalancedSymbolChecker.checkFile("Class7.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests against the expected results of Class2.java
	 */
	@Test
	public void testClass8() {
		try {
			assertEquals(
					"ERROR: Unmatched symbol at line 5 and column 30. Expected }, but read ) instead.",
					BalancedSymbolChecker.checkFile("Class8.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests against the expected results of Class2.java
	 */
	@Test
	public void testClass9() {
		try {
			assertEquals(
					"ERROR: Unmatched symbol at line 3 and column 33. Expected ), but read ] instead.",
					BalancedSymbolChecker.checkFile("Class9.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests against the expected results of Class2.java
	 */
	@Test
	public void testClass10() {
		try {
			assertEquals(
					"ERROR: Unmatched symbol at line 5 and column 10. Expected }, but read ] instead.",
					BalancedSymbolChecker.checkFile("Class10.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests against the expected results of Class2.java
	 */
	@Test
	public void testClass11() {
		try {
			assertEquals(
					"ERROR: Unmatched symbol at the end of file. Expected }.",
					BalancedSymbolChecker.checkFile("Class11.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests against the expected results of Class2.java
	 */
	@Test
	public void testClass12() {
		try {
			assertEquals(
					"No errors found. All symbols match.",
					BalancedSymbolChecker.checkFile("Class12.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests against the expected results of Class2.java
	 */
	@Test
	public void testClass13() {
		try {
			assertEquals(
					"No errors found. All symbols match.",
					BalancedSymbolChecker.checkFile("Class13.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests against the expected results of Class2.java
	 */
	@Test
	public void testClass14() {
		try {
			assertEquals(
					"No errors found. All symbols match.",
					BalancedSymbolChecker.checkFile("Class14.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testClass15() {
		try {
			assertEquals(
					"No errors found. All symbols match.",
					BalancedSymbolChecker.checkFile("Class15.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testClass16() {
		try {
			assertEquals(
					"No errors found. All symbols match.",
					BalancedSymbolChecker.checkFile("Class16.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testClass17() {
		try {
			assertEquals(
					"ERROR: Unmatched symbol at line 9 and column 1. Expected  , but read } instead.",
					BalancedSymbolChecker.checkFile("Class17.java"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
