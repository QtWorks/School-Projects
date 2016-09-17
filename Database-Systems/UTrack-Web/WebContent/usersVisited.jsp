<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*" %>
<%
int id = Integer.parseInt(request.getParameter("idpoi"));
POIService poiService = (POIService) session.getAttribute("poiService");
POIRecord poi = poiService.getPOIbyId(id);
UserService userService = (UserService) session.getAttribute("userService");
ArrayList<UserRecord> users = userService.getUsersByPOI(poi);
session.setAttribute("userResults", users);
%>
<jsp:include page="userResults.jsp"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Visited <%=poi.getName()%></title>
</head>
<body>
</body>
</html>