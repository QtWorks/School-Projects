package assignment10;

public class GoodHashFunctor implements HashFunctor{

	@Override
	public int hash(String item) {
		// TODO Auto-generated method stub
		int hashCode = 1;
		int length = item.length();
		for(int i = 0; i < length; i++){
			hashCode = hashCode * 47 + item.charAt(i);
		}
		return Math.abs(hashCode);
	}
}