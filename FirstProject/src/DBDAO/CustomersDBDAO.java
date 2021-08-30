package DBDAO;

import Beans.Company;
import Beans.Customer;
import DAO.CustomersDAO;
import Exceptions.CustomerSameEmail;
import SQL.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * crud actions on customers db
 */
public class CustomersDBDAO implements CustomersDAO {

    private static final String IS_CUSTOMER_EXISTS = "SELECT * FROM `projectcoupon`.`customers` WHERE email=? AND password=?";
    private static final String ADD_CUSTOMER = "INSERT INTO `projectcoupon`.`customers` (`first_name`,`last_name`,`email`,`password`) VALUES (?,?,?,?)";
    private static final String UPDATE_CUSTOMER = "UPDATE `projectcoupon`.`customers` set first_name=?, last_name=?, email=?, password=? WHERE id=?";
    private static final String DELETE_CUSTOMER = "DELETE FROM `projectcoupon`.`customers` WHERE id=?";
    private static final String GET_ALL_CUSTOMERS = "SELECT * FROM `projectcoupon`.`customers`";
    private static final String GET_ONE_CUSTOMER = "SELECT * FROM `projectcoupon`.`customers` WHERE id=?";
    Connection connection;

    /**
     * constructor
     */
    public CustomersDBDAO() {
        try {
            ConnectionPool.getInstance();
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
    }

    /**
     * checks if customer exist
     * @param email string
     * @param password string
     * @return boolean if customer exist
     * @throws SQLException exception
     */
    @Override
    public boolean isCustomerExists(String email, String password) throws SQLException {
        try {
            Customer customer = null;
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_CUSTOMER_EXISTS);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
            }
            return customer != null;
        } catch (InterruptedException | SQLException exception) {
            System.out.println("Error with credentials/site error");
            return false;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }

    /**
     * adds customer to db
     * @param customer customer
     * @return boolean if added customer
     * @throws SQLException exception
     * @throws CustomerSameEmail custom exception that checks if mail already exists
     */
    @Override
    public Boolean addCustomer(Customer customer) throws SQLException, CustomerSameEmail {
        try {

            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_CUSTOMER);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.execute();
            return true;
        } catch (InterruptedException e) {
            System.out.println("site error");
            return false;
        } catch (SQLException f) {
            throw new CustomerSameEmail();

        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * updates customer on db
     * @param customer customer
     * @throws SQLException exception
     */
    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPassword());
            statement.setInt(5, customer.getId());
            statement.execute();
        } catch (SQLException | InterruptedException Exception) {
            System.out.println("Error with credentials/site error");
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * deletes customer from db
     * @param customerID int
     *    deletes customer
     */
    @Override
    public void deleteCustomer(int customerID) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER);
            statement.setInt(1, customerID);
            statement.execute();
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error with credentials/site error");
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException sqlException) {
                System.out.println("connection error");
            }
        }
    }

    /**
     * returns list of all customers
     * @return a list of all customers
     */
    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> customers = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));

                customers.add(customer);
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error with credentials/site error");
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException sqlException) {
                System.out.println("connection error");
            }
        }
        return customers;
    }

    /**
     * returns one customer
     * @param customerID int
     * @return one customer
     */
    @Override
    public Customer getOneCustomer(int customerID) {
        Customer customer = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_CUSTOMER);
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error with credentials/site error");
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException sqlException) {
                System.out.println("connection error");
            }
        }
        return customer;
    }

    /**
     * returns customer id
     * @param email string
     * @param password string
     * @return id of one customer
     * @throws SQLException exception
     */
    @Override
    public int getCustomerId(String email, String password) throws SQLException {
        Customer customer = null;
        try {

            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_CUSTOMER_EXISTS);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
            }
        } catch (InterruptedException | SQLException exception) {
            System.out.println("Error with credentials/site error");

        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return customer.getId();
    }
}
