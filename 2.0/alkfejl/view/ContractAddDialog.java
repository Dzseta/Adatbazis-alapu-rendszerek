package hu.alkfejl.view;

import hu.alkfejl.controller.PersonController;
import hu.alkfejl.model.Contract;
import hu.alkfejl.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ContractAddDialog extends Stage {

    private PersonController controller;
    private Contract autoBoundContract = new Contract();

    public ContractAddDialog(PersonController controller) {
        this.controller = controller;
        construct();
    }

    private void construct() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));

        TextField nameTF = new TextField();
        TextField posTF = new TextField();
        CheckBox partTime = new CheckBox("Part time");

        Bindings.bindBidirectional(nameTF.textProperty(), autoBoundContract.nameProperty());
        Bindings.bindBidirectional(posTF.textProperty(), autoBoundContract.posProperty());
        Bindings.bindBidirectional(partTime.selectedProperty(), autoBoundContract.partTimeProperty());

        gridPane.add(new Text("Nev:"), 0, 0);
        gridPane.add(nameTF, 1, 0);
        gridPane.add(new Text("Munkakor:"), 0, 1);
        gridPane.add(posTF, 1, 1);
        gridPane.add(partTime, 0, 2, 2, 1);

        Button okButton = new Button("OK");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
            if (nameTF.getText() == null || nameTF.getText().contentEquals("")) {
                Utils.showWarning("A nev nem lehet ures");
                return;
            }
            if (posTF.getText() == null || posTF.getText().contentEquals("")) {
                Utils.showWarning("A munkakort meg kell adni");
                return;
            }

            if (controller.addContract(autoBoundContract)) {
                close();
            } else {
                Utils.showWarning("Nem sikerult a mentes");
                return;
            }
        });

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> {
            autoBoundContract.setName("");
            autoBoundContract.setPos("");
            autoBoundContract.setPartTime(false);
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(e -> {
            close();
        });

        FlowPane buttonPane = new FlowPane();
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        buttonPane.setHgap(15);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(clearButton, okButton, cancelButton);

        gridPane.add(buttonPane, 0, 4, 2, 1);

        Scene scene = new Scene(gridPane);
        setScene(scene);
        setTitle("Szemely felvetele");
        show();
    }
}
