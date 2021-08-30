package Exceptions;

/**
 * custom exception that checks if customer email already exists in db
 */
public class CustomerSameEmail extends Exception{
    /**
     * content of the message
     */
    public CustomerSameEmail(){
        super("Exception: Customer email is already taken\n----------");
    }
}
