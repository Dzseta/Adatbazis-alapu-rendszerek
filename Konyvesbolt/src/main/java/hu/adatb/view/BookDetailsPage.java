package hu.adatb.view;

import hu.adatb.controller.AuthorController;
import hu.adatb.controller.SessionController;
import hu.adatb.model.Author;
import hu.adatb.model.Book;
import hu.adatb.util.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BookDetailsPage extends Stage {
    private AuthorController authorController;

    private ShoppingCart shoppingCart;
    private SessionController sessionController;

    private Book book;

    public BookDetailsPage(AuthorController authorController, Book book) {
        this.authorController = authorController;
        this.book = book;
        shoppingCart = ShoppingCart.getInstance();
        sessionController = SessionController.getInstance();
        construct();
    }

    private void construct(){
        BorderPane root = new BorderPane();

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        Text isbn = new Text(String.valueOf(book.getIsbn()));
        Text title = new Text(book.getTitle());

        StringBuilder builder = new StringBuilder();
        for(Author a: authorController.getSelectedAuthors(book.getIsbn())){
            builder.append(a.getName());
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);

        Text authors = new Text("Szerző(k): " + builder.toString());
        Text published = new Text(book.getPublished() + ", ");
        Text publisher = new Text(book.getPublisher());
        Text pages = new Text(book.getPages() + " oldal, ");
        Text cover = new Text(book.getCover());
        Text size = new Text(book.getSize());
        Text price = new Text(book.getPrice() + " Ft");
        Text genre = new Text("Minta műfaj");

        grid.add(isbn, 0, 0);
        grid.add(title, 1, 0);
        grid.add(genre, 0, 1, 2, 1);
        grid.add(authors, 0, 2, 2, 1);
        grid.add(published, 0, 3);
        grid.add(publisher, 1, 3);
        grid.add(pages, 0, 4);
        grid.add(cover, 1, 4);
        grid.add(size, 0, 5);
        grid.add(price, 1, 5);

        Button orderButton = new Button("Kosárba");
        orderButton.setOnAction(e -> {
            if(sessionController.userLoggedIn()){
                shoppingCart.addBook(book);
                close();
            } else {
                Utils.showWarning("A kosrába rakáshoz jelentkezzen be!");
            }

        });
        Button cancelButton = new Button("Mégse");
        cancelButton.setOnAction(e -> close());

        FlowPane buttonPane = new FlowPane();
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        buttonPane.setHgap(15);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(orderButton, cancelButton);

        grid.add(buttonPane, 0, 6, 2, 1);

        root.setCenter(grid);

        Scene scene = new Scene(root);
        setScene(scene);
        setTitle(book.getTitle());
        show();

    }
}
