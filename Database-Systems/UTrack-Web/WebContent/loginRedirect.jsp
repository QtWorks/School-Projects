<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
if(session == null || session.getAttribute("user") == null){
	response.sendRedirect("index.jsp");
}
%>