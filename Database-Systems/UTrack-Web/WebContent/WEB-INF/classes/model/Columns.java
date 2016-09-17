package model;
/**
 * Schema:
 * Address(id,country,state,city,zip,street,number)
 * Favorite(user,idpoi)
 * Feedback(user,idpoi,score)
 * FeedbackRating(marking_user,marked_user,idpoi,rating)
 * Keywords(wid, keyword, language)
 * HasKeyword(idpoi,wid)
 * POI(id,name,address,url,phone,year_est,hours,price,category)
 * ShortText(user,idpoi,text)
 * Trusted(marked_user,marking_user,trusted)
 * User(login,name,password,address,phone)
 * Visit(idvisit,idpoi,user,date,spent,party)
 * 
 * 
 * @author Tanner
 *
 */
public final class Columns {

	public static final String USER = "user";
	public static final String LOGIN = "login";
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String ADDRESS = "address";
	public static final String PHONE = "phone";
	public static final String IDPOI = "idpoi";
	public static final String IDVISIT = "idvisit";
	public static final String CATEGORY = "category";
	public static final String SCORE = "score";
	public static final String MARKEDUSER = "marked_user";
	public static final String RATING = "rating";
	public static final String MARKINGUSER = "marking_user";
	public static final String DATE = "date";
	public static final String SPENT = "spent";
	public static final String PARTY = "party";
	public static final String TRUSTED = "trusted";
	public static final String TEXT = "text";
	public static final String ID = "id";
	public static final String URL = "url";
	public static final String YEAR_EST = "year_est";
	public static final String HOURS = "hours";
	public static final String PRICE = "price";
	public static final String KEYWORD = "keyword";
	public static final String LANGUAGE = "language";
	public static final String WID = "wid";
	public static final String COUNTRY = "country";
	public static final String STATE = "state";
	public static final String CITY = "city";
	public static final String ZIP = "zip";
	public static final String STREET = "street";
	public static final String NUMBER = "number";
	public static final String IS_ADMIN = "isAdmin";
	public static final String ADMIN_PASSWORD = "databases12";
	public static final String LAST_INSERT = "LAST_INSERT_ID()";
	public static final String DISABLE_FK = "SET FOREIGN_KEY_CHECKS = 0; ";
	public static final String ENABLE_DATES = "SET @@SESSION.sql_mode='ALLOW_INVALID_DATES';";
}
