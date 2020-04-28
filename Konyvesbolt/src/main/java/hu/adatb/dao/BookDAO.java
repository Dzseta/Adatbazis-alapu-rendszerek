package hu.adatb.dao;

import hu.adatb.model.Book;

import java.util.List;

public interface BookDAO {
    boolean add(Book book);
    boolean delete(Book book);
    boolean update(Book book, int oldIsbn);
    List<Book> list();
}
