<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<jsp:include page="loginRedirect.jsp"/>
<%
String name = request.getParameter("name");
String country = request.getParameter("country");
String state = request.getParameter("state");
String city = request.getParameter("city");
String street = request.getParameter("street");
String number = request.getParameter("number");
String url = request.getParameter("url");
String phone = request.getParameter("phone");
String year = request.getParameter("year");
String hours = request.getParameter("hours");
String price = request.getParameter("price");
String category = request.getParameter("category");
String keywords = request.getParameter("keywords");

AddressRecord address = new AddressRecord();

address.setCountry(country);
address.setState(state);
address.setCity(city);
address.setStreet(street);
address.setNumber(Integer.parseInt(number));


session.setAttribute("currentAddres", address);
Boolean update = (Boolean) session.getAttribute("updatePoi");
POIRecord poi;
if(!update)
	poi = new POIRecord();
else
	poi = (POIRecord) session.getAttribute("currentPoi");

poi.setAddress(address);
poi.setName(name);
poi.setUrl(url);
poi.setPhone(phone);
poi.setYear_est(Integer.parseInt(year));
poi.setHours(hours);
poi.setPrice(Double.parseDouble(price));
poi.setCategory(category);
if(keywords != null && keywords.trim().length() > 0)
	poi.setKeywords(keywords);

session.setAttribute("currentPoi", poi);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm POI</title>
</head>
<body>
<jsp:include page="displayPoi.jsp"/>
<form method="post" action="confirmPoi.jsp">
<input type="submit" name="yes" value="Confirm"/>
<input type="submit" name="no" value="Cancel"/>
</form>
<jsp:include page="options.jsp"/>

</body>
</html>


