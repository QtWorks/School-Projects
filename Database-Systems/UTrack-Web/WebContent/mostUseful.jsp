<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*" import="beans.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
int n = Integer.parseInt(request.getParameter("n"));
UserService userService = (UserService) session.getAttribute("userService");
ArrayList<UserRecord> results = userService.getTopUsers(n, 2);
session.setAttribute("userResults", results);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Top <%=n%> Useful Users</title>
</head>
<body>
These are the top <%=n%> most useful users
<jsp:include page="userResults.jsp"/>
</body>
</html>