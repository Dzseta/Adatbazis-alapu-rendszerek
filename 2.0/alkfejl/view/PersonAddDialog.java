package hu.alkfejl.view;

import hu.alkfejl.controller.PersonController;
import hu.alkfejl.model.Person;
import hu.alkfejl.utils.Utils;
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

public class PersonAddDialog extends Stage {
    PersonController controller;

    public PersonAddDialog(PersonController controller) {
        this.controller = controller;
        construct();
    }

    private void construct() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));

        TextField nevTF = new TextField();
        TextField emailTF = new TextField();
        TextField evTF = new TextField();

        gridPane.add(new Text("Nev:"), 0, 0);
        gridPane.add(nevTF, 1, 0);
        gridPane.add(new Text("Email:"), 0, 1);
        gridPane.add(emailTF, 1, 1);
        gridPane.add(new Text("Szul. ev:"), 0, 3);
        gridPane.add(evTF, 1, 3);

        Button okButton = new Button("OK");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
            if (nevTF.getText().contentEquals("")) {
                Utils.showWarning("A nev nem lehet ures");
                return;
            }
            if (emailTF.getText().contentEquals("")) {
                Utils.showWarning("Az email cimet meg kell adni");
                return;
            }
            if (evTF.getText().contentEquals("")) {
                Utils.showWarning("A szul. evet meg kell adni");
                return;
            }
            int ev;

            try {
                ev = Integer.parseInt(evTF.getText());
            } catch (NumberFormatException nfe) {
                Utils.showWarning("A szul. ev nem szam");
                return;
            }

            if (controller.add(new Person(nevTF.getText(), emailTF.getText(), ev))) {
                close();
            } else {
                Utils.showWarning("Nem sikerult a mentes");
                return;
            }
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
        buttonPane.getChildren().addAll(okButton, cancelButton);

        gridPane.add(buttonPane, 0, 4, 2, 1);

        Scene scene = new Scene(gridPane);
        setScene(scene);
        setTitle("Szemely felvetele");
        show();
    }
}
