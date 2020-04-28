package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.model.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl implements GenreDAO{

    private Connection conn;

    private static final String ADD_MUFAJOK_STR = "INSERT INTO MUFAJOK VALUES (?,?) ";

    private static final String DELETE_MUFAJOK_STR = "DELETE FROM MUFAJOK WHERE ISBN=?, MUFAJ=? ";

    private static final String UPDATE_MUFAJOK_STR = "UPDATE MUFAJOK SET mufaj=?,isbn=? WHERE MUFAJ=?, ISBN=? ";

    private static final String LIST_MUFAJOK_STR = "SELECT * FROM MUFAJOK ";

    private static final String LIST_KONYVEK_STR = "SELECT * FROM KONYVEK WHERE ISBN=? ";

    public void initialize(){
        conn = DBController.connect();
    }

    public GenreDAOImpl(){
        initialize();
    }

    public boolean foreignKey(Genre genre) {
        try (PreparedStatement st = conn.prepareStatement(LIST_KONYVEK_STR)){
            st.setInt(1, genre.getIsbn());

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
    public boolean add(Genre genre) {
        try (PreparedStatement st = conn.prepareStatement(ADD_MUFAJOK_STR)){
            st.setString(1, genre.getName());
            st.setInt(2, genre.getIsbn());

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
    public boolean delete(Genre genre) {
        try (PreparedStatement st = conn.prepareStatement(DELETE_MUFAJOK_STR)){
            st.setString(1, genre.getName());
            st.setInt(2, genre.getIsbn());

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
    public boolean update(Genre genre, String oldName, int oldIsbn) {
        try (PreparedStatement st = conn.prepareStatement(UPDATE_MUFAJOK_STR)){
            st.setString(1, genre.getName());
            st.setInt(2, genre.getIsbn());
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
    public List<Genre> list() {
        List<Genre> genres = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(LIST_MUFAJOK_STR)){
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                Genre genre = new Genre();
                genre.setName(rs.getString("MŰFAJ"));
                genre.setIsbn(rs.getInt("ISBN"));

                genres.add(genre);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return genres;
    }
}
