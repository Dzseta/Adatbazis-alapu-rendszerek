package hu.adatb.view;

import hu.adatb.controller.ShopController;
import hu.adatb.model.Shop;
import hu.adatb.util.Utils;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class ListShopDialog extends Stage {

    private ShopController controller;

    private TableView<Shop> table;

    public ListShopDialog(ShopController controller){
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
        setTitle("Áruházak");
        show();
    }

    public void initializeTable(){
        table = new TableView<>();
        TableColumn<Shop, String> idCol = new TableColumn<>("Azonosító");
        TableColumn<Shop, String> nameCol = new TableColumn<>("Név");
        TableColumn<Shop, String> zipcodeCol = new TableColumn<>("Irányítószám");
        TableColumn<Shop, String> cityCol = new TableColumn<>("Város");
        TableColumn<Shop, String> streetCol = new TableColumn<>("Utca");
        TableColumn<Shop, String> houseCol = new TableColumn<>("Házszám");

        idCol.setCellValueFactory(data -> data.getValue().idProperty().asString());
        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        zipcodeCol.setCellValueFactory(data -> data.getValue().zipcodeProperty().asString());
        cityCol.setCellValueFactory(data -> data.getValue().cityProperty());
        streetCol.setCellValueFactory(data -> data.getValue().streetProperty());
        houseCol.setCellValueFactory(data -> data.getValue().hnumberProperty());

        table.getColumns().addAll(idCol, nameCol, zipcodeCol, cityCol, streetCol, houseCol);
        List<Shop> list = controller.list();
        table.setItems(FXCollections.observableList(list));

    }

    public void refreshTable(){
        List<Shop> list = controller.list();
        table.setItems(FXCollections.observableList(list));
    }

    public void deleteItem(){
        Shop shop = table.getSelectionModel().getSelectedItem();
        if(shop != null && Utils.showConfirmation("Biztos, hogy törli a kijelölt elemet?")){
            controller.delete(shop);
            refreshTable();
        }
    }

    public void modifyItem(){
        Shop shop = table.getSelectionModel().getSelectedItem();
        if(shop != null){
            UpdateShopDialog dialog = new UpdateShopDialog(controller, shop, this);
        }
    }
}
