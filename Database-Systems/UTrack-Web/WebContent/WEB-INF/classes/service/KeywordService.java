package service;


import static model.Columns.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cs5530.Connector;
import model.KeywordRecord;

/*
 * POI,Keyword
 */
public class KeywordService {
	
	Connector con;

	public KeywordService(Connector con) {
		this.con = con;
	}

	public void insertKeyword(int idpoi, String keyword, String language) {
		boolean exists = keywordExists(keyword,language);
		if(!exists){
			String sql = "INSERT INTO `cs5530db53`.`Keywords` " + 
					"(`" + KEYWORD + "`,`" + LANGUAGE + "`) " +
					"VALUES('" + keyword + "','" + language + "');";
			try{
				int result2 = con.stmt.executeUpdate(sql);
			}catch(SQLException e){
				System.out.println("Cannot execute the query");
			}
		}
		int id = getId(keyword,language);
		String sql = "INSERT INTO `cs5530db53`.`HasKeywords` " +
				"(`" + WID + "`,`" + IDPOI + "`) " +
				"VALUES (" + id + "," + idpoi + ");";
		try {
			int result = con.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("cannot execute query");
		}
	}
	
	private int getId(String keyword, String language) {
		ResultSet rs = null;
		int id = 0;
		try{
			String sql = "select " + WID +" from Keywords WHERE keyword = '" + keyword + 
					"' AND language = '" + language + "' LIMIT 1";
			System.out.println(sql);
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				id = rs.getInt(1);
			}
			
		}catch(SQLException e){
			System.out.println("Cannot execute the query");
		}finally
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
		return id;
	}

	private boolean keywordExists(String keyword, String language) {
		boolean result = false;
		String sql = "select exists (select * from Keywords where keyword = '" + keyword + 
				"' and language = '" + language + "');";
		ResultSet rs = null;
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				result = rs.getBoolean(1);
			}
		}
		catch(SQLException e){
			System.out.println("Cannot execute the query");
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
		return result;
	}

	public void removeAllKeywords(int idpoi){
		String sql = "DELETE FROM `cs5530db53`.`HasKeywords` WHERE `idpoi`= " + idpoi;
		try{
			int result = con.stmt.executeUpdate(sql);
		}catch(SQLException e){
			System.out.println("Cannot execute the query");
		}
	}

	public ArrayList<KeywordRecord> getKeywords(int idpoi) {
		ArrayList<KeywordRecord> keywords = new ArrayList<KeywordRecord>();
		ResultSet rs = null;
		String sql = "Select * from Keywords k, HasKeywords h where k.wid = h.wid " +
					" and h.idpoi = " + idpoi + ";";
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				KeywordRecord k = new KeywordRecord();
				k.setKeyword(rs.getString(KEYWORD));
				k.setLanguage(rs.getString(LANGUAGE));
				keywords.add(k);
			}
		}catch(Exception e){
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
		return keywords;
	}

}
