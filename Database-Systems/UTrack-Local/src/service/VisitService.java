package service;

import cs5530.Connector;
import model.POIRecord;
import model.UserRecord;
import model.VisitRecord;
import static model.Columns.*;

import java.sql.ResultSet;
import java.util.ArrayList;

/*
 * POI,User,Date,Spent,Party
 */
public class VisitService {
	
	Connector con;

	public VisitService(Connector con) {
		this.con = con;
	}

	public void insertVisit(VisitRecord vr) {
		String sql = "INSERT INTO `cs5530db53`.`Visit` " + 
				"(`idpoi`,`user`,`date`,`spent`,`party`) "
				+ "VALUES (" +
		vr.getPoi().getId() + ", '" + 
		vr.getUser().getLogin() + "', '" +
		vr.sqlDate() + "', " + 
		vr.getSpent() + ", " +
		vr.getParty() + ");";
		ResultSet rs = null;
		try{
			int result1 = con.stmt.executeUpdate(ENABLE_DATES);
			int result2 = con.stmt.executeUpdate(sql);
			sql = "select " + LAST_INSERT +" from Visit LIMIT 1";
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt(LAST_INSERT);
				vr.setIdvisit(id);
			}
			System.out.println(result2);
		}
		catch(Exception e){
			System.out.println("cannot execute the query");
		}
	}

	public boolean userHasVisited(UserRecord currentUser, POIRecord selection) {
		String sql = "Select exists ( Select * from Visit where user like " + currentUser.getLogin() +
				" and idpoi = " + selection.getId();
		ResultSet rs = null;
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				return rs.getBoolean(1);
			}
		}
		catch(Exception e){
			System.out.println("cannot execute the query");
		}
		return false;
	}

	public ArrayList<VisitRecord> getVisitRecords(UserRecord currentUser, POIRecord selection) {
		String sql = "Select * from Visit where user like '" + currentUser.getLogin() + "'" + 
				" and idpoi = " + selection.getId();
		ArrayList<VisitRecord> visits = new ArrayList<VisitRecord>();
		ResultSet rs = null;
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				VisitRecord vr = new VisitRecord();
				vr.setUser(currentUser);
				vr.setPoi(selection);
				vr.setDate(rs.getDate(DATE));
				vr.setSpent(rs.getDouble(SPENT));
				vr.setParty(rs.getInt(PARTY));
				visits.add(vr);
			}
		}
		catch(Exception e){
			System.out.println("cannot execute the query");
		}
		return visits;
	}

}
