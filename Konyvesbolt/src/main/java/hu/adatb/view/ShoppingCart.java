package hu.adatb.view;

import hu.adatb.model.Book;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends Stage {
    private List<Book> books;

    private static ShoppingCart cart;

    private BorderPane contents;

    public static ShoppingCart getInstance(){
        if(cart == null){
            cart = new ShoppingCart();
        }
        return cart;
    }

    private ShoppingCart() {
        books = new ArrayList<>();
        construct();
    }

    public void addBook(Book book){
        if(!books.contains(book)){
            books.add(book);
            refreshContents();
        }
    }

    private void construct(){
        contents = new BorderPane();

        Button orderButton = new Button("Megrendel");
        Button cancelButton = new Button("Mégse");
        cancelButton.setOnAction(e -> close());

        FlowPane buttonPane = new FlowPane();
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        buttonPane.setHgap(15);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(orderButton, cancelButton);

        contents.setCenter(createItems());
        contents.setBottom(buttonPane);
        BorderPane.setAlignment(buttonPane, Pos.CENTER);

        Scene scene = new Scene(contents);
        setScene(scene);
        setTitle("Kosár");
    }

    private GridPane createItems(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        int index = 0;

        for(Book b: books){
            Spinner<Integer> amountSpinner = new Spinner<>(1, 10, 1);
            Button removeButton = new Button("Eltávolítás");
            removeButton.setOnAction(e -> {
                books.remove(b);
                refreshContents();
            });

            grid.add(new Text(b.getTitle()), 0, index);
            grid.add(amountSpinner, 1, index);
            grid.add(removeButton, 2, index);

            index++;

        }

        return grid;
    }

    public void refreshContents(){
        contents.setCenter(createItems());
    }

}
