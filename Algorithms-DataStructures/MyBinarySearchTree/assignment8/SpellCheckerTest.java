package assignment8;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @authors Joshua Callahan & Tanner Barlow
 *
 */
public class SpellCheckerTest {
	SpellChecker dictionary;
	SpellChecker test;
	SpellChecker listDictionary;
	ArrayList<String> list;

	@Before
	public void setUp() throws Exception {
		
		// A dictionary BST is made from the given dictionary file.
		dictionary = new SpellChecker(new File("dictionary.txt"));
		
		test = new SpellChecker(new File("hello_world.txt"));
		//A list of 6 words is created to be added to a BST.
		list = new ArrayList<String>();
		list.add("hello");
		list.add("there");
		list.add("world");
		list.add("nice");
		list.add("to");
		list.add("meet");

	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests the SpellChecker constructor for an empty BST
	 */
	@Test
	public void testSpellChecker() {
		


	}
	/**
	 * Tests the SpellChecker constructor for a BST made from a list of Strings
	 */
	@Test
	public void testSpellCheckerListOfString() {
		//A BST dictionary is created from 6 words and tested.
		listDictionary = new SpellChecker(list);

		assertTrue(returnMisspelledWords(listDictionary, "hello_world.txt").size() == 1);
		assertTrue(returnMisspelledWords(listDictionary, "hello_world.txt").contains("you"));
	}
	/**
	 * Tests the SpellChecker constructor for a BST made from a file.
	 */
	@Test
	public void testSpellCheckerFile() {
		
		dictionary = new SpellChecker(new File("dictionary.txt"));
		assertTrue(returnMisspelledWords(dictionary, "hello_world.txt").size() == 0);

	}
	/**
	 * Tests to ensure words can be easily added to the dictionary BST
	 */
	@Test
	public void testAddToDictionary() {
		assertTrue(returnMisspelledWords(dictionary, "text_with_new_words.txt").contains("josh"));
		dictionary.addToDictionary("josh");
		assertFalse(returnMisspelledWords(dictionary, "text_with_new_words.txt").contains("josh"));
	}
	/**
	 * Tests to ensure words can be easily removed from the dictionary BST
	 */
	@Test
	public void testRemoveFromDictionary() {
		assertFalse(returnMisspelledWords(test, "hello_world.txt").contains("hello"));
		test.removeFromDictionary("hello");
		assertTrue(returnMisspelledWords(test, "hello_world.txt").contains("hello"));
		System.err.println(test.toString());
		assertFalse(returnMisspelledWords(dictionary, "hello_world.txt").contains("hello"));
		dictionary.removeFromDictionary("hello");
		assertTrue(returnMisspelledWords(dictionary, "hello_world.txt").contains("hello"));
	}
	/**
	 * Tests the spellCheck method
	 */
	@Test
	public void testSpellCheck() {
		
		//Hello_World.txt does not contained any misspelled words.
		assertTrue(returnMisspelledWords(dictionary, "hello_world.txt").size() == 0);
		
		//Good_Luck.txt contains "bst" as a misspelled word.
		assertTrue(returnMisspelledWords(dictionary, "good_luck.txt").size() == 1);
		assertTrue(returnMisspelledWords(dictionary, "good_luck.txt").contains("bst"));		
	}
	
  private static List<String> returnMisspelledWords(SpellChecker sc, String documentFilename) {

	    File doc = new File(documentFilename);
	    List<String> misspelledWords = sc.spellCheck(doc);
	    if (misspelledWords.size() == 0) {
	      //System.out.println("There are no misspelled words in file " + doc + ".");
	    } else {
	     // System.out.println("The misspelled words in file " + doc + " are:");
	      for (String w : misspelledWords) {
	       // System.out.println("\t" + w);
	      }
	    }
	    return misspelledWords;
	  }
}
