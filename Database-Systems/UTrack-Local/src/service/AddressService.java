package service;

import java.sql.ResultSet;
import java.util.ArrayList;

import cs5530.Connector;
import model.AddressRecord;
import static model.Columns.*;


public class AddressService {
	
	private Connector con;


	public AddressService(Connector con) {
		this.con = con;
	}
	
	public ArrayList<AddressRecord> getAddresses(String sql){
		ArrayList<AddressRecord> addresses = new ArrayList<AddressRecord>();
		ResultSet rs = null;
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				AddressRecord ar = new AddressRecord();
				ar.setCountry(rs.getString(COUNTRY));
				ar.setState(rs.getString(STATE));
				ar.setCity(rs.getString(CITY));
				ar.setZip(rs.getInt(ZIP));
				ar.setStreet(rs.getString(STREET));
				ar.setNumber(rs.getInt(NUMBER));
				addresses.add(ar);
			}
		}
		catch(Exception e){
			System.out.println("cannot execute the query");
		}
		finally
	 	{
	 		try{
	 		if (rs!=null && !rs.isClosed())
	 			rs.close();
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		return addresses;
	}

	public void insertAddress(AddressRecord userAddress) {
		String sql = "INSERT INTO `cs5530db53`.`Address` (`country`, `state`, `city`, `zip`, `street`, `number`) " +
		"VALUES ('" + userAddress.getCountry() + "', '" + userAddress.getState() + "', '" + userAddress.getCity() +
		"'," + userAddress.getZip() + ", '" + userAddress.getStreet() + "', " + userAddress.getNumber() + ")";
		ResultSet rs = null;
		try{
			int result = con.stmt.executeUpdate(sql);
			sql = "select " + LAST_INSERT +" from Address LIMIT 1";
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt(LAST_INSERT);
				userAddress.setId(id);
			}
		}
		catch(Exception e){
			System.out.println("cannot execute the query");
		}
		finally
	 	{
	 		try{
	 		if (rs!=null && !rs.isClosed())
	 			rs.close();
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
	}

	public AddressRecord getAddressById(int index) {
		String sql = "select * from Address where id =" + index;
		ArrayList<AddressRecord> addresses = getAddresses(sql);
		if(addresses.size() > 0)
			return addresses.get(0);
		return null;
	}

}
