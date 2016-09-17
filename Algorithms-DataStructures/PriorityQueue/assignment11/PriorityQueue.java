package assignment11;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Represents a priority queue of generically-typed items. 
 * The queue is implemented as a min heap. 
 * The min heap is implemented implicitly as an array.
 * 
 * @author Kevin Griggs
 * @author Tanner Barlow
 */
public class PriorityQueue<AnyType> {

	private int currentSize;

	private AnyType[] array;

	private Comparator<? super AnyType> cmp;

	/**
	 * Constructs an empty priority queue. Orders elements according
	 * to their natural ordering (i.e., AnyType is expected to be Comparable)
	 * AnyType is not forced to be Comparable.
	 */
	public PriorityQueue() {
		currentSize = 0;
		cmp = null;
		array = (AnyType[]) new Object[10]; // safe to ignore warning
	}

	/**
	 * Construct an empty priority queue with a specified comparator.
	 * Orders elements according to the input Comparator (i.e., AnyType need not
	 * be Comparable).
	 */
	public PriorityQueue(Comparator<? super AnyType> c) {
		currentSize = 0;
		cmp = c;
		array = (AnyType[]) new Object[10]; // safe to ignore warning
	}

	/**
	 * @return the number of items in this priority queue.
	 */
	public int size() {
		return currentSize;
	}

	/**
	 * Makes this priority queue empty.
	 */
	public void clear() {
		currentSize = 0;
	}

	/**
	 * @return the minimum item in this priority queue.
	 * @throws NoSuchElementException if this priority queue is empty.
	 * 
	 * (Runs in constant time.)
	 */
	public AnyType findMin() throws NoSuchElementException {
		// FILL IN -- do not return null
		if(currentSize == 0)
			throw new NoSuchElementException();
		return array[0];
	}


	/**
	 * Removes and returns the minimum item in this priority queue.
	 * 
	 * @throws NoSuchElementException if this priority queue is empty.
	 * 
	 * (Runs in logarithmic time.)
	 */
	public AnyType deleteMin() throws NoSuchElementException {
		
		// if the heap is empty, throw a NoSuchElementException
		if(currentSize == 0)
			throw new NoSuchElementException();
		// store the minimum item so that it may be returned at the end
		AnyType temp = array[0];
		// replace the item at minIndex with the last item in the tree
		array[0] = array[currentSize - 1];
		// update size
		currentSize--;
		// percolate the item at minIndex down the tree until heap order is restored
		// It is STRONGLY recommended that you write a percolateDown helper method!
		int index = 0;
		percolateDown(0);
		// return the minimum item that was stored
		return temp;
	}


	private void percolateDown(int index) {
		//check to see if has two children
		//check for left child
		int leftChild = index * 2 + 1;
		int rightChild = index * 2 + 2;
		if (leftChild >= currentSize)
			return;
		//check for right child
		if (rightChild >= currentSize) {
			//swap parent with left child if it's less than
			if (compare(array[index], array[leftChild]) > 0){
				swap(index, leftChild);
			}
			else
				return;
		}
		
		//now it is safe to assume it has two children
		int minChildIndex = 0;
		int compVar = compare(array[leftChild], array[rightChild]);
		if (compVar < 0) {
			minChildIndex = index*2+1;
		} 
		else minChildIndex = index*2 + 2;
		
		//now compare the minchild to the parent
		if (compare(array[minChildIndex], array[index]) < 0){
			swap (minChildIndex, index);
			percolateDown(minChildIndex);
		}
	}

	/**
	 * Swaps the contents of the two indices
	 * @param index1 
	 * @param index2
	 */
	private void swap(int index1, int index2) {
		AnyType temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
	
	
	/**
	 * Adds an item to this priority queue.
	 * 
	 * (Runs in logarithmic time.) Can sometimes terminate early.
	 * 
	 * @param x -- the item to be inserted
	 */
	public void add(AnyType x) {
		// FILL IN
		// if the array is full, double its capacity
		if(currentSize == 0){
			array[0] = x;
			currentSize++;
			return;
		}
		if(currentSize >= array.length -1){
			AnyType[] temp = (AnyType[]) new Object[array.length * 2];
			for(int i = 0; i < array.length; i++){
				temp[i] = array[i];
			}
			array = temp;	
		}
		// add the new item to the next available node in the tree, so that
		// complete tree structure is maintained
		array[currentSize] = x;
		// update size
		currentSize++;
		// percolate the new item up the levels of the tree until heap order is restored
		// It is STRONGLY recommended that you write a percolateUp helper method!
		percolateUp(currentSize - 1);
	}

	private void percolateUp(int index) {
		int parent = (index - 1) / 2;
		int comp = (compare(array[parent], array[index]));
		// if parent is less than or equal to child, we don't do anything
		if (comp <= 0){
			return;
		}
		// if parent is greater than child, switch them and enter recursion
		else{
			swap(parent, index);
			percolateUp(parent);
		}
	}

	/**
	 * Generates a DOT file for visualizing the binary heap.
	 */
	public void generateDotFile(String filename) {
		try {
			PrintWriter out = new PrintWriter(filename);
			out.println("digraph Heap {\n\tnode [shape=record]\n");

			for(int i = 0; i < currentSize; i++) {
				out.println("\tnode" + i + " [label = \"<f0> |<f1> " + array[i] + "|<f2> \"]");
				if(((i*2) + 1) < currentSize)
					out.println("\tnode" + i + ":f0 -> node" + ((i*2) + 1) + ":f1");
				if(((i*2) + 2) < currentSize)
					out.println("\tnode" + i + ":f2 -> node" + ((i*2) + 2) + ":f1");
			}

			out.println("}");
			out.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Internal method for comparing lhs and rhs using Comparator if provided by the
	 * user at construction time, or Comparable, if no Comparator was provided.
	 */
	private int compare(AnyType lhs, AnyType rhs) {
		if (cmp == null)
			return ((Comparable<? super AnyType>) lhs).compareTo(rhs); // safe to ignore warning
		// We won't test your code on non-Comparable types if we didn't supply a Comparator
		return cmp.compare(lhs, rhs);
	}



	//LEAVE IN for grading purposes
	public Object[] toArray() {    
		Object[] ret = new Object[currentSize];
		for(int i = 0; i < currentSize; i++)
			ret[i] = array[i];
		return ret;
	}
}