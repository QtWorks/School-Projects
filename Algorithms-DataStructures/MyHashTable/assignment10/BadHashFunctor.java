package assignment10;

public class BadHashFunctor implements HashFunctor{

	@Override
	public int hash(String item) {
		// TODO Auto-generated method stub
		return item.length();
	}

}
