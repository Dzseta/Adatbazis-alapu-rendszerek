package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.controller.ShopController;
import hu.adatb.model.Book;
import java.util.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatsDAOImpl implements StatsDAO {

    private Connection conn;
    private static final String LIST_KONYVEK_STR = "SELECT * FROM KONYVEK WHERE isbn = ? ";
    private static final String COUNT_RENDELESEK_STR = "SELECT isbn, sum(darabszam) FROM rendelesek GROUP BY isbn ORDER BY sum(darabszam) DESC, isbn ";
    private static final String HONAPOK_STR = "{ ? = call income(?)} ";
    private static final String COUNT_STOCK_STR = "SELECT aruhazak.nev, SUM(raktaron.darabszam) FROM aruhazak NATURAL JOIN raktaron GROUP BY aruhazak.nev ";

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

    @Override
    public int income(int honap) {

        try (CallableStatement st = conn.prepareCall(HONAPOK_STR)){
            st.registerOutParameter(1, Types.INTEGER);
            st.setInt(2, honap);
            st.execute();

            int count = st.getInt(1);
            return  count;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public HashMap<String, Integer> shops() {
        HashMap<String, Integer> shopStocks = new HashMap<>();

        try (PreparedStatement st = conn.prepareStatement(COUNT_STOCK_STR)){
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                shopStocks.put(rs.getString(1), rs.getInt(2));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return shopStocks;
    }
}
