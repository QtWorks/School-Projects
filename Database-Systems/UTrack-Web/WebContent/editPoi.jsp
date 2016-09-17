<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit POI</title>
</head>
<body>
<%
POIRecord poi = (POIRecord) session.getAttribute("currentPoi"); 
session.setAttribute("updatePoi", true);
%>
<form method="post" action="createPoi.jsp">
	<table>
		<tr>
			<td>Name:</td>
			<td><input type="text" name="name" value="<%=poi.getName()%>"></td>
		</tr>
		<tr>
			<td>Country:</td>
			<td><input type="text" name="country" value="<%=poi.getAddress().getCountry()%>"></td>
		</tr>
		<tr>
			<td>State:</td>
			<td><input type="text" name="state" value="<%=poi.getAddress().getState()%>"></td>
		</tr>
		<tr>
			<td>City:</td>
			<td><input type="text" name="city" value="<%=poi.getAddress().getState()%>"></td>
		</tr>
		<tr>
			<td>Street:</td>
			<td><input type="text" name="street" value="<%=poi.getAddress().getStreet()%>"></td>
		</tr>
		<tr>
			<td>Number:</td>
			<td><input type="text" name="number" value="<%=poi.getAddress().getNumber()%>"></td>
		</tr>
		<tr>
			<td>URL:</td>
			<td><input type="text" name="url" value="<%=poi.getUrl()%>"></td>
		</tr>
		<tr>
			<td>Phone Number:</td>
			<td><input type="text" name="phone" value="<%=poi.getPhone()%>"></td>
		</tr>
		<tr>
			<td>Year Established:</td>
			<td><input type="text" name="year" value="<%=poi.getYear_est()%>"></td>
		</tr>
		<tr>
			<td>Hours:</td>
			<td><input type="text" name="hours" value="<%=poi.getHours()%>"></td>
		</tr>
		<tr>
			<td>Price:</td>
			<td><input type="text" name="price" value="<%=poi.getPrice()%>"></td>
		</tr>
		<tr>
			<td>Category:</td>
			<td><input type="text" name="category" value="<%=poi.getCategory()%>"></td>
		</tr>
		<tr>
			<td>Keywords:</td>
			<td><input type="text" name="keywords" value="<%=poi.getKeywordsString()%>">(Example: English/hello, Spanish/hola)</td>
		</tr>
		<tr>
			<td><input type="submit" value="Submit Changes"/>
		</tr>
	</table>
</form>



</form>
<jsp:include page="options.jsp"/>
</body>
</html>