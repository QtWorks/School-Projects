package assignment10;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QuadProbeHashTableTest {

	QuadProbeHashTable quad;
	BadHashFunctor bad;
	ArrayList<String> testList;
	
	@Before
	public void setUp() throws Exception {
		bad = new BadHashFunctor();
		quad = new QuadProbeHashTable(3, bad);
		
		testList = new ArrayList<String>();
		testList.add("phone");
		testList.add("CS2420");
		testList.add("tree");
		testList.add("ring");
		testList.add("institute");
		testList.add("technology");
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAdd() {
		assertTrue(quad.add("Rusty"));
		assertTrue(quad.add("Tanner"));
		assertTrue(quad.add("Elijah"));
		assertTrue(quad.add("Josh"));
		assertTrue(quad.add("library"));
		assertTrue(quad.add("computer"));
		assertTrue(quad.add("face"));
		assertTrue(quad.add("book"));
		assertTrue(quad.add("dell"));
		assertTrue(quad.add("inspiron"));
		assertTrue(quad.add("newwords"));
		assertTrue(quad.add("teamAwesome"));
		System.out.println("First Collisions: " + quad.getCollisions());

		assertFalse(quad.add("dell"));
		assertFalse(quad.add("inspiron"));
		assertFalse(quad.add("newwords"));
		assertFalse(quad.add("teamAwesome"));
		
		System.out.println("Add method: ");
//		String[] quadArray = quad.getArray();
//		for(int i = 0; i < quadArray.length; i++){
//			System.out.println(i + " " + quadArray[i]);
//		}
	}

	@Test
	public void testGrow() {
		quad.add("Rusty"); 
		assertEquals(3, quad.getCapacity());
		quad.add("Tanner");
		assertEquals(3, quad.getCapacity());
		quad.add("Elijah");		
		assertEquals(5, quad.getCapacity());
		quad.add("Josh");
		assertEquals(7, quad.getCapacity());
		quad.add("library");
		assertEquals(11, quad.getCapacity());
		quad.add("computer");
		assertEquals(11, quad.getCapacity());
		quad.add("face");
		assertEquals(13, quad.getCapacity());
		quad.add("book");
		assertEquals(17, quad.getCapacity());
		quad.add("dell");
		assertEquals(17, quad.getCapacity());
		quad.add("inspiron");
		assertEquals(19, quad.getCapacity());
		quad.add("newwords");
		assertEquals(23, quad.getCapacity());
		quad.add("teamAwesome");
		assertEquals(23, quad.getCapacity());
		quad.add("class");
		assertEquals(29, quad.getCapacity());
		quad.addAll(testList);
		assertEquals(37, quad.getCapacity());
//		System.out.println("Collisions: " + quad.getCollisions());
	}
	
	@Test
	public void testAddAll() {
		assertTrue(quad.addAll(testList));
		assertFalse(quad.addAll(testList));
		assertEquals(6, quad.size());
	}

	@Test
	public void testClear() {
		quad.addAll(testList);
		assertEquals(6, quad.size());
		quad.clear();
		assertEquals(0, quad.size());
	}

	@Test
	public void testContains() {
		quad.add("Tanner");
		quad.addAll(testList);
		assertTrue(quad.contains("Tanner"));
		
		assertFalse(quad.contains("Basketball"));
		assertFalse(quad.contains("UCLA"));
		
	}

	@Test
	public void testContainsAll() {
		quad.addAll(testList);
		assertTrue(quad.containsAll(testList));
	}

	@Test
	public void testIsEmpty() {
		assertTrue(quad.isEmpty());
		quad.add("chair");
		assertFalse(quad.isEmpty());
	}

	@Test
	public void testSize() {
		assertEquals(0, quad.size());
		quad.add("desk");
		assertEquals(1, quad.size());
		quad.addAll(testList);
		assertEquals(7, quad.size());
	}

}
