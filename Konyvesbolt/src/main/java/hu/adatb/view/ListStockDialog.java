package hu.adatb.view;

import hu.adatb.controller.BookController;
import hu.adatb.controller.ShopController;
import hu.adatb.controller.StockController;
import hu.adatb.model.Stock;
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

public class ListStockDialog extends Stage {
    private StockController controller;
    private BookController bookController;
    private ShopController shopController;

    private TableView<Stock> table;

    public ListStockDialog(StockController controller, BookController bookController, ShopController shopController){
        this.controller = controller;
        this.bookController = bookController;
        this.shopController = shopController;
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
        buttonPane.getChildren().addAll(okButton, modifyButton, deleteButton, cancelButton);

        grid.add(buttonPane, 0, 1, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Raktárak");
        show();
    }

    public void initializeTable(){
        table = new TableView<>();
        TableColumn<Stock, String> isbnCol = new TableColumn<>("ISBN");
        TableColumn<Stock, String> idCol = new TableColumn<>("Áruház azonosító");
        TableColumn<Stock, String> countCol = new TableColumn<>("Darabszám");

        isbnCol.setCellValueFactory(data -> data.getValue().isbnProperty().asString());
        idCol.setCellValueFactory(data -> data.getValue().idProperty().asString());
        countCol.setCellValueFactory(data -> data.getValue().quantityProperty().asString());

        table.getColumns().addAll(isbnCol, idCol, countCol);
        List<Stock> list = controller.list();
        table.setItems(FXCollections.observableList(list));

    }

    public void refreshTable(){
        List<Stock> list = controller.list();
        table.setItems(FXCollections.observableList(list));
    }

    public void deleteItem(){
        Stock stock = table.getSelectionModel().getSelectedItem();
        if(stock != null && Utils.showConfirmation("Biztos, hogy törli a kijelölt elemet?")){
            controller.delete(stock);
            refreshTable();
        }
    }

    public void modifyItem(){
        Stock stock = table.getSelectionModel().getSelectedItem();
        if(stock != null) {
            ModifyStockDialog dialog = new ModifyStockDialog(controller, bookController, shopController, stock, this);
        }
    }
}
