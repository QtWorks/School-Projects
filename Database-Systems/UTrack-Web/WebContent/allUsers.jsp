<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>All Users</title>
</head>
<body>
<%
Connector con = (Connector) session.getAttribute("connector");
UserService userService = new UserService(con);
ArrayList<UserRecord> users = userService.getAllUsers();
session.setAttribute("userResults", users);
%>
<jsp:include page="userResults.jsp"/>
</body>
</html>