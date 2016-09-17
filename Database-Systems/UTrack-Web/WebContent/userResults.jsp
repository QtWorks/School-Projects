<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Results</title>
</head>
<body>

<% 
ArrayList<UserRecord> results = (ArrayList<UserRecord>) session.getAttribute("userResults"); 
UserRecord user = (UserRecord) session.getAttribute("user");
%>
<form action="${pageContext.request.contextPath}/servlet" method="post">
<table>
<%
for(int i = 0; i < results.size(); i++){
	String index = (i+1) + ". ";
	%>
	<tr>
		<td><%=index %><a href="userMenu.jsp?user=<%=results.get(i).getLogin()%>"><%=results.get(i).getLogin()%></a></td>
		<%
		if(user.isAdmin()){
			%>
			<td>
			</td>
			<%
		}
		%>
	</tr>
	
	<%
}

%>



</table>
</form>
<jsp:include page="options.jsp"/>

</body>
</html>