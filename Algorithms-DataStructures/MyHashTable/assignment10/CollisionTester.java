package assignment10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CollisionTester {
	private static final int START = 0, FINISH = 10000, INCREMENT = 1000;
	static QuadProbeHashTable quad;
	static ChainingHashTable chain;
	static ArrayList<String> testWords;
	static BadHashFunctor bad;
	static MediocreHashFunctor med;
	static GoodHashFunctor good;
	static Scanner s;
	static Random r;

	public static void main(String[] args) {
		bad = new BadHashFunctor();
		med = new MediocreHashFunctor();
		good = new GoodHashFunctor();

		for(int i = START; i <= FINISH; i+= INCREMENT){
//			runTest(i, bad);
			testFunctor(i, good);
		}
//		printContents(quad);
	}

	private static void testFunctor(int i, HashFunctor functor) {
		// TODO Auto-generated method stub
		testWords = generateRandomWordsSpecifiedLength(i, 100);
		
		quad = new QuadProbeHashTable(testWords.size(), functor);
		chain = new ChainingHashTable(testWords.size(), functor);
		for(String s : testWords){
			quad.add(s);
		}
		
		for(int j = 0; j < testWords.size(); j++){
			chain.add(testWords.get(j));
		}
//		System.out.println(i + " " + quad.getCollisions());
		System.out.println(i + " " + chain.getCollisions());

	}

	private static ArrayList<String> generateRandomWordsSpecifiedLength(int size, int numberOfWords) {
		// TODO Auto-generated method stub
		ArrayList<String> randomStrings = new ArrayList<String>();
	    r = new Random();
	    for(int i = 0; i < numberOfWords; i++)
	    {
	        char[] word = new char[size]; 
	        for(int j = 0; j < word.length; j++)
	        {
	            word[j] = (char)('a' + r.nextInt(26));
	        }
	        randomStrings.add(new String(word));
	    }
	    return randomStrings;
	}

	private static void runTest(int size, HashFunctor functor) {

		testWords = new ArrayList<String>();
		testWords = generateRandomWords(size);

		quad = new QuadProbeHashTable(testWords.size(), functor);
//		chain = new ChainingHashTable(testWords.size(), functor);

		quad.addAll(testWords);
//		chain.addAll(testWords);

		System.out.println(size + " " + quad.getCollisions());
//		System.out.println(size + " " + chain.getCollisions());
	}

	private static void printContents(ChainingHashTable c) {
		for(int i = 0; i < c.size(); i++)
			if(c.getArray()[i] != null)
				System.out.println("Chain: " + i + " " + c.getArray()[i]);
	}

	private static void printContents(QuadProbeHashTable q) {
		for(int i = 0; i < q.size(); i++)
			if(q.getArray()[i] != null)
				System.out.println("Quad: " + i + " " + q.getArray()[i]);
		
	}

		

	public static ArrayList<String> generateRandomWords(int numberOfWords)
	{
	    ArrayList<String> randomStrings = new ArrayList<String>();
	    r = new Random();
	    for(int i = 0; i < numberOfWords; i++)
	    {
	        char[] word = new char[r.nextInt(8)+123]; 
	        for(int j = 0; j < word.length; j++)
	        {
	            word[j] = (char)('a' + r.nextInt(26));
	        }
	        randomStrings.add(new String(word));
	    }
	    return randomStrings;
	}

}
