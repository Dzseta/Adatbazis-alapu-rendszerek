package hu.adatb.view;

import hu.adatb.controller.AuthorController;
import hu.adatb.controller.BookController;
import hu.adatb.controller.GenreController;
import hu.adatb.controller.PublisherController;
import hu.adatb.model.Author;
import hu.adatb.model.Book;
import hu.adatb.model.Genre;
import hu.adatb.model.Publisher;
import hu.adatb.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class UpdateBookDialog extends Stage {

    private BookController controller;
    private ListBookDialog parent;
    private PublisherController publisherController;
    private AuthorController authorController;
    private GenreController genreController;

    private Book book;
    private int oldIsbn;

    public UpdateBookDialog(BookController controller, Book book, ListBookDialog parent, PublisherController publisherController, AuthorController authorController, GenreController genreController){
        this.controller = controller;
        this.publisherController = publisherController;
        this.authorController = authorController;
        this.genreController = genreController;
        this.parent = parent;
        this.book = book;
        oldIsbn = book.getIsbn();
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField isbnField = new TextField();
        TextField titleField = new TextField();
        TextField authorsField = new TextField();
        TextField genreField = new TextField();
        TextField publishedField = new TextField();
        ComboBox<String> publisherField = new ComboBox<>(listPublishers());
        TextField pagesField = new TextField();
        TextField coverField = new TextField();
        TextField sizeField = new TextField();
        TextField priceField = new TextField();

        isbnField.setText(String.valueOf(book.getIsbn()));
        titleField.setText(book.getTitle());

        StringBuilder authorBuilder = new StringBuilder();
        for(Author a: authorController.getSelectedAuthors(oldIsbn)){
            authorBuilder.append(a.getName());
            authorBuilder.append(",");
        }
        authorBuilder.deleteCharAt(authorBuilder.length() - 1);

        StringBuilder genreBuilder = new StringBuilder();
        for(Genre g: genreController.getSelectedGenre(oldIsbn)){
            genreBuilder.append(g.getName());
            genreBuilder.append(",");
        }
        genreBuilder.deleteCharAt(genreBuilder.length() - 1);

        authorsField.setText(authorBuilder.toString());
        genreField.setText(genreBuilder.toString());
        publishedField.setText(String.valueOf(book.getPublished()));
        publisherField.getSelectionModel().select(book.getPublisher());
        pagesField.setText(String.valueOf(book.getPages()));
        coverField.setText(book.getCover());
        sizeField.setText(book.getSize());
        priceField.setText(String.valueOf(book.getPrice()));

        grid.add(new Text("Könyv ISBN száma:"), 0, 0);
        grid.add(isbnField, 1 , 0);
        grid.add(new Text("Könyv címe:"), 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(new Text("Szerző(k):"), 0, 2);
        grid.add(authorsField, 1, 2);
        grid.add(new Text("Műfaj(ok):"), 0, 3);
        grid.add(genreField, 1, 3);
        grid.add(new Text("Kiadás éve:"), 0, 4);
        grid.add(publishedField, 1, 4);
        grid.add(new Text("Kiadó:"), 0, 5);
        grid.add(publisherField, 1, 5);
        grid.add(new Text("Oldalszám:"), 0, 6);
        grid.add(pagesField, 1, 6);
        grid.add(new Text("Borító kötése:"), 0, 7);
        grid.add(coverField, 1, 7);
        grid.add(new Text("Méret:"), 0, 8);
        grid.add(sizeField, 1, 8);
        grid.add(new Text("Ár (Forint):"), 0, 9);
        grid.add(priceField, 1, 9);

        Button okButton = new Button("Módosítás");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
            if(isbnField.getText().contentEquals("")){
                Utils.showWarning("Az ISBN szém nem lehet üres!");
                return;
            }
            if(titleField.getText().contentEquals("")){
                Utils.showWarning("A cím nem lehet üres!");
                return;
            }
            if(authorsField.getText().contentEquals("")){
                Utils.showWarning("A szerző nem lehet üres!");
                return;
            }
            if(publishedField.getText().contentEquals("")){
                Utils.showWarning("A kiadási év nem lehet üres!");
                return;
            }
            if(genreField.getText().contentEquals("")){
                Utils.showWarning("A műfaj nem lehet üres!");
                return;
            }
            if(publisherField.getSelectionModel().isEmpty()){
                Utils.showWarning("A kiadó neve nem lehet üres!");
                return;
            }
            if(pagesField.getText().contentEquals("")){
                Utils.showWarning("Az oldalszám nem lehet üres!");
                return;
            }
            if(coverField.getText().contentEquals("")){
                Utils.showWarning("A borító típusa nem lehet üres!");
                return;
            }
            if(sizeField.getText().contentEquals("")){
                Utils.showWarning("A méret nem lehet üres!");
                return;
            }
            if(priceField.getText().contentEquals("")){
                Utils.showWarning("Az ár nem lehet üres!");
                return;
            }

            int isbn;
            try{
                isbn = Integer.parseInt(isbnField.getText());
            } catch (Exception ex){
                Utils.showWarning("Az ISBN nem szám!");
                return;
            }
            if(isbn < 0){
                Utils.showWarning("Az ISBN nem lehet negatív szám!");
                return;
            }

            int published;
            try{
                published = Integer.parseInt(publishedField.getText());
            } catch (Exception ex){
                Utils.showWarning("A kiadás éve nem szám!");
                return;
            }

            int pages;
            try{
                pages = Integer.parseInt(pagesField.getText());
            } catch (Exception ex){
                Utils.showWarning("Az oldalszám nem szám!");
                return;
            }
            if(pages <= 0){
                Utils.showWarning("Az oldalszám nem lehet negatív!");
                return;
            }

            int price;
            try{
                price = Integer.parseInt(priceField.getText());
            } catch (Exception ex){
                Utils.showWarning("Az ár nem szám!");
                return;
            }
            if(isbn <= 0){
                Utils.showWarning("Az árnak pozitívnak kell lennie!");
                return;
            }

            book.setIsbn(isbn);
            book.setTitle(titleField.getText());
            book.setPublished(published);
            book.setPublisher(publisherField.getSelectionModel().getSelectedItem());
            book.setPages(pages);
            book.setCover(coverField.getText());
            book.setSize(sizeField.getText());
            book.setPrice(price);

            if(controller.update(book, oldIsbn)){
                String[] authors = authorsField.getText().split(",");
                String[] oldAuthors = authorBuilder.toString().split(",");
                for(int i=0; i<oldAuthors.length; i++){
                    String oldName = oldAuthors[i].strip();
                    if(!authorController.delete(new Author(oldName, oldIsbn))){
                        Utils.showWarning("Nem sikerült a szerzők frissítése!");
                        return;
                    }
                }
                for(int i=0; i<authors.length; i++){
                    String name = authors[i].strip();
                    if(!authorController.add(new Author(name, isbn))){
                        Utils.showWarning("Nem sikerült a szerzők frissítése!");
                        return;
                    }
                }

                String[] genres = genreField.getText().split(",");
                String[] oldGenres = genreBuilder.toString().split(",");
                for(int i=0; i<oldGenres.length; i++){
                    String oldName = oldGenres[i].strip();
                    if(!genreController.delete(new Genre(oldName, oldIsbn))){
                        Utils.showWarning("Nem sikerült a műfajok frissítése!");
                        return;
                    }
                }
                for(int i=0; i<genres.length; i++){
                    String name = genres[i].strip();
                    if(!genreController.add(new Genre(name, isbn))){
                        Utils.showWarning("Nem sikerült a műfajok frissítése!");
                        return;
                    }
                }

                parent.refreshTable();
                close();
            } else {
                Utils.showWarning("Nem sikerült a könyv frissítése!");
                return;
            }

        });

        Button cancelButton = new Button("Mégse");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(e -> {
            close();
        });

        FlowPane buttonPane = new FlowPane();
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        buttonPane.setHgap(15);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(okButton, cancelButton);

        grid.add(buttonPane, 0, 10, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Könyv frissítése");
        show();
    }

    private ObservableList<String> listPublishers(){
        ObservableList<String> list = null;

        List<Publisher> publisherList = publisherController.list();
        List<String> publisherNames = new ArrayList<>();

        for(Publisher p: publisherList){
            publisherNames.add(p.getName());
        }

        list = FXCollections.observableList(publisherNames);

        return list;
    }
}
