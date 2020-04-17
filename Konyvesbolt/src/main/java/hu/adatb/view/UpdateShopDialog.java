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

    ShopController controller;

    public UpdateShopDialog(ShopController controller){
        this.controller = controller;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField nameField = new TextField();
        TextField idField = new TextField();
        TextField zipcodeField = new TextField();
        TextField cityField = new TextField();
        TextField streetField = new TextField();
        TextField hnumberField = new TextField();
        TextField oldIdField = new TextField();

        grid.add(new Text("Áruház azonosítója:"), 0, 0);
        grid.add(oldIdField, 1, 0);
        grid.add(new Text("Áruház új neve:"), 0, 1);
        grid.add(nameField, 1 , 1);
        grid.add(new Text("Áruház új azonosítója:"), 0, 2);
        grid.add(idField, 1, 2);
        grid.add(new Text("Új irányítószám:"), 0, 3);
        grid.add(zipcodeField, 1, 3);
        grid.add(new Text("Új város:"), 0, 4);
        grid.add(cityField, 1, 4);
        grid.add(new Text("Új utca:"), 0, 5);
        grid.add(streetField, 1, 5);
        grid.add(new Text("Új házszám:"), 0, 6);
        grid.add(hnumberField, 1, 6);

        Button okButton = new Button("Áruház adatainak frissítése");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
            if(oldIdField.getText().contentEquals("")){
                Utils.showWarning("Az azonosító nem lehet üres!");
                return;
            }
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
            int oldId;
            try{
                oldId = Integer.parseInt(oldIdField.getText());
            } catch (Exception ex){
                Utils.showWarning("Az azonosító nem szám!");
                return;
            }

            if(controller.update(new Shop(id, zipcode, cityField.getText(), streetField.getText(), hnumberField.getText(), nameField.getText()), oldId)){
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
