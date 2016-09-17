<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" import="cs5530.*" import="model.*" 
    import="service.*" import="exceptions.*"%>
<jsp:include page="loginRedirect.jsp"/>
<%
AddressService addressService = (AddressService) session.getAttribute("addressService");
POIService poiService = (POIService) session.getAttribute("poiService");
POIRecord poi = (POIRecord) session.getAttribute("currentPoi");
AddressRecord address = poi.getAddress();

addressService.insertAddress(address);

poi.setAddress(address);
Boolean update = (Boolean) session.getAttribute("updatePoi");
if(update)
	poiService.updatePOI(poi);
else
	poiService.insertPOI(poi);
session.setAttribute("currentPoi", poi);
response.sendRedirect("poiMenu.jsp?idpoi=" + poi.getId());
%>


