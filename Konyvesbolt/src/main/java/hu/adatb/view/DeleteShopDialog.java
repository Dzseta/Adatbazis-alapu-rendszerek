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

public class DeleteShopDialog extends Stage {

    ShopController controller;

    public DeleteShopDialog(ShopController controller){
        this.controller = controller;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField idField = new TextField();

        grid.add(new Text("Áruház azonosítója:"), 0, 0);
        grid.add(idField, 1 , 0);

        Button okButton = new Button("Áruház törlése");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
            if(idField.getText().contentEquals("")){
                Utils.showWarning("Az azonosító nem lehet üres!");
                return;
            }

            int id;
            try{
                id = Integer.parseInt(idField.getText());
            } catch (Exception ex){
                Utils.showWarning("Az azonosító nem szám!");
                return;
            }

            if(controller.delete(new Shop(id))){
                close();
            } else {
                Utils.showWarning("Nem sikerült az áruház törlése!");
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
        setTitle("Áruház törlése");
        show();
    }
}
