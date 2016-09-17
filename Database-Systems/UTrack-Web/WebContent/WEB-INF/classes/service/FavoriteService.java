package service;

import static model.Columns.DISABLE_FK;

import cs5530.Connector;
import model.*;

public class FavoriteService {
	
	Connector con;

	public FavoriteService(Connector con) {
		this.con = con;
	}
	/**
	 * Returns true if user had one and updated it
	 * Returns false if none existed previously
	 * @param user
	 * @param poi
	 * @return
	 */
	public void insertFavorite(UserRecord user, POIRecord poi){
		String sql = "INSERT INTO `cs5530db53`.`Favorite` (`user`, `idpoi`, `date`) VALUES ('" 
			+ user.getLogin() + "'," + poi.getId() + ", CURDATE())" 
			+ " ON DUPLICATE KEY UPDATE "
			+ "`date` = CURDATE();";
		try{
			int result = con.stmt.executeUpdate(DISABLE_FK);
			int result2 = con.stmt.executeUpdate(sql);
		}
		catch(Exception e){
			System.out.println("cannot execute the query");
		}
	}

}
