package hu.adatb.dao;

import hu.adatb.model.User;

import java.util.List;

public interface UserDAO {
    boolean addUser(User user);
    boolean login(User user);
    User getUser(String email);
    boolean deleteUser(String email);
    boolean modifyUser(User user, String email);
    boolean isAdmin(String email);
    List<User> listCities();
}
