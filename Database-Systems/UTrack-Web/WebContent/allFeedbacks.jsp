<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
    <jsp:include page="loginRedirect.jsp"/>
    
<%
String param = request.getParameter("idpoi");
int idpoi = Integer.parseInt(param);
POIService poiService = (POIService) session.getAttribute("poiService");
POIRecord poi = poiService.getPOIbyId(idpoi);
FeedbackService feedbackService = (FeedbackService) session.getAttribute("feedbackService");
ArrayList<FeedbackRecord> results = feedbackService.getFeedbacksByPOI(poi);
session.setAttribute("feedbackResults", results);
response.sendRedirect("feedbackResults.jsp");
%>