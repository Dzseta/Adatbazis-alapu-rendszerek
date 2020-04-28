package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private Connection conn;

    private static final String ADD_RENDELESEK_STR = "INSERT INTO RENDELESEK VALUES (?,?,?,?,?,?) ";

    private static final String LIST_RENDELESEK_STR = "SELECT * FROM RENDELESEK ";

    private static final String LIST_KONYVEK_STR = "SELECT * FROM KONYVEK WHERE IMDB=? ";

    private static final String LIST_FELHASZNALOK_STR = "SELECT * FROM FELHASZNALOK WHERE EMAIL=? ";

    public void initialize(){
        conn = DBController.connect();
    }

    public OrderDAOImpl(){
        initialize();
    }

    public boolean foreignKey(Order order) {
        try (PreparedStatement st = conn.prepareStatement(LIST_KONYVEK_STR)){
            st.setInt(1, order.getIsbn());

            int res = st.executeUpdate();

            ResultSet rs = st.executeQuery();

            if(!(rs.next())) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try (PreparedStatement st = conn.prepareStatement(LIST_FELHASZNALOK_STR)){
            st.setString(1, order.getEmail());

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
    public boolean add(Order order) {
        try (PreparedStatement st = conn.prepareStatement(ADD_RENDELESEK_STR)){
            st.setString(1, order.getEmail());
            st.setInt(2, order.getIsbn());
            st.setInt(3, order.getQuantity());
            st.setString(4, order.getLocation());
            st.setDate(5, new java.sql.Date(Long.parseLong(LocalDateTime.now().toString())));
            st.setDate(6, null);

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
    public List<Order> list() {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(LIST_RENDELESEK_STR)){
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                Order order = new Order();
                order.setEmail(rs.getString("EMAIL"));
                order.setIsbn(rs.getInt("ISBN"));
                order.setQuantity(rs.getInt("PÉLDÁNYSZÁM"));
                order.setLocation(rs.getString("ÁTVÉTEL HELYSZÍNE"));
                order.setTimeOrder(rs.getDate("RENDELÉS IDEJE"));
                order.setTimeReceipt(rs.getDate("ÁTVÉTEL IDEJE"));

                orders.add(order);
            }


        } catch (Exception e){
            e.printStackTrace();
        }

        return orders;
    }
}
