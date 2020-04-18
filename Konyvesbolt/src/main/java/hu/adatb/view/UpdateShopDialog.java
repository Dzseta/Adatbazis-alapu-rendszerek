package hu.adatb.view;

import hu.adatb.controller.ShopController;
import hu.adatb.model.Shop;
import hu.adatb.util.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UpdateShopDialog extends Stage {

    private ShopController controller;
    private ListShopDialog parent;

    private Shop shop;
    private int oldId;

    public UpdateShopDialog(ShopController controller, Shop shop, ListShopDialog parent){
        this.controller = controller;
        this.parent = parent;
        this.shop = shop;
        oldId = shop.getId();
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField nameField = new TextField();
        nameField.setText(shop.getName());
        TextField idField = new TextField();
        idField.setText(String.valueOf(shop.getId()));
        TextField zipcodeField = new TextField();
        zipcodeField.setText(String.valueOf(shop.getZipcode()));
        TextField cityField = new TextField();
        cityField.setText(shop.getCity());
        TextField streetField = new TextField();
        streetField.setText(shop.getStreet());
        TextField hnumberField = new TextField();
        hnumberField.setText(shop.getHnumber());

        grid.add(new Text("Áruház azonosítója:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Text("Áruház Neve:"), 0, 1);
        grid.add(nameField, 1 , 1);
        grid.add(new Text("Irányítószám:"), 0, 2);
        grid.add(zipcodeField, 1, 2);
        grid.add(new Text("Város:"), 0, 3);
        grid.add(cityField, 1, 3);
        grid.add(new Text("Utca:"), 0, 4);
        grid.add(streetField, 1, 4);
        grid.add(new Text("Házszám:"), 0, 5);
        grid.add(hnumberField, 1, 5);

        Button okButton = new Button("Módosítás");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
            if(idField.getText().contentEquals("")){
                Utils.showWarning("Az új azonosító nem lehet üres!");
                return;
            }
            if(nameField.getText().contentEquals("")){
                Utils.showWarning("Az új név nem lehet üres!");
                return;
            }
            if(zipcodeField.getText().contentEquals("")){
                Utils.showWarning("Az irányítószám nem lehet üres!");
                return;
            }
            if(cityField.getText().contentEquals("")){
                Utils.showWarning("A város nem lehet üres!");
                return;
            }
            if(streetField.getText().contentEquals("")){
                Utils.showWarning("Az utca nem lehet üres!");
                return;
            }
            if(hnumberField.getText().contentEquals("")){
                Utils.showWarning("A házszám nem lehet üres!");
                return;
            }

            int zipcode;
            try{
                zipcode = Integer.parseInt(zipcodeField.getText());
            } catch (Exception ex){
                Utils.showWarning("Az irányítószám nem szám!");
                return;
            }
            int id;
            try{
                id = Integer.parseInt(idField.getText());
            } catch (Exception ex){
                Utils.showWarning("Az új azonosító nem szám!");
                return;
            }

            shop.setName(nameField.getText());
            shop.setId(id);
            shop.setZipcode(zipcode);
            shop.setCity(cityField.getText());
            shop.setStreet(streetField.getText());
            shop.setHnumber(hnumberField.getText());

            if(controller.update(shop, oldId)){
                parent.refreshTable();
                close();
            } else {
                Utils.showWarning("Nem sikerült az áruház frissítése!");
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

        grid.add(buttonPane, 0, 9, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Áruház frissítése");
        show();
    }
}
