package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private IntegerProperty isbn = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private IntegerProperty published = new SimpleIntegerProperty();
    private StringProperty publisher = new SimpleStringProperty();
    private IntegerProperty pages = new SimpleIntegerProperty();
    private StringProperty cover = new SimpleStringProperty();
    private StringProperty size = new SimpleStringProperty();
    private IntegerProperty price = new SimpleIntegerProperty();

    public Book(int isbn, String title, int published, String publisher, int pages, String cover, String size, int price){
        this.isbn.set(isbn);
        this.title.set(title);
        this.published.set(published);
        this.publisher.set(publisher);
        this.pages.set(pages);
        this.cover.set(cover);
        this.size.set(size);
        this.price.set(price);
    }

    public Book(){

    };

    public Book(int isbn) {
        this.isbn.set(isbn);
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

    public String getTitle() {
        return title.get();
    }
    public StringProperty titleProperty() {
        return title;
    }
    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getPublished() {
        return published.get();
    }
    public IntegerProperty publishedProperty() {
        return published;
    }
    public void setPublished(int published) {
        this.published.set(published);
    }

    public String getPublisher() {
        return publisher.get();
    }
    public StringProperty publisherProperty() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
    }

    public int getPages() {
        return pages.get();
    }
    public IntegerProperty pagesProperty() {
        return pages;
    }
    public void setPages(int pages) {
        this.pages.set(pages);
    }

    public String getCover() {
        return cover.get();
    }
    public StringProperty coverProperty() {
        return cover;
    }
    public void setCover(String cover) {
        this.cover.set(cover);
    }

    public String getSize() {
        return size.get();
    }
    public StringProperty sizeProperty() {
        return size;
    }
    public void setSize(String size) {
        this.size.set(size);
    }

    public int getPrice() {
        return price.get();
    }
    public IntegerProperty priceProperty() {
        return price;
    }
    public void setPrice(int price) {
        this.price.set(price);
    }
}
