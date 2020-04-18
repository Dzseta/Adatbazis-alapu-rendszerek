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
    private ListCouponDialog parent;

    private Coupon coupon;
    private String oldCode;

    public ModifyCouponDialog(CouponController controller, Coupon coupon, ListCouponDialog parent){
        this.controller = controller;
        this.coupon = coupon;
        this.parent = parent;
        oldCode = coupon.getCode();
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));


        TextField newCodeField = new TextField();
        newCodeField.setText(coupon.getCode());
        Spinner<Integer> discountSpinner= new Spinner(0, 100, coupon.getDiscount());
        TextField genreField = new TextField();
        genreField.setText(coupon.getGenre());

        grid.add(new Text("Kupon kódja:"), 0, 0);
        grid.add(newCodeField, 1, 0);
        grid.add(new Text("Kedvezmény mértéke:"),0, 1);
        grid.add(discountSpinner, 1, 1);
        grid.add(new Text("Műfaj:"), 0, 2);
        grid.add(genreField, 1, 2);

        Button okButton = new Button("Módosítás");
        okButton.setOnAction(e -> {
            if(newCodeField.getText().contentEquals("")){
                Utils.showWarning("A kód nem lehet üres!");
                return;
            }
            if(genreField.getText().contentEquals("")){
                Utils.showWarning("A műfaj nem lehet üres!");
                return;
            }

            coupon.setCode(newCodeField.getText());
            coupon.setDiscount(discountSpinner.getValue());
            coupon.setGenre(genreField.getText());

            if(controller.modifyCoupon(coupon, oldCode)){
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
        setTitle("Kupon módosítása");
        show();
    }
}
