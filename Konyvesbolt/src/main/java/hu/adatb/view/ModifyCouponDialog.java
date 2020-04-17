package hu.adatb.view;

import hu.adatb.controller.CouponController;
import hu.adatb.model.Coupon;
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

public class ModifyCouponDialog extends Stage {
    private CouponController controller;

    public ModifyCouponDialog(CouponController controller){
        this.controller = controller;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField oldCodeField = new TextField();
        TextField newCodeField = new TextField();
        Spinner<Integer> discountSpinner= new Spinner(0, 100, 0);
        TextField genreField = new TextField();

        grid.add(new Text("Kupon jelenlegi kódja:"), 0, 0);
        grid.add(oldCodeField, 1, 0);
        grid.add(new Text("Kupon új kódja:"), 0, 1);
        grid.add(newCodeField, 1, 1);
        grid.add(new Text("Kedvezmény mértéke:"),0, 2);
        grid.add(discountSpinner, 1, 2);
        grid.add(new Text("Műfaj:"), 0, 3);
        grid.add(genreField, 1, 3);

        Button okButton = new Button("Módosítás");
        okButton.setOnAction(e -> {
            if(newCodeField.getText().contentEquals("") || oldCodeField.getText().contentEquals("")){
                Utils.showWarning("A kód nem lehet üres!");
                return;
            }
            if(genreField.getText().contentEquals("")){
                Utils.showWarning("A műfaj nem lehet üres!");
                return;
            }

            if(controller.modifyCoupon(new Coupon(newCodeField.getText(), discountSpinner.getValue(), genreField.getText()),
                    oldCodeField.getText())){
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

        grid.add(buttonPane, 0, 4, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Kupon módosítása");
        show();
    }
}
