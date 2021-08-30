package Beans;

import java.util.ArrayList;
import java.util.List;

/**
 * class of customer
 */
public class Customer {

    private int id;
    private String firstName, lastName, email, password;
    private List<Coupon> coupons = new ArrayList<>();

    /**
     * full constructor
     *
     * @param id        int
     * @param firstName string
     * @param lastName  string
     * @param email     string
     * @param password  string
     */
    public Customer(int id, String firstName, String lastName, String email, String password) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);
        setCoupons(coupons);
    }

    /**
     * empty constructor
     */
    public Customer() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * @return basic toString with parameters
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
