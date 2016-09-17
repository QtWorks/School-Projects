<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="cs5530.*" import="model.*" 
    import="java.util.*" import="service.*" import="exceptions.*"%>

<%

Driver driver = new Driver();
session.setAttribute("driver", driver);

Connector con = new Connector();
session.setAttribute("connector", con);

UserService userService = new UserService(con);
session.setAttribute("userService",userService);

POIService poiService = new POIService(con);
session.setAttribute("poiService",poiService);

AddressService addressService = new AddressService(con);
session.setAttribute("addressService",addressService);

FavoriteService favoriteService = new FavoriteService(con);
session.setAttribute("favoriteService",favoriteService);

FeedbackRatingService ratingService = new FeedbackRatingService(con);
session.setAttribute("ratingService",ratingService);

FeedbackService feedbackService = new FeedbackService(con);
session.setAttribute("feedbackService",feedbackService);

KeywordService keywordService = new KeywordService(con);
session.setAttribute("keywordService",keywordService);

ShortTextService textService = new ShortTextService(con);
session.setAttribute("textService",textService);

TrustedService trustedService = new TrustedService(con);
session.setAttribute("trustedService",trustedService);

VisitService visitService = new VisitService(con);
session.setAttribute("visitService",visitService);

ArrayList<Object[]> visitCart = new ArrayList<Object[]>();
session.setAttribute("visitCart", visitCart);

%>