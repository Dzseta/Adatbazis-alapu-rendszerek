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
    private PublisherController publisherController = new PublisherController();
    private ShopController shopController = new ShopController();
    private BookController bookController = new BookController();
    private GenreController genreController = new GenreController();
    private StockController stockController = new StockController();
    private OrderController orderController = new OrderController();
    private SeriesController seriesController = new SeriesController();
    private AuthorController authorController = new AuthorController();
    private SessionController sessionController = SessionController.getInstance();

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(createMenuBar(stage));

        Scene scene = new Scene(root, 960, 520);
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
        Menu genreMenu = new Menu("Műfajok");
        Menu stockMenu = new Menu("Raktár");
        Menu orderMenu = new Menu("Rendelések");
        Menu seriesMenu = new Menu("Könyvsorozatok");

        menuBar.getMenus().addAll(userMenu, bookMenu, publisherMenu, shopMenu, couponMenu, genreMenu, stockMenu, orderMenu, seriesMenu);

        MenuItem addUser = new MenuItem("Regisztráció");
        MenuItem loginUser = new MenuItem("Bejelentkezés");
        MenuItem logoutUser = new MenuItem("Kijelentkezés");
        MenuItem userPage = new MenuItem("Saját adatok");
        MenuItem addBook = new MenuItem("Könyv felvétele");
        MenuItem listBook = new MenuItem("Könyvek listázása");
        MenuItem addPublisher = new MenuItem("Kiadó felvétele");
        MenuItem listPublisher = new MenuItem("Kiadók listázása");
        MenuItem addShop = new MenuItem("Áruház felvétele");
        MenuItem listShop = new MenuItem("Áruházak listázása");
        MenuItem addCoupon = new MenuItem("Kupon felvétele");
        MenuItem listCoupon = new MenuItem("Kuponok listázása");
        MenuItem addGenre = new MenuItem("Műfaj felvétele");
        MenuItem listGenre = new MenuItem("Műfajok listázása");
        MenuItem addStock = new MenuItem("Könyv felvétele a raktárba");
        MenuItem listStock = new MenuItem("Raktár tartalmának listázása");
        MenuItem addOrder = new MenuItem("Rendelés felvétele");
        MenuItem listOrder = new MenuItem("Rendelések listázása");
        MenuItem addSeries = new MenuItem("Könyvsorozat felvétele");
        MenuItem listSeries = new MenuItem("Könyvsorozatok listázása");
        MenuItem addAuthor = new MenuItem("Szerző felvétele");
        MenuItem listAuthor = new MenuItem("Kuponok listázása");

        addUser.setOnAction(e -> new AddUserDialog(userController));
        loginUser.setOnAction(e -> new LoginUserDialog(userController));
        userPage.setOnAction(e -> new UserDataDialog(userController));
        logoutUser.setOnAction(e -> sessionController.logout());

        addBook.setOnAction(e -> new AddBookDialog(bookController, publisherController, authorController));
        listBook.setOnAction(e -> new ListBookDialog(bookController, publisherController, authorController));

        addPublisher.setOnAction(e -> new AddPublisherDialog(publisherController));
        listPublisher.setOnAction(e -> new ListPublisherDialog(publisherController));

        addShop.setOnAction(e -> new AddShopDialog(shopController));
        listShop.setOnAction(e -> new ListShopDialog(shopController));

        addCoupon.setOnAction(e -> new AddCouponDialog(couponController));
        listCoupon.setOnAction(e -> new ListCouponDialog(couponController));

        addGenre.setOnAction(e -> new AddGenreDialog(genreController));
        //listGenre.setOnAction(e -> new ListGenreDialog(genreController));

        //addStock.setOnAction(e -> new AddStockDialog(stockController));
        //.setOnAction(e -> new ListStockDialog(stockController));

        //addOrder.setOnAction(e -> new AddOrderDialog(orderController));
        //listOrder.setOnAction(e -> new ListOrderDialog(orderController));

        //addSeries.setOnAction(e -> new AddSeriesDialog(seriesController));
        //listSeries.setOnAction(e -> new ListSeriesDialog(seriesController));


        addUser.visibleProperty().bind(sessionController.isLoggedIn().not());
        loginUser.visibleProperty().bind(sessionController.isLoggedIn().not());
        userPage.visibleProperty().bind(sessionController.isLoggedIn());
        logoutUser.visibleProperty().bind(sessionController.isLoggedIn());
        publisherMenu.disableProperty().bind(sessionController.isAdmin().not());
        shopMenu.disableProperty().bind(sessionController.isAdmin().not());
        couponMenu.disableProperty().bind(sessionController.isAdmin().not());
        bookMenu.disableProperty().bind(sessionController.isLoggedIn().not());
        genreMenu.disableProperty().bind(sessionController.isAdmin().not());
        stockMenu.disableProperty().bind(sessionController.isAdmin().not());
        orderMenu.disableProperty().bind(sessionController.isLoggedIn().not());
        addOrder.visibleProperty().bind(sessionController.isLoggedIn());
        listOrder.visibleProperty().bind(sessionController.isAdmin());
        seriesMenu.disableProperty().bind(sessionController.isAdmin().not());

        userMenu.getItems().addAll(addUser, loginUser, userPage, logoutUser);
        bookMenu.getItems().addAll(addBook, listBook);
        publisherMenu.getItems().addAll(addPublisher, listPublisher);
        shopMenu.getItems().addAll(addShop, listShop);
        couponMenu.getItems().addAll(addCoupon, listCoupon);
        genreMenu.getItems().addAll(addGenre, listGenre);
        stockMenu.getItems().addAll(addStock, listStock);
        orderMenu.getItems().addAll(addOrder, listOrder);
        seriesMenu.getItems().addAll(addSeries, listSeries);

        return menuBar;
    }

    public static void main(String[] args) {
        launch();
    }

}