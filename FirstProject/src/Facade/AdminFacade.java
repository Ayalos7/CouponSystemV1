package Facade;


import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;
import Exceptions.CompanySameNameEmailException;
import Exceptions.CouponTitleAlreadyExits;
import Exceptions.CustomerSameEmail;
import Utils.Art;
import infoDB.CompaniesGenerator;
import infoDB.CustomersGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * class of AdminFacade
 */
public class AdminFacade extends ClientFacade {

    CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
    CustomersDBDAO customersDBDAO = new CustomersDBDAO();
    CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

    /**
     * constructor
     *
     * @param isLogged boolean
     *                 if true, starts the facade methods
     */
    public AdminFacade(Boolean isLogged) {
        if (isLogged) {
            runAdminProgram();
        }
    }

    /**
     * demonstrates this facade's actions
     */
    private void runAdminProgram() {
        List<Company> companyList;
        List<Customer> customerList;
        System.out.println(Art.admin);

        System.out.println("Adding 4 companies (1 repeating coupon)\n----------");
        addCategory("Electricity");
        addCompany(CompaniesGenerator.electricCompany());
        addCategory("Food");
        addCompany(CompaniesGenerator.foodCompany());
        addCategory("Vacation");
        addCompany(CompaniesGenerator.vacationCompany());
        addCompany(CompaniesGenerator.companyForDeletion());
        System.out.println("Four companies were added\n----------");
        companyList = getAllCompanies();
        for (Company company : companyList) {
            System.out.println(company);
        }
        System.out.println("----------\nLets try to add FoodCompany again- we are expecting an exception on same name/email\n----------");
        addCompany(CompaniesGenerator.foodCompany());
        System.out.println("Now lets update Electricity Company's email\n----------");
        Company companyForUpdate = CompaniesGenerator.electricCompany();
        companyForUpdate.setEmail("elecUpdated@gmail");
        companyForUpdate.setPassword("elecUpdatedPass");
        updateCompany(companyForUpdate);
        System.out.println("Now lets delete a company (companyForDeletion)\n----------");
        deleteCompany(4);
        System.out.println("All companies after update and deletion:\n----------");
        companyList = getAllCompanies();
        for (Company company : companyList) {
            System.out.println(company);
        }
        System.out.println("----------");
        System.out.println("Lets print a specific company - foodCompany\n----------");
        System.out.println(getOneCompany(2));
        System.out.println("----------\nAdding 4 customers\n----------");
        addCustomer(CustomersGenerator.customer1());
        addCustomer(CustomersGenerator.customer2());
        addCustomer(CustomersGenerator.customer3());
        addCustomer(CustomersGenerator.customer4());
        System.out.println("4 customers were added\n----------");
        customerList = getAllCustomer();
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
        System.out.println("----------\nLets try to add a customer that already exists, we are expecting an exception on email\n----------");
        addCustomer(CustomersGenerator.customer3());
        System.out.println("Lets update customer Yaniv\n----------");
        Customer customerForUpdate = CustomersGenerator.customer2();
        customerForUpdate.setFirstName("YanivUpdated");
        customerForUpdate.setLastName("YanUpdated");
        customerForUpdate.setEmail("YanivUpdated@gmail");
        companyForUpdate.setPassword("yanivUpdatedPass");
        updateCustomer(customerForUpdate);
        System.out.println("Now lets delete a customer (Yasmin)\n----------");
        deleteCustomer(4);
        System.out.println("All customers after update and deletion\n----------");
        customerList = getAllCustomer();
        for (Customer customer : customerList) {
            System.out.println(customer);
        }

        System.out.println("----------\nLets print a specific customer - Danny\n----------");
        System.out.println(getOneCustomer(1));

        System.out.println("----------\nAdmin facade is finished\n----------");

    }

    /**
     * method that checks if logged or not (via email and password)
     *
     * @param email    string
     * @param password string
     * @return boolean if logged or not (via email and password)
     */
    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    /**
     * method that adds a category to db
     *
     * @param category category
     */
    public void addCategory(String category) {
        try {
            couponsDBDAO.addCategory(category);
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
    }

    /**
     * method that adds a company to db
     *
     * @param company company
     */
    public void addCompany(Company company) {
        try {
            companiesDBDAO.addCompany(company);
            for (Coupon coupon : company.getCoupons()) {
                couponsDBDAO.addCoupon(coupon);
            }
        } catch (SQLException | CompanySameNameEmailException | CouponTitleAlreadyExits e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * method that updates a company
     *
     * @param company company
     */
    public void updateCompany(Company company) {
        try {
            companiesDBDAO.updateCompany(company);
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
    }

    /**
     * method that deletes a company from db
     *
     * @param companyID int
     */
    public void deleteCompany(int companyID) {

        try {
            companiesDBDAO.deleteCompany(companyID);
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
    }

    /**
     * method that return a list of all companies
     *
     * @return a list of all companies
     */
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        try {
            companies = companiesDBDAO.getAllCompanies();
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
        return companies;
    }

    /**
     * method that returns one company
     *
     * @param companyID int
     * @return one company with companyId
     */
    public Company getOneCompany(int companyID) {
        return companiesDBDAO.getOneCompany(companyID);

    }

    /**
     * method that adds a customer to db
     *
     * @param customer customer
     * @return boolean if customer added successfully or not
     */
    public Boolean addCustomer(Customer customer) {
        try {
            return customersDBDAO.addCustomer(customer);
        } catch (SQLException | CustomerSameEmail e) {
            System.out.println(e.getMessage());

        }
        return false;
    }

    /**
     * method that updates customer
     *
     * @param customer customer
     *                 updates customer
     */
    public void updateCustomer(Customer customer) {

        try {
            customersDBDAO.updateCustomer(customer);
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
    }

    /**
     * method that deletes a customer
     *
     * @param customerID int
     * deletes customer from db with customerID
     */

    public void deleteCustomer(int customerID) {
        customersDBDAO.deleteCustomer(customerID);
    }

    /**
     * method returns list of all customers
     *
     * @return list of all customers
     */
    public List<Customer> getAllCustomer() {
        return customersDBDAO.getAllCustomer();
    }

    /**
     * method that returns one customer
     *
     * @param customerID int
     * @return one customer with customerID
     */
    public Customer getOneCustomer(int customerID) {
        return customersDBDAO.getOneCustomer(customerID);
    }

    /**
     * method deletes customer purchase 
     * @param customerID int
     * @param coupon coupon
     */
    public void deleteCustomerPurchase(int customerID, Coupon coupon) {
        couponsDBDAO.deleteCouponPurchase(customerID, coupon.getId());
    }
}
