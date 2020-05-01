package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.controller.ShopController;
import hu.adatb.model.Book;
import hu.adatb.model.Shop;
import hu.adatb.util.Utils;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatsDAOImpl implements StatsDAO {

    private Connection conn;
    private static final String LIST_KONYVEK_STR = "SELECT * FROM KONYVEK WHERE isbn = ? ";
    private static final String COUNT_RENDELESEK_STR = "SELECT isbn, sum(darabszam) FROM rendelesek GROUP BY isbn ORDER BY sum(darabszam) DESC, isbn ";

    public void initialize(){
        conn = DBController.connect();
    }

    public StatsDAOImpl(){
        initialize();
    }

    @Override
    public List<Book> topList() {
        List<Integer> isbns = new ArrayList<>();
        List<Book> books = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(COUNT_RENDELESEK_STR)){
            ResultSet rs = st.executeQuery();
            int index = 0;

            while(rs.next() && index < 10){
                index++;
                isbns.add(rs.getInt("ISBN"));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        for(int i=0; i<isbns.size(); i++) {
            try (PreparedStatement st = conn.prepareStatement(LIST_KONYVEK_STR)) {
                st.setInt(1, isbns.get(i));

                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    Book book = new Book();
                    book.setIsbn(rs.getInt("ISBN"));
                    book.setTitle(rs.getString("CIM"));
                    book.setPublished(rs.getInt("KIADAS"));
                    book.setPublisher(rs.getString("KIADONEV"));
                    book.setPages(rs.getInt("OLDALSZAM"));
                    book.setCover(rs.getString("KOTES"));
                    book.setSize(rs.getString("MERET"));
                    book.setPrice(rs.getInt("AR"));

                    books.add(book);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return books;
    }
}
