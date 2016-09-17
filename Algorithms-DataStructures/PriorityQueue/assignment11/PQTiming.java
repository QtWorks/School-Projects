package assignment11;

import java.util.ArrayList;



/**
 * 
 * @author Kevin Griggs
 * @author Tanner Barlow
 *
 */
public class PQTiming {
	
	private static final int STARTING_POINT = 2;	
	private static final int MAX_SIZE = 50002;
	private static final int INCREMENT = 10000;
	

  public static void main(String[] args) {
	
	
	 
	long timesToLoop = 1000;
	
	for(int j = STARTING_POINT; j <= MAX_SIZE; j+= INCREMENT)
	{

		ArrayList<PriorityQueue<Integer>> pqList = new ArrayList<PriorityQueue<Integer>>();
		
		for(int i = 0; i < timesToLoop; i++){
			PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
			for(int k = 0; k < j; k++)
				pq.add(k);
			pqList.add(pq);
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
	    	pqList.get((int) i).findMin();
	    }
	    	

	    midpointTime = System.nanoTime();

	    // Run an empty loop to capture the cost of running the loop.

	    for (long i = 0; i < timesToLoop; i++) { // empty blocks
	    	pqList.get((int) i);
	    }

	    stopTime = System.nanoTime();

	    // Compute the time, subtract the cost of running the loop
	    // from the cost of running the loop and computing square roots.
	    // Average it over the number of runs.
	    
	   

	    double averageTime = (((midpointTime - startTime)) - (stopTime - midpointTime))
	        / timesToLoop;

	    System.out.println(j + " " + averageTime);


	  }
	}
	
}