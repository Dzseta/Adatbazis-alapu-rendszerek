package hu.adatb.view;

import hu.adatb.controller.OrderController;
import hu.adatb.controller.SessionController;
import hu.adatb.model.Order;
import hu.adatb.util.Utils;
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

public class ListOrderDialog extends Stage {
    private OrderController orderController;
    private SessionController sessionController;

    private TableView<Order> table;

    public ListOrderDialog(OrderController orderController) {
        this.orderController = orderController;
        sessionController = SessionController.getInstance();
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

        Button cancelButton = new Button("Mégse");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(e -> {
            close();
        });

        FlowPane buttonPane = new FlowPane();
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        buttonPane.setHgap(15);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(okButton, deleteButton, cancelButton);

        grid.add(buttonPane, 0, 1, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Rendelések");
        show();
    }

    private void initializeTable(){
        table = new TableView<>();
        TableColumn<Order, String> isbnCol = new TableColumn<>("Isbn");
        TableColumn<Order, String> amountCol = new TableColumn<>("Darabszám");
        TableColumn<Order, String> timeCol = new TableColumn<>("Rendelés ideje");
        TableColumn<Order, String> subtotalCol = new TableColumn<>("Részösszeg");

        isbnCol.setCellValueFactory(data -> data.getValue().isbnProperty().asString());
        amountCol.setCellValueFactory(data -> data.getValue().quantityProperty().asString());
        timeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTimeOrder().toString()));
        subtotalCol.setCellValueFactory(data -> data.getValue().subtotalProperty().asString());


        table.getColumns().addAll(isbnCol, amountCol, timeCol, subtotalCol);
        List<Order> list = orderController.list(sessionController.getUser().getEmail());
        table.setItems(FXCollections.observableList(list));
    }

    private void refreshTable(){
        List<Order> list = orderController.list(sessionController.getUser().getEmail());
        table.setItems(FXCollections.observableList(list));
    }

    private void deleteItem(){
        Order order = table.getSelectionModel().getSelectedItem();
        if(order != null && Utils.showConfirmation("Biztos, hogy törli a kijelölt elemet?")){
            orderController.delete(order);
            refreshTable();
        }
    }
}
