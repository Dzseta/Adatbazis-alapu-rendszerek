package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.model.Book;
import hu.adatb.model.Publisher;
import hu.adatb.util.Utils;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO{

    private Connection conn;

    private static final String ADD_KONYVEK_STR = "INSERT INTO KONYVEK VALUES (?,?,?,?,?,?,?,?) ";

    private static final String DELETE_KONYVEK_STR = "DELETE FROM KONYVEK WHERE ISBN = ? ";

    private static final String UPDATE_KONYVEK_STR = "UPDATE KONYVEK SET isbn=?,cim=?,kiadas=?,kiadonev=?,oldalszam=?,kotes=?,meret=?,ar=? WHERE ISBN = ? ";

    private static final String LIST_KONYVEK_STR = "SELECT * FROM KONYVEK ";

    private static final String LIST_KIADOK_STR = "SELECT * FROM KIADO WHERE NEV=? ";

    public void initialize(){
        conn = DBController.connect();
    }

    public BookDAOImpl(){
        initialize();
    }

    public boolean foreignKey(Book book) {
        try (PreparedStatement st = conn.prepareStatement(LIST_KIADOK_STR)){
            st.setString(1, book.getPublisher());

            int res = st.executeUpdate();

            ResultSet rs = st.executeQuery();

            if(!(rs.next())) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(Book book) {
        try (PreparedStatement st = conn.prepareStatement(ADD_KONYVEK_STR)){
            st.setInt(1, book.getIsbn());
            st.setString(2, book.getTitle());
            st.setInt(3, book.getPublished());
            st.setString(4, book.getPublisher());
            st.setInt(5, book.getPages());
            st.setString(6, book.getCover());
            st.setString(7, book.getSize());
            st.setInt(8, book.getPrice());

            int res = st.executeUpdate();

            if(res == 1){
                return true;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Book book) {
        try (PreparedStatement st = conn.prepareStatement(DELETE_KONYVEK_STR)){
            st.setInt(1, book.getIsbn());

            int res = st.executeUpdate();

            if(res == 1){
                return true;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Book book, int oldIsbn) {
        try (PreparedStatement st = conn.prepareStatement(UPDATE_KONYVEK_STR)){
            st.setInt(1, book.getIsbn());
            st.setString(2, book.getTitle());
            st.setInt(3, book.getPublished());
            st.setString(4, book.getPublisher());
            st.setInt(5, book.getPages());
            st.setString(6, book.getCover());
            st.setString(7, book.getSize());
            st.setInt(8, book.getPrice());
            st.setInt(9, oldIsbn);

            int res = st.executeUpdate();

            if(res == 1){
                return true;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Book> list() {
        List<Book> books = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(LIST_KONYVEK_STR)){
            ResultSet rs = st.executeQuery();

            while(rs.next()){
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


        } catch (Exception e){
            e.printStackTrace();
        }

        return books;
    }
}
