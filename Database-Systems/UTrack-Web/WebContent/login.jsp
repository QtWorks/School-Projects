<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs5530.*" import="model.*" import="service.*" import="exceptions.*"%>
<%

	UserService userService = (UserService) session.getAttribute("userService");
	
	String login = request.getParameter("username");
	String password = request.getParameter("pword");
	
	UserRecord user = userService.getUserByLogin(login);
	
	if(user != null && user.getPassword().equals(password)){
		session.setAttribute("login", login);
		session.setAttribute("user",user);
		if(user.isAdmin())
			response.sendRedirect("adminhome.jsp");
		else
			response.sendRedirect("userhome.jsp");
	}else{
		out.println("Invalid combination <a href='index.jsp'>Try Again</a>");
	}
%>