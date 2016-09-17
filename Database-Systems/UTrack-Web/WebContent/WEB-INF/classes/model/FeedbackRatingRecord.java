package model;

public class FeedbackRatingRecord {
	
	UserRecord markingUser;
	FeedbackRecord feedback;
	int rating;

	
	public UserRecord getMarkingUser() {
		return markingUser;
	}

	public void setMarkingUser(UserRecord markingUser) {
		this.markingUser = markingUser;
	}

	public FeedbackRecord getFeedback() {
		return feedback;
	}

	public void setFeedback(FeedbackRecord feedback) {
		this.feedback = feedback;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public FeedbackRatingRecord() {

	}

}
