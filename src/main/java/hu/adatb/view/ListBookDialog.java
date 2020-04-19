package hu.adatb.view;

import hu.adatb.controller.BookController;
import hu.adatb.model.Book;
import hu.adatb.util.Utils;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class ListBookDialog extends Stage {

    private BookController controller;

    private TableView<Book> table;

    public ListBookDialog(BookController controller){
        this.controller = controller;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        initializeTable();

        grid.add(table, 0, 0, 2, 1);

        Button okButton = new Button("Frissítés");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> refreshTable());

        Button deleteButton = new Button("Törlés");
        deleteButton.setOnAction(e -> deleteItem());

        Button modifyButton = new Button("Módosítás");
        modifyButton.setOnAction(e -> modifyItem());

        Button cancelButton = new Button("Mégse");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(e -> {
            close();
        });

        FlowPane buttonPane = new FlowPane();
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        buttonPane.setHgap(15);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(okButton, modifyButton,deleteButton,cancelButton);

        grid.add(buttonPane, 0, 1, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Könyvek");
        show();
    }

    public void initializeTable(){
        table = new TableView<>();
        TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        TableColumn<Book, String> titleCol = new TableColumn<>("Cím");
        TableColumn<Book, String> publishedCol = new TableColumn<>("Kiadás éve");
        TableColumn<Book, String> publisherCol = new TableColumn<>("Kiadó");
        TableColumn<Book, String> pagesCol = new TableColumn<>("Oldalszám");
        TableColumn<Book, String> coverCol = new TableColumn<>("Borító kötése");
        TableColumn<Book, String> sizeCol = new TableColumn<>("Méret");
        TableColumn<Book, String> priceCol = new TableColumn<>("Ár (Forint)");

        isbnCol.setCellValueFactory(data -> data.getValue().isbnProperty().asString());
        titleCol.setCellValueFactory(data -> data.getValue().titleProperty());
        publishedCol.setCellValueFactory(data -> data.getValue().publishedProperty().asString());
        publisherCol.setCellValueFactory(data -> data.getValue().publisherProperty());
        pagesCol.setCellValueFactory(data -> data.getValue().pagesProperty().asString());
        coverCol.setCellValueFactory(data -> data.getValue().coverProperty());
        sizeCol.setCellValueFactory(data -> data.getValue().sizeProperty());
        priceCol.setCellValueFactory(data -> data.getValue().priceProperty().asString());

        table.getColumns().addAll(isbnCol, titleCol, publishedCol, publisherCol, pagesCol, coverCol, sizeCol, priceCol);
        List<Book> list = controller.list();
        table.setItems(FXCollections.observableList(list));
    }

    public void refreshTable(){
        List<Book> list = controller.list();
        table.setItems(FXCollections.observableList(list));
    }

    public void deleteItem(){
        Book book = table.getSelectionModel().getSelectedItem();
        if(book != null && Utils.showConfirmation("Biztos, hogy törli a kijelölt elemet?")){
            controller.delete(book);
            refreshTable();
        }
    }

    public void modifyItem(){
        Book book = table.getSelectionModel().getSelectedItem();
        if(book != null){
            UpdateBookDialog dialog = new UpdateBookDialog(controller, book, this);
        }
    }
}
