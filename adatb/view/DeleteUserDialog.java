package hu.adatb.view;

import hu.adatb.controller.SessionController;
import hu.adatb.controller.UserController;
import hu.adatb.util.Encoder;
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

public class DeleteUserDialog extends Stage {
    private UserController controller;
    private UserDataDialog parent;
    private SessionController sessionController;

    public DeleteUserDialog(UserController controller, UserDataDialog parent){
        this.controller = controller;
        this.parent = parent;
        sessionController = SessionController.getInstance();
        construct();
    }

    public void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField passwordTextField = new TextField();

        grid.add(new Text("A törléshez erősítse meg a jelszavát!"), 0, 0, 2, 1);
        grid.add(new Text("Jelszó:"), 0, 1);
        grid.add(passwordTextField, 1, 1);

        Button okButton = new Button("Törlés");
        okButton.setOnAction(e -> {
            String password = Encoder.GetMD5(passwordTextField.getText());
            if(password.equals(sessionController.getUser().getPassword())){
                if(Utils.showConfirmation("Biztos benne, hogy törli a felhasználóját? (Ez nem visszafordítható)")){
                    parent.userDeleted();
                    close();
                } else {
                    Utils.showWarning("A törlés sikertelen!");
                    close();
                }

            } else {
                Utils.showWarning("A jelszó nem megfelelő!");
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

        grid.add(buttonPane, 0, 2, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Törlés");
        show();

    }
}
