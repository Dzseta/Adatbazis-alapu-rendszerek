package hu.adatb.dao;

import hu.adatb.model.Book;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StatsDAO {
    List<Book> topList();
    int income(int honap);
    HashMap<String, Integer> shops();
}
