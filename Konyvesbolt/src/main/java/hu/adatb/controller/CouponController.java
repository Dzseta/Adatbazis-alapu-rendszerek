package hu.adatb.controller;

import hu.adatb.dao.CouponDAO;
import hu.adatb.dao.CouponDAOImpl;
import hu.adatb.model.Coupon;

public class CouponController {
    private CouponDAO dao = new CouponDAOImpl();

    public CouponController(){

    }

    public boolean addCoupon(Coupon coupon){
        return dao.addCoupon(coupon);
    }

    public boolean deleteCoupon(String code){
        return dao.deleteCoupon(code);
    }

    public boolean modifyCoupon(Coupon coupon, String code){
        return dao.modifyCoupon(coupon, code);
    }

    public Coupon getCoupon(String code){
        return dao.getCoupon(code);
    }
}
