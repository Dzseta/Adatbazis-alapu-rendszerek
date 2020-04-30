package hu.adatb.dao;

import hu.adatb.model.Publisher;

import java.util.List;

public interface PublisherDAO {
    boolean add(Publisher publisher);
    boolean delete(Publisher publisher);
    boolean update(Publisher publisher, String oldName);
    List<Publisher> list();
}
