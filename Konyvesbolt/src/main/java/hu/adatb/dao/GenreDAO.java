package hu.adatb.dao;

import hu.adatb.model.Genre;

import java.util.List;

public interface GenreDAO {
    boolean foreignKey(Genre genre);
    boolean add(Genre genre);
    boolean delete(Genre genre);
    boolean update(Genre genre, String oldName, int oldIsbn);
    List<Genre> list();
}
