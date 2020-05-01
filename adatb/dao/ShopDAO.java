package hu.adatb.dao;

import hu.adatb.model.Shop;

import java.util.List;

public interface ShopDAO {
    boolean add(Shop shop);
    boolean delete(Shop shop);
    boolean update(Shop shop, int oldId);
    List<Shop> list();
    Shop getSelectedShop(String name);
}
