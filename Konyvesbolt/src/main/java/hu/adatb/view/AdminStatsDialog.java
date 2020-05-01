package hu.adatb.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AdminStatsDialog extends Stage {

    public static ObservableList<PieChart.Data> getChartData()
    {
        ObservableList<PieChart.Data> data = FXCollections. observableArrayList();
        data.add(new PieChart.Data("China", 1275));
        data.add(new PieChart.Data("India", 1017));
        data.add(new PieChart.Data("Brazil", 172));
        data.add(new PieChart.Data("UK", 59));
        data.add(new PieChart.Data("USA", 285));
        return data;
    }


    public void test(Stage stage)
    {
        // Create the PieChart
        PieChart chart = new PieChart();
        // Set the Title of the Chart
        chart.setTitle("Population in Year 2000");
        // Place the legend on the left side
        chart.setLegendSide(Side.LEFT);
        // Set the Data for the Chart
        ObservableList<PieChart.Data> chartData = AdminStatsDialog.getChartData();
        chart.setData(chartData);

        // Create a Stackpane
        StackPane root = new StackPane(chart);
        // Set the Style-properties of the Pane
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        // Create the Scene
        Scene scene = new Scene(root);
        // Add the Stylesheet to the Scene
        scene.getStylesheets().add(getClass().getResource("piechart.css").toExternalForm());
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("A Pie Chart Example");
        // Display the Stage
        stage.show();
    }
}
