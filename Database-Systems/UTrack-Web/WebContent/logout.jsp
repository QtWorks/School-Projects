<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*" %>
<%
ArrayList<Object[]> visitCart = (ArrayList<Object[]>) session.getAttribute("visitCart");
if(visitCart.size() == 0){
	session.setAttribute("user", null);
	response.sendRedirect("index.jsp");
}else{
	%>
	You still have unconfirmed visits in your cart<br>
	Would you like to <a href="confirmVisits.jsp">confirm your visits </a> or <a href="clearVisits.jsp">clear your visits and log out</a>?
	<%
}


%>