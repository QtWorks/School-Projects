package assignment10;

public class MediocreHashFunctor implements HashFunctor{

	@Override
	public int hash(String item) {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i = 0; i < item.length(); i++){
			sum += (int) item.charAt(i);
		}
		return sum;
	}

}
