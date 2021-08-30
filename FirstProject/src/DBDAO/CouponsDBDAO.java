package DBDAO;

import Beans.Category;
import Beans.Coupon;
import DAO.CouponsDAO;
import Exceptions.CouponTitleAlreadyExits;
import SQL.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * crud actions on coupons, customers vs coupons db
 */
public class CouponsDBDAO implements CouponsDAO {

    private static final String ADD_COUPON = "INSERT INTO `projectcoupon`.`coupons` (`company_id`,`category_id`,`title`,`description`,`start_date`,`end_date`,`amount`,`price`,`image`) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_COUPON = "UPDATE `projectcoupon`.`coupons` SET title=?,description=?,start_date=?,end_date=?,amount=?,price=?,image=? WHERE id=?";
    private static final String DELETE_COUPON = "DELETE FROM `projectcoupon`.`coupons` WHERE id=?";
    private static final String GET_ALL_COUPONS = "SELECT * FROM `projectcoupon`.`coupons`";
    private static final String GET_ONE_COUPON = "SELECT * FROM `projectcoupon`.`coupons` WHERE id=?";
    private static final String ADD_CATEGORY = "INSERT INTO `projectcoupon`.`categories`(`name`) VALUES (?)";
    private static final String ADD_COUPON_PURCHASE = "INSERT INTO `projectcoupon`.`Customers_Vs_Coupons` (`CUSTOMER_ID`,`COUPON_ID`) VALUES (?,?)";
    private static final String GET_CUSTOMER_COUPON ="SELECT * FROM `projectcoupon`.`Customers_Vs_Coupons` WHERE CUSTOMER_ID=?";
    private static final String DELETE_COUPON_PURCHASE = "DELETE FROM `projectcoupon`.`Customers_Vs_Coupons` WHERE CUSTOMER_ID=? AND COUPON_ID=?";
    Connection connection;

    /**
     * constructor
     */
    public CouponsDBDAO() {
        try {
            ConnectionPool.getInstance();
        } catch (SQLException sqlException) {
            System.out.println("site error");
        }
    }

    /**
     * adds a category to db
     * @param category category
     * @return boolean if added category
     * @throws SQLException exception
     */
    public boolean addCategory(String category) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_CATEGORY);
            statement.setString(1, category);
            statement.execute();
            return true;
        } catch (SQLException | InterruptedException exception) {
            System.out.println("Error with credentials/site error");
            return false;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * adds coupon to the db
     * @param coupon coupon
     * @return boolean if added coupon
     * @throws SQLException exception
     * @throws CouponTitleAlreadyExits custom exception that checks if the title already exist
     */
    @Override
    public Boolean addCoupon(Coupon coupon) throws SQLException, CouponTitleAlreadyExits {
        try {
            connection = ConnectionPool.getInstance().getConnection();

            PreparedStatement statement = connection.prepareStatement(ADD_COUPON);
            statement.setInt(1, coupon.getCategory().ordinal() + 1);
            statement.setInt(2, coupon.getCompanyID());
            statement.setString(3, coupon.getTitle());
            statement.setString(4, coupon.getDescription());
            statement.setDate(5, coupon.getStartDate());
            statement.setDate(6, coupon.getEndDate());
            statement.setInt(7, coupon.getAmount());
            statement.setDouble(8, coupon.getPrice());
            statement.setString(9, coupon.getImage());
            statement.execute();
            return true;

        } catch (SQLException | InterruptedException exception) {
            throw new CouponTitleAlreadyExits();

        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * updates coupon on db
     * @param coupon coupon
     * @throws SQLException exception
     */
    @Override
    public void updateCoupon(Coupon coupon) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_COUPON);
            statement.setString(1,coupon.getTitle());
            statement.setString(2, coupon.getDescription());
            statement.setDate(3,coupon.getStartDate());
            statement.setDate(4,coupon.getEndDate());
            statement.setInt(5,coupon.getAmount());
            statement.setDouble(6,coupon.getPrice());
            statement.setString(7,coupon.getImage());
            statement.setInt(8,coupon.getId());
            statement.execute();
        } catch (InterruptedException | SQLException interruptedException) {
            System.out.println("Error with credentials/site error");
        } finally{
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * deletes coupon from db
     * @param couponID int
     * @throws SQLException exception
     */
    @Override
    public void deleteCoupon(int couponID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON);
            statement.setInt(1, couponID);
            statement.execute();
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error with credentials/site error");
        } finally{
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * returns list of all coupons
     * @return list of coupons
     * @throws SQLException exception
     */
    @Override
    public List<Coupon> getAllCoupons() throws SQLException {
        List<Coupon> couponList = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coupon coupon = new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[ (resultSet.getInt(3))-1], resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7), resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10));
                couponList.add(coupon);
            }
        } catch (InterruptedException | SQLException interruptedException) {
            System.out.println("Error with credentials/site error");
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return couponList;
    }

    /**
     * returns one coupon
     * @param couponID int
     * @return one coupon
     * @throws SQLException exception
     */
    @Override
    public Coupon getOneCoupon(int couponID) throws SQLException {
        Coupon coupon = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COUPON);
            statement.setInt(1, couponID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coupon = new Coupon(resultSet.getInt(1), resultSet.getInt(2), Category.values()[ (resultSet.getInt(3))-1], resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7), resultSet.getInt(8), resultSet.getDouble(9), resultSet.getString(10));
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error with credentials/site error");
        } finally{
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupon;
    }

    /**
     * adds coupon to table customer vs coupons
     * @param customerID int
     * @param couponID int
     * @return boolean of if added coupon to customer
     * @throws SQLException exception
     */
    @Override
    public Boolean addCouponPurchase(int customerID, int couponID) throws SQLException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_COUPON_PURCHASE);
            statement.setInt(1, customerID);
            statement.setInt(2, couponID);
            statement.execute();
            return true;
        } catch (InterruptedException | SQLException Exception) {
            System.out.println("Error with credentials/site error");
            return false;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COUPON_PURCHASE);
            statement.setInt(1, customerID);
            statement.setInt(2,couponID);
            statement.execute();
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error with credentials/site error");
        } finally{
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException sqlException) {
                System.out.println("site error");
            }
        }
    }

    /**
     * returns customer's coupons
     * @param customerID int
     * @return list of customer coupons
     * @throws SQLException exception
     */
    @Override
    public List<Integer> getCustomerCoupons(int customerID) throws SQLException {
        List<Integer> couponIdList = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_CUSTOMER_COUPON);
            statement.setInt(1,customerID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int num = (resultSet.getInt(2));
                couponIdList.add(num);
            }
        } catch (InterruptedException | SQLException interruptedException) {
            System.out.println("Error with credentials/site error");
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return couponIdList;
    }


}
