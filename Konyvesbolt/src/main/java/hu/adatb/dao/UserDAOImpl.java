package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.model.User;
import hu.adatb.util.Encoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private Connection conn;

    private static final String ADD_USER_STR = "INSERT INTO FELHASZNALOK VALUES (?,?,?,?,?,?,?,?,?,0) ";

    private static final String LOGIN_USER_STR = "SELECT * FROM FELHASZNALOK WHERE EMAIL = ? AND JELSZO = ? ";

    private static final String GET_USER_STR = "SELECT * FROM FELHASZNALOK WHERE EMAIL = ? ";

    private static final String DELETE_USER_STR = "DELETE FROM FELHASZNALOK WHERE EMAIL = ?";

    private static final String MODIFY_USER_STR = "UPDATE FELHASZNALOK SET VEZETEKNEV = ?, KERESZTNEV = ?, " +
            "JELSZO = ?, EMAIL = ?, IRANYITOSZAM = ?, VAROS = ?, UTCA = ?, HAZSZAM = ?, SZULDATUM = ? WHERE + EMAIL = ? ";

    private static final String GET_CITIES_STR = "SELECT DISTINCT VAROS FROM FELHASZNALOK ";

    public void initialize(){
        conn = DBController.connect();
    }

    public UserDAOImpl(){
        initialize();
    }

    @Override
    public boolean addUser(User user) {
        try (PreparedStatement st = conn.prepareStatement(ADD_USER_STR)){
            st.setString(1, user.getVnev());
            st.setString(2, user.getKnev());
            st.setString(3, Encoder.GetMD5(user.getPassword()));
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

    @Override
    public boolean isAdmin(String email) {
        try (PreparedStatement st = conn.prepareStatement(GET_USER_STR)){
            st.setString(1, email);

            ResultSet rs = st.executeQuery();

            if(rs.next()){
                return rs.getInt("ADMIN") == 1;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUser(String email) {
        User user = new User();

        try (PreparedStatement st = conn.prepareStatement(GET_USER_STR)){
            st.setString(1, email);

            ResultSet rs = st.executeQuery();

            if(rs.next()){
                user.setVnev(rs.getString("VEZETEKNEV"));
                user.setKnev(rs.getString("KERESZTNEV"));
                user.setPassword(rs.getString("JELSZO"));
                user.setEmail(email);
                user.setIrsz(rs.getInt("IRANYITOSZAM"));
                user.setCity(rs.getString("VAROS"));
                user.setStreet(rs.getString("UTCA"));
                user.setHouse(rs.getString("HAZSZAM"));
                user.setDate(rs.getDate("SZULDATUM"));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public boolean deleteUser(String email) {
        try (PreparedStatement st = conn.prepareStatement(DELETE_USER_STR)){
            st.setString(1, email);

            int res = st.executeUpdate();

            if(res == 1){
                return true;
            }


        } catch (SQLException e){
            if(e.getErrorCode() == 20111){
                return false;
            }
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean modifyUser(User user, String email) {
        try (PreparedStatement st = conn.prepareStatement(MODIFY_USER_STR)){
            st.setString(1, user.getVnev());
            st.setString(2, user.getKnev());
            st.setString(3, Encoder.GetMD5(user.getPassword()));
            st.setString(4, user.getEmail());
            st.setInt(5, user.getIrsz());
            st.setString(6, user.getCity());
            st.setString(7, user.getStreet());
            st.setString(8, user.getHouse());
            st.setDate(9, new java.sql.Date(user.getDate().getTime()));
            st.setString(10, email);

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
    public List<User> listCities() {
        List<User> list = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(GET_CITIES_STR)){
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                User user = new User();
                user.setCity(rs.getString(1));
                list.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
