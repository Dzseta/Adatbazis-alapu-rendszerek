package hu.adatb.controller;

import hu.adatb.model.User;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class SessionController {
    private static SessionController sessionController;

    private User user;
    private BooleanProperty loggedIn = new SimpleBooleanProperty(false);

    public static SessionController getInstance(){
        if(sessionController == null){
            sessionController = new SessionController();
        }
        return sessionController;
    }

    private SessionController(){

    }

    public void login(User user){
        this.user = user;
        loggedIn.setValue(true);
    }

    public void logout(){
        user = null;
        loggedIn.setValue(false);
    }

    public BooleanProperty isLoggedIn(){
        return loggedIn;
    }

    public User getUser(){
        return user;
    }
}
