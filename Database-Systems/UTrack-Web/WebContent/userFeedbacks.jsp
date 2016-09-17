<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
    <jsp:include page="loginRedirect.jsp"/>
<%
String login = request.getParameter("user");
UserService userService = (UserService) session.getAttribute("userService");
UserRecord user = userService.getUserByLogin(login);
FeedbackService feedbackService = (FeedbackService) session.getAttribute("feedbackService");
ArrayList<FeedbackRecord> feedbacks = feedbackService.getFeedbackByUser(user);
session.setAttribute("feedbackResults", feedbacks);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<h1>Reviews by <%=user.getLogin()%></h1>
<jsp:include page="feedbackResults.jsp"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
</html>