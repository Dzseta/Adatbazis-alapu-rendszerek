package hu.adatb.view;

import hu.adatb.controller.CouponController;
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

public class DeleteCouponDialog extends Stage {
    private CouponController controller;

    public DeleteCouponDialog(CouponController controller){
        this.controller = controller;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField codeField = new TextField();

        grid.add(new Text("Törlendő kupon kódja:"), 0, 0);
        grid.add(codeField, 1, 0);

        Button okButton = new Button("Törlés");
        okButton.setOnAction(e -> {
            if(controller.deleteCoupon(codeField.getText())){
                close();
            } else {
                Utils.showWarning("A törlés sikertelen!");
                return;
            }
        });

        Button cancelButton = new Button("Mégse");
        cancelButton.setOnAction(e -> {
            close();
        });

        FlowPane buttonPane = new FlowPane();
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        buttonPane.setHgap(15);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(okButton, cancelButton);

        grid.add(buttonPane, 0, 1, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Kupon törlése");
        show();
    }
}
