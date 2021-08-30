package DAO;


import Beans.Coupon;
import Exceptions.CouponTitleAlreadyExits;


import java.sql.SQLException;
import java.util.List;

public interface CouponsDAO {

     Boolean addCoupon(Coupon coupon) throws SQLException, CouponTitleAlreadyExits;

    void updateCoupon(Coupon coupon) throws SQLException;

     void deleteCoupon(int couponID) throws SQLException;

     List<Coupon> getAllCoupons() throws SQLException;

     Coupon getOneCoupon(int couponID) throws SQLException;

     Boolean addCouponPurchase(int customerID, int couponID) throws SQLException;

     void deleteCouponPurchase(int customerID, int couponID);


    List<Integer> getCustomerCoupons(int customerID) throws SQLException;
}
