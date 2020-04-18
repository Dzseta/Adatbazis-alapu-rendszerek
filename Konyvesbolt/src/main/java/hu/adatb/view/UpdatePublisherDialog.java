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

public class UpdatePublisherDialog extends Stage {

    private PublisherController controller;
    private ListPublisherDialog parent;

    private Publisher publisher;
    private String oldName;

    public UpdatePublisherDialog(PublisherController controller, Publisher publisher, ListPublisherDialog parent){
        this.controller = controller;
        this.parent = parent;
        this.publisher = publisher;
        oldName = publisher.getName();
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField nameField = new TextField();
        nameField.setText(publisher.getName());
        TextField zipcodeField = new TextField();
        zipcodeField.setText(String.valueOf(publisher.getZipcode()));
        TextField cityField = new TextField();
        cityField.setText(publisher.getCity());
        TextField streetField = new TextField();
        streetField.setText(publisher.getStreet());
        TextField hnumberField = new TextField();
        hnumberField.setText(publisher.getHnumber());

        grid.add(new Text("Kiadó neve:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Text("Irányítószám:"), 0, 1);
        grid.add(zipcodeField, 1, 1);
        grid.add(new Text("Város:"), 0, 2);
        grid.add(cityField, 1, 2);
        grid.add(new Text("Utca:"), 0, 3);
        grid.add(streetField, 1, 3);
        grid.add(new Text("Házszám:"), 0, 4);
        grid.add(hnumberField, 1, 4);

        Button okButton = new Button("Módosítás");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
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

            publisher.setName(nameField.getText());
            publisher.setZipcode(zipcode);
            publisher.setCity(cityField.getText());
            publisher.setStreet(streetField.getText());
            publisher.setHnumber(hnumberField.getText());

            if(controller.update(publisher, oldName)){
                parent.refreshTable();
                close();
            } else {
                Utils.showWarning("Nem sikerült a kiadó frissítése!");
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

        grid.add(buttonPane, 0, 5, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Kiadó frissítése");
        show();
    }
}
