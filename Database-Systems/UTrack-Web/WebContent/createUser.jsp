<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs5530.*" import="model.*" import="service.*" import="exceptions.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%

String login = request.getParameter("login");
String password = request.getParameter("password");
String name = request.getParameter("name");
String country = request.getParameter("country");
String state = request.getParameter("state");
String city = request.getParameter("city");
String street = request.getParameter("street");
String number = request.getParameter("number");
String phone = request.getParameter("phone");
String admin = request.getParameter("admin");

boolean success = true;
String message = "";

if(login.length() == 0 || password.length() == 0 || name.length() == 0 || 
country.length() == 0 || state.length() == 0 || city.length() == 0 || 
street.length() == 0 || number.length() == 0 || phone.length() == 0){
	message += "Did not enter required info. Please try again";
	success = false;
}

UserService userService = (UserService) session.getAttribute("userService");
AddressService addressService = (AddressService) session.getAttribute("addressService");


UserRecord user = new UserRecord();
user.setLogin(login);
user.setPassword(password);
user.setName(name);
user.setPhone(phone);
AddressRecord address = new AddressRecord();

if(!userService.isAvailableLogin(login)){
	message += login + " is already taken. Choose another login\n";
	success = false;
}else{
	user.setLogin(login);
}
if(success){
	address.setCountry(country);
	address.setState(state);
	address.setCity(city);
	address.setStreet(street);
	address.setNumber(Integer.parseInt(number));

	user.setAddress(address);
}


if(admin != null && admin.trim().length() > 0){
	if(!admin.equals("databases12")){
		message += "Incorrect admin password. Please try again\n";
		success = false;
	}else{
		user.setAdmin(true);
	}
}else{
	user.setAdmin(false);
}

if(success){
	addressService.insertAddress(address);
	userService.insertUser(user);
	out.println("User " + login + " added successfully");
	session.setAttribute("user", user);
	%>
	<a href="home.jsp">Go to dashboard</a>
	<%
}else{
	out.println(message);
	
	%>
	<jsp:include page="register.jsp"/>
	<%
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register User</title>
</head>
<body>
<%
if(success){
	%>
	<jsp:include page="options.jsp"/>
	<%
}
%>

</body>
</html>