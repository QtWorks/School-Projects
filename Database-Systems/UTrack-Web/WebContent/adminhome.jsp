<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs5530.*" import="model.*" import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dashboard</title>
<h1>Admin Options</h1>
</head>
<body>
<%
String login = (String) session.getAttribute("login");
UserService us = (UserService) session.getAttribute("userService");
UserRecord user = us.getUserByLogin(login);
if(user == null || !user.isAdmin())
	response.sendRedirect("userhome.jsp");
%>

<table>
  <tr>
    <td><a href="addPoi.jsp">Add</a> a point of interest</td>
  </tr>
  <tr>
    <td><a href=allPOIs.jsp>View/Edit</a> all points of interest</td>
  </tr>
  <tr>
    <td><a href=allUsers.jsp>View</a> all users</td>
  </tr>
  <tr>
    <td><a href=awardStats.jsp>Statistics</a> for awards</td>
  </tr>
</table>

<jsp:include page="userhome.jsp"/>
</body>
</html>