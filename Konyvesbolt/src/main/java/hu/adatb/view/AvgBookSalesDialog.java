package hu.adatb.view;

import hu.adatb.controller.BookController;
import hu.adatb.controller.OrderController;
import hu.adatb.model.Book;
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

public class AvgBookSalesDialog extends Stage {
    private BookController bookController;
    private OrderController orderController;

    private TableView<Book> table;

    public AvgBookSalesDialog(BookController bookController, OrderController orderController) {
        this.bookController = bookController;
        this.orderController = orderController;
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
        setTitle("Könyvek rendelésenkénti átlagos mennyisége");
        show();
    }

    private void initializeTable(){
        table = new TableView<>();
        TableColumn<Book, String> nameCol = new TableColumn<>("Könyv");
        TableColumn<Book, String> salesCol = new TableColumn<>("Átlagos rendelt mennyiség");

        nameCol.setCellValueFactory(data -> data.getValue().titleProperty());
        salesCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(orderController.getAvgBookSales(data.getValue().getIsbn()))));
        table.getColumns().addAll(nameCol, salesCol);
        List<Book> list = bookController.list();
        table.setItems(FXCollections.observableList(list));
    }

    private void refreshTable(){
        List<Book> list = bookController.list();
        table.setItems(FXCollections.observableList(list));
    }
}
