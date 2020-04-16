package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private IntegerProperty birthYear = new SimpleIntegerProperty();

    public Person(int id, String name, String email, int birthYear) {
        this.id.set(id);
        this.name.set(name);
        this.email.set(email);
        this.birthYear.set(birthYear);
    }

    public Person(String name, String email, int birthYear) {
        this.name.set(name);
        this.email.set(email);
        this.birthYear.set(birthYear);
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    public int getBirthYear() {
        return birthYear.get();
    }

    public IntegerProperty birthYearProperty() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear.set(birthYear);
    }
}
