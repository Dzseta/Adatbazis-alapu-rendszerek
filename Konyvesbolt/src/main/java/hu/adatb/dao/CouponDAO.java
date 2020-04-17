package hu.adatb.dao;

import hu.adatb.model.Coupon;

public interface CouponDAO {
    boolean addCoupon(Coupon coupon);
    boolean deleteCoupon(String code);
    boolean modifyCoupon(Coupon coupon, String code);
    Coupon getCoupon(String code);
}
