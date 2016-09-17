<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
ArrayList<Object[]> visitCart = (ArrayList<Object[]>) session.getAttribute("visitCart");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm Visits</title>
</head>
<body>
<%
if(visitCart.size() == 0){
	%>
	You have no visits to confirm
	<%
}else{
	for(Object[] item : visitCart){
		VisitRecord visit = (VisitRecord) item[0];
		FeedbackRecord feedback = (FeedbackRecord) item[1];
		%>
		<table>
			<tr>
				<td>Place:</td>
				<td><%=visit.getPoi().getName()%></td>
			</tr>
			<tr>
				<td>Date of Visit:</td>
				<td><%=visit.getDate()%></td>
			</tr>
			<tr>
				<td>Amount Spent:</td>
				<td><%=visit.getSpent()%></td>
			</tr>
			<tr>
				<td>Number In Party:</td>
				<td><%=visit.getParty()%></td>
			</tr>
		<%
		if(feedback != null){
			%>
			<tr>
				<td>Score:</td>
				<td><%=feedback.getScore()%></td>
			</tr>
			<tr>
				<td>Additional Info:</td>
				<td><%=feedback.getText()%></td>
			</tr>
			<%
		}
		%>
			<tr>
				<td>
					<a href="visitConfirmed.jsp?date=<%=visit.getDate()%>&poi=<%=visit.getPoi().getId()%>">Confirm</a>
				</td>
				<td>
					<a href="visitDeleted.jsp?date=<%=visit.getDate()%>&poi=<%=visit.getPoi().getId()%>">Delete</a>
				</td>
			</tr>
		</table>	
		</form>
		
		<%
	}
}
%>
<jsp:include page="options.jsp"/>
</body>
</html>