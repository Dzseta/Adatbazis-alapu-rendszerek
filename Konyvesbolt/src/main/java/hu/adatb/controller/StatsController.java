package hu.adatb.controller;

import hu.adatb.dao.StatsDAO;
import hu.adatb.dao.StatsDAOImpl;
import hu.adatb.model.Book;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsController {
    private StatsDAO dao = new StatsDAOImpl();

    public StatsController(){ };

    public List<Book> list(){
        return dao.topList();
    }

    public int income(int honap) {
        return  dao.income(honap);
    }

    public HashMap<String, Integer> shops() { return dao.shops(); }

}
