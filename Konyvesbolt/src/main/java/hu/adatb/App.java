package hu.adatb;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.*;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Statement stmt = null;

        try {
            OracleDataSource ods = new OracleDataSource();
            Class.forName ("oracle.jdbc.OracleDriver");
            ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
            Connection conn = ods.getConnection("SYSTEM","SYSTEM");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_READ_ONLY);
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }

        try{
            ResultSet rs = stmt.executeQuery("SELECT * FROM FELHASZNALO");
            while(rs.next()){
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " "+ rs.getString(4));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}