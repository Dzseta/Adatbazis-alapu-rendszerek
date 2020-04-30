package hu.adatb.controller;

import hu.adatb.dao.BookDAO;
import hu.adatb.dao.BookDAOImpl;
import hu.adatb.model.Book;

import java.util.List;

public class BookController {

    private BookDAO dao = new BookDAOImpl();

    public BookController(){ };

    public boolean add(Book book){
        return dao.add(book);
    }

    public boolean delete(Book book){
        return dao.delete(book);
    }

    public boolean update(Book book, int oldIsbn) { return dao.update(book, oldIsbn); }

    public List<Book> list(){
        return dao.list();
    }
}
