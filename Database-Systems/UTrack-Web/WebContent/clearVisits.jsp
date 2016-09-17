<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*" %>
<%
ArrayList<Object[]> visitCart = (ArrayList<Object[]>) session.getAttribute("visitCart");
visitCart.clear();
%>
<jsp:include page="logout.jsp"/>