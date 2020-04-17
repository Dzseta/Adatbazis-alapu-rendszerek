package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Coupon {
    private StringProperty code = new SimpleStringProperty();
    private IntegerProperty discount = new SimpleIntegerProperty();
    private StringProperty genre = new SimpleStringProperty();

    public Coupon(String code, int discount, String genre){
        this.code.setValue(code);
        this.discount.setValue(discount);
        this.genre.setValue(genre);
    }

    public Coupon(){

    }

    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public int getDiscount() {
        return discount.get();
    }

    public IntegerProperty discountProperty() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount.set(discount);
    }

    public String getGenre() {
        return genre.get();
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }
}
