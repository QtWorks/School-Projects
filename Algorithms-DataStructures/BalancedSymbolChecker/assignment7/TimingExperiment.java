package assignment7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * 
 * @author Joshua Callahan
 * @author Tanner Barlow
 *
 */
public class TimingExperiment {
	
	private static final int MAX_SIZE = 100000;
	private static final int INCREMENT = 10000;

	
	
	private static MyStack<Integer> stack;

  public static void main(String[] args) {
	
	
	 
	long timesToLoop = 1000;
	
	for(int j = 2; j <= MAX_SIZE; j+= INCREMENT)
	{
		ArrayList<MyStack<Integer>> stackArrayList = new ArrayList<MyStack<Integer>>();

		for(int k = 0; k < timesToLoop; k++)
		{
			stack = new MyStack<Integer>();
			for(int i = 0; i < j; i++){
				stack.push(i);
			}
			
			stackArrayList.add(stack);		
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
	    	stackArrayList.get((int) i).peek(); 	
	    }
	    	

	    midpointTime = System.nanoTime();

	    // Run an empty loop to capture the cost of running the loop.

	    for (long i = 0; i < timesToLoop; i++) { // empty block
	    	stackArrayList.get((int) i);
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
	
}