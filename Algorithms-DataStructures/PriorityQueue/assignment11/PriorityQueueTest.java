package assignment11;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kevin Griggs
 * @author Tanner Barlow
 *
 */
public class PriorityQueueTest {

	PriorityQueue<Integer> pq;
	PriorityQueue<String> st;
	
	@Before
	public void setUp() throws Exception {
		pq = new PriorityQueue<Integer>();
		st = new PriorityQueue<String>();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindMinException() {
		// Make sure exception is thrown on empty PriorityQueue
		try{
			pq.findMin();
			fail("Exception not thrown");
		}catch (NoSuchElementException e){}
		//Add element to make pq not empty
		pq.add(4);
		// Maks sure no exception is thrown on non-empty pq
		try{
			assertEquals(4, (int) pq.findMin());
		}catch (NoSuchElementException e){
			fail("Exception thrown");
		}
		
		// Same test as above with strings
		try{
			st.findMin();
			fail("Exception not thrown");
		}catch (NoSuchElementException e){}
		
		st.add("Clara");
		
		try{
			assertTrue(st.findMin().equals("Clara"));
		}catch (NoSuchElementException e){
			fail("Exception thrown");
		}
	}
	
	@Test
	public void testFindMin() {
		//Add five elements in random order
		pq.add(5);
		pq.add(4);
		pq.add(7);
		pq.add(2);
		pq.add(1000);
		
		//For debugging purposes
		pq.generateDotFile("min.dot");
		
		assertEquals(2, (int) pq.findMin());
		
		//Add elements
		st.add("Rusty");
		st.add("and");
		st.add("Clara");
		st.add("sitting");
		st.add("in");
		st.add("a");
		st.add("tree");
		
		st.generateDotFile("Rusty.dot");
		//Make sure Clara is at the top of Rusty's priority queue
		//*wink wink ;)
		assertTrue(st.findMin().equals("Clara"));
	}

	@Test
	public void testDeleteMinException() {
		//Make sure exception is thrown on empty pq
		try{
			pq.deleteMin();
			fail("Exception not thrown");
		}catch (NoSuchElementException e){}
		//Add element to make pq not empty
		pq.add(4);
		//Make sure exception is not thrown on non-empty pq
		try{
			assertEquals(4, (int) pq.deleteMin());
		}catch (NoSuchElementException e){
			fail("Exception thrown");
		}
	}
	
	@Test
	public void testDeleteMin() {
		//Add several elements
		pq.add(5);
		pq.add(4);
		pq.add(7);
		pq.add(2);
		pq.add(1000);
		//Check initial size
		assertEquals(5, pq.size());
		//Make sure we are removing the right min
		assertEquals(2, (int) pq.deleteMin());
		//Make sure min was updated correctly
		assertEquals(4, (int) pq.findMin());
		//Verify new size
		assertEquals(4, pq.size());
	}

	@Test
	public void testAdd() {
		assertEquals(0, pq.size());
		pq.add(5);
		assertEquals(1, pq.size());
		//Check to see if min is correct
		assertEquals(5, (int) pq.findMin());
		pq.add(4);
		pq.add(5);
		pq.add(6);
		pq.add(10000);
		pq.add(9);
		//Make sure min was updated correctly
		assertEquals(4, (int) pq.findMin());
		pq.add(2);
		//Make sure min was updated correctly
		assertEquals(2, (int) pq.findMin());
		//Add more elements
		pq.add(40);
		pq.add(50);
		pq.add(60);
		pq.add(100000);
		pq.add(-1);
		//Make sure min was updated correctly
		assertEquals(-1, (int) pq.findMin());
	}
}