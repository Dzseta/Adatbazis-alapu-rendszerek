package hu.adatb;

import hu.adatb.controller.*;
import hu.adatb.model.Book;
import hu.adatb.model.Shop;
import hu.adatb.view.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    private StatsController statsController = new StatsController();

    private SessionController sessionController = SessionController.getInstance();
    private ShoppingCart shoppingCart = ShoppingCart.getInstance();

    private FlowPane books;
    private BorderPane contents;

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(createMenuBar(stage));

        contents = new BorderPane();
        contents.prefHeightProperty().bind(stage.heightProperty());
        contents.prefWidthProperty().bind(stage.widthProperty());
        books = createBookPanels(stage);
        GridPane search = createSearchBar(stage);

        Button refreshButton = new Button("Frissítés");
        refreshButton.setOnAction(e -> refreshContents(stage));

        contents.setTop(search);
        contents.setCenter(books);
        contents.setBottom(refreshButton);
        BorderPane.setAlignment(refreshButton, Pos.CENTER);
        BorderPane.setAlignment(search, Pos.CENTER);

        root.getChildren().add(contents);

        Scene scene = new Scene(root, 960, 520);
        stage.setScene(scene);
        stage.show();
    }

    private MenuBar createMenuBar(Stage stage){
        MenuBar menuBar = new MenuBar();
        Menu userMenu = new Menu("Felhasználó");
        Menu statsMenu = new Menu("Toplisták");
        Menu bookMenu = new Menu("Könyv");
        Menu publisherMenu = new Menu("Kiadó");
        Menu shopMenu = new Menu("Áruházak");
        Menu couponMenu = new Menu("Kuponok");
        Menu genreMenu = new Menu("Műfajok");
        Menu stockMenu = new Menu("Raktár");
        Menu orderMenu = new Menu("Rendelések");
        Menu seriesMenu = new Menu("Könyvsorozatok");

        menuBar.getMenus().addAll(userMenu, statsMenu, orderMenu, bookMenu, publisherMenu, shopMenu, couponMenu, genreMenu, stockMenu, seriesMenu);

        MenuItem addUser = new MenuItem("Regisztráció");
        MenuItem loginUser = new MenuItem("Bejelentkezés");
        MenuItem logoutUser = new MenuItem("Kijelentkezés");
        MenuItem userPage = new MenuItem("Saját adatok");
        MenuItem cartMenu = new MenuItem("Bevásárlókosár");
        MenuItem topList = new MenuItem("Legkelendőbb könyvek");
        MenuItem adminStats = new MenuItem("Részletes statisztikák");
        MenuItem addBook = new MenuItem("Könyv felvétele");
        MenuItem listBook = new MenuItem("Könyvek listázása");
        MenuItem addPublisher = new MenuItem("Kiadó felvétele");
        MenuItem listPublisher = new MenuItem("Kiadók listázása");
        MenuItem addShop = new MenuItem("Áruház felvétele");
        MenuItem listShop = new MenuItem("Áruházak listázása");
        MenuItem addCoupon = new MenuItem("Kupon felvétele");
        MenuItem listCoupon = new MenuItem("Kuponok listázása");
        MenuItem listGenre = new MenuItem("Műfajok listázása");
        MenuItem addStock = new MenuItem("Könyv felvétele a raktárba");
        MenuItem listStock = new MenuItem("Raktár tartalmának listázása");
        MenuItem listOrder = new MenuItem("Rendelések listázása");
        MenuItem addSeries = new MenuItem("Könyvsorozat felvétele");
        MenuItem listSeries = new MenuItem("Könyvsorozatok listázása");

        addUser.setOnAction(e -> new AddUserDialog(userController));
        loginUser.setOnAction(e -> new LoginUserDialog(userController));
        userPage.setOnAction(e -> new UserDataDialog(userController));
        cartMenu.setOnAction(e -> shoppingCart.show());
        logoutUser.setOnAction(e -> sessionController.logout());

        topList.setOnAction(e -> new TopListDialog(statsController, bookController, authorController));
        adminStats.setOnAction(e -> new AdminStatsDialog(seriesController, orderController, userController, bookController, genreController, statsController, shopController));

        addBook.setOnAction(e -> new AddBookDialog(bookController, publisherController, authorController, genreController));
        listBook.setOnAction(e -> new ListBookDialog(bookController, publisherController, authorController, genreController));

        addPublisher.setOnAction(e -> new AddPublisherDialog(publisherController));
        listPublisher.setOnAction(e -> new ListPublisherDialog(publisherController));

        addShop.setOnAction(e -> new AddShopDialog(shopController));
        listShop.setOnAction(e -> new ListShopDialog(shopController));

        addCoupon.setOnAction(e -> new AddCouponDialog(couponController));
        listCoupon.setOnAction(e -> new ListCouponDialog(couponController));

        listGenre.setOnAction(e -> new ListGenreDialog(genreController));

        addStock.setOnAction(e -> new AddStockDialog(stockController, bookController, shopController));
        listStock.setOnAction(e -> new ListStockDialog(stockController, bookController, shopController));

        listOrder.setOnAction(e -> new ListOrderDialog(orderController));

        addSeries.setOnAction(e -> new AddSeriesDialog(seriesController));
        listSeries.setOnAction(e -> new ListSeriesDialog(seriesController));


        addBook.visibleProperty().bind(sessionController.isAdmin());
        addUser.visibleProperty().bind(sessionController.isLoggedIn().not());
        loginUser.visibleProperty().bind(sessionController.isLoggedIn().not());
        cartMenu.visibleProperty().bind(sessionController.isLoggedIn());
        userPage.visibleProperty().bind(sessionController.isLoggedIn());
        logoutUser.visibleProperty().bind(sessionController.isLoggedIn());
        publisherMenu.disableProperty().bind(sessionController.isAdmin().not());
        shopMenu.disableProperty().bind(sessionController.isAdmin().not());
        couponMenu.disableProperty().bind(sessionController.isAdmin().not());
        bookMenu.disableProperty().bind(sessionController.isAdmin().not());
        genreMenu.disableProperty().bind(sessionController.isAdmin().not());
        stockMenu.disableProperty().bind(sessionController.isAdmin().not());
        orderMenu.disableProperty().bind(sessionController.isLoggedIn().not());
        listOrder.visibleProperty().bind(sessionController.isLoggedIn());
        seriesMenu.disableProperty().bind(sessionController.isAdmin().not());
        adminStats.visibleProperty().bind(sessionController.isAdmin());

        userMenu.getItems().addAll(addUser, loginUser, cartMenu, userPage, logoutUser);
        statsMenu.getItems().addAll(topList, adminStats);
        bookMenu.getItems().addAll(addBook, listBook);
        publisherMenu.getItems().addAll(addPublisher, listPublisher);
        shopMenu.getItems().addAll(addShop, listShop);
        couponMenu.getItems().addAll(addCoupon, listCoupon);
        genreMenu.getItems().addAll(listGenre);
        stockMenu.getItems().addAll(addStock, listStock);
        orderMenu.getItems().addAll(listOrder);
        seriesMenu.getItems().addAll(addSeries, listSeries);

        return menuBar;
    }

    private FlowPane createBookPanels(Stage stage){
        FlowPane panels = new FlowPane();
        panels.setOrientation(Orientation.HORIZONTAL);
        panels.prefWidthProperty().bind(stage.widthProperty());
        for(Book b: bookController.list()){
            BookPanel panel = new BookPanel(b, authorController, genreController, bookController);
            panel.prefWidthProperty().bind(stage.widthProperty().subtract(20).divide(3));
            panels.getChildren().add(panel);
        }
        return panels;
    }

    private GridPane createSearchBar(Stage stage){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField genreField = new TextField();

        Button searchButton = new Button("Keresés");
        searchButton.setOnAction(e -> {
            contents.getChildren().remove(books);
            books = createBookPanelsWithQuery(stage, titleField.getText(), authorField.getText(), genreField.getText());
            contents.setCenter(books);
        });

        grid.add(new Text("Cím:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Text("Szerző:"), 2, 0);
        grid.add(authorField, 3, 0);
        grid.add(new Text("Műfaj:"), 4, 0);
        grid.add(genreField, 5, 0);
        grid.add(searchButton, 6, 0);

        return grid;
    }

    private FlowPane createBookPanelsWithQuery(Stage stage, String title, String author, String genre){
        FlowPane panels = new FlowPane();
        panels.setOrientation(Orientation.HORIZONTAL);
        panels.prefWidthProperty().bind(stage.widthProperty());
        for(Book b: bookController.getSelectedBooks(title, author, genre)){
            BookPanel panel = new BookPanel(b, authorController, genreController, bookController);
            panel.prefWidthProperty().bind(stage.widthProperty().subtract(20).divide(3));
            panels.getChildren().add(panel);
        }

        return panels;
    }

    public void refreshContents(Stage stage){
        contents.getChildren().remove(books);
        books = createBookPanels(stage);
        contents.setCenter(books);
    }

    public static void main(String[] args) {
        launch();
    }

}