package hu.adatb.view;

import hu.adatb.controller.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AdminStatsDialog extends Stage {
    private SeriesController seriesController;
    private OrderController orderController;
    private UserController userController;
    private BookController bookController;
    private GenreController genreController;
    private StatsController statsController;
    private ShopController shopController;

    public AdminStatsDialog(SeriesController seriesController, OrderController orderController, UserController userController, BookController bookController, GenreController genreController, StatsController statsController, ShopController shopController) {
        this.seriesController = seriesController;
        this.orderController = orderController;
        this.userController = userController;
        this.bookController = bookController;
        this.genreController = genreController;
        this.statsController = statsController;
        this.shopController = shopController;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        Button seriesSalesButton = new Button("Megtekint");
        seriesSalesButton.setOnAction(e -> new SeriesSalesDialog(seriesController));

        Button citiesSalesButton = new Button("Megtekint");
        citiesSalesButton.setOnAction(e -> new CitySalesDialog(orderController, userController));

        Button avgBookSalesButton = new Button("Megtekint");
        avgBookSalesButton.setOnAction(e -> new AvgBookSalesDialog(bookController, orderController));

        Button bookPerGenreButton = new Button("Megtekint");
        bookPerGenreButton.setOnAction(e -> new BookPerGenreDialog(bookController, genreController));

        Button incomeChartButton = new Button("Megtekint");
        incomeChartButton.setOnAction(e -> {
            try {
                new IncomeChartDialog(statsController);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Button stockChartButton = new Button("Megtekint");
        stockChartButton.setOnAction(e -> {
            try {
                new StockChartDialog(statsController, shopController);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        grid.add(new Text("Eladások száma sorozatonként"), 0, 0);
        grid.add(seriesSalesButton, 1, 0);
        grid.add(new Text("Eladások száma városonként"), 0, 1);
        grid.add(citiesSalesButton, 1, 1);
        grid.add(new Text("Átlagos eladások száma könyvenként"), 0, 2);
        grid.add(avgBookSalesButton, 1, 2);
        grid.add(new Text("Könyvek száma műfajonként"), 0, 3);
        grid.add(bookPerGenreButton, 1, 3);
        grid.add(new Text("Bevétel havi bontásban"), 0, 4);
        grid.add(incomeChartButton, 1, 4);
        grid.add(new Text("Raktáron levő könyvek statisztikája"), 0, 5);
        grid.add(stockChartButton, 1, 5);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Részletes statisztikák");
        show();
    }


}
