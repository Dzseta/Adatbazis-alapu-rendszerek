package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Author {
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty isbn = new SimpleIntegerProperty();

    public Author(String name, int isbn){
        this.isbn.setValue(isbn);
        this.name.setValue(name);
    }

    public Author(){ }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getIsbn() {
        return isbn.get();
    }

    public IntegerProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn.set(isbn);
    }
}
