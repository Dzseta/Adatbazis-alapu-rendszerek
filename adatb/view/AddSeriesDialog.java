package hu.adatb.view;

import hu.adatb.controller.SeriesController;
import hu.adatb.model.Series;
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

public class AddSeriesDialog extends Stage {

    SeriesController controller;

    public AddSeriesDialog(SeriesController controller){
        this.controller = controller;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField nameField = new TextField();
        TextField isbnField = new TextField();

        grid.add(new Text("Sorozat neve:"), 0, 0);
        grid.add(nameField, 1 , 0);
        grid.add(new Text("Könyv ISBN száma:"), 0, 1);
        grid.add(isbnField, 1, 1);

        Button okButton = new Button("Sorozat hozzáadása a könyvhöz");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
            if(isbnField.getText().contentEquals("")){
                Utils.showWarning("Az ISBN szám nem lehet üres!");
                return;
            }
            if(nameField.getText().contentEquals("")){
                Utils.showWarning("A név nem lehet üres!");
                return;
            }

            int isbn;
            try{
                isbn = Integer.parseInt(isbnField.getText());
            } catch (Exception ex){
                Utils.showWarning("Az ISBN nem szám!");
                return;
            }

            if(controller.foreignKey(new Series(nameField.getText(), isbn))) {
                Utils.showWarning("Nincs ilyen könyv!");
                return;
            }

            if(controller.add(new Series(nameField.getText(), isbn))){
                close();
            } else {
                Utils.showWarning("Nem sikerült a sorozathoz adás!");
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
        setTitle("Sorozathoz adás");
        show();
    }
}
