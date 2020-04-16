package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAOImpl implements UserDAO {
    private Connection conn;

    private static final String ADD_USER_STR = "INSERT INTO FELHASZNALOK VALUES (?,?,?,?,?,?,?,?,?) ";

    private static final String LOGIN_USER_STR = "SELECT * FROM FELHASZNALOK WHERE EMAIL = ? AND JELSZO = ? ";

    public void initialize(){
        conn = DBController.connect();
    }

    public UserDAOImpl(){
        initialize();
    }

    @Override
    public boolean add(User user) {
        try (PreparedStatement st = conn.prepareStatement(ADD_USER_STR)){
            st.setString(1, user.getVnev());
            st.setString(2, user.getKnev());
            st.setString(3, user.getPassword());
            st.setString(4, user.getEmail());
            st.setInt(5, user.getIrsz());
            st.setString(6, user.getCity());
            st.setString(7, user.getStreet());
            st.setString(8, user.getHouse());
            st.setDate(9, new java.sql.Date(user.getDate().getTime()));

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
    public boolean login(User user) {
        try (PreparedStatement st = conn.prepareStatement(LOGIN_USER_STR)){
            st.setString(1, user.getEmail());
            st.setString(2, user.getPassword());

            ResultSet rs = st.executeQuery();

            if(rs.next()){
                return true;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
