package hu.adatb.view;

import hu.adatb.controller.PublisherController;
import hu.adatb.model.Publisher;
import hu.adatb.util.Utils;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class ListPublisherDialog extends Stage {

    private PublisherController controller;

    private TableView<Publisher> table;

    public ListPublisherDialog(PublisherController controller){
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

        Button okButton = new Button("Kiadók listázása");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> refreshTable());

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

        grid.add(buttonPane, 0, 1, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Kiadók");
        show();
    }

    public void initializeTable(){
        table = new TableView<>();
        TableColumn<Publisher, String> nameCol = new TableColumn<>("Név");
        TableColumn<Publisher, String> zipcodeCol = new TableColumn<>("Irányítószám");
        TableColumn<Publisher, String> cityCol = new TableColumn<>("Város");
        TableColumn<Publisher, String> streetCol = new TableColumn<>("Utca");
        TableColumn<Publisher, String> houseCol = new TableColumn<>("Házszám");

        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        zipcodeCol.setCellValueFactory(data -> data.getValue().zipcodeProperty().asString());
        cityCol.setCellValueFactory(data -> data.getValue().cityProperty());
        streetCol.setCellValueFactory(data -> data.getValue().streetProperty());
        houseCol.setCellValueFactory(data -> data.getValue().hnumberProperty());

        table.getColumns().addAll(nameCol, zipcodeCol, cityCol, streetCol, houseCol);
        List<Publisher> list = controller.list();
        table.setItems(FXCollections.observableList(list));
    }

    public void refreshTable(){
        List<Publisher> list = controller.list();
        table.setItems(FXCollections.observableList(list));
    }
}
