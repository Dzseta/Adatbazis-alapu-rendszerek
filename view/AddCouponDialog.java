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

public class AddCouponDialog extends Stage {
    private CouponController controller;

    public AddCouponDialog(CouponController controller){
        this.controller = controller;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField codeField = new TextField();
        Spinner<Integer> discountSpinner= new Spinner(0, 100, 0);
        TextField genreField = new TextField();

        grid.add(new Text("Kód:"), 0, 0);
        grid.add(codeField, 1, 0);
        grid.add(new Text("Kedvezmény mértéke:"), 0, 1);
        grid.add(discountSpinner, 1, 1);
        grid.add(new Text("Műfaj:"), 0, 2);
        grid.add(genreField, 1, 2);

        Button okButton = new Button("Hozzáadás");
        okButton.setOnAction(e -> {
            if(codeField.getText().contentEquals("")){
                Utils.showWarning("A kód nem lehet üres!");
                return;
            }
            if(codeField.getText().length() > 10){
                Utils.showWarning("A kód maximum 10 jegyű lehet!");
                return;
            }
            if(genreField.getText().contentEquals("")){
                Utils.showWarning("A műfaj nem lehet üres!");
                return;
            }
            if(controller.addCoupon(new Coupon(codeField.getText(), discountSpinner.getValue(), genreField.getText()))){
                close();
            } else {
                Utils.showWarning("Nem sikerült a hozzáadás!");
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
        setTitle("Kupon hozzáadása");
        show();
    }
}
