<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="loginRedirect.jsp"/>
<html>
<%
String markedUser = request.getParameter("markedUser");
UserService userService = (UserService) session.getAttribute("userService");
UserRecord marked = userService.getUserByLogin(markedUser);
TrustedService trustedService = (TrustedService) session.getAttribute("trustedService");
UserRecord user = (UserRecord) session.getAttribute("user");
trustedService.markUserTrustedBy(marked, user, true);
out.println("Successfully marked " + markedUser + " as trusted");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mark <%=markedUser%> Trusted</title>
</head>
<body>
<jsp:include page="options.jsp"/>
</body>
</html>