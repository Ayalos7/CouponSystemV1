package SQL;

import java.sql.SQLException;

public class DataBaseManager {
    //connection DB
    public static final String URL = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "12345678";

    //create & drop database
    private static final String CREATE_DB = "CREATE SCHEMA if not exists ProjectCoupon";
    private static final String DROP_DB = "DROP SCHEMA Projectcoupon";

    //create & drop tables
    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE if not exists ProjectCoupon.Companies(  \n" +
            "ID INT NOT NULL AUTO_INCREMENT, \n" +
            "NAME VARCHAR(50) NOT NULL UNIQUE,\n" +
            "EMAIL VARCHAR(50) NOT NULL UNIQUE, \n" +
            "PASSWORD VARCHAR (50) NOT NULL,\n" +
            "PRIMARY KEY(ID));" + "\n";
    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE if not exists ProjectCoupon.Customers(\n" +
            "ID INT NOT NULL AUTO_INCREMENT, \n" +
            "FIRST_NAME VARCHAR(50) NOT NULL,\n" +
            "LAST_NAME VARCHAR (50) NOT NULL,\n" +
            "EMAIL VARCHAR(50) NOT NULL UNIQUE, \n" +
            "PASSWORD VARCHAR (50) NOT NULL,\n" +
            "PRIMARY KEY(ID));\n";
    private static final String CREATE_TABLE_Categories = "CREATE TABLE if not exists ProjectCoupon.Categories(\n" +
            "ID INT NOT NULL AUTO_INCREMENT, \n" +
            "NAME VARCHAR(50) NOT NULL UNIQUE,\n" +
            "PRIMARY KEY(ID));\n";
    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE if not exists ProjectCoupon.Coupons(\n" +
            "ID INT NOT NULL AUTO_INCREMENT, \n" +
            "COMPANY_ID INT NOT NULL,\n" +
            "CATEGORY_ID INT NOT NULL,\n" +
            "TITLE VARCHAR (50) NOT NULL UNIQUE,\n" +
            "DESCRIPTION VARCHAR(50) NOT NULL, \n" +
            "START_DATE DATE NOT NULL,\n" +
            "END_DATE DATE NOT NULL,\n" +
            "AMOUNT INT NOT NULL,\n" +
            "PRICE DOUBLE NOT NULL,\n" +
            "IMAGE VARCHAR(50) NOT NULL,\n" +
            "PRIMARY KEY(ID), \n" +
            "FOREIGN KEY (COMPANY_ID) REFERENCES Companies(ID) ON DELETE CASCADE, \n" +
            "FOREIGN KEY (CATEGORY_ID) REFERENCES Categories(ID) ON DELETE CASCADE);";

    private static final String CREATE_TABLE_CUSTOMER_VS_COUPONS = "CREATE TABLE if not exists ProjectCoupon.Customers_Vs_Coupons(\n" +
            "CUSTOMER_ID INT NOT NULL, \n" +
            "COUPON_ID INT NOT NULL,\n" +

            "PRIMARY KEY(CUSTOMER_ID,COUPON_ID),\n" +
            "FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMERS(ID) ON DELETE CASCADE,\n" +
            "FOREIGN KEY (COUPON_ID) REFERENCES COUPONS(ID) ON DELETE CASCADE);\n";

    public static void createDataBase() {
        try {
            DButils.runQuery(CREATE_DB);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public static void createTablesCompanies() {
        try {
            DButils.runQuery(CREATE_TABLE_COMPANIES);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public static void createTablesCustomers() {
        try {
            DButils.runQuery(CREATE_TABLE_CUSTOMERS);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public static void createTablesCategory() {
        try {
            DButils.runQuery(CREATE_TABLE_Categories);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }


    public static void createTablesCoupons() {
        try {
            DButils.runQuery(CREATE_TABLE_COUPONS);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public static void createTablesCustomersVsCoupons() {
        try {
            DButils.runQuery(CREATE_TABLE_CUSTOMER_VS_COUPONS);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public static void dropDB() {
        try {
            DButils.runQuery(DROP_DB);
        } catch (SQLException sqlException) {
            System.out.println("No Database detected, Creating new one now\n---------");
        }
    }
}


