<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All POIs</title>
</head>
<body>
<%
Connector con = (Connector) session.getAttribute("connector");
POIService poiService = new POIService(con);
ArrayList<POIRecord> pois = poiService.getAllPois();
session.setAttribute("poiResults", pois);
%>
<jsp:include page="poiResults.jsp"/>
</body>
</html>