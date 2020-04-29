package hu.adatb.controller;

import hu.adatb.model.User;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class SessionController {
    private static SessionController sessionController;

    private User user;
    private BooleanProperty loggedIn = new SimpleBooleanProperty(false);
    private BooleanProperty admin = new SimpleBooleanProperty(false);

    public static SessionController getInstance(){
        if(sessionController == null){
            sessionController = new SessionController();
        }
        return sessionController;
    }

    private SessionController(){

    }

    public void login(User user, boolean isAdmin){
        this.user = user;
        loggedIn.setValue(true);
        admin.setValue(isAdmin);
    }

    public void logout(){
        admin.setValue(false);
        loggedIn.setValue(false);
        user = null;
    }

    public BooleanProperty isLoggedIn(){
        return loggedIn;
    }

    public boolean userLoggedIn(){
        return loggedIn.get();
    }

    public BooleanProperty isAdmin(){
        return admin;
    }

    public User getUser(){
        return user;
    }
}
