<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<%
UserRecord user = (UserRecord) session.getAttribute("user");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dashboard</title>
<h1>User Options</h1>
</head>
<body>
<table>
  <tr>
    <td><a href="search.jsp">Search</a> points of interest by price, location, category and/or keyword</td>
  </tr>
  <tr>
    <td><a href=allPOIs.jsp>View</a> all points of interest</td>
  </tr>
  <tr>
    <td>View places <a href=userVisited.jsp?user=<%=user.getLogin() %>>you have visited</a></td>
  </tr>
  <tr>
    <td>View <a href="userFavorites.jsp?user=<%=user.getLogin()%>">your favorite places</a></td>
  </tr>
  <tr>
    <td>
    <form action="mostPopular.jsp">
		View top <input size="1"  type="text" name="n" value="5"/> most popular points of interest for each category <input type="submit" value="Go"/>
	</form>
	</td>
  </tr>
  <tr>
    <td><form action="mostExpensive.jsp">
		View top <input size="1" type="text" name="n" value="5"/> most expensive points of interest for each category <input type="submit" value="Go"/>
	</form></td>
  </tr>
  <tr>
    <td><form action="highestRated.jsp">
		View top <input size="1" type="text" name="n" value="5"/> highest rated points of interest for each category <input type="submit" value="Go"/>
	</form></td>
  </tr>
  <tr>
    <td><a href=degreeFinder.jsp>Degree of Separation Finder</a></td>
  </tr>
</table>
<jsp:include page="options.jsp"/>
</body>
</html>