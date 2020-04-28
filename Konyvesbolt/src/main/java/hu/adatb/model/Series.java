package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Series {

    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty isbn = new SimpleIntegerProperty();
    private IntegerProperty quantity = new SimpleIntegerProperty();

    public Series(String name, int isbn, int quantity){
        this.name.setValue(name);
        this.isbn.setValue(isbn);
        this.quantity.setValue(quantity);
    }

    public Series(){ }

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

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }
}
