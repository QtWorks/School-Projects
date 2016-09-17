<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*" import="beans.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="loginRedirect.jsp"/>
<head>
<script LANGUAGE="javascript">

function editPoiRedirect(form_obj){
	
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Results</title>
</head>
<body>
<% 
ArrayList<POIRecord> results = (ArrayList<POIRecord>) session.getAttribute("poiResults"); 
UserRecord user = (UserRecord) session.getAttribute("user");
%>
<%
for(int i = 0; i < results.size(); i++){
	String index = (i+1) + ". ";
	POIRecord poi = results.get(i);
	int id = poi.getId();
	%>
	<%=index %><a href="poiMenu.jsp?idpoi=<%=poi.getId()%>"><%=poi.getName()%></a><br>
	<%
}

%>
<br>
<jsp:include page="options.jsp"/>
</body>
</html>