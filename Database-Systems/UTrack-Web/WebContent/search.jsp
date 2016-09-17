<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search POI</title>
</head>
<body>
<form method="post" action="searchResults.jsp">
	<table>
		<tr>
			<td>Price Range (low-high):</td>
			<td><input type="text" name="range" value=""></td>
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
			<td>Keywords:</td>
			<td><input type="text" name="keywords" value=""></td>
		</tr>
		<tr>
			<td>Category:</td>
			<td><input type="text" name="category" value=""></td>
		</tr>
		<tr>
			<td>Order By:</td>
			<td>
				<select name="orderBy">
					<option value="1">Price</option>
					<option value="2">Average Feedback Score</option>
					<option value="3">Average Feedback Score of Trusted Users</option>
					<option value="4">No Order</option>				
				</select>
			</td>
		</tr>
	</table>
	<input type="submit" value="Search"/>
</form>
<jsp:include page="options.jsp"/>
</body>
</html>