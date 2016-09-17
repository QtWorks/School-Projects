<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
ArrayList<FeedbackRecord> results = (ArrayList<FeedbackRecord>) session.getAttribute("feedbackResults");
FeedbackRatingService ratingService = (FeedbackRatingService) session.getAttribute("ratingService");
UserRecord user = (UserRecord) session.getAttribute("user");
%>
<title>Reviews</title>
</head>
<body>
<%
for(FeedbackRecord feedback : results){
	%>
	<table>
	<tr>
		<td>
			Place:
		</td>
		<td>
			<a href="poiMenu.jsp?idpoi=<%=feedback.getPoi().getId()%>"><%=feedback.getPoi().getName()%></a>
		</td>
	</tr>
	<tr>
		<td>
			User:
		</td>
		<td>
			<a href="userMenu.jsp?user=<%=feedback.getUser().getLogin()%>"><%=feedback.getUser().getLogin()%></a>
		</td>
	</tr>
	<tr>
		<td>
			Score:
		</td>
		<td>
			<%=feedback.getScore()%>
		</td>
	</tr>
	<tr>
		<td>
			Additional Info:
		</td>
		<td>
			<%=feedback.getText()%>
		</td>
	</tr>
	<%
	String markedUser = feedback.getUser().getLogin();
	String markingUser = user.getLogin();
	if(ratingService.hasRating(markedUser,markingUser, feedback.getPoi().getId())){
		%>
		<tr>
			<td>Rate:</td>
			<td>You have already rated this feedback</td>
		</tr>
		<%
	}else{
		if(!feedback.getUser().getLogin().equals(user.getLogin())){
			%>
			<tr>
				<td>Rate:</td>
				<td>
					<a href="markUseful.jsp?markedUser=<%=feedback.getUser().getLogin()%>&rating=0&idpoi=<%=feedback.getPoi().getId()%>">Useless</a>
					<a href="markUseful.jsp?markedUser=<%=feedback.getUser().getLogin()%>&rating=1&idpoi=<%=feedback.getPoi().getId()%>"> Useful</a>
					<a href="markUseful.jsp?markedUser=<%=feedback.getUser().getLogin()%>&rating=2&idpoi=<%=feedback.getPoi().getId()%>"> Very Useful</a>
				</td>
			</tr>
			<%
		}else{
			%>
			<tr>
				<td>Rate:</td>
				<td>
					Cannot rate your own review
				</td>
			</tr>
			<%
		}
	}
	
	
	%>
	</table>
	<br><br>
	<%
}

%>


<jsp:include page="options.jsp"/>
</body>
</html>