<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%


FeedbackRatingService ratingService = (FeedbackRatingService) session.getAttribute("ratingService");
FeedbackService feedbackService = (FeedbackService) session.getAttribute("feedbackService");
UserRecord user = (UserRecord) session.getAttribute("user");

int rating = Integer.parseInt(request.getParameter("rating"));

int idpoi = Integer.parseInt(request.getParameter("idpoi"));

String markedUser = request.getParameter("markedUser");
if(markedUser.equals(user.getLogin())){
	out.println("Cannot rate your own feedbacks");
}else{
	FeedbackRecord feedback = feedbackService.getFeedbackRecord(markedUser, idpoi);
	int prevRating = ratingService.getRating(markedUser, user.getLogin(), idpoi);

	if(prevRating > 0){
		//Already have rating
		out.println("You've already rated this review");
	}else{
		FeedbackRatingRecord feedbackRating = new FeedbackRatingRecord();
		feedbackRating.setFeedback(feedback);
		feedbackRating.setMarkingUser(user);
		feedbackRating.setRating(rating);
		ratingService.insertFeedbackRating(feedbackRating);
		if(rating == 0){
			out.println("Successfully marked this review as useless");
		}else if(rating == 1){
			out.println("Successfully marked this review as useful");
		}else if(rating == 2){
			out.println("Successfully marked this review as very useful");
		}
	}
}


%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rate Review</title>
</head>
<body>
<jsp:include page="options.jsp"/>
</body>
</html>