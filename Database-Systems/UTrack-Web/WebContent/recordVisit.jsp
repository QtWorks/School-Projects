<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<head>
<script LANGUAGE="javascript">
function insertVisit(form_obj){
	
}
</script> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
VisitService visitService = (VisitService) session.getAttribute("visitService");
POIRecord poi = (POIRecord) session.getAttribute("currentPoi");
UserRecord user = (UserRecord) session.getAttribute("user");
FeedbackService feedbackService = (FeedbackService) session.getAttribute("feedbackService");
%>
<title>Record Visit</title>
</head>
<body>
<form method="post" action="insertVisit.jsp">
	<table>
		<tr>
			<td>Date of Visit:</td>
			<td><input type="text" name="date" value=""></td>
		</tr>
		<tr>
			<td>Amount Spent:</td>
			<td><input type="text" name="spent" value=""></td>
		</tr>
		<tr>
			<td>Number In Party:</td>
			<td><input type="text" name="party" value=""></td>
		</tr>
		<%
			if(feedbackService.getFeedbackRecords(user, poi).size() > 0){
				//User has given feedback for this place
				%>
				</table>
				*You have already given feedback for this location

				<%
			}else{
				%>
				<tr>
					<td>Rate Your Experience (1-10):</td>
					<td><input type="text" name="score" value=""></td>
				</tr>
				<tr>
					<td>Additional Info:</td>
					<td><textarea name="shortText" cols="40" rows="5"></textarea>
				</tr>
			</table>
				
				<%
			}
	%>
  <br><br><input type="submit" value="Submit">
</form>
<jsp:include page="options.jsp"/>
</body>
</html>