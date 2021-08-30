package DBDAO;

import Beans.Company;
import DAO.CompaniesDAO;
import Exceptions.CompanySameNameEmailException;
import SQL.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * crud actions on companies db
 */
public class CompaniesDBDAO implements CompaniesDAO {

    private static final String IS_COMPANY_EXISTS = "SELECT * FROM `projectcoupon`.`companies` WHERE email=? AND password=?";
    private static final String ADD_COMPANY = "INSERT INTO `projectcoupon`.`companies` (`name`,`email`,`password`) VALUES (?,?,?)";
    private static final String UPDATE_COMPANY = "UPDATE `projectcoupon`.`companies` SET email=?, password=? WHERE id=?";
    private static final String DELETE_COMPANY = "DELETE FROM `projectcoupon`.`companies` WHERE id=?";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM `projectcoupon`.`companies`";
    private static final String GET_ONE_COMPANY = "SELECT * FROM `projectcoupon`.`companies` WHERE id=?";
    Connection connection;

    /**
     * constructor
     */
    public CompaniesDBDAO() {
        try {
            ConnectionPool.getInstance();
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
    }

    /**
     * checks if a company exists
     * @param email string
     * @param password string
     * @return boolean if company exists
     * @throws SQLException
     * checks if a company exists
     */
    @Override
    public boolean isCompanyExists(String email, String password) throws SQLException {
        Company company = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_COMPANY_EXISTS);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                company = new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            }
            if (company == null) {
                return false;
            }
            return true;
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error with credentials/site error");
            return false;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

    }

    /**
     * adding a company to db
     * @param company company
     * @throws SQLException exception
     * @throws CompanySameNameEmailException
     * adding a company to db
     */
    @Override
    public void addCompany(Company company) throws SQLException, CompanySameNameEmailException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_COMPANY);
            statement.setString(1, company.getName());
            statement.setString(2, company.getEmail());
            statement.setString(3, company.getPassword());
            statement.execute();
        } catch (InterruptedException | SQLException exception) {
            throw new CompanySameNameEmailException();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * updating a company
     * @param company company
     * @throws SQLException
     * updating a company
     */
    @Override
    public void updateCompany(Company company) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_COMPANY);
            statement.setString(1, company.getEmail());
            statement.setString(2, company.getPassword());
            statement.setInt(3, company.getId());
            statement.execute();
        } catch (InterruptedException | SQLException exception) {
            System.out.println("Error with credentials/site error");
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * deleting a company
     * @param companyID int
     * @throws SQLException
     * deleting a company
     */
    @Override
    public void deleteCompany(int companyID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COMPANY);
            statement.setInt(1, companyID);
            statement.execute();
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error with credentials/site error");
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * returns all companies
     * @return all companies
     * @throws SQLException exception
     */
    @Override
    public List<Company> getAllCompanies() throws SQLException {
        List<Company> companies = new ArrayList<>();

        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANIES);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                companies.add(company);
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error with credentials/site error");
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return companies;
    }

    /**
     * returns only one company
     * @param companyID int
     * @return only one company
     */
    @Override
    public Company getOneCompany(int companyID) {
        Company company = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COMPANY);
            statement.setInt(1, companyID); //where id = ? => select * from repair where id = 1
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                company = new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
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
        return company;
    }

    /**
     *return companyId (int)
     * @param email string
     * @param password string
     * @return int id of the company
     * @throws SQLException exception
     */
    @Override
    public int getCompanyId(String email, String password) throws SQLException {
        Company company = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_COMPANY_EXISTS);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                company = new Company(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            }

        } catch (InterruptedException | SQLException e) {
            System.out.println("Error with credentials/site error");

        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }

        return company.getId();

    }
}
