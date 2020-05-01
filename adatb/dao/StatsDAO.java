package hu.adatb.dao;

import hu.adatb.model.Book;

import java.util.List;

public interface StatsDAO {
    List<Book> topList();
}
