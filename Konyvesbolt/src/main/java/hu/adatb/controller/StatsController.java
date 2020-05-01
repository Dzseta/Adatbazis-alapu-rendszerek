package hu.adatb.controller;

import hu.adatb.dao.StatsDAO;
import hu.adatb.dao.StatsDAOImpl;
import hu.adatb.model.Book;

import java.util.List;

public class StatsController {
    private StatsDAO dao = new StatsDAOImpl();

    public StatsController(){ };

    public List<Book> list(){
        return dao.topList();
    }

}
