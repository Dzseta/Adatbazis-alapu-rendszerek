package hu.adatb.view;

import hu.adatb.controller.BookController;
import hu.adatb.controller.GenreController;
import hu.adatb.model.Book;
import hu.adatb.model.Genre;
import javafx.beans.property.SimpleStringProperty;
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

public class BookPerGenreDialog extends Stage {
    private BookController bookController;
    private GenreController genreController;

    private TableView<Genre> table;

    public BookPerGenreDialog(BookController bookController, GenreController genreController) {
        this.bookController = bookController;
        this.genreController = genreController;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        initializeTable();

        Button refreshButton = new Button("Frissítés");
        refreshButton.setOnAction(e -> refreshTable());

        Button cancelButton = new Button("Mégse");
        cancelButton.setOnAction(e -> close());

        FlowPane buttonPane = new FlowPane();
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        buttonPane.setHgap(15);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(refreshButton, cancelButton);

        grid.add(table, 0, 0);
        grid.add(buttonPane, 0, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Könyvek száma műfajonként");
        show();
    }

    private void initializeTable(){
        table = new TableView<>();
        TableColumn<Genre, String> nameCol = new TableColumn<>("Műfaj");
        TableColumn<Genre, String> salesCol = new TableColumn<>("Könyvek száma");

        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        salesCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(bookController.getBookPerGenre(data.getValue().getName()))));
        table.getColumns().addAll(nameCol, salesCol);
        List<Genre> list = genreController.listDistinct();
        table.setItems(FXCollections.observableList(list));
    }

    private void refreshTable(){
        List<Genre> list = genreController.listDistinct();
        table.setItems(FXCollections.observableList(list));
    }
}
