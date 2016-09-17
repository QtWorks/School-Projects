<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add POI</title>
</head>
<body>
<%
session.setAttribute("updatePoi", false);
%>
<form method="post" action="createPoi.jsp">
	<table>
		<tr>
			<td>Name:</td>
			<td><input type="text" name="name" value=""></td>
		</tr>
		<tr>
			<td>Country:</td>
			<td><input type="text" name="country" value=""></td>
		</tr>
		<tr>
			<td>State:</td>
			<td><input type="text" name="state" value=""></td>
		</tr>
		<tr>
			<td>City:</td>
			<td><input type="text" name="city" value=""></td>
		</tr>
		<tr>
			<td>Street:</td>
			<td><input type="text" name="street" value=""></td>
		</tr>
		<tr>
			<td>Number:</td>
			<td><input type="text" name="number" value=""></td>
		</tr>
		<tr>
			<td>URL:</td>
			<td><input type="text" name="url" value=""></td>
		</tr>
		<tr>
			<td>Phone Number:</td>
			<td><input type="text" name="phone" value=""></td>
		</tr>
		<tr>
			<td>Year Established:</td>
			<td><input type="text" name="year" value=""></td>
		</tr>
		<tr>
			<td>Hours:</td>
			<td><input type="text" name="hours" value=""></td>
		</tr>
		<tr>
			<td>Price:</td>
			<td><input type="text" name="price" value=""></td>
		</tr>
		<tr>
			<td>Category:</td>
			<td><input type="text" name="category" value=""></td>
		</tr>
		<tr>
			<td>Keywords:</td>
			<td><input type="text" name="keywords" value="">(Example: English/hello, Spanish/hola)</td>
		</tr>
		<tr>
			<td><input type="submit" value="Add"/>
		</tr>
	</table>
</form>
<jsp:include page="options.jsp"/>
</body>
</html>