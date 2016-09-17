package service;

import static model.Columns.DISABLE_FK;
import static model.Columns.IDPOI;
import static model.Columns.SCORE;
import static model.Columns.USER;

import java.sql.ResultSet;
import java.util.ArrayList;

import static model.Columns.TEXT;

import cs5530.Connector;
import model.FeedbackRecord;
import model.POIRecord;
import model.ShortTextRecord;
import model.UserRecord;
import model.VisitRecord;

/*
 * User,POI,Text
 */
public class ShortTextService {
	
	Connector con;

	public ShortTextService(Connector con) {
		this.con = con;
	}
	
	public ArrayList<ShortTextRecord> getText(String sql){
		ResultSet rs = null;
		ArrayList<ShortTextRecord> texts = new ArrayList<ShortTextRecord>();
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				ShortTextRecord s = new ShortTextRecord();
				s.setText(rs.getString(TEXT));
				UserRecord user = new UserRecord();
				user.setLogin(rs.getString(USER));
				
				POIRecord poi = new POIRecord();
				poi.setId(rs.getInt(IDPOI));
				
				s.setVisit(new VisitRecord(user, poi));
				
				texts.add(s);
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
		return texts;
	}

	public void insertText(ShortTextRecord text) {
		String t = text.getText();
		int a = t.indexOf('\'');
		if(a > 0){
			t = t.substring(0, a) + "\'" + t.substring(a);
		}
		String sql = "INSERT INTO `cs5530db53`.`ShortText` " + 
				"(`" + IDPOI + "`,`" + USER + "`,`" + TEXT + "`)" + " VALUES "
				+ "(" + text.getVisit().getPoi().getId() + ",'" 
				+ text.getVisit().getUser().getLogin() + "','"
				+ t + "');";
		try{
			int result = con.stmt.executeUpdate(DISABLE_FK);
			int result2 = con.stmt.executeUpdate(sql);
		}
		catch(Exception e){
			System.out.println("cannot execute the query");
		}
	}

	public String getText(int idpoi, String login) {
		String sql = "Select * from ShortText where idpoi = " + idpoi + " and user like '" + login + "';";
		ArrayList<ShortTextRecord> list = getText(sql);
		if(list.size() > 0)
			return list.get(0).getText();
		return "";
	}

}
