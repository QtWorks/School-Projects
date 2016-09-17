<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
    <jsp:include page="loginRedirect.jsp"/>
<%
int n = Integer.parseInt(request.getParameter("n"));
int idpoi = Integer.parseInt(request.getParameter("idpoi"));

FeedbackService feedbackService = (FeedbackService) session.getAttribute("feedbackService");
POIService poiService = (POIService) session.getAttribute("poiService");
POIRecord poi = poiService.getPOIbyId(idpoi);
ArrayList<FeedbackRecord> topFeedbacks = feedbackService.getTopFeedbacksByPOI(poi, n);
session.setAttribute("feedbackResults", topFeedbacks);
%>
<title>Top Feedbacks</title>
Top <%=n%> most useful feedbacks for <%=poi.getName()%><br>
<jsp:include page="feedbackResults.jsp"/>