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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class ModifyUserDialog extends Stage {
    private UserController controller;
    private User user;
    private UserDataDialog parent;
    private String emailBefore;

    public ModifyUserDialog(UserController controller, User user, UserDataDialog parent){
        this.controller = controller;
        this.user = user;
        this.parent = parent;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField vnevField = new TextField();
        vnevField.setText(user.getVnev());
        TextField knevField = new TextField();
        knevField.setText(user.getKnev());
        TextField emailField = new TextField();
        emailField.setText(user.getEmail());
        emailBefore = user.getEmail();
        TextField passwordField = new TextField();
        TextField irszField = new TextField();
        irszField.setText(String.valueOf(user.getIrsz()));
        TextField cityField = new TextField();
        cityField.setText(user.getCity());
        TextField streetField = new TextField();
        streetField.setText(user.getStreet());
        TextField houseField = new TextField();
        houseField.setText(user.getHouse());
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(Utils.toLocalDate(user.getDate().toString()));

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
        grid.add(datePicker, 1, 8);

        Button okButton = new Button("Módosítás");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
            if(vnevField.getText().contentEquals("")){
                Utils.showWarning("A név nem lehet üres!");
                return;
            }
            if(knevField.getText().contentEquals("")){
                Utils.showWarning("A név nem lehet üres!");
                return;
            }
            if(emailField.getText().contentEquals("")){
                Utils.showWarning("Az email nem lehet üres!");
                return;
            }
            if(passwordField.getText().contentEquals("")){
                Utils.showWarning("A jelszó nem lehet üres!");
                return;
            }
            if(passwordField.getText().length() < 6){
                Utils.showWarning("A jelszónak legalább 6 karakter hosszúnak kell lennie!");
                return;
            }
            if(irszField.getText().contentEquals("")){
                Utils.showWarning("Az irányítószám nem lehet üres!");
                return;
            }
            if(cityField.getText().contentEquals("")){
                Utils.showWarning("A város nem lehet üres!");
                return;
            }
            if(streetField.getText().contentEquals("")){
                Utils.showWarning("Az utca nem lehet üres!");
                return;
            }
            if(houseField.getText().contentEquals("")){
                Utils.showWarning("A házszám nem lehet üres!");
                return;
            }
            if(datePicker.getValue() == null){
                Utils.showWarning("A dátum nem lehet üres!");
                return;
            }

            int irsz;
            try{
                irsz = Integer.parseInt(irszField.getText());
            } catch (Exception ex){
                Utils.showWarning("Az irányítószám nem szám!");
                return;
            }
            Instant instant = Instant.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);

            user.setVnev(vnevField.getText());
            user.setKnev(knevField.getText());
            user.setPassword(passwordField.getText());
            user.setEmail(emailField.getText());
            user.setIrsz(irsz);
            user.setCity(cityField.getText());
            user.setStreet(streetField.getText());
            user.setHouse(houseField.getText());
            user.setDate(date);

            if(controller.modifyUser(user, emailBefore)){
                parent.userModified();
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

        grid.add(buttonPane, 0, 9, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Adatok Módosítása");
        show();
    }
}
