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
UserRecord user = userService.getUserByLogin(request.getParameter("user"));
UserRecord currentUser = (UserRecord) session.getAttribute("user");
%>
<title><%=user.getLogin()%></title>
<h1><%=user.getLogin()%></h1>
</head>
<body>
<table>
	<tr>
		<td>
		<a href="userFeedbacks.jsp?user=<%=user.getLogin()%>">Reviews</a> <%=user.getLogin()%> has made
		</td>
	</tr>
	<tr>
		<td>
		<a href="userVisited.jsp?user=<%=user.getLogin()%>">Places</a> <%=user.getLogin()%> has visited
		</td>
	</tr>
	<tr>
		<td>
		<%=user.getLogin()%>'s <a href="userFavorites.jsp?user=<%=user.getLogin()%>">favorite places</a>
		</td>
	</tr>
	<tr>
		<td>
		<a href="displayDegrees.jsp?login1=<%=user.getLogin()%>&login2=<%=currentUser.getLogin()%>">Your degree of separation </a> with <%=user.getLogin()%>
		</td>
	</tr>
	<tr>
		<td>
		Mark <%=user.getLogin()%> as <a href="markTrusted.jsp?markedUser=<%=user.getLogin()%>">trusted </a> or 
		<a href="markNotTrusted.jsp?markedUser=<%=user.getLogin()%>">not trusted </a>
		</td>
	</tr>
	<tr>
		<td>
		<form action="nDegreesAway.jsp?user=<%=user.getLogin()%>">
			Get users <input type="text"  size="1" value="1" name="degrees"/> degree(s) away from <%=user.getLogin()%>
			<input type="submit" value="Go"/>
			<input type="hidden" value="<%=user.getLogin()%>" name="user"/>
		</form>
		</td>
	</tr>
</table>
<jsp:include page="options.jsp"/>

</body>
</html>