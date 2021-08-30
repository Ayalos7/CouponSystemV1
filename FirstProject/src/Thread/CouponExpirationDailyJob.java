package Thread;

import Beans.Coupon;
import DBDAO.CouponsDBDAO;

import java.sql.SQLException;
import java.util.List;

public class CouponExpirationDailyJob implements Runnable {
    private CouponsDBDAO couponsDBDAO;
    private boolean quit;

    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    public CouponExpirationDailyJob() {
        this.quit = false;
        this.couponsDBDAO = new CouponsDBDAO();
    }

    @Override
    public void run() {
        try {
            while (!this.quit) {
                Thread.sleep(10 * 1000);
                stop();
                Thread.sleep(60*60*1000);
                checkExpired();

            }
        } catch (InterruptedException interruptedException) {
            System.out.println("Main thread was interrupted");
        }
    }

    public void stop() {
        if (this.quit) {
            System.out.println("----------\nThread will now be interrupted");
            Thread.currentThread().interrupt();
        }
    }


    public void checkExpired() {
        List<Coupon> couponList;
        try {
            couponList = couponsDBDAO.getAllCoupons();
            for (Coupon coupon : couponList) {

                long millis = System.currentTimeMillis();
                java.sql.Date date = new java.sql.Date(millis);
                if (coupon.getEndDate().before(date)) {
                    System.out.println("Coupon already expired!");
                    couponsDBDAO.deleteCoupon(coupon.getId());
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
    }
}
