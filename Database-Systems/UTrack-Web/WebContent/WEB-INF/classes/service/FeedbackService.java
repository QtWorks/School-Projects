package service;

import java.sql.*;
import java.util.ArrayList;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import cs5530.Connector;
import model.FeedbackRecord;
import model.POIRecord;
import model.UserRecord;
import model.VisitRecord;

import static model.Columns.*;
/*
 * POI, User, Score
 */
public class FeedbackService {
	
	Connector con;
	
	public FeedbackService(Connector con){
		this.con = con;
	}
		
	public ArrayList<FeedbackRecord> getFeedbacks(String sql){
		ResultSet rs = null;
		ArrayList<FeedbackRecord> feedbacks = new ArrayList<FeedbackRecord>();
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				FeedbackRecord fr = new FeedbackRecord();
				UserRecord ur = new UserRecord();
				ur.setLogin(rs.getString(USER));
				fr.setUser(ur);
				
				POIRecord pr = new POIRecord();
				pr.setId(rs.getInt(IDPOI));
				fr.setPoi(pr);
				
				fr.setScore(rs.getInt(SCORE));
				
				feedbacks.add(fr);
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
		POIService ps = new POIService(con);
		ShortTextService ts = new ShortTextService(con);
		for(FeedbackRecord f : feedbacks){
			POIRecord poi = ps.getPOIbyId(f.getPoi().getId());
			f.setPoi(poi);
			
			f.setText(ts.getText(poi.getId(),f.getUser().getLogin()));
		}
		return feedbacks;
	}

	public boolean insertFeedback(FeedbackRecord fr) {
		String sql = "INSERT INTO `cs5530db53`.`Feedback` " + 
				"(`" + USER + "`,`" + IDPOI + "`,`" + SCORE + "`)" + " VALUES "
				+ "('" + fr.getUser().getLogin() + "'," 
				+ fr.getPoi().getId() + ","
				+ fr.getScore() + ");";
		try{
			int result = con.stmt.executeUpdate(DISABLE_FK);
			int result2 = con.stmt.executeUpdate(sql);
		}
		catch(SQLException e){
			String message = e.getMessage();
			if(message.contains("Duplicate")){
				System.out.println("It looks like you already have a review for this place.");
				System.out.println("This review cannot be changed or updated.");
			}
			else{
				System.out.println("Cannot execute the query");
			}
			return false;
		}
		return true;
		
	}

	public ArrayList<FeedbackRecord> getFeedbacksByPOI(POIRecord selection) {
		String sql = "SELECT * FROM Feedback f where f.idpoi = " + selection.getId();
		return getFeedbacks(sql);
	}
	
	public ArrayList<FeedbackRecord> getFeedbackRecords(UserRecord currentUser, POIRecord selection){
		String sql = "SELECT * FROM Feedback f where user like '" + currentUser.getLogin() + "'" +
				" and idpoi = " + selection.getId();
		return getFeedbacks(sql);
	}
	
	public FeedbackRecord getFeedbackRecord(String user, int idpoi){
		String sql = "SELECT * FROM Feedback f where user like '" + user + "'" +
				" and idpoi = " + idpoi;
		ArrayList<FeedbackRecord> result = getFeedbacks(sql);
		if(result.size() > 0)
			return result.get(0);
		else
			return null;
	}

	public ArrayList<FeedbackRecord> getFeedbackByUser(UserRecord user) {
		String sql = "SELECT * FROM Feedback f where f.user like '" + user.getLogin() + "'" ;
		return getFeedbacks(sql);
	}

	public ArrayList<FeedbackRecord> getTopFeedbacksByPOI(POIRecord poi, int n) {
		String sql = "Select * from Feedback f where f.idpoi = " + poi.getId() + 
				" order by " +
				"(select avg(fr.rating) from FeedbackRating fr " + 
				" where fr.marked_user like f.user " +
				" and fr.idpoi = f.idpoi) DESC LIMIT " + n + ";";
		return getFeedbacks(sql);
	}

}
