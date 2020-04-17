package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.model.Publisher;
import hu.adatb.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

public class PublisherDAOImpl implements PublisherDAO {

    private Connection conn;

    private static final String ADD_PUBLISHER_STR = "INSERT INTO KIADO VALUES (?,?,?,?,?) ";

    private static final String DELETE_PUBLISHER_STR = "DELETE FROM KIADO WHERE NEV = ? ";

    private static final String UPDATE_PUBLISHER_STR = "UPDATE KIADO SET nev=?,iranyitoszam=?,varos=?,utca=?,hazszam=? WHERE NEV = ? ";

    private static final String LIST_PUBLISHER_STR = "SELECT * FROM KIADO ";

    public void initialize(){
        conn = DBController.connect();
    }

    public PublisherDAOImpl(){
        initialize();
    }

    @Override
    public boolean add(Publisher publisher) {
        try (PreparedStatement st = conn.prepareStatement(ADD_PUBLISHER_STR)){
            st.setString(1, publisher.getName());
            st.setInt(2, publisher.getZipcode());
            st.setString(3, publisher.getCity());
            st.setString(4, publisher.getStreet());
            st.setString(5, publisher.getHnumber());

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
    public boolean delete(Publisher publisher) {
        try (PreparedStatement st = conn.prepareStatement(DELETE_PUBLISHER_STR)){
            st.setString(1, publisher.getName());

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
    public boolean update(Publisher publisher, String oldName) {
        try (PreparedStatement st = conn.prepareStatement(UPDATE_PUBLISHER_STR)){
            st.setString(1, publisher.getName());
            st.setInt(2, publisher.getZipcode());
            st.setString(3, publisher.getCity());
            st.setString(4, publisher.getStreet());
            st.setString(5, publisher.getHnumber());
            st.setString(6, oldName);

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
    public List<Publisher> list() {
        List<Publisher> publishers = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(LIST_PUBLISHER_STR)){
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                Publisher publisher = new Publisher();
                publisher.setName(rs.getString("NEV"));
                publisher.setZipcode(rs.getInt("IRANYITOSZAM"));
                publisher.setCity(rs.getString("VAROS"));
                publisher.setStreet(rs.getString("UTCA"));
                publisher.setHnumber(rs.getString("HAZSZAM"));

                publishers.add(publisher);
            }


        } catch (Exception e){
            e.printStackTrace();
        }

        return publishers;

    }

}
