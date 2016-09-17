<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
UserRecord user = (UserRecord) session.getAttribute("user");
POIService poiService = (POIService) session.getAttribute("poiService");
String param = request.getParameter("idpoi");
int id = Integer.parseInt(param);
POIRecord poi = poiService.getPOIbyId(id);
session.setAttribute("currentPoi", poi);
%>
<title><%=poi.getName()%></title>
<h1><%=poi.getName()%></h1>
</head>
<body>
<jsp:include page="displayPoi.jsp"/>
<table>
  <tr>
    <td><%
    if(user.isAdmin()){
    	%>
    	<a href="editPoi.jsp?idpoi=<%=id%>">Edit</a>
    	<%
    }else{
    	%>
    	Cannot Edit
    	<%
    }    
    %></td>
    <td>
      <a href="allFeedbacks.jsp?idpoi=<%=id%>">View All Reviews</a>  
    </td>
  </tr>
  <tr>
    <td>
      <a href="recordVisit.jsp?idpoi=<%=id%>">Record Visit</a>  
    </td>    
    <td>
      <a href="addFavorite.jsp?idpoi=<%=id%>">Mark as Favorite</a>  
    </td> 
  </tr>
  <tr>
    <td>
		<a href="usersVisited.jsp?idpoi=<%=id%>">Users that have been here</a>  
    </td>    
  </tr>
</table>
<br>
<form action="mostUsefulFeedbacks.jsp">
		View top <input size="1" type="text" name="n" value="5"/> most useful reviews
		<input type="hidden" name="idpoi" value="<%=id%>"/>
		<input type="submit" value="Go"/>
</form> 
<jsp:include page="options.jsp"/>
</body>
</html>