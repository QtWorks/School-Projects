<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
UserService userService = (UserService) session.getAttribute("userService");
String login = request.getParameter("user");
UserRecord user = userService.getUserByLogin(login);
POIService poiService = (POIService) session.getAttribute("poiService");
session.setAttribute("poiResults", poiService.getFavoritePlaces(user));
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=login%>'s Favorite Places</title>
</head>
<body>
<%=login%>'s Favorite Places<br><br>
<jsp:include page="poiResults.jsp"/>
</body>
</html>