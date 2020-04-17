package hu.adatb.controller;

import hu.adatb.dao.ShopDAO;
import hu.adatb.dao.ShopDAOImpl;
import hu.adatb.model.Shop;

public class ShopController {

    private ShopDAO dao = new ShopDAOImpl();

    public ShopController(){

    };

    public boolean add(Shop shop){
        return dao.add(shop);
    }

    public boolean delete(Shop shop){
        return dao.delete(shop);
    }

    public boolean update(Shop shop, int oldId) { return dao.update(shop, oldId); }

    public boolean list(){
        return dao.list();
    }
}
