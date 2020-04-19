package hu.adatb.controller;

import hu.adatb.dao.PublisherDAO;
import hu.adatb.dao.PublisherDAOImpl;
import hu.adatb.model.Publisher;

import java.util.List;

public class PublisherController {
    private PublisherDAO dao = new PublisherDAOImpl();

    public PublisherController(){

    };

    public boolean add(Publisher publisher){
        return dao.add(publisher);
    }

    public boolean delete(Publisher publisher){
        return dao.delete(publisher);
    }

    public boolean update(Publisher publisher, String oldName) { return dao.update(publisher, oldName); }

    public List<Publisher> list(){
        return dao.list();
    }
}
