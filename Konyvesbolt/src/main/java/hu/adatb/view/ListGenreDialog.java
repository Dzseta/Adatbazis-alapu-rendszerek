package hu.adatb.view;

import hu.adatb.controller.GenreController;
import hu.adatb.model.Genre;
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

public class ListGenreDialog extends Stage {
    private GenreController controller;

    private TableView<Genre> table;

    public ListGenreDialog(GenreController controller){
        this.controller = controller;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        initializeTable();

        table.prefHeightProperty().bind(grid.heightProperty());
        table.prefWidthProperty().bind(grid.widthProperty());

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
        buttonPane.getChildren().addAll(okButton, modifyButton, deleteButton, cancelButton);

        grid.add(buttonPane, 0, 1, 2, 1);

        Scene scene = new Scene(grid, 580, 480);
        setScene(scene);
        setTitle("Műfajok");
        show();
    }

    public void initializeTable(){
        table = new TableView<>();
        TableColumn<Genre, String> nameCol = new TableColumn<>("Műfaj");
        TableColumn<Genre, String> isbnCol = new TableColumn<>("Könyv ISBN száma");

        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        isbnCol.setCellValueFactory(data -> data.getValue().isbnProperty().asString());

        table.getColumns().addAll(nameCol, isbnCol);
        List<Genre> list = controller.list();
        table.setItems(FXCollections.observableList(list));

    }

    public void refreshTable(){
        List<Genre> list = controller.list();
        table.setItems(FXCollections.observableList(list));
    }

    public void deleteItem(){
        Genre genre = table.getSelectionModel().getSelectedItem();
        if(genre != null && Utils.showConfirmation("Biztos, hogy törli a kijelölt elemet?")){
            controller.delete(genre);
            refreshTable();
        }
    }

    public void modifyItem(){
        Genre genre = table.getSelectionModel().getSelectedItem();
        if(genre != null) {
            ModifyGenreDialog dialog = new ModifyGenreDialog(controller, genre, this);
        }
    }
}
