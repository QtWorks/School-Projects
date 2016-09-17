<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
int idpoi = Integer.parseInt(request.getParameter("idpoi"));
POIService poiService = (POIService) session.getAttribute("poiService");
POIRecord poi = poiService.getPOIbyId(idpoi);
UserRecord user = (UserRecord) session.getAttribute("user");
FavoriteService favoriteService = (FavoriteService) session.getAttribute("favoriteService");
try{
	favoriteService.insertFavorite(user, poi);
	out.println(poi.getName() + " successfully marked as your favorite");
	%><br><%
	out.println("Your favorite places:");
	session.setAttribute("poiResults", poiService.getFavoritePlaces(user));
	%>
	<br><jsp:include page="poiResults.jsp"/>
	<%
}catch(Exception e){
	out.println("There was a problem with marking " + poi.getName() + " as your favorite");
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Favorite</title>
</head>
<body>
</body>
</html>