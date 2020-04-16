package hu.adatb.dao;

import hu.adatb.model.User;

public interface UserDAO {
    boolean add(User user);
    boolean login(User user);
}
