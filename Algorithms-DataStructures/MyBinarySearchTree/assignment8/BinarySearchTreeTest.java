package assignment8;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import assignment8.BinarySearchTree.BSTNode;

/**
 * @authors Joshua Callahan & Tanner Barlow
 */
public class BinarySearchTreeTest {

	// An integer tree is created to be tested.
	BinarySearchTree<Integer> intTree;

	// An integer ArrayList is created to be added to the intTree.
	ArrayList<Integer> intsToAdd, intTreeMinusRoot;

	// A String tree is created to be tested.
	BinarySearchTree<String> stringTree;

	// A second String tree is created to be tested.
	BinarySearchTree<String> stringTree2;

	@Before
	public void setUp() throws Exception {

		intTree = new BinarySearchTree<Integer>();

		intTreeMinusRoot = new ArrayList<Integer>();

		intTree.add(6);
		intTree.add(7);
		intTree.add(20);
		intTree.add(4);
		intTree.add(3);
		intTree.add(5);
		intTree.add(18);

		intTreeMinusRoot.add(7);
		intTreeMinusRoot.add(20);
		intTreeMinusRoot.add(4);
		intTreeMinusRoot.add(3);
		intTreeMinusRoot.add(5);
		intTreeMinusRoot.add(18);

		intsToAdd = new ArrayList<Integer>();
		for (int i = 30; i < 40; i++) {
			intsToAdd.add(i);
		}

		stringTree = new BinarySearchTree<String>();

		stringTree.add("j");
		stringTree.add("e");
		stringTree.add("t");
		stringTree.add("f");
		stringTree.add("s");
//		stringTree.add("f");
//		stringTree.add("v");
//		stringTree.add("b");
//		stringTree.add("s");
//		stringTree.add("d");
//		stringTree.add("w");
//		stringTree.add("a");
		
		
		stringTree2 = new BinarySearchTree<String>();
		stringTree2.add("Hello");
		stringTree2.add("world");
		stringTree2.add("nice");
		stringTree2.add("to");
		stringTree2.add("meet");
		stringTree2.add("you");

		

	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests the BST add method.
	 */
	@Test
	public void testAdd() {
		intTree.writeDot("AddBefore.dot");
		assertTrue(intTree.add(13));
		assertTrue(intTree.contains(13));
		assertFalse(intTree.add(18));
		assertTrue(intTree.contains(18));
		intTree.writeDot("AddAfter.dot");
	}

	/**
	 * Tests the BST add method.
	 */
	@Test
	public void testAddException() {
		try {

			intTree.add(null);
			fail("Add did not throw an exception.");
		} catch (NullPointerException e) {
		}
	}

	/**
	 * Tests the BST addAll method.
	 */
	@Test
	public void testAddAll() {
		intTree.writeDot("AddAll before.dot");
		assertTrue(intTree.addAll(intsToAdd));
		assertTrue(intTree.containsAll(intsToAdd));
		intTree.writeDot("AddAll after.dot");
		
		assertFalse(intTree.addAll(intsToAdd));
		assertTrue(intTree.containsAll(intsToAdd));
	}

	/**
	 * Tests the BST addAll method.
	 */
	@Test
	public void testAddAllException() {
		try {
			intsToAdd.set(4, null);
			intTree.addAll(intsToAdd);
			fail("addAll did not throw an exception.");
		} catch (NullPointerException e) {
		}
	}

	/**
	 * Tests the BST clear method.
	 */
	@Test
	public void testClear() {
		assertTrue(intTree.addAll(intsToAdd));
		intTree.clear();
		assertEquals(0, intTree.size());
	}

	/**
	 * Tests the BST contains method.
	 */
	@Test
	public void testContains() {
		assertTrue(intTree.contains(20));

		assertTrue(intTree.add(13));
		assertTrue(intTree.contains(13));

		assertFalse(intTree.add(18));
		assertTrue(intTree.contains(18));

		assertFalse(intTree.contains(17));
	}

	/**
	 * Tests the BST contains method.
	 */
	@Test
	public void testContainsException() {
		try {
			intTree.contains(null);
			fail("Contains did not throw an exception.");
		} catch (NullPointerException e) {
		}
	}

	/**
	 * Tests the BST containsAll method.
	 */
	@Test
	public void testContainsAll() {
		assertTrue(intTree.addAll(intsToAdd));
		assertTrue(intTree.containsAll(intsToAdd));

		assertFalse(intTree.addAll(intsToAdd));
		assertTrue(intTree.containsAll(intsToAdd));

		intsToAdd.set(4, 15);
		assertFalse(intTree.containsAll(intsToAdd));
	}

	/**
	 * Tests the BST containsAll method.
	 */
	@Test
	public void testContainsAllException() {
		intTree.addAll(intsToAdd);
		try {
			intsToAdd.set(4, null);
			intTree.containsAll(intsToAdd);
			fail("addAll did not throw an exception.");
		} catch (NullPointerException e) {
		}
	}

	/**
	 * Tests the BST first method.
	 */
	@Test
	public void testFirst() {
		assertEquals(3, (int) intTree.first());
		assertTrue(intTree.remove(3));
		assertEquals(4, intTree.first());
		assertTrue(intTree.remove(4));
		assertEquals(5, intTree.first());
		assertTrue(intTree.remove(5));
		assertEquals(6, intTree.first());
		assertTrue(intTree.remove(6));
		assertEquals(7, intTree.first());
		assertTrue(intTree.remove(7));
		assertEquals(18, intTree.first());
		assertTrue(intTree.remove(18));
		assertEquals(20, intTree.first());
		assertTrue(intTree.remove(20));
	}

	/**
	 * Tests the BST first method.
	 */
	@Test
	public void testFirstException() {

		try {
			BinarySearchTree emptyTree = new BinarySearchTree();
			emptyTree.first();
			fail("First did not throw an exception.");
		} catch (NoSuchElementException e) {
		}
	}

	/**
	 * Tests the BST last method.
	 */
	@Test
	public void testLast() {
		assertEquals(20, (int) intTree.last());
	}

	/**
	 * Tests the BST last method.
	 */
	@Test
	public void testLastException() {

		try {
			BinarySearchTree emptyTree = new BinarySearchTree();
			emptyTree.last();
			fail("Last did not throw an exception.");
		} catch (NoSuchElementException e) {
		}
	}

	/**
	 * Tests the BST isEmpty method.
	 */
	@Test
	public void testisEmpty() {
		assertFalse(intTree.isEmpty());

		BinarySearchTree emptyTree = new BinarySearchTree();
		assertTrue(emptyTree.isEmpty());
	}

	/**
	 * Tests the BST size method.
	 */
	@Test
	public void testSize() {
		assertEquals(7, intTree.size());

		intTree.addAll(intsToAdd);
		assertEquals(17, intTree.size());

		BinarySearchTree emptyTree = new BinarySearchTree();
		assertEquals(0, (int) emptyTree.size());
	}

	/**
	 * Tests the BST remove method.
	 */
	@Test
	public void testRemoveRoot() {
		// Remove the root
		assertTrue(stringTree.remove("j"));
		assertFalse(stringTree.contains("j"));

		// Make sure root updates correctly
		assertEquals("s", stringTree.getRoot());
		
		assertTrue(intTree.remove(6));
		assertFalse(intTree.contains(6));
		assertTrue(containsAllExcept(6, intTree, intTreeMinusRoot));
		assertEquals(7, intTree.getRoot());

		stringTree2.writeDot("String2Before.dot");
		stringTree2.remove("Hello");
		assertFalse(stringTree2.contains("Hello"));
		stringTree2.writeDot("String2After.dot");

	}

	/**
	 * Tests the BST remove method.
	 */
	@Test
	public void testRemoveParentWithOneChild() {
		// Has only right child
		assertTrue(stringTree.remove("e"));
		assertFalse(stringTree.contains("e"));

		// Has only left child
		assertTrue(stringTree.remove("t"));
		assertFalse(stringTree.contains("t"));

		assertTrue(intTree.remove(7));
		assertFalse(intTree.contains(7));

		assertTrue(intTree.remove(20));
		assertFalse(intTree.contains(20));

	}

	/**
	 * Tests the BST remove method.
	 */
	@Test
	public void testRemoveParentWithTwoChildrenNotRoot() {

		assertTrue(intTree.remove(4));
		assertFalse(intTree.contains(4));

		assertTrue(stringTree.remove("s"));
		assertFalse(stringTree.contains("s"));
	}

	/**
	 * Tests the BST remove method.
	 */
	@Test
	public void testRemoveLeaf() {
		// Remove the leaf
		assertTrue(intTree.remove(18));
		assertFalse(intTree.contains(18));

		assertTrue(stringTree.remove("s"));
		assertFalse(stringTree.contains("s"));
	}

	/**
	 * Tests the BST remove method.
	 */
	@Test
	public void testRemoveException() {
		try {
			intTree.remove(null);
			fail("Remove did not throw an exception.");
		} catch (NullPointerException e) {
		}
	}

	/**
	 * Tests the BST removeAll method.
	 */
	@Test
	public void testRemoveAllException() {
		intTree.addAll(intsToAdd);
		try {
			intsToAdd.set(4, null);
			intTree.removeAll(intsToAdd);
			fail("removeAll did not throw an exception.");
		} catch (NullPointerException e) {
		}
	}

	/**
	 * Tests the BST toArrayList method.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testToArrayList() {

		// An ArrayList is created to compare.
		ArrayList<Integer> testList = new ArrayList<Integer>();
		testList.add(3);
		testList.add(4);
		testList.add(5);
		testList.add(6);
		testList.add(7);
		testList.add(18);
		testList.add(20);

		ArrayList<Integer> intTreeList = new ArrayList<Integer>();
		intTreeList = intTree.toArrayList();

		// The ArrayLists are checked to see if they contain the same elements.
		for (int i = 0; i < testList.size(); i++) {
			if (testList.get(i) != intTreeList.get(i)) {
				System.err.println("Expected: " + testList.get(i) + " Actual: "
						+ intTreeList.get(i));
				fail("Lists don't Match");
			}

		}
	}

	/**
	 * Tests to make sure that all elements are still contained in the tree
	 * other than the element we wanted to delete
	 * 
	 * @param c
	 *            - item that was deleted
	 * @param b
	 *            - BST
	 * @param arr
	 *            - ArrayList containing elements
	 * @return
	 */
	public boolean containsAllExcept(Comparable c, BinarySearchTree<Integer> b,
			ArrayList<Integer> arr) {
		if (b.contains(c)) {
			return false;
		}
		if (!b.containsAll(arr)) {
			return false;
		}
		return true;
	}

}
