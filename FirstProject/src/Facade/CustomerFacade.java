package Facade;


import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;
import Utils.Art;
import infoDB.CouponGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * class of CustomerFacade
 */
public class CustomerFacade extends ClientFacade {
    CustomersDBDAO customersDBDAO = new CustomersDBDAO();
    CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
    private static int customerID;

    /**
     * this method saves the id that will belong to the logged customer
     * @param email string
     * @param password string
     */
    public void saveId(String email,String password){
        try {
            customerID = customersDBDAO.getCustomerId(email,password);
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
    }

    /**
     * constructor
     * @param isLogged boolean
     * if true, starts the facade methods
     */
    public CustomerFacade(boolean isLogged) {
        if (isLogged) {
            runCustomerProgram();
        }
    }

    /**
     * demonstrates this facade's actions
     */
    private void runCustomerProgram() {

        System.out.println(Art.customer);
        System.out.println("Purchasing a coupon\n----------");
        purchaseCoupon(CouponGenerator.getFoodCoupons().get(2));
        System.out.println("Purchasing another coupon\n----------");
        purchaseCoupon(CouponGenerator.getVacationCoupons().get(0));
        System.out.println("Trying to purchase another coupon- which has 0 amount\n----------");
        purchaseCoupon(CouponGenerator.getVacationCoupons().get(1));
        System.out.println("----------\nTrying to purchase another coupon- which expired\n----------");
        purchaseCoupon(CouponGenerator.getElectricCoupons().get(0));
        System.out.println("----------\n4 purchase attempts were made. only two were valid\n----------");
        System.out.println("Viewing customer's coupons\n----------");
        for (Coupon coupon : getCustomerCoupons()) {
            System.out.println(coupon);
        }
        System.out.println("----------\nViewing customer's coupons from category Food\n----------");
        for(Coupon coupon: getCustomerCoupons(Category.Food)){
            System.out.println(coupon);
        }
        System.out.println("----------\nViewing customer's coupon up to price 65\n----------");
        for(Coupon coupon: getCustomerCoupon(65)){
            System.out.println(coupon);
        }
        System.out.println("----------\nViewing details of the logged customer\n----------");
        System.out.println(getCustomerDetails());
        System.out.println("----------\nCustomer facade is finished");
    }

    /**
     * method returns boolean if logged or not (via email and password)
     * @param email string
     * @param password string
     * @return boolean if logged or not (via email and password)
     */
    @Override
    public boolean login(String email, String password) {
        try {
            return customersDBDAO.isCustomerExists(email, password);
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
        return false;
    }

    /**
     * method tries to purchase a coupon
     * @param coupon coupon
     *  this method doesn't throw customExceptions- it takes care of them with if condition
     */
    public void purchaseCoupon(Coupon coupon) {
        try {
            Coupon coupon1 = couponsDBDAO.getOneCoupon(coupon.getId());
            if (coupon1.getAmount() == 0) {
                System.out.println("Coupon is not in stock!");
                return;
            }
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);
            if (coupon1.getEndDate().before(date)) {
                System.out.println("Coupon already expired!");
                return;
            }
            couponsDBDAO.addCouponPurchase(customerID, coupon.getId());
            coupon.setAmount(coupon.getAmount()-1);
            couponsDBDAO.updateCoupon(coupon);
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
    }

    /**
     *method returns list of coupons that belongs to the logged customer
     * @return list of coupons that belongs to the logged customer
     */
    public List<Coupon> getCustomerCoupons() {
        List<Integer> couponsId;
        List<Coupon> couponsList = new ArrayList<>();
        try {
            couponsId = couponsDBDAO.getCustomerCoupons(customerID);
            List<Coupon> coupons = couponsDBDAO.getAllCoupons();
            for (Coupon coupon : coupons) {
                for (Integer integer : couponsId) {
                    if (coupon.getId() == integer) {
                        couponsList.add(coupon);
                    }
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
        return couponsList;
    }

    /**
     * method returns list of coupons that belong to the logged customer only from specific category
     * @param category category
     * @return list of coupons that belong to the logged customer only from specific category
     */
    public List<Coupon> getCustomerCoupons(Category category)  {
        List<Coupon> allCustomerCoupons = getCustomerCoupons();
        List<Coupon> couponsCategory = new ArrayList<>();
        for(Coupon coupon:allCustomerCoupons){
            if(coupon.getCategory()==category){
                couponsCategory.add(coupon);
            }
        }
        return couponsCategory;
    }

    /**
     * method returns list of coupons that belong to the logged customer only up to specific maxPrice
     * @param maxPrice double
     * @return list of coupons that belong to the logged customer only up to specific maxPrice
     */
    public List<Coupon> getCustomerCoupon(double maxPrice)  {
        List<Coupon> allCustomerCoupons = getCustomerCoupons();
        List<Coupon> couponsCategory = new ArrayList<>();
        for(Coupon coupon:allCustomerCoupons){
            if(coupon.getPrice()<= maxPrice){
                couponsCategory.add(coupon);
            }
        }
        return couponsCategory;
    }

    /**
     * method returns the logged customer details
     * @return the logged customer's details
     */
    public Customer getCustomerDetails() {
        return customersDBDAO.getOneCustomer(customerID);
    }

}
