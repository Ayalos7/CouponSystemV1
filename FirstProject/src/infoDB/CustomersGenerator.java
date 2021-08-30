package infoDB;

import Beans.Customer;

/**
 * class that contains mock data on customers
 */
public class CustomersGenerator {

    /**
     *
     * @return prepared customer
     */
    public static Customer customer1() {
        return new Customer(1, "Danny", "Dan", "Dan@gmail", "danPass");
    }

    /**
     *
     * @return prepared customer
     */
    public static Customer customer2() {
        return new Customer(2, "Yaniv", "Yan", "Yaniv@gmail", "yanivPass");
    }

    /**
     *
     * @return prepared customer
     */
    public static Customer customer3() {
        return new Customer(3, "Lana", "Lan", "Lana@gmail", "lanaPass");
    }

    /**
     *
     * @return prepared customer
     */
    public static Customer customer4() {
        return new Customer(4, "Yasmin", "Yas", "Yasmin@gmail", "yasminPass");
    }
}
