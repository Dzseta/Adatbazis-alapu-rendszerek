package hu.adatb.view;

import hu.adatb.controller.PublisherController;
import hu.adatb.controller.ShopController;
import hu.adatb.controller.StatsController;
import hu.adatb.model.Publisher;
import hu.adatb.model.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class StockChartDialog extends Stage {

    StatsController controller;
    ShopController shopController;
    Stage stage = new Stage();

    public StockChartDialog(StatsController controller, ShopController shopController) throws SQLException {
        this.controller = controller;
        this.shopController = shopController;
        construct(stage);
    }

    private void construct(Stage stage) throws SQLException {
        int shops = shopController.list().size();
        HashMap<String, Integer> shopStocks = controller.shops();

        Scene scene = new Scene(new Group());
        stage.setTitle("Raktáron lévő könyvek boltonként");
        stage.setWidth(500);
        stage.setHeight(600);

        PieChart chart = new PieChart();

        for(String i : shopStocks.keySet()) {
            chart.getData().add(new PieChart.Data(i, shopStocks.get(i)));
        }

        chart.setTitle("Raktáron lévő könyvek eloszlása boltonként");
        chart.setPrefSize(500, 500);
        chart.setStartAngle(30);

        final Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: 12 arial;");

        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());

                    caption.setText(String.valueOf(data.getPieValue()));
                }
            });
        }


        ((Group) scene.getRoot()).getChildren().addAll(chart, caption);
        stage.setScene(scene);
        stage.show();
    }
}
