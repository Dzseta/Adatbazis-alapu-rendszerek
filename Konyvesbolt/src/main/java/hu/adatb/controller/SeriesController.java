package hu.adatb.controller;

import hu.adatb.dao.SeriesDAO;
import hu.adatb.dao.SeriesDAOImpl;
import hu.adatb.model.Series;

import java.util.List;

public class SeriesController {
    private SeriesDAO dao = new SeriesDAOImpl();

    public SeriesController(){ };

    public boolean foreignKey(Series series) { return dao.foreignKey(series); };

    public boolean add(Series series){
        return dao.add(series);
    }

    public boolean delete(Series series){
        return dao.delete(series);
    }

    public boolean update(Series series, String oldName) { return dao.update(series, oldName); }

    public List<Series> list(){
        return dao.list();
    }
}
