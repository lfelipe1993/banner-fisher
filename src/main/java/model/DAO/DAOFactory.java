package model.DAO;

import db.DB;
import model.DAO.impl.BannersJDBC;

public class DAOFactory {
	
	public static BannersJDBC CreateBannersDao() {
		return new BannersJDBC(DB.getConnection());
	}

}
