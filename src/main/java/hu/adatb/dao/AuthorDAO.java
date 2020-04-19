package hu.adatb.dao;

import hu.adatb.model.Author;

import java.util.List;

public interface AuthorDAO {
    boolean foreignKey(Author author);
    boolean add(Author author);
    boolean delete(Author author);
    boolean update(Author author, String oldName, int oldIsbn);
    List<Author> list();
}
