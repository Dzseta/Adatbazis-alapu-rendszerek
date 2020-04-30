package hu.adatb.dao;

import hu.adatb.model.Author;

import java.util.List;

public interface AuthorDAO {
    boolean add(Author author);
    boolean delete(Author author);
    boolean update(Author author, String oldName, int oldIsbn);
    List<Author> list();
    List<Author> getSelectedAuthors(int isbn);
}
