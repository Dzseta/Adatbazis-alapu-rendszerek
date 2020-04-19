package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.model.Shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ShopDAOImpl implements ShopDAO {

    private Connection conn;

    private static final String ADD_SHOP_STR = "INSERT INTO ARUHAZAK VALUES (?,?,?,?,?,?) ";

    private static final String DELETE_SHOP_STR = "DELETE FROM ARUHAZAK WHERE azonosito = ? ";

    private static final String UPDATE_SHOP_STR = "UPDATE ARUHAZAK SET azonosito=?,iranyitoszam=?,varos=?,utca=?,hazszam=?,nev=? WHERE azonosito = ? ";

    private static final String LIST_SHOP_STR = "SELECT * FROM ARUHAZAK ";

    public void initialize(){
        conn = DBController.connect();
    }

    public ShopDAOImpl(){
        initialize();
    }

    @Override
    public boolean add(Shop shop) {
        try (PreparedStatement st = conn.prepareStatement(ADD_SHOP_STR)){
            st.setInt(1, shop.getId());
            st.setInt(2, shop.getZipcode());
            st.setString(3, shop.getCity());
            st.setString(4, shop.getStreet());
            st.setString(5, shop.getHnumber());
            st.setString(6, shop.getName());

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
    public boolean delete(Shop shop) {
        try (PreparedStatement st = conn.prepareStatement(DELETE_SHOP_STR)){
            st.setInt(1, shop.getId());

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
    public boolean update(Shop shop, int oldId) {
        try (PreparedStatement st = conn.prepareStatement(UPDATE_SHOP_STR)){
            st.setInt(1, shop.getId());
            st.setInt(2, shop.getZipcode());
            st.setString(3, shop.getCity());
            st.setString(4, shop.getStreet());
            st.setString(5, shop.getHnumber());
            st.setString(6, shop.getName());
            st.setInt(7, oldId);

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
    public List<Shop> list() {
        List<Shop> shops = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(LIST_SHOP_STR)){
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                Shop shop = new Shop();
                shop.setId(rs.getInt("AZONOSITO"));
                shop.setZipcode(rs.getInt("IRANYITOSZAM"));
                shop.setCity(rs.getString("VAROS"));
                shop.setStreet(rs.getString("UTCA"));
                shop.setHnumber(rs.getString("HAZSZAM"));
                shop.setName(rs.getString("NEV"));

                shops.add(shop);
            }


        } catch (Exception e){
            e.printStackTrace();
        }

        return shops;
    }
}
