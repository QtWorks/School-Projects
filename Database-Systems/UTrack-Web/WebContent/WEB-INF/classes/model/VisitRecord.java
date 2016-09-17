package model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class VisitRecord {
	
	int idvisit;
	POIRecord poi;
	UserRecord user;
	Date date;
	double spent;
	int party;

	
	public int getIdvisit() {
		return idvisit;
	}

	public void setIdvisit(int idvisit) {
		this.idvisit = idvisit;
	}

	public POIRecord getPoi() {
		return poi;
	}

	public void setPoi(POIRecord poi) {
		this.poi = poi;
	}

	public UserRecord getUser() {
		return user;
	}

	public void setUser(UserRecord user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}
	
	public String sqlDate(){
		SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd");
		return to.format(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getSpent() {
		return spent;
	}

	public void setSpent(double spent) {
		this.spent = spent;
	}

	public int getParty() {
		return party;
	}

	public void setParty(int party) {
		this.party = party;
	}

	public VisitRecord() {

	}
	
	public String toStringFull(){
		return "{" 
				+ "\n\tPlace: " + poi.getName()
				+ "\n\tDate: " + date
				+ "\n\tSpent: $" + spent
				+ "\n\tNumber in Party: " + party
			+"\n}";	
	}

	public VisitRecord(UserRecord currentUser, POIRecord poi) {
		this.user = currentUser;
		this.poi = poi;
	}
}
