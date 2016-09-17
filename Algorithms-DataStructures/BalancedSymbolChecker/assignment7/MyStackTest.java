package assignment7;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import assignment7.MyLinkedList;

/**
 * 
 * @authors Joshua Callahan & Tanner Barlow
 *
 */
public class MyStackTest {

	private MyStack<Integer> intStack;
	private MyStack<String> stringStack;
	private MyStack<Integer> emptyStack;

	@Before
	public void setUp() throws Exception {

		// Integers 0 - 9 are pushed onto the intStack.
		intStack = new MyStack<Integer>();
		for (int i = 0; i < 10; i++) {
			intStack.push(i);
		}

		// The strings "What's", "up", and "doc?" are pushed onto the
		// stringStack.
		stringStack = new MyStack<String>();
		stringStack.push("What's");
		stringStack.push("up");
		stringStack.push("doc?");

		emptyStack = new MyStack<Integer>();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests to ensure exceptions are thrown on empty stacks when the peek or
	 * pop methods are called
	 */
	@Test
	public void testExceptions() {
		try {
			emptyStack.peek();
			fail("An exception was not thrown on an empty stack");
		} catch (NoSuchElementException e) {
		}

		try {
			emptyStack.pop();
			fail("An exception was not thrown on an empty stack");
		} catch (NoSuchElementException e) {
		}
	}

	/**
	 * Tests the MyStack clear method
	 */
	@Test
	public void testClear() {
		intStack.clear();
		assertTrue(intStack.isEmpty());

		stringStack.clear();
		assertTrue(stringStack.isEmpty());
	}

	/**
	 * Tests the MyStack isEmpty method
	 */
	@Test
	public void testIsEmpty() {
		
		assertTrue(emptyStack.isEmpty());
		
		assertFalse(intStack.isEmpty());
		intStack.clear();
		assertTrue(intStack.isEmpty());

		assertFalse(stringStack.isEmpty());
		stringStack.clear();
		assertTrue(stringStack.isEmpty());
	}

	/**
	 * Tests the MyStack peek method
	 */
	@Test
	public void testPeek() {
		assertEquals(9, (int) intStack.peek());
		assertEquals("doc?", stringStack.peek());
	}

	/**
	 * Tests the MyStack pop method
	 */
	@Test
	public void testPop() {
		intStack.pop();
		assertEquals(8, (int) intStack.peek());
		intStack.pop();
		assertEquals(7, (int) intStack.peek());
		
		stringStack.pop();
		assertEquals("up", stringStack.peek());
	}

}
