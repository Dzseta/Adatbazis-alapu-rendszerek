package hu.adatb.dao;

import hu.adatb.model.Coupon;

import java.util.List;

public interface CouponDAO {
    boolean addCoupon(Coupon coupon);
    boolean deleteCoupon(String code);
    boolean modifyCoupon(Coupon coupon, String code);
    List<Coupon> list();
    Coupon getSelectedCoupon(String code);
}
