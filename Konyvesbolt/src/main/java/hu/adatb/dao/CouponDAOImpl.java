package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.model.Coupon;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CouponDAOImpl implements CouponDAO {
    private Connection conn;

    private static final String ADD_COUPON_STR = "INSERT INTO KUPONOK VALUES(?, ?, ?) ";

    private static final String DELETE_COUPON_STR = "DELETE FROM KUPONOK WHERE KOD = ? ";

    private static final String MODIFY_COUPON_STR = "UPDATE KUPONOK SET KOD = ?, KEDVEZMENY = ?, MUFAJ = ? WHERE KOD = ? ";

    private static final String GET_COUPON_STR = "SELECT * FROM KUPONOK WHERE KOD = ? ";

    public void initialize(){
        conn = DBController.connect();
    }

    public CouponDAOImpl(){
        initialize();
    }

    @Override
    public boolean addCoupon(Coupon coupon) {
        try (PreparedStatement st = conn.prepareStatement(ADD_COUPON_STR)){
            st.setString(1, coupon.getCode());
            st.setInt(2, coupon.getDiscount());
            st.setString(3, coupon.getGenre());

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
    public boolean deleteCoupon(String code) {
        try (PreparedStatement st = conn.prepareStatement(DELETE_COUPON_STR)){
            st.setString(1, code);

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
    public boolean modifyCoupon(Coupon coupon, String code) {
        try (PreparedStatement st = conn.prepareStatement(MODIFY_COUPON_STR)){
            st.setString(1, coupon.getCode());
            st.setInt(2, coupon.getDiscount());
            st.setString(3, coupon.getGenre());
            st.setString(4, code);

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
    public Coupon getCoupon(String code) {
        Coupon coupon = new Coupon();

        try (PreparedStatement st = conn.prepareStatement(GET_COUPON_STR)){
            st.setString(1, code);

            ResultSet rs = st.executeQuery();

            if(rs.next()){
                coupon.setCode(rs.getString("KOD"));
                coupon.setDiscount(rs.getInt("KEDVEZMENY"));
                coupon.setGenre(rs.getString("MUFAJ"));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return coupon;
    }
}
