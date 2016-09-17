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
%>
<title>Degrees of Separation</title>
</head>
<body>
<form method="get" action="displayDegrees.jsp">
	<table>
		<tr>
			<td>
				<select name="login1">
			    <%
			    for(UserRecord user : allUsers){
			    	%>
			    	<option value="<%=user.getLogin()%>"><%=user.getLogin()%></option>
			    	<%
			    }
			    %>
			    </select>
			</td>
			<td>
				<select name="login2">
			    <%
			    for(UserRecord user : allUsers){
			    	%>
			    	<option value="<%=user.getLogin()%>"><%=user.getLogin()%></option>
			    	<%
			    }
			    %>
			    </select>
			</td>
		</tr>
	</table>
  <br><br>
  <input type="submit" value="Find Degree of Separation">
</form>
<jsp:include page="options.jsp"/>
</body>
</html>