package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Stock {

    private IntegerProperty isbn = new SimpleIntegerProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty quantity = new SimpleIntegerProperty();

    public Stock(int isbn, int id, int quantity){
        this.isbn.setValue(isbn);
        this.id.setValue(id);
        this.quantity.setValue(quantity);
    }

    public Stock(){ }

    public int getIsbn() {
        return isbn.get();
    }

    public IntegerProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn.set(isbn);
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
