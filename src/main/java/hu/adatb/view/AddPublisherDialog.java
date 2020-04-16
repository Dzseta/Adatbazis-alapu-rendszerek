package hu.adatb.view;

import hu.adatb.controller.PublisherController;
import hu.adatb.model.Publisher;
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

public class AddPublisherDialog extends Stage {

    PublisherController controller;

    public AddPublisherDialog(PublisherController controller){
        this.controller = controller;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField nameField = new TextField();
        TextField zipcodeField = new TextField();
        TextField cityField = new TextField();
        TextField streetField = new TextField();
        TextField hnumberField = new TextField();

        grid.add(new Text("Kiadó neve:"), 0, 0);
        grid.add(nameField, 1 , 0);
        grid.add(new Text("Irányítószám:"), 0, 1);
        grid.add(zipcodeField, 1, 1);
        grid.add(new Text("Város:"), 0, 2);
        grid.add(cityField, 1, 2);
        grid.add(new Text("Utca:"), 0, 3);
        grid.add(streetField, 1, 3);
        grid.add(new Text("Házszám:"), 0, 4);
        grid.add(hnumberField, 1, 4);

        Button okButton = new Button("Kiadó felvitele");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
            if(nameField.getText().contentEquals("")){
                Utils.showWarning("A név nem lehet üres!");
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

            if(controller.add(new Publisher(nameField.getText(), zipcode, cityField.getText(), streetField.getText(), hnumberField.getText()))){
                close();
            } else {
                Utils.showWarning("Nem sikerült a kiadó felvétele!");
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
        setTitle("Kiadó felvitele");
        show();
    }
}
