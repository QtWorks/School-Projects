<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*" import="beans.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body><br><br>
<%
ArrayList<ArrayList<POIRecord>> results = (ArrayList<ArrayList<POIRecord>>) session.getAttribute("categoricalResults");
for(ArrayList<POIRecord> category : results){
	%>
	<%=category.get(0).getCategory().toUpperCase()%><br>
	<%
	for(POIRecord poi : category){
	%>
		<a href="poiMenu.jsp?idpoi=<%=poi.getId()%>"><%=poi.getName()%></a><br>
	<%
	}
	%>
	<br><br>
	<%
}
%>
<jsp:include page="options.jsp"/>
</body>
</html>