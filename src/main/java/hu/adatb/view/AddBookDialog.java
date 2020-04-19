package hu.adatb.view;

import hu.adatb.controller.BookController;
import hu.adatb.model.Book;
import hu.adatb.util.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddBookDialog extends Stage {

    BookController controller;

    public AddBookDialog(BookController controller){
        this.controller = controller;
        construct();
    }

    private void construct(){
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10));

        TextField isbnField = new TextField();
        TextField titleField = new TextField();
        TextField publishedField = new TextField();
        TextField publisherField = new TextField();
        TextField pagesField = new TextField();
        TextField coverField = new TextField();
        TextField sizeField = new TextField();
        TextField priceField = new TextField();

        grid.add(new Text("Könyv ISBN száma:"), 0, 0);
        grid.add(isbnField, 1 , 0);
        grid.add(new Text("Könyv címe:"), 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(new Text("Kiadás éve:"), 0, 2);
        grid.add(publishedField, 1, 2);
        grid.add(new Text("Kiadó:"), 0, 3);
        grid.add(publisherField, 1, 3);
        grid.add(new Text("Oldalszám:"), 0, 4);
        grid.add(pagesField, 1, 4);
        grid.add(new Text("Borító kötése:"), 0, 5);
        grid.add(coverField, 1, 5);
        grid.add(new Text("Méret:"), 0, 6);
        grid.add(sizeField, 1, 6);
        grid.add(new Text("Ár (Forint):"), 0, 7);
        grid.add(priceField, 1, 7);

        Button okButton = new Button("Könyv felvitele");
        okButton.setDefaultButton(true);
        okButton.setOnAction(e -> {
            if(isbnField.getText().contentEquals("")){
                Utils.showWarning("Az ISBN szám nem lehet üres!");
                return;
            }
            if(titleField.getText().contentEquals("")){
                Utils.showWarning("A cím nem lehet üres!");
                return;
            }
            if(publishedField.getText().contentEquals("")){
                Utils.showWarning("A kiadási év nem lehet üres!");
                return;
            }
            if(publisherField.getText().contentEquals("")){
                Utils.showWarning("A kiadó neve nem lehet üres!");
                return;
            }
            if(pagesField.getText().contentEquals("")){
                Utils.showWarning("Az oldalszám nem lehet üres!");
                return;
            }
            if(coverField.getText().contentEquals("")){
                Utils.showWarning("A borító típusa nem lehet üres!");
                return;
            }
            if(sizeField.getText().contentEquals("")){
                Utils.showWarning("A méret nem lehet üres!");
                return;
            }
            if(priceField.getText().contentEquals("")){
                Utils.showWarning("Az ár nem lehet üres!");
                return;
            }

            int isbn;
            try{
                isbn = Integer.parseInt(isbnField.getText());
            } catch (Exception ex){
                Utils.showWarning("Az ISBN nem szám!");
                return;
            }
            if(isbn < 0){
                Utils.showWarning("Az ISBN nem lehet negatív szám!");
                return;
            }

            int published;
            try{
                published = Integer.parseInt(publishedField.getText());
            } catch (Exception ex){
                Utils.showWarning("A kiadás éve nem szám!");
                return;
            }

            int pages;
            try{
                pages = Integer.parseInt(pagesField.getText());
            } catch (Exception ex){
                Utils.showWarning("Az oldalszám nem szám!");
                return;
            }
            if(pages <= 0){
                Utils.showWarning("Az oldalszám nem lehet negatív!");
                return;
            }

            int price;
            try{
                price = Integer.parseInt(priceField.getText());
            } catch (Exception ex){
                Utils.showWarning("Az ár nem szám!");
                return;
            }
            if(isbn <= 0){
                Utils.showWarning("Az árnak pozitívnak kell lennie!");
                return;
            }

            if(controller.foreignKey(new Book(isbn, titleField.getText(), published, publisherField.getText(), pages, coverField.getText(), sizeField.getText(), price))) {
                Utils.showWarning("Nincs ilyen kiadó!");
                return;
            }

            if(controller.add(new Book(isbn, titleField.getText(), published, publisherField.getText(), pages, coverField.getText(), sizeField.getText(), price))){
                close();
            } else {
                Utils.showWarning("Nem sikerült a könyv felvétele!");
                return;
            }

        });

        Button cancelButton = new Button("Mégse");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(e -> {
            close();
        });

        FlowPane buttonPane = new FlowPane();
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        buttonPane.setHgap(15);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(okButton, cancelButton);

        grid.add(buttonPane, 0, 9, 2, 1);

        Scene scene = new Scene(grid);
        setScene(scene);
        setTitle("Könyv felvitele");
        show();
    }
}
