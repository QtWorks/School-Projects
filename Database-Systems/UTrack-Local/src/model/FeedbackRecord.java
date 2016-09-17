package model;

public class FeedbackRecord {
	
	UserRecord user;
	POIRecord poi;
	int score;
	String text;	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public UserRecord getUser() {
		return user;
	}

	public void setUser(UserRecord user) {
		this.user = user;
	}

	public POIRecord getPoi() {
		return poi;
	}

	public void setPoi(POIRecord poi) {
		this.poi = poi;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public String toString(){
		return poi.getName() + "\t" + user.getLogin() + "\t" + score + "\t" + text;
	}

	public FeedbackRecord() {

	}

}
