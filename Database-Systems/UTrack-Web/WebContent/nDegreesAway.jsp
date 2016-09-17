<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
    <jsp:include page="loginRedirect.jsp"/>
    
<%
UserService userService = (UserService) session.getAttribute("userService");

String login = request.getParameter("user");
UserRecord user = userService.getUserByLogin(login);
int n = Integer.parseInt(request.getParameter("degrees"));
ArrayList<UserRecord> users = userService.getNDegreesAway(user, n);
if(users.size() == 0){
	out.println("No users " + n + " degrees away from " + login);
}else{
	session.setAttribute("userResults", users);
	out.println("Users " + n + " degrees away from " + login);
	%>
	<br>
	<jsp:include page="userResults.jsp"/>
	<%
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=n%> degrees away</title>
</head>
<body>
</body>
</html>