<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*" import="beans.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
int n = Integer.parseInt(request.getParameter("n"));
POIService poiService = (POIService) session.getAttribute("poiService");
ArrayList<ArrayList<POIRecord>> results = poiService.getTopByCategory(n,3);
session.setAttribute("categoricalResults", results);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Top <%=n%> Highest Rated Places</title>
</head>
<body>
These are the top <%=n%> highest rated places for each category
<jsp:include page="categoricalResults.jsp"/>
</body>
</html>