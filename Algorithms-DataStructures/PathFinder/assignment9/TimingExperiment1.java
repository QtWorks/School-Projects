package assignment9;

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
	
	private static final int STARTING_POINT = 0;	
	private static final int MAX_SIZE = 1;
	private static final int INCREMENT = 1;
	

  public static void main(String[] args) {
	
	
	 
	long timesToLoop = 1000;
	
	for(int j = STARTING_POINT; j < MAX_SIZE; j+= INCREMENT)
	{
			  
		  
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
	    	PathFinder.solveMaze("huge.txt", "hugeOutput.txt");   	
	    }
	    	

	    midpointTime = System.nanoTime();

	    // Run an empty loop to capture the cost of running the loop.

	    for (long i = 0; i < timesToLoop; i++) { // empty blocks
	    }

	    stopTime = System.nanoTime();

	    // Compute the time, subtract the cost of running the loop
	    // from the cost of running the loop and computing square roots.
	    // Average it over the number of runs.
	    
	   

	    double averageTime = (((midpointTime - startTime)) - (stopTime - midpointTime))
	        / timesToLoop;

	    System.out.println(averageTime);


	  }
	}
	
}