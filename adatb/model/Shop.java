package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Shop {
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty zipcode = new SimpleIntegerProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty street = new SimpleStringProperty();
    private StringProperty hnumber = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();

    public Shop(int id, int zipcode, String city, String street, String hnumber, String name){
        this.name.set(name);
        this.id.set(id);
        this.zipcode.set(zipcode);
        this.city.set(city);
        this.street.set(street);
        this.hnumber.set(hnumber);
    }

    public Shop(){

    };

    public Shop(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public int getId() {
        return id.get();
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public void setId(int id) {
        this.id.set(id);
    }

    public int getZipcode() {
        return zipcode.get();
    }
    public IntegerProperty zipcodeProperty() {
        return zipcode;
    }
    public void setZipcode(int zipcode) {
        this.zipcode.set(zipcode);
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

    public String getHnumber() {
        return hnumber.get();
    }
    public StringProperty hnumberProperty() {
        return hnumber;
    }
    public void setHnumber(String hnumber) {
        this.hnumber.set(hnumber);
    }
}
