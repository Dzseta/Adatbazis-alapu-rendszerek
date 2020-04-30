package hu.adatb.view;

import hu.adatb.controller.SeriesController;
import hu.adatb.model.Series;
import hu.adatb.util.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ModifySeriesDialog extends Stage {
    private SeriesController controller;
    private ListSeriesDialog parent;

    private Series series;
    private String oldName;
    private int oldIsbn;

    public ModifySeriesDialog(SeriesController controller, Series series, ListSeriesDialog parent){
        this.controller = controller;
        this.series = series;
        this.parent = parent;
        oldName = series.getName();
        oldIsbn = series.getIsbn();
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));


        TextField newNameField = new TextField();
        newNameField.setText(series.getName());
        TextField isbnField = new TextField();
        isbnField.setText(String.valueOf(series.getIsbn()));

        grid.add(new Text("Új sorozatcím"), 0, 0);
        grid.add(newNameField, 1, 0);
        grid.add(new Text("ISBN szám"),0, 1);
        grid.add(isbnField, 1, 1);

        Button okButton = new Button("Módosítás");
        okButton.setOnAction(e -> {
            if(newNameField.getText().contentEquals("")){
                Utils.showWarning("A név nem lehet üres!");
                return;
            }
            if(isbnField.getText().contentEquals("")){
                Utils.showWarning("Az isbn nem lehet üres!");
                return;
            }

            int isbn;
            try{
                isbn = Integer.parseInt(isbnField.getText());
            } catch (Exception ex){
                Utils.showWarning("Az ISBN nem szám!");
                return;
            }

            series.setName(newNameField.getText());
            series.setIsbn(isbn);

            if(controller.update(series, oldName, oldIsbn)){
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
        setTitle("Sorozathoz adás");
        show();
    }
}
