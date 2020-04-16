package hu.adatb.dao;

import hu.adatb.model.Publisher;

public interface PublisherDAO {
    boolean add(Publisher publisher);
    boolean delete(Publisher publisher);
    boolean update(Publisher publisher, String oldName);
    boolean list();
}
