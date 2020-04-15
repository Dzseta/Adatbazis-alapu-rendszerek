package hu.adatb;

import hu.adatb.controller.UserController;
import hu.adatb.view.AddUserDialog;
import hu.adatb.view.LoginUserDialog;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;

/**
 * JavaFX App
 */
public class App extends Application {
    UserController controller = new UserController();

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(createMenuBar(stage));

        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private MenuBar createMenuBar(Stage stage){
        MenuBar menuBar = new MenuBar();
        Menu userMenu = new Menu("Felhasználó");
        Menu bookMenu = new Menu("Könyv");

        menuBar.getMenus().addAll(userMenu, bookMenu);

        MenuItem addUser = new MenuItem("Regisztráció");
        MenuItem loginUser = new MenuItem("Bejelentkezés");
        MenuItem logoutUser = new MenuItem("Kijelentkezés");
        MenuItem addBook = new MenuItem("Könyv felvétele");

        addUser.setOnAction(e -> new AddUserDialog(controller));
        loginUser.setOnAction(e -> new LoginUserDialog(controller));

        userMenu.getItems().addAll(addUser, loginUser, logoutUser);
        bookMenu.getItems().addAll(addBook);

        return menuBar;
    }

    public static void main(String[] args) {
        launch();
    }

}