package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.model.Order;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private Connection conn;

    private static final String ADD_RENDELESEK_STR = "INSERT INTO RENDELESEK VALUES (?,?,?,?,?,?,?) ";

    private static final String LIST_RENDELESEK_STR = "SELECT * FROM RENDELESEK WHERE EMAIL = ? ORDER BY RENDELES_IDEJE DESC ";

    private static final String LIST_KONYVEK_STR = "SELECT * FROM KONYVEK WHERE IMDB=? ";

    private static final String LIST_FELHASZNALOK_STR = "SELECT * FROM FELHASZNALOK WHERE EMAIL=? ";

    private static final String DELETE_RENDELES_STR = "DELETE FROM RENDELESEK WHERE EMAIL=? AND ISBN=? AND RENDELES_IDEJE =? ";

    private static final String SELECT_RAKTARON_STR = "SELECT * FROM RAKTARON WHERE ISBN = ? AND AZONOSITO = ? ";

    private static final String GET_CITY_SALES_STR = "SELECT SUM(DARABSZAM) FROM FELHASZNALOK, RENDELESEK WHERE " +
            "FELHASZNALOK.EMAIL = RENDELESEK.EMAIL AND FELHASZNALOK.VAROS = ? ";

    private static final String GET_AVG_BOOK_STR = "SELECT AVG(DARABSZAM) FROM KONYVEK, RENDELESEK WHERE " +
            "KONYVEK.ISBN = RENDELESEK.ISBN AND KONYVEK.ISBN = ? ";

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
            long millis = LocalDateTime.now().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
            st.setDate(5, new Date(millis));
            st.setDate(6, null);
            st.setDouble(7, order.getSubtotal());

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
    public List<Order> list(String email) {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(LIST_RENDELESEK_STR)){
            st.setString(1, email);

            ResultSet rs = st.executeQuery();

            while(rs.next()){
                Order order = new Order();
                order.setEmail(rs.getString("EMAIL"));
                order.setIsbn(rs.getInt("ISBN"));
                order.setQuantity(rs.getInt("DARABSZAM"));
                order.setLocation(rs.getString("ATV_HELYSZIN"));
                order.setTimeOrder(rs.getDate("RENDELES_IDEJE"));
                order.setTimeReceipt(rs.getDate("ATVETEL_IDEJE"));
                order.setSubtotal(rs.getInt("SUBTOTAL"));

                orders.add(order);
            }


        } catch (Exception e){
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public boolean quantityNumber(Order order, int id) {
        int count = 0;
        try (PreparedStatement st = conn.prepareStatement(SELECT_RAKTARON_STR)){
            st.setInt(1, order.getIsbn());
            st.setInt(2, id);

            ResultSet rs = st.executeQuery();

            if((rs.next())) {
                count = rs.getInt(3);
                if (order.getQuantity() <= count) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean delete(Order order) {
        try (PreparedStatement st = conn.prepareStatement(DELETE_RENDELES_STR)){
            st.setString(1, order.getEmail());
            st.setInt(2, order.getIsbn());
            st.setDate(3, new java.sql.Date(order.getTimeOrder().getTime()));

            int res = st.executeUpdate();

            if(res == 1){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int getSalesPerCity(String name) {
        try (PreparedStatement st = conn.prepareStatement(GET_CITY_SALES_STR)){
            st.setString(1, name);

            ResultSet rs = st.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double getAvgBookSales(int isbn) {
        try (PreparedStatement st = conn.prepareStatement(GET_AVG_BOOK_STR)){
            st.setInt(1, isbn);

            ResultSet rs = st.executeQuery();

            if(rs.next()){
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
