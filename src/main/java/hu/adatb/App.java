package hu.adatb;

import hu.adatb.controller.PublisherController;
import hu.adatb.controller.ShopController;
import hu.adatb.controller.UserController;
import hu.adatb.model.User;
import hu.adatb.view.*;
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
    UserController UserController = new UserController();
    PublisherController PublisherController = new PublisherController();
    hu.adatb.controller.ShopController ShopController = new ShopController();

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
        Menu publisherMenu = new Menu("Kiadó");
        Menu shopMenu = new Menu("Áruházak");

        menuBar.getMenus().addAll(userMenu, bookMenu, publisherMenu, shopMenu);

        MenuItem addUser = new MenuItem("Regisztráció");
        MenuItem loginUser = new MenuItem("Bejelentkezés");
        MenuItem logoutUser = new MenuItem("Kijelentkezés");
        MenuItem addBook = new MenuItem("Könyv felvétele");
        MenuItem addPublisher = new MenuItem("Kiadó felvétele");
        MenuItem deletePublisher = new MenuItem("Kiadó törlése");
        MenuItem updatePublisher = new MenuItem("Kiadó adatainak frissítése");
        MenuItem listPublisher = new MenuItem("Kiadók listázása");
        MenuItem addShop = new MenuItem("Áruház felvétele");
        MenuItem deleteShop = new MenuItem("Áruház törlése");
        MenuItem updateShop = new MenuItem("Áruház adatainak frissítése");
        MenuItem listShop = new MenuItem("Áruházak listázása");

        addUser.setOnAction(e -> new AddUserDialog(UserController));
        loginUser.setOnAction(e -> new LoginUserDialog(UserController));
        addPublisher.setOnAction(e -> new AddPublisherDialog(PublisherController));
        deletePublisher.setOnAction(e -> new DeletePublisherDialog(PublisherController));
        updatePublisher.setOnAction(e -> new UpdatePublisherDialog(PublisherController));
        listPublisher.setOnAction(e -> new ListPublisherDialog(PublisherController));
        addShop.setOnAction(e -> new AddShopDialog(ShopController));
        deleteShop.setOnAction(e -> new DeleteShopDialog(ShopController));
        updateShop.setOnAction(e -> new UpdateShopDialog(ShopController));
        listShop.setOnAction(e -> new ListShopDialog(ShopController));

        userMenu.getItems().addAll(addUser, loginUser, logoutUser);
        bookMenu.getItems().addAll(addBook);
        publisherMenu.getItems().addAll(addPublisher,deletePublisher, updatePublisher, listPublisher);
        shopMenu.getItems().addAll(addShop, deleteShop, updateShop, listShop);

        return menuBar;
    }

    public static void main(String[] args) {
        launch();
    }

}