package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.model.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImpl implements AuthorDAO{

    private Connection conn;

    private static final String ADD_SZERZOK_STR = "INSERT INTO SZERZOK VALUES (?,?) ";

    private static final String DELETE_SZERZOK_STR = "DELETE FROM SZERZOK WHERE NEV=?, ISBN=? ";

    private static final String UPDATE_SZERZOK_STR = "UPDATE SZERZOK SET nev=?,isbn=? WHERE NEV=?, ISBN=? ";

    private static final String LIST_SZERZOK_STR = "SELECT * FROM SZERZOK ";

    private static final String LIST_KONYVEK_STR = "SELECT * FROM KONYVEK WHERE ISBN=? ";

    public void initialize(){
        conn = DBController.connect();
    }

    public AuthorDAOImpl(){
        initialize();
    }

    public boolean foreignKey(Author author) {
        try (PreparedStatement st = conn.prepareStatement(LIST_SZERZOK_STR)){
            st.setInt(1, author.getIsbn());

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
    public boolean add(Author author) {
        try (PreparedStatement st = conn.prepareStatement(ADD_SZERZOK_STR)){
            st.setString(1, author.getName());
            st.setInt(2, author.getIsbn());

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
    public boolean delete(Author author) {
        try (PreparedStatement st = conn.prepareStatement(DELETE_SZERZOK_STR)){
            st.setString(1, author.getName());
            st.setInt(2, author.getIsbn());

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
    public boolean update(Author author, String oldName, int oldIsbn) {
        try (PreparedStatement st = conn.prepareStatement(UPDATE_SZERZOK_STR)){
            st.setString(1, author.getName());
            st.setInt(2, author.getIsbn());
            st.setString(3, oldName);
            st.setInt(4, oldIsbn);

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
    public List<Author> list() {
        List<Author> authors = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(LIST_SZERZOK_STR)){
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                Author author = new Author();
                author.setName(rs.getString("Szerző neve"));
                author.setIsbn(rs.getInt("Könyv ISBN száma"));

                authors.add(author);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return authors;
    }
}
