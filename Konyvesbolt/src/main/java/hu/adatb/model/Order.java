package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Order {
    private StringProperty email = new SimpleStringProperty();
    private IntegerProperty isbn = new SimpleIntegerProperty();
    private IntegerProperty quantity = new SimpleIntegerProperty();
    private StringProperty location = new SimpleStringProperty();
    private Date timeOrder = new Date();
    private Date timeReceipt = new Date();
    private IntegerProperty subtotal = new SimpleIntegerProperty();

    public Order(String email, int isbn, int quantity, String location, Date timeOrder, Date timeReceipt, int subtotal){
        this.email.setValue(email);
        this.isbn.setValue(isbn);
        this.quantity.setValue(quantity);
        this.location.setValue(location);
        this.timeOrder = timeOrder;
        this.timeReceipt = timeReceipt;
        this.subtotal.setValue(subtotal);
    }

    public Order(String email, int isbn, int quantity, String location, Date timeOrder){
        this.email.setValue(email);
        this.isbn.setValue(isbn);
        this.quantity.setValue(quantity);
        this.location.setValue(location);
        this.timeOrder = timeOrder;
    }

    public Order(){ }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
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

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public Date getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(Date timeOrder) {
        this.timeOrder = timeOrder;
    }

    public Date getTimeReceipt() {
        return timeReceipt;
    }

    public void setTimeReceipt(Date timeReceipt) {
        this.timeReceipt = timeReceipt;
    }

    public int getSubtotal() {
        return subtotal.get();
    }

    public IntegerProperty subtotalProperty() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal.set(subtotal);
    }
}
