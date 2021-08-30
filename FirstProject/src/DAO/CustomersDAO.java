package DAO;

import Beans.Customer;
import Exceptions.CustomerSameEmail;

import java.sql.SQLException;

import java.util.List;

public interface CustomersDAO {
     boolean isCustomerExists(String email, String password) throws SQLException;

     Boolean addCustomer(Customer customer) throws SQLException, CustomerSameEmail;

     void updateCustomer(Customer customer) throws  SQLException;

     void deleteCustomer(int customerID);

     List<Customer> getAllCustomer();

     Customer getOneCustomer(int customerID);

    int getCustomerId(String email, String password) throws SQLException;
}
