package hu.adatb.view;

import hu.adatb.controller.AuthorController;
import hu.adatb.model.Author;
import hu.adatb.model.Book;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class BookPanel extends GridPane {
    private Book book;
    private AuthorController authorController;

    public BookPanel(Book book, AuthorController authorController) {
        this.book = book;
        this.authorController = authorController;
        construct();
    }

    private void construct(){
        setVgap(10);
        setHgap(10);
        setPadding(new Insets(10));
        setStyle("-fx-border-style: solid inside;" +
                "-fx-border-width: 2;");

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(75);
        col2.setPercentWidth(25);

        getColumnConstraints().addAll(col1, col2);

        Text title = new Text(book.getTitle());

        StringBuilder builder = new StringBuilder();
        for(Author a: authorController.getSelectedAuthors(book.getIsbn())){
            builder.append(a.getName());
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);

        Text authors = new Text("Szerző(k): " + builder.toString());
        Text price = new Text(String.valueOf(book.getPrice()) + " Ft");

        Button detailsButton = new Button("Részletek");
        detailsButton.setOnAction(e -> new BookDetailsPage(authorController, book));

        add(title, 0, 0);
        add(price, 1, 0);
        add(authors, 0, 1);
        add(detailsButton, 1, 1);

    }
}
