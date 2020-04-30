package hu.adatb.controller;

import hu.adatb.dao.AuthorDAO;
import hu.adatb.dao.BookDAO;
import hu.adatb.dao.AuthorDAOImpl;
import hu.adatb.model.Author;

import java.util.List;

public class AuthorController {

    private AuthorDAO dao = new AuthorDAOImpl();

    public AuthorController(){ };

    public boolean add(Author author){
        return dao.add(author);
    }

    public boolean delete(Author author){
        return dao.delete(author);
    }

    public boolean update(Author author, String oldName, int oldIsbn) { return dao.update(author, oldName, oldIsbn); }

    public List<Author> list(){
        return dao.list();
    }

    public List<Author> getSelectedAuthors(int isbn){
        return dao.getSelectedAuthors(isbn);
    }
}
