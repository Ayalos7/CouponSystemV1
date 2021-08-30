package Login;

import Facade.AdminFacade;
import Facade.ClientFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;

/**
 * singleton class that you have to pass to get into the facades
 */
public class LoginManager {

    public static LoginManager instance = null;

    private LoginManager() {

    }

    /**
     * get instance method of a singleton
     * @return instance of the private constructor
     */
    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    /**
     * this login method calls login method inside the different facades
     * @param email string
     * @param password string
     * @param clientType clienttype
     * @return different facades according to login parameters
     */
    public ClientFacade login(String email, String password, ClientType clientType) {
        switch (clientType) {
            case Administrator:
                System.out.println("Trying to log as admin\n----------");
                AdminFacade adminFacade = new AdminFacade(false);
                if ((adminFacade.login(email, password))) {
                    System.out.println("Admin logged in System\n----------");
                    return new AdminFacade(true);

                } else {
                    System.out.println("Admin-Mistake in email/password\n----------");
                    break;
                }

            case Company:
                System.out.println("Trying to log as company\n----------");
                CompanyFacade companyFacadeLogin = new CompanyFacade(false);
                if (companyFacadeLogin.login(email, password)) {
                    companyFacadeLogin.saveId(email, password);
                    System.out.println("Company logged successfully\n----------");
                    return new CompanyFacade(true);
                }
                System.out.println("Company-Mistake in email/password\n----------");
                break;

            case Customer:
                System.out.println("Trying to log as customer\n----------");
                CustomerFacade customerFacade = new CustomerFacade(false);
                if (customerFacade.login(email, password)) {
                    customerFacade.saveId(email, password);
                    System.out.println("Customer logged successfully\n----------");
                    return new CustomerFacade(true);
                }
                System.out.println("Customer-Mistake in email/password\n----------");
                break;


        }
        return null;
    }
}
