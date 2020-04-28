package hu.adatb.controller;

import hu.adatb.dao.StockDAO;
import hu.adatb.dao.StockDAOImpl;
import hu.adatb.model.Stock;

import java.util.List;

public class StockController {
    private StockDAO dao = new StockDAOImpl();

    public StockController(){ };

    public boolean foreignKey(Stock stock) { return dao.foreignKey(stock); };

    public boolean add(Stock stock){
        return dao.add(stock);
    }

    public boolean delete(Stock stock){
        return dao.delete(stock);
    }

    public boolean update(Stock stock, int oldisbn, int oldId) { return dao.update(stock, oldisbn, oldId); }

    public List<Stock> list(){
        return dao.list();
    }
}
