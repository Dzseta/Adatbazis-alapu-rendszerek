package hu.adatb.view;

import hu.adatb.controller.CouponController;
import hu.adatb.model.Coupon;
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

public class ListCouponDialog extends Stage {
    private CouponController controller;

    private TableView<Coupon> table;

    public ListCouponDialog(CouponController controller){
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
        setTitle("Kuponok");
        show();
    }

    public void initializeTable(){
        table = new TableView<>();
        TableColumn<Coupon, String> codeCol = new TableColumn<>("Kód");
        TableColumn<Coupon, String> discountCol = new TableColumn<>("Kedvezmény mértéke");
        TableColumn<Coupon, String> genreCol = new TableColumn<>("Műfaj");

        codeCol.setCellValueFactory(data -> data.getValue().codeProperty());
        discountCol.setCellValueFactory(data -> data.getValue().discountProperty().asString());
        genreCol.setCellValueFactory(data -> data.getValue().genreProperty());

        table.getColumns().addAll(codeCol, discountCol, genreCol);
        List<Coupon> list = controller.list();
        table.setItems(FXCollections.observableList(list));

    }

    public void refreshTable(){
        List<Coupon> list = controller.list();
        table.setItems(FXCollections.observableList(list));
    }

    public void deleteItem(){
        Coupon coupon = table.getSelectionModel().getSelectedItem();
        if(coupon != null && Utils.showConfirmation("Biztos, hogy törli a kijelölt elemet?")){
            controller.deleteCoupon(coupon.getCode());
            refreshTable();
        }
    }

    public void modifyItem(){
        Coupon coupon = table.getSelectionModel().getSelectedItem();
        if(coupon != null) {
            ModifyCouponDialog dialog = new ModifyCouponDialog(controller, coupon, this);
        }
    }
}
