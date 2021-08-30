package Facade;

import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;

/**
 * abstract class- template for the system's facades
 */

public abstract class ClientFacade {
    protected CompaniesDBDAO companiesDBDAO;
    protected CustomersDBDAO customersDBDAO;
    protected CouponsDBDAO couponsDBDAO;

    /**
     * abstract login method
     * @param email string
     * @param password string
     * @return boolean value if logged or not (via email and password)
     */
    public abstract boolean login(String email, String password);

    }




