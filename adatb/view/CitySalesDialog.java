package hu.adatb.view;

import hu.adatb.controller.OrderController;
import hu.adatb.controller.UserController;
import hu.adatb.model.User;
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

public class CitySalesDialog extends Stage {
    private OrderController orderController;
    private UserController userController;

    private TableView<User> table;

    public CitySalesDialog(OrderController orderController, UserController userController) {
        this.orderController = orderController;
        this.userController = userController;
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
        setTitle("Eladások városok szerint");
        show();
    }

    private void initializeTable(){
        table = new TableView<>();
        TableColumn<User, String> nameCol = new TableColumn<>("Város");
        TableColumn<User, String> salesCol = new TableColumn<>("Eladott mennyiség");

        nameCol.setCellValueFactory(data -> data.getValue().cityProperty());
        salesCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(orderController.getSalesPerCity(data.getValue().getCity()))));
        table.getColumns().addAll(nameCol, salesCol);
        List<User> list = userController.listCities();
        table.setItems(FXCollections.observableList(list));
    }

    private void refreshTable(){
        List<User> list = userController.listCities();
        table.setItems(FXCollections.observableList(list));
    }
}
