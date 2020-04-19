package hu.adatb.dao;

import hu.adatb.model.Stock;

import java.util.List;

public interface StockDAO {
    boolean foreignKey(Stock stock);
    boolean add(Stock stock);
    boolean delete(Stock stock);
    boolean update(Stock stock, int oldIsbn, int oldId);
    List<Stock> list();
}
