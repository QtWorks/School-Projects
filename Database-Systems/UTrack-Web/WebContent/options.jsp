<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Options</title>
</head>
<body>
<br>
<a href="home.jsp">Dashboard</a>
<%
UserRecord user = (UserRecord) session.getAttribute("user");
if(user != null){
	%>
	<a href="userMenu.jsp?user=<%=user.getLogin()%>">Your Profile</a>
	<%
}else{
	%>
	Not Logged In
	<%
}

ArrayList<Object[]> visitCart = (ArrayList<Object[]>) session.getAttribute("visitCart");
%>
<a href="confirmVisits.jsp">Confirm Visits(<%=visitCart.size()%>)</a>
<a href="logout.jsp">Logout</a>
</body>
</html>