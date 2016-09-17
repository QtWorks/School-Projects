package assignment8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;

/**
 * 
 * @author Joshua Callahan
 * @author Tanner Barlow
 *
 */
public class TimingExperiment1 {
	
	private static final int STARTING_POINT = 900;	
	private static final int MAX_SIZE = 10000;
	private static final int INCREMENT = 100;
	

  public static void main(String[] args) {
	
	
	 
	long timesToLoop = 1000;
	
	for(int j = STARTING_POINT; j <= MAX_SIZE; j+= INCREMENT)
	{

		BinarySearchTree<Integer> sortedTree = new BinarySearchTree<Integer>();
		BinarySearchTree<Integer> randomTree = new BinarySearchTree<Integer>();
		TreeSet<Integer> treeSet = new TreeSet<Integer>();

		ArrayList<Integer> sortedList = new ArrayList<Integer>();
		ArrayList<Integer> randomList = new ArrayList<Integer>();
		
		for(int i = 0; i < j; i++){
			sortedList.add(i);
			randomList.add(i);
		}
		
		Collections.shuffle(randomList);
		
		for(int i = 0; i < j; i++){
			sortedTree.add(sortedList.get(i));
			randomTree.add(randomList.get(i));
		}

			
		  
			  
		  
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
	    	for(int h = 0; h < j; h++){
	    		randomTree.contains(h);
//	    		sortedTree.contains(h);
	    	}	    	
	    }
	    	

	    midpointTime = System.nanoTime();

	    // Run an empty loop to capture the cost of running the loop.

	    for (long i = 0; i < timesToLoop; i++) { // empty blocks
	    	for(int h = 0; h < j; h++){
	    	}
	    }

	    stopTime = System.nanoTime();

	    // Compute the time, subtract the cost of running the loop
	    // from the cost of running the loop and computing square roots.
	    // Average it over the number of runs.
	    
	   

	    double averageTime = (((midpointTime - startTime)/j) - (stopTime - midpointTime))
	        / timesToLoop;

	    System.out.println(j + " " + averageTime);


	  }
	}
	
}