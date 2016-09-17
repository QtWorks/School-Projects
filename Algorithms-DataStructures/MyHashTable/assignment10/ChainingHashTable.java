package assignment10;

import java.util.Collection;
import java.util.LinkedList;

public class ChainingHashTable implements Set<String> {
	
	private static final double LOAD_FACTOR = .5;
	private LinkedList<String>[] storage;
	private HashFunctor myFunctor;
	private int arrayCapacity;
	private int size;
	private int collisions;
	private double lambda;
	
	@SuppressWarnings("unchecked")
	public ChainingHashTable(int capacity, HashFunctor functor) {

	     storage = (LinkedList<String>[]) new LinkedList[capacity];
	     size = 0;
	     lambda = 0;
	     collisions = 0;
	     myFunctor = functor;
	     arrayCapacity = capacity;
	     storage = (LinkedList<String>[]) new LinkedList[capacity];
	     for(int i = 0; i < arrayCapacity; i++)
	    	 storage[i] = new LinkedList<String>();
	}


	@Override
	public boolean add(String item) {
		lambda = size / arrayCapacity;
		if(lambda >= LOAD_FACTOR){
			rehash();
		}
		int hashCode = myFunctor.hash(item) % arrayCapacity;
		LinkedList<String> list = storage[hashCode];
		if(list.isEmpty()){
			list.add(item);
			size++;
			lambda = size / arrayCapacity;
			return true;
		}else{
			if(list.contains(item))
				return false;
			collisions++;
			list.add(item);
			size++;
			lambda = size / arrayCapacity;
			return true;
		}
	}


	private void rehash() {
		LinkedList<String>[] temp = (LinkedList<String>[]) new LinkedList[arrayCapacity];
		for(int i = 0; i < arrayCapacity; i++){
			temp[i] = storage[i];
		}
		arrayCapacity *= 2;
		storage = (LinkedList<String>[]) new LinkedList[arrayCapacity];
		for(int i = 0; i < arrayCapacity; i++)
	    	 storage[i] = new LinkedList<String>();
		size = 0;
		for(int i = 0; i < temp.length; i++){
			if(temp[i] != null)
				for(String s : temp[i]){
					add(s);
				}
		}
		
	}


	@Override
	public boolean addAll(Collection<? extends String> items) {
		// TODO Auto-generated method stub
		for (String i: items){
			if (!add(i))
				return false;
		}
		return true;
	}


	@Override
	public void clear() {
		// TODO Auto-generated method stub
		for ( int i = 0; i < arrayCapacity; i++)
			storage[i].clear();
		size = 0;
	}


	@Override
	public boolean contains(String item) {
		// TODO Auto-generated method stub
		int hashCode = myFunctor.hash(item) % arrayCapacity;
		return storage[hashCode].contains(item);		
	}


	@Override
	public boolean containsAll(Collection<? extends String> items) {
		for (String i: items) {
			if(!contains(i))
				return false;
		}
		return true;
	}


	@Override
	public boolean isEmpty() {
		return size() == 0;
	}


	@Override
	public int size() {
		return size;
	}
	
	public LinkedList<String>[] getArray(){
		return storage;
	}

	public int getCapacity(){
		return arrayCapacity;
	}

	public int getCollisions(){
		return collisions;
	}
}
