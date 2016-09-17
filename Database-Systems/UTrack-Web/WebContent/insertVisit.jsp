<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*" import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
VisitService visitService = (VisitService) session.getAttribute("visitService");
POIRecord poi = (POIRecord) session.getAttribute("currentPoi");
UserRecord user = (UserRecord) session.getAttribute("user");
FeedbackService feedbackService = (FeedbackService) session.getAttribute("feedbackService");
UserService userService = (UserService) session.getAttribute("userService");
%>
<title>Insert title here</title>
</head>
<body>
<%
String date = request.getParameter("date");
String spent = request.getParameter("spent");
String party = request.getParameter("party");
String score = request.getParameter("score");
String text = request.getParameter("shortText");

VisitRecord visit = new VisitRecord();

visit.setPoi(poi);
visit.setUser(user);

SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
java.sql.Date sqlDate = new java.sql.Date(format.parse(date).getTime());
visit.setDate(sqlDate);

double dSpent = Double.parseDouble(spent);
visit.setSpent(dSpent);

int iParty = Integer.parseInt(party);
visit.setParty(iParty);
ArrayList<Object[]> visitCart = (ArrayList<Object[]>) session.getAttribute("visitCart");
Object[] tuple = new Object[2];
tuple[0] = visit;
POIService poiService = (POIService) session.getAttribute("poiService");
ArrayList<POIRecord> suggested = poiService.getSuggestedPOIs(visit);
session.setAttribute("poiResults", suggested);


if(score != null){
	FeedbackRecord feedback = new FeedbackRecord();
	feedback.setPoi(poi);
	feedback.setUser(user);

	int iScore = Integer.parseInt(score);

	feedback.setScore(iScore);
	
	if(text != null && text.length() > 0){
		feedback.setText(text);
	}
	tuple[1] = feedback;
}
visitCart.add(tuple);
if(suggested.size() > 0){
	%>
	Here are some places you should visit:<br>
	<jsp:include page="poiResults.jsp"/>
	<%
}else{
	out.println("No suggested places. This must be a new place!");
	%>
	<jsp:include page="options.jsp"/>
	<%
}
%>
	

</body>
</html>