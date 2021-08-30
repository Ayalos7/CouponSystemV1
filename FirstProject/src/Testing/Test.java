package Testing;

import Login.ClientType;
import Login.LoginManager;
import SQL.ConnectionPool;
import SQL.DataBaseManager;
import Thread.CouponExpirationDailyJob;

import java.sql.SQLException;

/**
 * class test- starts a thread,calls to the LoginManager that runs the relevant facades and finishes
 */

public class Test {
    /**
     * calls to method testAll which starts the program
     *
     * @param args args
     */
    public static void main(String[] args) {
        testAll();
    }

    /**
     * method that runs the entire project
     */
    private static void testAll() {
        try {
            DataBaseManager.dropDB();
            DataBaseManager.createDataBase();
            DataBaseManager.createTablesCompanies();
            DataBaseManager.createTablesCustomers();
            DataBaseManager.createTablesCategory();
            DataBaseManager.createTablesCoupons();
            DataBaseManager.createTablesCustomersVsCoupons();
            System.out.println("Database and tables were created\n----------");
            //Starting the running job thread
            CouponExpirationDailyJob dailyJob = new CouponExpirationDailyJob();
            Thread thread = new Thread(dailyJob);
            thread.start();
            //Connecting as admin - wrong fields
            LoginManager.getInstance().login("admin@admin.com", "wrongPassword", ClientType.Administrator);
            //Connecting as admin - correct fields
            LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.Administrator);
            //Connecting as company - wrong fields
            LoginManager.getInstance().login("vacation@gmail", "wrongPass", ClientType.Company);
            //Connecting as company - correct fields
            LoginManager.getInstance().login("vacation@gmail", "vacationPass", ClientType.Company);
            //Connecting as customer - wrong fields
            LoginManager.getInstance().login("WrongCustomer@gmail", "lanaPass", ClientType.Customer);
            //Connecting as customer - correct fields
            LoginManager.getInstance().login("Lana@gmail", "lanaPass", ClientType.Customer);
            //Stopping the running job thread (After completing constant time check)
            dailyJob.setQuit(true);

            //Closing all connections
            ConnectionPool.getInstance().closeAllConnection();

        } catch (SQLException sqlException) {
            System.out.println("Site error");
        } catch (Exception exception) {
            System.out.println("General error- try again later!");
        }
    }
}