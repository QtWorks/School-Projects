<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
UserService userService = (UserService) session.getAttribute("userService");
ArrayList<UserRecord> allUsers = userService.getAllUsers();
String login1 = request.getParameter("login1");
String login2 = request.getParameter("login2");
int degrees  = -1;
if(login1 != null && login2 != null && login1.length() > 0 && login2.length() > 0){
	if(login1.equals(login2)){
		degrees = 0;
	}else{
		degrees = userService.findDegreesOfSeparation(login1, login2);
	}
}
String message = "";
if(degrees < 0){
	message = "There is no connection between " + login1 + " and " + login2;
}else if(degrees == 0){
	message = "These are the same users";
}else if(degrees == 1){
	message = "There is 1 degree of separation between " + login1 + " and " + login2;
}else{
	message = "There are " + degrees + " degrees of separation between " + login1 + " and " + login2;
}
%>
<title>Degrees of Separation</title>
</head>
<body>
<%=message%>
<jsp:include page="options.jsp"/>
</body>
</html>