package assignment10;

import java.util.Collection;

public class QuadProbeHashTable implements Set<String> {
	
	private int size;
	private String[] hashArray;
	private HashFunctor myFunctor;
	private int arrayCapacity;
	private double lambda;
	private int collisions;
	private final static double LOAD_FACTOR = .25;
	
	/** Constructs a hash table of the given capacity that uses the hashing function
     * specified by the given functor.
     */
   public QuadProbeHashTable(int capacity, HashFunctor functor){
	   
	   size = 0;
	   collisions = 0;
	   myFunctor = functor;
	   if(!isPrime(capacity)){
		   arrayCapacity = nextPrimeNumber(capacity);
	   }else{
		   arrayCapacity = capacity;
	   }
	   hashArray = new String[arrayCapacity];
   }

	private void growAndRehash() {
		String [] tempArray = new String [arrayCapacity];
		for(int i = 0; i < hashArray.length; i++){
			if(hashArray[i] != null){
				tempArray[i] = hashArray[i];
			}
		}
		arrayCapacity = nextPrimeNumber(arrayCapacity);
		hashArray = new String[arrayCapacity];
		size = 0;
		for(int i = 0; i < tempArray.length; i++){
			if(tempArray[i] != null)
				add(tempArray[i]);
		}
	}

	private int nextPrimeNumber(int n) {
		if(isPrime(n))
			n++;
		//loop through odd numbers checking whether or not it's prime
		if (n % 2 == 0)
			n++;
		//when the loop breaks, that's because n is prime and it is the next prime number
		for ( ; !isPrime(n); n+=2);
		return n;
	}

	private boolean isPrime (int n) {
		if (n%2 == 0)
			return false;
		for (int i = 3; i <= Math.sqrt(n); i+=2) {
			if (n%i == 0)
				return false;
		}
		return true;
		
	}

	@Override
	public boolean add(String item) {
		//Check to see if the array is big enough
		lambda = (double) size / (double) arrayCapacity;
		if(lambda >= LOAD_FACTOR){
			growAndRehash();
		}
		int hashCode = myFunctor.hash(item) % arrayCapacity;
		if (hashArray[hashCode] == null)
			hashArray[hashCode] = item;
		else {
			int index = hashCode;
			int j = 1;
			while(hashArray[index] != null){
				collisions++;
				if(hashArray[index].equals(item))
					return false;
				index = Math.abs((hashCode + j*j) % arrayCapacity);
				j++;
			}
			hashArray[index] = item;
		} 
		size++;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends String> items) {
		for (String i: items){
			if (!add(i))
				return false;
		}
		return true;
	}

	@Override
	public void clear() {
		//loop through the array and set each element to null
		for ( int i = 0; i < arrayCapacity; i++)
			hashArray[i] = null;
		size = 0;
		
	}

	@Override
	public boolean contains(String item) {
		int hashCode = myFunctor.hash(item) % arrayCapacity;
		if (hashArray[hashCode] == null) {
			return false;
		}else{
			if(hashArray[hashCode].equals(item))
				return true;
			else{
				int index = hashCode;
				int j = 1;
				while(hashArray[index] != null){
					index = (hashCode + j*j) % arrayCapacity;
					if(hashArray[index] == null){
						return false;
					}
					if(hashArray[index].equals(item)){
						return true;
					}
					j++;
				}
				return false;
			}
		}
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
	
	public String[] getArray(){
		return hashArray;
	}

	public int getCapacity(){
		return arrayCapacity;
	}
	
	public int getCollisions(){
		return collisions;
	}
}
