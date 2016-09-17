package assignment10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author Tanner Barlow
 *
 */
public class TimingExperiment {
	
	private static final int MAX_SIZE = 20002;
	private static final int INCREMENT = 500;
	static ArrayList<String> testList;
	static QuadProbeHashTable quad;
	static ChainingHashTable chain;
	static ArrayList<String> testWords;
	static BadHashFunctor bad;
	static MediocreHashFunctor med;
	static GoodHashFunctor good;
	static Random r;
	static ArrayList<String> words;
	
	


  public static void main(String[] args) {
	
	  words = new ArrayList<String>();
	  bad = new BadHashFunctor();
	  med = new MediocreHashFunctor();
	  good = new GoodHashFunctor();
	  
		
	
	 
	long timesToLoop = 1000;
	
	for(int j = 2; j <= MAX_SIZE; j+= INCREMENT)
	{
		ArrayList<QuadProbeHashTable> quadList = new ArrayList<QuadProbeHashTable>();
		ArrayList<ChainingHashTable> chainList = new ArrayList<ChainingHashTable>();

		testList = generateRandomWords(j);
		for(int k = 0; k < timesToLoop; k++)
		{
			quad = new QuadProbeHashTable(j, good);
//			chain = new ChainingHashTable(j, good);
			
			for(int i = 0; i < j; i++){
				quad.add(testList.get(i));
			}
			
//			for(int i = 0; i < j; i++){
//				chain.add(testList.get(i));
//			}
			quadList.add(quad);
//			chainList.add(chain);
		}
//		boolean print = true;
//		if(print){
//			printContents(quad);
//			System.out.println(quad.size());
//			System.out.println(quad.getCapacity());
//			break;
//		}
		
			  
		  
	    long startTime, midpointTime, stopTime;
	    

	    // First, spin computing stuff until one second has gone by.
	    // This allows this thread to stabilize.


	    startTime = System.nanoTime();
	    while (System.nanoTime() - startTime < 1000000000) { // empty block
	    }

	    // Now, run the test.

	    

	    startTime = System.nanoTime();

	    for (long i = 0; i < timesToLoop; i++)
	    {
	    	quadList.get((int) i).add("Tanner");
//	    	chainList.get((int) i).add("Tanner");
	    }
	    	

	    midpointTime = System.nanoTime();

	    // Run an empty loop to capture the cost of running the loop.

	    for (long i = 0; i < timesToLoop; i++) { // empty block
	    	quadList.get((int) i);
//	    	chainList.get((int) i);
	    }

	    stopTime = System.nanoTime();

	    // Compute the time, subtract the cost of running the loop
	    // from the cost of running the loop and computing square roots.
	    // Average it over the number of runs.
	    
	   

	    double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime))
	        / timesToLoop;

	    System.out.println(j + " " + averageTime);
	}
}




private static ArrayList<String> generateRandomWords(int numberOfWords) {
	
	    ArrayList<String> randomStrings = new ArrayList<String>();
	    r = new Random(843);
	    for(int i = 0; i < numberOfWords; i++)
	    {
	        char[] word = new char[r.nextInt(8)]; 
	        for(int j = 0; j < word.length; j++)
	        {
	            word[j] = (char)('a' + r.nextInt(26));
	        }
	        randomStrings.add(new String(word));
	    }
	    return randomStrings;
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
	
}