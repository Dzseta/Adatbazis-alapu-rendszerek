package hu.adatb.controller;

import hu.adatb.dao.GenreDAO;
import hu.adatb.dao.GenreDAOImpl;
import hu.adatb.model.Genre;

import java.util.List;

public class GenreController {

    private GenreDAO dao = new GenreDAOImpl();

    public GenreController(){ };

    public boolean foreignKey(Genre genre) { return dao.foreignKey(genre); };

    public boolean add(Genre genre){
        return dao.add(genre);
    }

    public boolean delete(Genre genre){
        return dao.delete(genre);
    }

    public boolean update(Genre genre, String oldName, int oldIsbn) { return dao.update(genre, oldName, oldIsbn); }

    public List<Genre> list(){
        return dao.list();
    }
}
