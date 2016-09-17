<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*" %>
<%
ArrayList<Object[]> visitCart = (ArrayList<Object[]>) session.getAttribute("visitCart");
int poi = Integer.parseInt(request.getParameter("poi"));
String date = request.getParameter("date");
Object[] visitFeedback = null;
for(Object[] item : visitCart){
	VisitRecord visit = (VisitRecord) item[0];
	if(visit.getPoi().getId() == poi && visit.getDate().toString().equals(date)){
		visitFeedback = item;
	}
}
visitCart.remove(visitFeedback);
if(visitFeedback != null){
	VisitRecord visit = (VisitRecord) visitFeedback[0];
	FeedbackRecord feedback = (FeedbackRecord) visitFeedback[1];
	VisitService visitService = (VisitService) session.getAttribute("visitService");
	visitService.insertVisit(visit);
	if(feedback != null){
		FeedbackService feedbackService = (FeedbackService) session.getAttribute("feedbackService");
		feedbackService.insertFeedback(feedback);
		if(feedback.getText() != null && feedback.getText().length() > 0){
			ShortTextService textService = (ShortTextService) session.getAttribute("textService");
			ShortTextRecord text = new ShortTextRecord();
			text.setText(feedback.getText());
			text.setVisit(visit);
			textService.insertText(text);
		}
	}
	%>
	Your visit to <%=visit.getPoi().getName()%> on <%=visit.getDate()%> was recorded successfully <br>
	<jsp:include page="confirmVisits.jsp"/>
	<%
}
%>
