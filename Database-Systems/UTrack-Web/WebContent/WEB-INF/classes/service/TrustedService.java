package service;

import static model.Columns.DISABLE_FK;

import java.sql.SQLException;

import cs5530.Connector;
import model.UserRecord;

/*
 * MarkedUser,MarkingUser,Trusted
 */
public class TrustedService {
	
	Connector con;

	public TrustedService(Connector con) {
		this.con = con;
	}

	//INSERT INTO `cs5530db53`.`Trusted` (`marked_user`, `marking_user`, `trusted`) VALUES ('dark_knight', 'tanner', '1');
	public void markUserTrustedBy(UserRecord marked_user, UserRecord marking_user, boolean trusted) {
		int t = (trusted) ? 1 : 0;
		String sql = "INSERT INTO `cs5530db53`.`Trusted` (`marked_user`, `marking_user`, `trusted`) VALUES ('" +
					marked_user.getLogin() + "','" + marking_user.getLogin() + "','" + t + "')"
					+ " ON DUPLICATE KEY UPDATE "
					+ "`trusted` = " + t + ";";
		try{
			int result = con.stmt.executeUpdate(DISABLE_FK);
			int result2 = con.stmt.executeUpdate(sql);
		}
		catch(SQLException e){
			System.out.println("Cannot execute the query");
		}
	}

}
