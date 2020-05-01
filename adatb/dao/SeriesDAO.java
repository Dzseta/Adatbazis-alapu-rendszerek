package hu.adatb.dao;

import hu.adatb.model.Series;

import java.util.List;

public interface SeriesDAO {
    boolean foreignKey(Series series);
    boolean add(Series series);
    boolean delete(Series series);
    boolean update(Series series, String oldName, int oldIsbn);
    List<Series> list();
    int getSalesPerSeries(String name);
}
