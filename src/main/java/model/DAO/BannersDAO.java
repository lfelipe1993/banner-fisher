package model.DAO;

import java.util.List;
import java.util.Set;

import model.banners.Banners;

public interface BannersDAO {
	void insert(Banners obj);
	Set<Banners> findBanners(Integer daysAgo);
}
