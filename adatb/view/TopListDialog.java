package hu.adatb.view;

import hu.adatb.controller.AuthorController;
import hu.adatb.controller.BookController;
import hu.adatb.controller.StatsController;
import hu.adatb.dao.StatsDAO;
import hu.adatb.model.Author;
import hu.adatb.model.Book;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class TopListDialog extends Stage {

    private StatsController controller;
    private BookController bookController;
    private AuthorController authorController;

    private TableView<Book> table;

    public TopListDialog(StatsController controller, BookController bookController, AuthorController authorController) {
        this.controller = controller;
        this.bookController = bookController;
        this.authorController = authorController;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        initializeTable();

        table.prefHeightProperty().bind(grid.heightProperty());
        table.prefWidthProperty().bind(grid.widthProperty());

        grid.add(table, 0, 1, 1, 1);

        Scene scene = new Scene(grid, 580, 520);
        setScene(scene);
        setTitle("Legkelendőbb könyvek");
        show();
    }

    public void initializeTable(){
        table = new TableView<>();
        TableColumn<Book, String> titleCol = new TableColumn<>("Cím");
        TableColumn<Book, String> publishedCol = new TableColumn<>("Kiadás éve");
        TableColumn<Book, String> priceCol = new TableColumn<>("Ár (Forint)");

        titleCol.setCellValueFactory(data -> data.getValue().titleProperty());
        priceCol.setCellValueFactory(data -> data.getValue().priceProperty().asString());
        publishedCol.setCellValueFactory(data ->  data.getValue().publishedProperty().asString());

        table.getColumns().addAll(titleCol, publishedCol, priceCol);
        List<Book> list = controller.list();
        table.setItems(FXCollections.observableList(list));
    }

    public void refreshTable(){
        List<Book> list = controller.list();
        table.setItems(FXCollections.observableList(list));
    }
}
