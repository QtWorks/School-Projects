package assignment10;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChainingHashTableTest {
	
	ChainingHashTable chainBad, chainMediocre, chainGood;
	BadHashFunctor bad;
	MediocreHashFunctor med;
	GoodHashFunctor good;
	ArrayList<String> testList;


	@Before
	public void setUp() throws Exception {
		bad = new BadHashFunctor();
		med = new MediocreHashFunctor();
		good = new GoodHashFunctor();
		chainBad = new ChainingHashTable(10, bad);
		chainMediocre = new ChainingHashTable(10, med);
		chainGood = new ChainingHashTable(10, good);
		testList = new ArrayList<String>();
		
		testList.add("a");
		testList.add("ab");
		testList.add("abc");
		testList.add("abcd");
		testList.add("abcde");
		testList.add("abcdef");
		testList.add("abcdefg");
		testList.add("abcdefgh");
		testList.add("abcdefghi");
		testList.add("abcdefghijklmnopqrstuvwxyz");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddBad() {
		assertEquals(0, chainBad.size());
		assertTrue(chainBad.add("Nike"));
		assertTrue(chainBad.add("Shoe"));
		assertTrue(chainBad.add("Tanner"));
		assertTrue(chainBad.add("Basketball"));
		assertTrue(chainBad.add("Kate"));
		assertTrue(chainBad.add("Rusty"));
		assertTrue(chainBad.add("Josh"));
		assertTrue(chainBad.add("abcdefghijklmnopqrstuvwxyz"));
		assertEquals(8, chainBad.size());
		System.err.println("Add: ");
		printContents(chainBad);
	}

	private void printContents(ChainingHashTable c) {
		// TODO Auto-generated method stub
		int arrLength = c.getArray().length;
		for(int i = 0; i < arrLength; i++){
			if(!c.getArray()[i].isEmpty()){
				for(String s : c.getArray()[i]){
					System.out.println(i + " " + s);
				}
			}
		}
	}

	@Test
	public void testAddAll() {
		assertTrue(chainBad.addAll(testList));
		assertFalse(chainBad.addAll(testList));
		assertTrue(chainBad.containsAll(testList));
		System.err.println("Add All:");
		printContents(chainBad);
	}

	@Test
	public void testClear() {
		chainBad.addAll(testList);
		assertEquals(10, chainBad.size());
		chainBad.clear();
		assertEquals(0, chainBad.size());
	}

	@Test
	public void testContains() {
		assertFalse(chainBad.contains("Kate"));
		chainBad.add("Kate");
		assertTrue(chainBad.contains("Kate"));
	}

	@Test
	public void testContainsAll() {
		assertFalse(chainBad.containsAll(testList));
		chainBad.addAll(testList);
		assertTrue(chainBad.containsAll(testList));
	}

	@Test
	public void testIsEmpty() {
		assertTrue(chainBad.isEmpty());
		chainBad.add("Kate");
		assertFalse(chainBad.isEmpty());
	}

	@Test
	public void testSize() {
		assertEquals(0, chainBad.size());
		chainBad.add("Kate");
		assertEquals(1, chainBad.size());
		chainBad.addAll(testList);
		assertEquals(11, chainBad.size());
	}

}
