package hu.adatb.controller;

import hu.adatb.dao.UserDAO;
import hu.adatb.dao.UserDAOImpl;
import hu.adatb.model.User;

import java.util.List;

public class UserController {
    private UserDAO dao = new UserDAOImpl();

    public UserController(){

    }

    public boolean addUser(User user){
        return dao.addUser(user);
    }

    public boolean login(User user){
        return dao.login(user);
    }

    public User getUser(String email){
        return dao.getUser(email);
    }

    public boolean deleteUSer(String email){
        return dao.deleteUser(email);
    }

    public boolean modifyUser(User user, String email){
        return dao.modifyUser(user, email);
    }

    public boolean isAdmin(String email){
        return dao.isAdmin(email);
    }

    public List<User> listCities(){
        return dao.listCities();
    }
}
