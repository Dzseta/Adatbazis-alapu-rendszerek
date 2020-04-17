package hu.adatb.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Utils {
    public static void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static boolean showConfirmation(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Megerősítés");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            return true;
        }

        return false;
    }

    public static LocalDate toLocalDate(String text){
        LocalDate date = null;

        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(text != null){
            date = LocalDate.parse(text, dt);
        }

        return date;
    }
}
