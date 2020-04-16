package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;


public class User {
    private StringProperty vnev = new SimpleStringProperty();
    private StringProperty knev = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private IntegerProperty irsz = new SimpleIntegerProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty street = new SimpleStringProperty();
    private StringProperty house = new SimpleStringProperty();
    private Date date = new Date();

    public User(String vnev, String knev, String email, String password, int irsz, String city, String street, String house, Date date){
        this.vnev.set(vnev);
        this.knev.set(knev);
        this.email.set(email);
        this.password.set(password);
        this.irsz.set(irsz);
        this.city.set(city);
        this.street.set(street);
        this.house.set(house);
        this.date = date;
    }

    public User(){

    };

    public String getVnev() {
        return vnev.get();
    }

    public StringProperty vnevProperty() {
        return vnev;
    }

    public void setVnev(String vnev) {
        this.vnev.set(vnev);
    }

    public String getKnev() {
        return knev.get();
    }

    public StringProperty knevProperty() {
        return knev;
    }

    public void setKnev(String knev) {
        this.knev.set(knev);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public int getIrsz() {
        return irsz.get();
    }

    public IntegerProperty irszProperty() {
        return irsz;
    }

    public void setIrsz(int irsz) {
        this.irsz.set(irsz);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getStreet() {
        return street.get();
    }

    public StringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getHouse() {
        return house.get();
    }

    public StringProperty houseProperty() {
        return house;
    }

    public void setHouse(String house) {
        this.house.set(house);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
