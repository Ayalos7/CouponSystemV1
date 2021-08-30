package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import Exceptions.CouponTitleAlreadyExits;
import Utils.Art;
import infoDB.CouponGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * class of CompanyFacade 
 */

public class CompanyFacade extends ClientFacade {

    static int companyID;
    CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
    CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

    /**
     * this method saves the id that will belong to the logged company
     * @param email string
     * @param password string
     */
    public void saveId(String email,String password){
        try {
            companyID = companiesDBDAO.getCompanyId(email,password);
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
    }

    /**
     * constructor
     * @param isLogged
     * if true, starts the facade methods
     */
    public CompanyFacade(boolean isLogged) {
        if (isLogged) {
            runCompanyProgram();
        }
    }

    /**
     * demonstrates this facade's actions
     */
    private void runCompanyProgram() {
        System.out.println(Art.company);
        System.out.println("Before we start, here are all the coupons of the logged company\n----------");

        List<Coupon> coupons = getCompanyCoupons();
        for (Coupon coupon : coupons) {
            System.out.println(coupon);
        }

        System.out.println("Adding 1 coupon\n----------");
        addCoupon(CouponGenerator.getPreparedCoupon());
        System.out.println("Adding a coupon that with a title that exists in this company already- we are expecting an exception on title name\n----------");
        addCoupon(CouponGenerator.getPreparedCoupon());
        System.out.println("Updating 1 coupon\n----------");
        Coupon couponUpdated = CouponGenerator.getVacationCoupons().get(1);
        couponUpdated.setTitle("free night updated");
        updateCoupon(couponUpdated);
        System.out.println("Deleting 1 coupon\n----------");
        deleteCoupon(13);
        System.out.println("Company's coupons after changes\n----------");
        List<Coupon> couponsAfterDeleteUpdate = getCompanyCoupons(Category.Vacation);
        for (Coupon coupon : couponsAfterDeleteUpdate) {
            System.out.println(coupon);
        }
        System.out.println("Company's coupons only from one category (electricity- the company has no coupons in this category)\n----------");
        System.out.println("Can be changed to vacation after first run to show proof of concept\n----------");
        List<Coupon> couponsModified = getCompanyCoupons(Category.Electricity);
        for (Coupon coupon : couponsModified) {
            System.out.println(coupon);
        }
        System.out.println("Company's coupons lower than the price of 70 shekels\n----------");
        List<Coupon> couponsPriceSort = getCompanyCoupons(70);
        for (Coupon coupon : couponsPriceSort) {
            System.out.println(coupon);
        }
        System.out.println("Company's details\n----------");
        System.out.println(getCompanyDetails() + "\n----------");
        System.out.println("Company's facade is finished\n----------");

    }

    /**
     * method that checks if logged or not (via email and password)
     * @param email string
     * @param password string
     * @return boolean if logged or not (via email and password)
     */
    @Override
    public boolean login(String email, String password) {
        try {
            if (companiesDBDAO.isCompanyExists(email, password)) {

                return true;
            }
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
        return false;
    }

    /**
     * method that adds coupon to db
     * @param coupon coupon
     * @return boolean if coupon was added to db or not
     */
    public Boolean addCoupon(Coupon coupon) {
        try {
            return couponsDBDAO.addCoupon(coupon);
        } catch (SQLException | CouponTitleAlreadyExits Exception) {
            System.out.println(Exception.getMessage());
        }
        return false;
    }

    /**
     * method updates coupon on db to the received coupon
     * @param coupon coupon
     */
    public void updateCoupon(Coupon coupon) {
        try {
            couponsDBDAO.updateCoupon(coupon);
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
    }

    /**
     * method deletes coupon from db with couponId
     * @param couponID int
     */
    public void deleteCoupon(int couponID) {
        try {
            couponsDBDAO.deleteCoupon(couponID);
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
    }

    /**
     * method that return list of coupons
     * @return list of all coupons that belongs to the logged company
     */

    public List<Coupon> getCompanyCoupons() {
        List<Coupon> coupons;
        List<Coupon> couponList = new ArrayList<>();
        try {
            coupons = couponsDBDAO.getAllCoupons();
            for (Coupon coupon : coupons) {
                if (coupon.getCompanyID() == companyID) {
                    couponList.add(coupon);
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }

        return couponList;
    }

    /**
     * method returns list of coupons from one category
     * @param category category
     * @return list of all coupons from one category that belongs to this company
     */

    public List<Coupon> getCompanyCoupons(Category category) {
        List<Coupon> coupons = getCompanyCoupons();
        List<Coupon> couponList = new ArrayList<>();
        for (Coupon coupon : coupons) {
            if (coupon.getCategory() == category) {
                couponList.add(coupon);
            }
        }

        return couponList;
    }

    /**
     * method that returns list of coupons that their maxPrice is until the value set
     * @param maxPrice double
     * @return list of coupons that their maxPrice is until the value set
     */
    public List<Coupon> getCompanyCoupons(double maxPrice) {
        List<Coupon> coupons = getCompanyCoupons();
        List<Coupon> couponList = new ArrayList<>();
        for (Coupon coupon : coupons) {
            if (coupon.getPrice() <=maxPrice) {
                couponList.add(coupon);
            }
        }

        return couponList;
    }

    /**
     * method returns the logged company details
     * @return the logged company details
     */
    public Company getCompanyDetails() {
        return companiesDBDAO.getOneCompany(companyID);
    }


}
