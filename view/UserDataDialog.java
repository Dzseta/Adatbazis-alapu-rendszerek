package hu.adatb.view;

import hu.adatb.controller.SessionController;
import hu.adatb.controller.UserController;
import hu.adatb.model.User;
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

public class UserDataDialog extends Stage {
    private SessionController sessionController;
    private UserController controller;
    private User user;


    public UserDataDialog(UserController controller){
        sessionController = SessionController.getInstance();
        user = sessionController.getUser();
        this.controller = controller;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField vnevField = new TextField();
        vnevField.setDisable(true);
        vnevField.setText(user.getVnev());

        TextField knevField = new TextField();
        knevField.setDisable(true);
        knevField.setText(user.getKnev());

        TextField emailField = new TextField();
        emailField.setDisable(true);
        emailField.setText(user.getEmail());

        TextField passwordField = new TextField();
        passwordField.setDisable(true);
        passwordField.setText("");

        TextField irszField = new TextField();
        irszField.setDisable(true);
        irszField.setText(String.valueOf(user.getIrsz()));

        TextField cityField = new TextField();
        cityField.setDisable(true);
        cityField.setText(user.getCity());

        TextField streetField = new TextField();
        streetField.setDisable(true);
        streetField.setText(user.getStreet());

        TextField houseField = new TextField();
        houseField.setDisable(true);
        houseField.setText(user.getHouse());

        TextField dateField = new TextField();
        dateField.setDisable(true);
        dateField.setText(user.getDate().toString());

        grid.add(new Text("Vezetéknév:"), 0, 0);
        grid.add(vnevField, 1 , 0);
        grid.add(new Text("Keresztnév:"), 0, 1);
        grid.add(knevField, 1, 1);
        grid.add(new Text("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Text("Jelszó:"), 0, 3);
        grid.add(passwordField, 1, 3);
        grid.add(new Text("Irányítószám:"), 0, 4);
        grid.add(irszField, 1, 4);
        grid.add(new Text("Város:"), 0, 5);
        grid.add(cityField, 1, 5);
        grid.add(new Text("Utca:"), 0,  6);
        grid.add(streetField, 1, 6);
        grid.add(new Text("Házszám:"), 0, 7);
        grid.add(houseField, 1, 7);
        grid.add(new Text("Születési dátum:"), 0, 8);
        grid.add(dateField, 1, 8);

        Button modifyButton = new Button("Módosítás");
        modifyButton.setOnAction(e -> new ModifyUserDialog(controller, user, this));

        Button deleteButton = new Button("Felhasználó törlése");
        deleteButton.setOnAction(e -> new DeleteUserDialog(controller, this));

        FlowPane buttonPane = new FlowPane();
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        buttonPane.setHgap(15);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(modifyButton, deleteButton);

        grid.add(buttonPane, 0, 9, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Saját adatok");
        show();

    }

    public void userDeleted(){
        if(!controller.deleteUSer(user.getEmail())){
            Utils.showWarning("A törlés sikertelen!");
        }
        sessionController.logout();
        close();
    }

    public void userModified(){
        sessionController.logout();
        close();
    }
}
