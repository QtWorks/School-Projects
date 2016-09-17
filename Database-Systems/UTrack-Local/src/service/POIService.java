package service;

import static model.Columns.*;

import java.sql.*;
import java.util.ArrayList;

import cs5530.Connector;
import model.*;

/*
 * ID(auto-increment),
 * Name,Address,URL,Phone,Year_Est,Hours,Price
 */
public class POIService {
	
	Connector con;

	public POIService(Connector con) {

		this.con = con;
	}
	
	public ArrayList<POIRecord> getPOIs(String sql){
		ResultSet rs = null;
		ArrayList<POIRecord> pois = new ArrayList<POIRecord>();
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				POIRecord pr = new POIRecord();
				pr.setId(rs.getInt(ID));
				pr.setName(rs.getString(NAME));
				pr.setAddressId(rs.getInt(ADDRESS));
				pr.setUrl(rs.getString(URL));
				pr.setPhone(rs.getString(PHONE));
				pr.setYear_est(rs.getInt(YEAR_EST));
				pr.setPrice(rs.getDouble(PRICE));
				pr.setCategory(rs.getString(CATEGORY));
				pr.setHours(rs.getString(HOURS));
				pois.add(pr);
			}
			rs.close();
		}
		catch(Exception e){
			System.out.println("cannot execute the query");
		}
		finally
	 	{
	 		try{
	 		if (rs!=null && !rs.isClosed())
	 			rs.close();
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		AddressService as = new AddressService(con);
		for(POIRecord poi : pois){
			AddressRecord address = as.getAddressById(poi.getAddressId());
			poi.setAddress(address);
		}
		KeywordService ks = new KeywordService(con);
		for(POIRecord poi : pois){
			ArrayList<KeywordRecord> keywords = ks.getKeywords(poi.getId());
			poi.setKeywords(keywords);
		}
		return pois;
	}

	public ArrayList<POIRecord> getPOIbyUser(String user) {
		
		user = "%" + user + "%";
		String sql = "select * from POI p1 where p1.id in(select p.id from POI p, Visit v where p.id = v.idpoi and " +
						" v.user like '" + user + "');";
		return getPOIs(sql);
	}

	public void insertPOI(POIRecord pr) {
		String sql = "INSERT INTO `cs5530db53`.`POI` (`name`, `address`, `url`, `phone`, `year_est`, `hours`, `price`, `category`) "
				+ "VALUES ('" + pr.getName() + "', '"
				+ pr.getAddress().getId() + "', '" 
				+ pr.getUrl() + "', '"
				+ pr.getPhone() + "', " 
				+ pr.getYear_est() + ", '"
				+ pr.getHours() + "', " 
				+ pr.getPrice() +", '" 
				+ pr.getCategory() + "');";
		ResultSet rs = null;
		try{
			int result = con.stmt.executeUpdate(DISABLE_FK);
			int result2 = con.stmt.executeUpdate(sql);
			
			sql = "select " + LAST_INSERT +" from POI LIMIT 1";
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt(LAST_INSERT);
				pr.setId(id);
			}
			KeywordService ks = new KeywordService(con);
			for(KeywordRecord keyword : pr.getKeywords()){
				ks.insertKeyword(pr.getId(),keyword.getKeyword(), keyword.getLanguage());
			}
		}
		catch(Exception e){
			System.out.println("cannot execute the query");
		}
		finally
	 	{
	 		try{
	 		if (rs!=null && !rs.isClosed())
	 			rs.close();
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		
	}

	public void updatePOI(POIRecord previous) {
		String sql = "UPDATE POI " +
		"SET name = '" + previous.getName() + "'," +
		"address = " + previous.getAddressId() + "," +
		"url = '" + previous.getUrl() + "'," +
		"phone = '" + previous.getPhone() + "'," +
		"year_est = " + previous.getYear_est() + "," +
		"hours = '" + previous.getHours() + "'," +
		"price = " + previous.getPrice() + "," +
		"category = '" + previous.getCategory() + "'" +		
		" WHERE id=" + previous.getId();
		try{
			int result = con.stmt.executeUpdate(DISABLE_FK);
			int result2 = con.stmt.executeUpdate(sql);
			
		}
		catch(Exception e){
			System.out.println("cannot execute the query");
		}
		KeywordService k = new KeywordService(con);
		for(KeywordRecord keyword : previous.getKeywords()){
			k.insertKeyword(previous.getId(), keyword.getKeyword(), keyword.getLanguage());
		}
		
	}

	public POIRecord getPOIbyId(int id) {
		String sql = "Select * from POI where id = " + id;
		ArrayList<POIRecord> pois = getPOIs(sql);
		if(pois.size() > 0)
			return pois.get(0);
		return null;
	}

	public ArrayList<POIRecord> getAllPois() {
		String sql = "Select * from POI";
		return getPOIs(sql);
	}

	/**
	 * Select * from POI p, Visit v, HasKeywords h, Address a 
	 * where p.id = h.idpoi and a.id=p.address  
	 * and a.state like '%%' and a.city like '%%' 
	 * and p.category like '%%' and p.id = v.idpoi 
	 * and exists 
	 * (select * from HasKeywords h1, Keywords k  
	 * where p.id = h1.idpoi  and h1.wid = k.wid  
	 * and k.keyword like '%sp%') 
	 * and exists 
	 * (select * from HasKeywords h1, Keywords k  
	 * where p.id = h1.idpoi  and h1.wid = k.wid 
	 * and k.keyword like '%soccer%') 
	 * @param search
	 * @return
	 */
	public ArrayList<POIRecord> getSearchResults(SearchModel search) {
		String sql = "";
		String orderBy = "";
		int sortedBy = search.getSortedBy();
		if(sortedBy == 1){
			orderBy = " order by avg(v.spent / v.party);";
		}else if(sortedBy == 2){
			orderBy = " order by (select avg(f.score) from Feedback f where f.idpoi = p.id) DESC;";
		}else if(sortedBy == 3){
			orderBy = "order by (select avg(f.score) from Feedback f " + 
					"where f.idpoi = p.id and f.user in (select u.login "
					+"from User u where " +
					"(select avg(t.trusted) as tValue " +
					"from Trusted t where t.marked_user = u.login) > 0.5)) DESC;";
		}
		if(search.hasKeywords())
			sql = "Select * from POI p, Visit v, HasKeywords h, Address a where p.id = h.idpoi and a.id=p.address ";
		else
			sql = "Select * from POI p, Visit v, Address a where a.id=p.address";
			sql += " and a.country like '%" + search.getCountry() + "%'" 
				+ " and a.state like '%" + search.getState() + "%'"
				+ " and a.city like '%" + search.getCity() + "%'"
				+ " and p.category like '%" + search.getCategory() + "%'"
				+ " and p.id = v.idpoi"
				+ getPriceRange(search)
				+ addKeywords(search)
				+ " group by p.id " + 
				orderBy;
		return getPOIs(sql);
	}
	
	private String getPriceRange(SearchModel search) {
		if(!search.hasPrice())
			return "";
		else{
			return " and p.price <= " + search.getHi()
					+ " and p.price >= " + search.getLo() + " ";
		}
	}

	private ArrayList<String> getAllCategories(){
		String sql = "select category from POI group by category;";
		ResultSet rs = null;
		ArrayList<String> categories = new ArrayList<String>();
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next()){
				categories.add(rs.getString(CATEGORY));
			}
			rs.close();
		}
		catch(Exception e){
			System.out.println("cannot execute the query");
		}
		finally
	 	{
	 		try{
	 		if (rs!=null && !rs.isClosed())
	 			rs.close();
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		return categories;
	}

	private String addKeywords(SearchModel search) {
		StringBuilder sb = new StringBuilder();
		for(String keyword : search.getKeywords()){
			sb.append(" and exists (select * from HasKeywords h1, Keywords k " +
					" where p.id = h1.idpoi " +
					" and h1.wid = k.wid " +
					" and k.keyword like '%" + keyword + "%')");
		}
		return sb.toString();
	}

	public ArrayList<ArrayList<POIRecord>> getTopByCategory(int n, int sortedBy) {
		ArrayList<ArrayList<POIRecord>> result = new ArrayList<ArrayList<POIRecord>>();
		ArrayList<String> categories = getAllCategories();
		String orderBy = " order by (";
		if(sortedBy ==1){
			orderBy += "select count(*) " +
						"from Visit v " +
						"where v.idpoi = p.id)";
		}else if(sortedBy == 2){
			orderBy += "select avg(v.spent / v.party) " +
					"from Visit v " +
					"where v.idpoi = p.id)";
		}else if(sortedBy == 3){
			orderBy += "select avg(f.score) " +
						"from Feedback f " +
						"where f.idpoi = p.id)";
		}
		for(String category : categories){
			String sql = "select * from POI p " +
					"where p.category like '" + category + "'" +
					orderBy + " DESC LIMIT " + n + ";";
			ArrayList<POIRecord> list = getPOIs(sql);
			result.add(list);
		}
		return result;
	}

	public ArrayList<POIRecord> getSuggestedPOIs(VisitRecord vr) {
		String sql = "select distinct p.id,p.hours,p.name,p.address,p.url,p.phone,p.year_est,p.price,p.category from POI p, Visit v " +
						"where p.id = v.idpoi " +
						"and p.id != " + vr.getPoi().getId() + 
						" and v.user in (select u.login from User u, Visit v1 " +
							"where v1.user = u.login " +
							" and u.login != '" + vr.getUser().getLogin() +
							"' and v1.idpoi = " + vr.getPoi().getId() +
						") order by "  + "(select count(*) from Visit v where v.idpoi = p.id );";
		return getPOIs(sql);
	}
	
	public ArrayList<POIRecord> getFavoritePlaces(UserRecord user){
		String sql = "select * from Favorite f, POI p where f.user like '" + user.getLogin() + "' and p.id = f.idpoi;";
		return getPOIs(sql);
	}
}