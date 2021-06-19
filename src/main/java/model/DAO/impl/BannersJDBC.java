package model.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.DAO.BannersDAO;
import model.banners.Banners;
import pescador.Utils;

public class BannersJDBC implements BannersDAO {
	private Connection conn;

	public BannersJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Banners obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO banners " + "(url) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getUrl());
			
			
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Banners> findBanners(Integer daysAgo) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		Timestamp date = Utils.localDateTimeToTimeStamp(LocalDateTime.now().minusDays(Math.abs(daysAgo)));

		try {
			st = conn.prepareStatement(
					"SELECT * FROM banners WHERE date_time > ?");

			st.setTimestamp(1, date);

			//System.out.println(st);
			rs = st.executeQuery();
			
			List<Banners> list = new ArrayList<>();

			// se veio algum resultado
			while (rs.next()) {
				list.add(instantiateBannersList(rs));
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}
	
	private Banners instantiateBannersList(ResultSet rs) throws SQLException {
		Banners get = new Banners();
		
		get.setId(rs.getInt("id"));
		get.setUrl(rs.getString("url"));
		get.setDateTime(rs.getTimestamp("date_time"));
		
		return get;
	}
	
}
