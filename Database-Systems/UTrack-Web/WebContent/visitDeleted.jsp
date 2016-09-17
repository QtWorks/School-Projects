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
VisitRecord visit = (VisitRecord) visitFeedback[0];
%>
Your visit to <%=visit.getPoi().getName()%> on <%=visit.getDate()%> was deleted successfully <br>
<jsp:include page="confirmVisits.jsp"/>
