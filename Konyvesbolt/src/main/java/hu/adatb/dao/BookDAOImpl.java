package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO{

    private Connection conn;

    private static final String ADD_KONYVEK_STR = "INSERT INTO KONYVEK VALUES (?,?,?,?,?,?,?,?) ";

    private static final String DELETE_KONYVEK_STR = "DELETE FROM KONYVEK WHERE ISBN = ? ";

    private static final String UPDATE_KONYVEK_STR = "UPDATE KONYVEK SET isbn=?,cim=?,kiadas=?,kiadonev=?,oldalszam=?,kotes=?,meret=?,ar=? WHERE ISBN = ? ";

    private static final String LIST_KONYVEK_STR = "SELECT * FROM KONYVEK ORDER BY ISBN ";

    private static final String GET_KONYV_STR = "SELECT * FROM KONYVEK WHERE ISBN=? ";

    private static final String LIST_BOOKS_STR = "SELECT DISTINCT KONYVEK.ISBN, CIM, KIADAS, KIADONEV, OLDALSZAM, KOTES, MERET, AR " +
            "FROM KONYVEK, SZERZOK, MUFAJOK WHERE KONYVEK.ISBN = SZERZOK.ISBN " +
            "AND KONYVEK.ISBN = MUFAJOK.ISBN ";

    private static final String GET_SIMILAR_STR = "{? = call GET_SIMILAR(?)} ";

    private static final String GET_BOOK_PER_GENRE_STR = "SELECT COUNT(*) FROM KONYVEK, MUFAJOK WHERE " +
            "KONYVEK.ISBN = MUFAJOK.ISBN AND MUFAJOK.MUFAJ = ? ";

    public void initialize(){
        conn = DBController.connect();
    }

    public BookDAOImpl(){
        initialize();
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

    @Override
    public Book getSelectedBook(int isbn) {
        Book book = new Book();

        try (PreparedStatement st = conn.prepareStatement(GET_KONYV_STR)){
            st.setInt(1, isbn);

            ResultSet rs = st.executeQuery();

            while(rs.next()){
                book.setIsbn(rs.getInt("ISBN"));
                book.setTitle(rs.getString("CIM"));
                book.setPublished(rs.getInt("KIADAS"));
                book.setPublisher(rs.getString("KIADONEV"));
                book.setPages(rs.getInt("OLDALSZAM"));
                book.setCover(rs.getString("KOTES"));
                book.setSize(rs.getString("MERET"));
                book.setPrice(rs.getInt("AR"));

            }


        } catch (Exception e){
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public List<Book> getSelectedBooks(String title, String author, String genre) {
        List<Book> list = new ArrayList<>();

        String localStr = LIST_BOOKS_STR;
        if(!title.isEmpty()){
            localStr += "AND CIM LIKE ? ";
        }
        if(!author.isEmpty()){
            localStr += "AND NEV LIKE ? ";
        }
        if(!genre.isEmpty()){
            localStr += "AND MUFAJ LIKE ? ";
        }


        try (PreparedStatement st = conn.prepareStatement(localStr)){
            int args = 1;
            if(!title.isEmpty()){
                st.setString(args++, '%' + title + '%');
            }
            if(!author.isEmpty()){
                st.setString(args++, '%' + author + '%');
            }
            if(!genre.isEmpty()){
                st.setString(args, '%' + genre + '%');
            }

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

                list.add(book);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Book getSimilar(int isbn) {

        try (CallableStatement st = conn.prepareCall(GET_SIMILAR_STR)){
            st.registerOutParameter(1, Types.INTEGER);
            st.setInt(2, isbn);
            st.execute();

            int bookIsbn = st.getInt(1);

            if(bookIsbn > -1 ){
                return getSelectedBook(bookIsbn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int getBookPerGenre(String genre) {
        try (PreparedStatement st = conn.prepareStatement(GET_BOOK_PER_GENRE_STR)){
            st.setString(1, genre);

            ResultSet rs = st.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
