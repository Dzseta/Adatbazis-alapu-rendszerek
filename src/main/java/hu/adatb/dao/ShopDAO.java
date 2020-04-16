package hu.adatb.dao;

import hu.adatb.model.Shop;

public interface ShopDAO {
    boolean add(Shop shop);
    boolean delete(Shop shop);
    boolean update(Shop shop, int oldId);
    boolean list();
}
