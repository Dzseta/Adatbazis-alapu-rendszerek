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

public class DeletePublisherDialog extends Stage {

    PublisherController controller;

    public DeletePublisherDialog(PublisherController controller){
        this.controller = controller;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField nameField = new TextField();

        grid.add(new Text("Kiadó neve:"), 0, 0);
        grid.add(nameField, 1 , 0);

        Button okButton = new Button("Kiadó törlése");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
            if(nameField.getText().contentEquals("")){
                Utils.showWarning("A név nem lehet üres!");
                return;
            }

            if(controller.delete(new Publisher(nameField.getText()))){
                close();
            } else {
                Utils.showWarning("Nem sikerült a kiadó törlése!");
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
        setTitle("Kiadó törlése");
        show();
    }
}
