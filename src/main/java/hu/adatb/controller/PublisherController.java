package hu.adatb.controller;

import hu.adatb.dao.PublisherDAO;
import hu.adatb.dao.PublisherDAOImpl;
import hu.adatb.model.Publisher;

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

    public boolean list(){
        return dao.list();
    }
}
