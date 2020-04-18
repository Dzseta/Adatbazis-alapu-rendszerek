package hu.adatb.view;

import hu.adatb.controller.SessionController;
import hu.adatb.controller.UserController;
import hu.adatb.model.User;
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

public class LoginUserDialog extends Stage {
    UserController controller;
    SessionController sessionController;

    public LoginUserDialog(UserController controller){
        this.controller = controller;
        sessionController = SessionController.getInstance();
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField emailField = new TextField();
        TextField passwordField = new TextField();

        grid.add(new Text("Email: "), 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(new Text("Jelszó:"), 0, 1);
        grid.add(passwordField, 1, 1);

        Button okButton = new Button("Bejelentkezés");
        okButton.setOnAction(e -> {
            if(emailField.getText().contentEquals("")){
                Utils.showWarning("Az email nem lehet üres!");
                return;
            }
            if(passwordField.getText().contentEquals("")){
                Utils.showWarning("A jelszó nem lehet üres!");
                return;
            }

            User user = new User();
            user.setEmail(emailField.getText());
            user.setPassword(Encoder.GetMD5(passwordField.getText()));
            if(controller.login(user)){
                user = controller.getUser(user.getEmail());
                sessionController.login(user, controller.isAdmin(user.getEmail()));
                close();
            } else {
                Utils.showWarning("Sikertelen bejelentkezés!");
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

        grid.add(buttonPane, 0, 2, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Bejelentkezés");
        show();
    }
}
