<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
    
<jsp:include page="loginRedirect.jsp"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
POIRecord poi = (POIRecord) session.getAttribute("currentPoi");
%>

<table border="1" cellpadding="3">
  <tr>
    <th>Attribute</th>
    <th>Value</th>
  </tr>
  <tr>
    <td>Title:</td>
    <td><%=poi.getName()%></td>
  </tr>
  <tr>
    <td>Country:</td>
    <td><%=poi.getAddress().getCountry()%></td>
  </tr>
  <tr>
    <td>City:</td>
    <td><%=poi.getAddress().getCity()%></td>
  </tr>
  <tr>
    <td>State:</td>
    <td><%=poi.getAddress().getState()%></td>
  </tr>
  <tr>
    <td>Street Address:</td>
    <td><%=poi.getAddress().getNumber() + " " + poi.getAddress().getStreet()%></td>
  </tr>
  <tr>
    <td>URL:</td>
    <td><%=poi.getUrl()%></td>
  </tr>
  <tr>
    <td>Year Established:</td>
    <td><%=poi.getYear_est()%></td>
  </tr>
  <tr>
    <td>Hours:</td>
    <td><%=poi.getHours()%></td>
  </tr>
  <tr>
    <td>Price:</td>
    <td><%=poi.getPrice()%></td>
  </tr>
  <tr>
    <td>Category:</td>
    <td><%=poi.getCategory()%></td>
  </tr>
  <tr>
    <td>Keywords:</td>
    <td><%=poi.getKeywordsString()%></td>
  </tr>
</table>

</body>
</html>