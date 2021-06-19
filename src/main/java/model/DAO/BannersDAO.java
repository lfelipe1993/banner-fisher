package model.DAO;

import java.util.List;

import model.banners.Banners;

public interface BannersDAO {
	void insert(Banners obj);
	List<Banners> findBanners(Integer daysAgo);
}
