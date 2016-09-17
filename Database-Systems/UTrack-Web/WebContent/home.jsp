<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs5530.*" import="model.*" import="service.*" import="exceptions.*"%>
<jsp:include page="loginRedirect.jsp"/>
<%
	UserRecord user = (UserRecord) session.getAttribute("user");
	if(user == null)
		response.sendRedirect("index.jsp");
	else if(user.isAdmin())
		response.sendRedirect("adminhome.jsp");
	else
		response.sendRedirect("userhome.jsp");
%>