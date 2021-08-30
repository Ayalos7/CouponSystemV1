package Beans;

import java.util.ArrayList;
import java.util.List;

/**
 * class of company
 */
public class Company {
    private int id;
    private String name, email, password;
    private List<Coupon> coupons = new ArrayList<>();

    /**
     * full constructor
     *
     * @param id       int
     * @param name     string
     * @param email    string
     * @param password string
     * @param coupons  list of coupons
     */
    public Company(int id, String name, String email, String password, List<Coupon> coupons) {
        this.id = id;
        setName(name);
        setEmail(email);
        setPassword(password);
        setCoupons(coupons);
    }

    /**
     * another constructor without list of coupons
     *
     * @param id       int
     * @param name     string
     * @param email    string
     * @param password string
     */
    public Company(int id, String name, String email, String password) {
        this.id = id;
        setName(name);
        setEmail(email);
        setPassword(password);
        setCoupons(coupons);
    }

    /**
     * another constructor without id, list of coupons
     *
     * @param name     string
     * @param email    string
     * @param password string
     */
    public Company(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);

    }

    /**
     * empty constructor
     */
    public Company() {

    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    /**
     * to string method
     *
     * @return toString of the company
     */
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
