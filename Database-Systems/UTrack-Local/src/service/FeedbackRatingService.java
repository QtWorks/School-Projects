package service;

import static model.Columns.DISABLE_FK;
import static model.Columns.IDPOI;
import static model.Columns.MARKEDUSER;
import static model.Columns.MARKINGUSER;
import static model.Columns.RATING;
import static model.Columns.SCORE;
import static model.Columns.USER;

import java.sql.ResultSet;
import java.util.ArrayList;

import cs5530.Connector;
import model.FeedbackRatingRecord;
import model.FeedbackRecord;
import model.POIRecord;
import model.UserRecord;
import model.VisitRecord;

/*
 * MarkedUser,POI,Rating,MarkingUser
 */
public class FeedbackRatingService {
	
	Connector con;

	public FeedbackRatingService(Connector con) {
		this.con = con;
	}
	
	public ArrayList<FeedbackRatingRecord> getRatings(String sql){
		ResultSet rs = null;
		ArrayList<FeedbackRatingRecord> ratings = new ArrayList<FeedbackRatingRecord>();
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				FeedbackRatingRecord rating = new FeedbackRatingRecord();
				FeedbackRecord fr = new FeedbackRecord();
				
				UserRecord ur = new UserRecord();
				ur.setLogin(rs.getString(USER));
				fr.setUser(ur);
				
				POIRecord pr = new POIRecord();
				pr.setId(rs.getInt(IDPOI));
				fr.setPoi(pr);
				
				fr.setScore(rs.getInt(SCORE));
				
				rating.setFeedback(fr);
				
				UserRecord marking = new UserRecord();
				marking.setLogin(rs.getString(MARKINGUSER));
				rating.setMarkingUser(marking);
				
				rating.setRating(rs.getInt(RATING));
				
				ratings.add(rating);
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
		return ratings;
	}
	
	public void insertFeedbackRating(FeedbackRatingRecord fr) {
		String sql = "INSERT INTO `cs5530db53`.`FeedbackRating` " + 
			"(`" + MARKINGUSER + "`,`" + MARKEDUSER + "`,`" + IDPOI + "`,`" + RATING +"`) VALUES "
			+ "('" + fr.getMarkingUser().getLogin()  + "','"
			+ fr.getFeedback().getUser().getLogin() + "',"
			+ fr.getFeedback().getPoi().getId() + ","
			+ fr.getRating() + ");";
		try{
			int result = con.stmt.executeUpdate(DISABLE_FK);
			int result2 = con.stmt.executeUpdate(sql);
		}
		catch(Exception e){
			String message = e.getMessage();
			if(message.contains("Duplicate")){
				System.out.println("It looks like you have already rated this review");
				System.out.println("This rating cannot be changed or updated.");
			}
			else{
				System.out.println("Cannot execute the query");
			}
		}
	}
	
	public int getRating(String markedUser, String markingUser, int idpoi){
		String sql = "select * from FeedbackRating r, Feedback f where f.user = r.marked_user and f.idpoi = r.idpoi " +
				" and r.marked_user = '" + markedUser + "' and r.marking_user = '" + markingUser 
				+ "' and f.idpoi = " + idpoi + ";";
		ArrayList<FeedbackRatingRecord> ratings = getRatings(sql);
		if(ratings.size() != 1){
			return -1;
		}else{
			return ratings.get(0).getRating();
		}
	}
	
	
	public boolean hasRating(String markedUser, String markingUser, int idpoi){
		return getRating(markedUser, markingUser, idpoi) >= 0;
	}

}
