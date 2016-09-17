<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
    <jsp:include page="loginRedirect.jsp"/>
    
<%
SearchModel search = new SearchModel();

String range = request.getParameter("range");
if(range != null){
	search.setPriceRange(range);
}else{
	search.setPriceRange("");
}

String country = request.getParameter("country");
search.setCountry(country);

String state = request.getParameter("state");
search.setState(state);
String city = request.getParameter("city");
search.setCity(city);
String keywords = request.getParameter("keywords");
search.setKeywords(keywords);
String category = request.getParameter("category");
search.setCategory(category);
String orderBy = request.getParameter("orderBy");
search.setSortedBy(Integer.parseInt(orderBy)); 

POIService poiService = (POIService) session.getAttribute("poiService");
ArrayList<POIRecord> results = poiService.getSearchResults(search);
session.setAttribute("poiResults", results);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
</head>
<body>
<jsp:include page="poiResults.jsp"/>
</body>
</html>
