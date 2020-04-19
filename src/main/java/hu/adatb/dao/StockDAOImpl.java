package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.model.Author;
import hu.adatb.model.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StockDAOImpl implements StockDAO {

    private Connection conn;

    private static final String ADD_RAKTARON_STR = "INSERT INTO RAKTARON VALUES (?,?,?) ";

    private static final String DELETE_RAKTARON_STR = "DELETE FROM RAKTARON WHERE ISBN=?, AZONOSITO=? ";

    private static final String UPDATE_RAKTARON_STR = "UPDATE RAKTARON SET isbn=?,azonosito=?,darabszam=? WHERE ISBN=?, AZONOSITO=? ";

    private static final String LIST_RAKTARON_STR = "SELECT * FROM RAKTARON ";

    private static final String LIST_KONYVEK_STR = "SELECT * FROM KONYVEK WHERE ISBN=? ";

    private static final String LIST_ARUHAZAK_STR = "SELECT * FROM ARUHAZAK WHERE AZONOSITO=? ";

    public void initialize(){
        conn = DBController.connect();
    }

    public StockDAOImpl(){
        initialize();
    }

    public boolean foreignKey(Stock stock) {
        try (PreparedStatement st = conn.prepareStatement(LIST_KONYVEK_STR)){
            st.setInt(1, stock.getIsbn());

            int res = st.executeUpdate();

            ResultSet rs = st.executeQuery();

            if(!(rs.next())) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try (PreparedStatement st = conn.prepareStatement(LIST_ARUHAZAK_STR)){
            st.setInt(1, stock.getId());

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
    public boolean add(Stock stock) {
        try (PreparedStatement st = conn.prepareStatement(ADD_RAKTARON_STR)){
            st.setInt(1, stock.getIsbn());
            st.setInt(2, stock.getId());
            st.setInt(3, stock.getQuantity());

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
    public boolean delete(Stock stock) {
        try (PreparedStatement st = conn.prepareStatement(DELETE_RAKTARON_STR)){
            st.setInt(1, stock.getIsbn());
            st.setInt(2, stock.getId());
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
    public boolean update(Stock stock, int oldIsbn, int oldId) {
        try (PreparedStatement st = conn.prepareStatement(UPDATE_RAKTARON_STR)){
            st.setInt(1, stock.getId());
            st.setInt(2, stock.getIsbn());
            st.setInt(3, stock.getQuantity());
            st.setInt(4, oldId);
            st.setInt(5, oldIsbn);

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
    public List<Stock> list() {
        List<Stock> stocklist = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(LIST_RAKTARON_STR)){
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                Stock stock = new Stock();
                stock.setIsbn(rs.getInt("ISBN"));
                stock.setId(rs.getInt("Áruház ID-je"));

                stocklist.add(stock);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return stocklist;
    }
}
