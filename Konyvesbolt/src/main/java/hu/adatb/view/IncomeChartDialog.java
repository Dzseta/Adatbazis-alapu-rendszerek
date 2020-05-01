package hu.adatb.view;

import hu.adatb.controller.StatsController;

import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class IncomeChartDialog extends Stage {

    StatsController controller;
    Stage stage = new Stage();

    public IncomeChartDialog(StatsController controller) throws SQLException {
        this.controller = controller;
        construct(stage);
    }

    private void construct(Stage stage) throws SQLException {
        stage.setTitle("Részletes statisztikák");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Hónap");

        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("Összes idei bevétel");

        XYChart.Series series = new XYChart.Series();
        series.setName("Bevétel (Ft)");

        series.getData().add(new XYChart.Data("Jan", controller.income(1)));
        series.getData().add(new XYChart.Data("Feb", controller.income(2)));
        series.getData().add(new XYChart.Data("Már", controller.income(3)));
        series.getData().add(new XYChart.Data("Ápr", controller.income(4)));
        series.getData().add(new XYChart.Data("Máj", controller.income(5)));
        series.getData().add(new XYChart.Data("Jún", controller.income(6)));
        series.getData().add(new XYChart.Data("Júl", controller.income(7)));
        series.getData().add(new XYChart.Data("Aug", controller.income(8)));
        series.getData().add(new XYChart.Data("Szep", controller.income(9)));
        series.getData().add(new XYChart.Data("Okt", controller.income(10)));
        series.getData().add(new XYChart.Data("Nov", controller.income(11)));
        series.getData().add(new XYChart.Data("Dec", controller.income(12)));


        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }
}
