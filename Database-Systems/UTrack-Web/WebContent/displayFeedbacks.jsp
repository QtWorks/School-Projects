<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
ArrayList<FeedbackRecord> feedbacks = (ArrayList<FeedbackRecord>) session.getAttribute("feedbackResults");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reviews</title>
</head>
<body>
<table>


</table>
<%
for(FeedbackRecord feedback : feedbacks){
	
	%>
	<tr>
		<td>User:</td>
		<td><a href="userMenu.jsp?user=<%=feedback.getUser().getLogin()%>"><%=feedback.getUser().getLogin()%></a></td>
	</tr>
	<tr>
		<td>Score:</td>
		<td><%=feedback.getScore()%></td>
	</tr>
	<tr>
		<td>Additional Info:</td>
		<td><%=feedback.getText()%></td>
	</tr>
	<tr>
		<td><a href="markUseful.jsp?user=<%=feedback.getUser().getLogin()%>&poi=<%=feedback.getPoi().getId()%>&rating=0"> Not Useful </a></td>
		<td><a href="markUseful.jsp?user=<%=feedback.getUser().getLogin()%>&poi=<%=feedback.getPoi().getId()%>&rating=1"> Useful </a></td>
		<td><a href="markUseful.jsp?user=<%=feedback.getUser().getLogin()%>&poi=<%=feedback.getPoi().getId()%>&rating=2"> Very Useful </a></td>
	</tr>
	<%
}
	
%>
</body>
</html>