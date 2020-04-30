package hu.adatb.view;

import hu.adatb.controller.BookController;
import hu.adatb.controller.ShopController;
import hu.adatb.controller.StockController;
import hu.adatb.model.Book;
import hu.adatb.model.Shop;
import hu.adatb.model.Stock;
import hu.adatb.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ModifyStockDialog extends Stage {
    private StockController controller;
    private BookController bookController;
    private ShopController shopController;
    private ListStockDialog parent;

    private Stock stock;
    private int oldIsbn;
    private int oldId;

    public ModifyStockDialog(StockController controller, BookController bookController, ShopController shopController, Stock stock, ListStockDialog parent){
        this.controller = controller;
        this.bookController = bookController;
        this.shopController = shopController;
        this.stock = stock;
        this.parent = parent;
        oldIsbn = stock.getIsbn();
        oldId = stock.getId();
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));


        ComboBox<Integer> isbnField = new ComboBox<>(listBook());
        isbnField.getSelectionModel().select(stock.getIsbn());
        ComboBox<Integer> idField = new ComboBox<>(listShop());
        idField.getSelectionModel().select(stock.getId());
        TextField newCountField = new TextField();
        newCountField.setText(String.valueOf(stock.getQuantity()));

        grid.add(new Text("Új ISBN"), 0, 0);
        grid.add(isbnField, 1, 0);
        grid.add(new Text("Új áruház azonosító"),0, 1);
        grid.add(idField, 1, 1);
        grid.add(new Text("Darabszám"),0, 2);
        grid.add(newCountField, 1, 2);

        Button okButton = new Button("Módosítás");
        okButton.setOnAction(e -> {
            if(isbnField.getSelectionModel().isEmpty()){
                Utils.showWarning("Az ISBN nem lehet üres!");
                return;
            }
            if(idField.getSelectionModel().isEmpty()){
                Utils.showWarning("Az áruház azonosító nem lehet üres!");
                return;
            }
            if(newCountField.getText().contentEquals("")){
                Utils.showWarning("A darabszám nem lehet üres!");
                return;
            }

            int count;
            try{
                count = Integer.parseInt(newCountField.getText());
            } catch (Exception ex){
                Utils.showWarning("A darab nem szám!");
                return;
            }

            stock.setIsbn(isbnField.getSelectionModel().getSelectedItem());
            stock.setId(idField.getSelectionModel().getSelectedItem());
            stock.setQuantity(count);

            if(controller.update(stock, oldIsbn, oldId)){
                parent.refreshTable();
                close();
            } else {
                Utils.showWarning("Nem sikerült a módosítás!");
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

        grid.add(buttonPane, 0, 3, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Raktár módosítása");
        show();
    }

    private ObservableList<Integer> listBook(){
        ObservableList<Integer> list = null;

        List<Book> bookList = bookController.list();
        List<Integer> bookIsbns = new ArrayList<>();

        for(Book b: bookList){
            bookIsbns.add(b.getIsbn());
        }

        list = FXCollections.observableList(bookIsbns);

        return list;
    }

    private ObservableList<Integer> listShop(){
        ObservableList<Integer> list = null;

        List<Shop> shopList = shopController.list();
        List<Integer> shopIds = new ArrayList<>();

        for(Shop s: shopList){
            shopIds.add(s.getId());
        }

        list = FXCollections.observableList(shopIds);

        return list;
    }
}
