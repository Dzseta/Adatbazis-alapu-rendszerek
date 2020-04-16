package hu.adatb.controller;

import hu.adatb.dao.UserDAO;
import hu.adatb.dao.UserDAOImpl;
import hu.adatb.model.User;

public class UserController {
    private UserDAO dao = new UserDAOImpl();

    public UserController(){

    };

    public boolean add(User user){
        return dao.add(user);
    }

    public boolean login(User user){
        return dao.login(user);
    }
}
