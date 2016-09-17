<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs5530.*" import="model.*" import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register New User</title>
</head>
<body>
<form method="post" action="createUser.jsp">
	<table>
		<tr>
			<td>Login:</td>
			<td><input type="text" name="login" value=""></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type="text" name="password" value=""></td>
		</tr>
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
			<td>Street Name:</td>
			<td><input type="text" name="street" value=""></td>
		</tr>
		<tr>
			<td>Street Number:</td>
			<td><input type="text" name="number" value="">
		</tr>
		<tr>
			<td>Phone:</td>
			<td><input type="text" name="phone" value=""></td>
		</tr>
		<tr>
			<td>Admin:</td>
			<td><input type="text" name="admin" value="">(If you are an admin, Enter admin password. Otherwise, leave blank)</td>
		</tr>
		<tr>
			<td><input type="submit" name="admin" value="Submit"></td>
		</tr>
	</table>
</form>
<jsp:include page="options.jsp"/>
</body>
</html>