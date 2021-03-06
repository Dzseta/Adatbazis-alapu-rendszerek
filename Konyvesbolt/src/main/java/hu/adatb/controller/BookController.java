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

    public Book getSelectedBook(int isbn){
        return dao.getSelectedBook(isbn);
    }

    public List<Book> getSelectedBooks(String title, String author, String genre){
        return dao.getSelectedBooks(title, author, genre);
    }

    public Book getSimilar(int isbn){
        return dao.getSimilar(isbn);
    }

    public int getBookPerGenre(String genre){
        return dao.getBookPerGenre(genre);
    }
}
