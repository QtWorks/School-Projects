package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

import cs5530.Connector;
import model.POIRecord;
import model.UserRecord;

import static model.Columns.*;

/*
 * Login,Name,Password,Address,Phone
 */
public class UserService {
	
	private Connector con;
		
	public UserService() {}

	public UserService(Connector con) {
		this.con = con;
	}


	public ArrayList<UserRecord> getUsers(String sql){
		ResultSet rs = null;
		ArrayList<UserRecord> users = new ArrayList<UserRecord>();
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				UserRecord ur = new UserRecord();
				ur.setLogin(rs.getString(LOGIN));
				ur.setName(rs.getString(NAME));
				ur.setPassword(rs.getString(PASSWORD));
				ur.setPhone(rs.getString(PHONE));
				ur.setAddressId(rs.getInt(ADDRESS));
				ur.setAdmin(rs.getBoolean(IS_ADMIN));
				users.add(ur);
			}			
			rs.close();
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
		AddressService as = new AddressService(con);
		for(UserRecord user : users){
			user.setAddress(as.getAddressById(user.getAddressId()));
		}
		return users;
	}
	
	public ArrayList<UserRecord> getUsersOneDegreeAway(UserRecord u1){
		String sql = "Select * from User where login in (" +
				"Select u2.login from User u1, User u2, Favorite f1, Favorite f2" +
				" where u1.login = f1.user" +
				" and u2.login = f2.user" +
				" and u1.login != u2.login" +
				" and f1.idpoi = f2.idpoi" +
				" and u1.login like '" + u1.getLogin() + "');";
		return getUsers(sql);
	}
	
	private boolean getBoolean(String sql) {
		ResultSet rs = null;
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				return rs.getBoolean(1);
			}			
			rs.close();
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
		return false;
	}

	public ArrayList<UserRecord> getUsersByFavorite(POIRecord favorite){
		String sql = "Select * from User u, Favorite f where u.login = f.user and f.idpoi = " + favorite.getId();
		return getUsers(sql);
	}


	public boolean isValidPassword(String login, String password) {
		UserRecord user = getUserByLogin(login);
		if(user != null){
			String userPass = user.getPassword();
			if(userPass != null)
				return getUserByLogin(login).getPassword().equals(password);
		}
		return false;
	}
	
	public UserRecord getUserByLogin(String login){
		ArrayList<UserRecord> results = getUsers("select * from User where login = '" + login + "'" );
		if(results.size() > 0)
			return results.get(0);
		return null;
	}

	public boolean isAvailableLogin(String login) {
		return getUserByLogin(login) == null;
	}

	public void insertUser(UserRecord ur) {
		String sql = "INSERT INTO `cs5530db53`.`User` (`login`, `name`, `password`, `phone`, `address`, `isAdmin`) "
				+ "VALUES ('" + ur.getLogin() + "', '"
				+ ur.getName() + "', '" 
				+ ur.getPassword() + "', "
				+ ur.getPhone() + ", " 
				+ ur.getAddress().getId() + ", " 
				+ ur.isAdmin() + ");";
		try{
			int result = con.stmt.executeUpdate(DISABLE_FK);
			int result2 = con.stmt.executeUpdate(sql);
		}
		catch(Exception e){
			System.out.println("cannot execute the query");
		}
	}

	public ArrayList<UserRecord> getUsersByPOI(POIRecord poi) {
		String sql = "Select * from User u, Visit v where u.login = v.user and v.idpoi = " + poi.getId() + ";";
		return getUsers(sql);
	}

	public ArrayList<UserRecord> getAllUsers() {
		String sql = "Select * from User";
		return getUsers(sql);
	}

	public ArrayList<UserRecord> getTopUsers(int n, int choice) {
		String orderBy = "";
		if(choice == 1){
			orderBy += ", (select u2.login,(sum(t.trusted = 1)-sum(t.trusted = 0)) as tValue " + 
					" from Trusted t, User u2 " +
					" where t.marked_user = u2.login " +
					" group by u2.login) as tTable " +
					" where u1.login = tTable.login " +
					" order by tTable.tValue ";
		}else if(choice == 2){
			orderBy += "order by (select avg(fr.rating) " +
						" from FeedbackRating fr " +
						"where fr.marked_user like u1.login) ";
		}
		String sql = "Select * from User u1 " + orderBy + " DESC LIMIT " + n + ";";
		return getUsers(sql);
	}
	
	public int findDegreesOfSeparation(String login1, String login2){
		Hashtable<String,Integer> distance = getDistances(login1);
		if(distance.containsKey(login2)){
			return distance.get(login2);
		}
		else{
			return -1;
		}
	}
	
	/**
	 * Gets degree distance from user to all other users
	 * (Could definitely be a little more efficient, but it works)
	 * @param login
	 * @return
	 */
	private Hashtable<String, Integer> getDistances(String login) {
		Hashtable<String,Integer> distance = new Hashtable<String,Integer>();
		distance.put(login, 0);
		HashSet<String> visited = new HashSet<String>();
		Queue<UserRecord> q = new LinkedList<UserRecord>();
		getUserByLogin(login);
		q.add(getUserByLogin(login));
		while(!q.isEmpty()){
			UserRecord current = q.remove();
			visited.add(current.getLogin());
			ArrayList<UserRecord> children = getUsersOneDegreeAway(current);
			for(UserRecord child : children){
				if(distance.containsKey(child.getLogin())){
					int prev = distance.get(child.getLogin());
					if(distance.get(current.getLogin()) + 1 < prev)
						distance.replace(child.getLogin(), distance.get(current.getLogin()) + 1);
				}else{
					distance.put(child.getLogin(), distance.get(current.getLogin()) + 1);
				}
				if(!visited.contains(child.getLogin()))
					q.add(child);
			}
		}
		return distance;
	}
	
	public ArrayList<UserRecord> getNDegreesAway(UserRecord user, int n) {
		ArrayList<UserRecord> result = new ArrayList<UserRecord>();
		Hashtable<String,Integer> distances = getDistances(user.getLogin());
		Enumeration<String> e = distances.keys();
	    while (e.hasMoreElements()){
	    	String login = e.nextElement();
	    	if(distances.get(login) == n){
	    		result.add(getUserByLogin(login));
	    	}
	    }
		return result;
	}

	
	


}
