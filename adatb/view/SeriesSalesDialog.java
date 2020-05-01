package hu.adatb.view;

import hu.adatb.controller.SeriesController;
import hu.adatb.model.Series;
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

public class SeriesSalesDialog extends Stage {
    private SeriesController seriesController;

    private TableView<Series> table;

    public SeriesSalesDialog(SeriesController seriesController) {
        this.seriesController = seriesController;
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

        Scene scene = new Scene(grid, 580, 480);
        setScene(scene);
        setTitle("Eladások sorozatok szerint");
        show();
    }

    private void initializeTable(){
        table = new TableView<>();
        TableColumn<Series, String> nameCol = new TableColumn<>("Sorozat");
        TableColumn<Series, String> salesCol = new TableColumn<>("Eladott mennyiség");

        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        salesCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(seriesController.getSalesPerSeries(data.getValue().getName()))));

        table.getColumns().addAll(nameCol, salesCol);
        List<Series> list = seriesController.list();
        table.setItems(FXCollections.observableList(list));
    }

    private void refreshTable(){
        List<Series> list = seriesController.list();
        table.setItems(FXCollections.observableList(list));
    }
}
