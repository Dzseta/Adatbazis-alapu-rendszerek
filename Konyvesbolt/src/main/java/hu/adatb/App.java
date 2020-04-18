package hu.adatb;

import hu.adatb.controller.*;
import hu.adatb.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    private UserController userController = new UserController();
    private CouponController couponController = new CouponController();
    PublisherController publisherController = new PublisherController();
    ShopController shopController = new ShopController();
    private SessionController sessionController = SessionController.getInstance();

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
        Menu couponMenu = new Menu("Kuponok");

        menuBar.getMenus().addAll(userMenu, bookMenu, publisherMenu, shopMenu, couponMenu);

        MenuItem addUser = new MenuItem("Regisztráció");
        MenuItem loginUser = new MenuItem("Bejelentkezés");
        MenuItem logoutUser = new MenuItem("Kijelentkezés");
        MenuItem userPage = new MenuItem("Saját adatok");
        MenuItem addBook = new MenuItem("Könyv felvétele");
        MenuItem addPublisher = new MenuItem("Kiadó felvétele");
        MenuItem listPublisher = new MenuItem("Kiadók listázása");
        MenuItem addShop = new MenuItem("Áruház felvétele");
        MenuItem listShop = new MenuItem("Áruházak listázása");
        MenuItem addCoupon = new MenuItem("Kupon felvétele");
        MenuItem listCoupon = new MenuItem("Kuponok listázása");

        addUser.setOnAction(e -> new AddUserDialog(userController));
        loginUser.setOnAction(e -> new LoginUserDialog(userController));
        userPage.setOnAction(e -> new UserDataDialog(userController));
        logoutUser.setOnAction(e -> sessionController.logout());

        addPublisher.setOnAction(e -> new AddPublisherDialog(publisherController));
        listPublisher.setOnAction(e -> new ListPublisherDialog(publisherController));

        addShop.setOnAction(e -> new AddShopDialog(shopController));
        listShop.setOnAction(e -> new ListShopDialog(shopController));

        addCoupon.setOnAction(e -> new AddCouponDialog(couponController));
        listCoupon.setOnAction(e -> new ListCouponDialog(couponController));

        addUser.visibleProperty().bind(sessionController.isLoggedIn().not());
        loginUser.visibleProperty().bind(sessionController.isLoggedIn().not());
        userPage.visibleProperty().bind(sessionController.isLoggedIn());
        logoutUser.visibleProperty().bind(sessionController.isLoggedIn());
        publisherMenu.disableProperty().bind(sessionController.isAdmin().not());
        shopMenu.disableProperty().bind(sessionController.isAdmin().not());
        couponMenu.disableProperty().bind(sessionController.isAdmin().not());

        userMenu.getItems().addAll(addUser, loginUser, userPage, logoutUser);
        bookMenu.getItems().addAll(addBook);
        publisherMenu.getItems().addAll(addPublisher, listPublisher);
        shopMenu.getItems().addAll(addShop, listShop);
        couponMenu.getItems().addAll(addCoupon, listCoupon);

        return menuBar;
    }

    public static void main(String[] args) {
        launch();
    }

}