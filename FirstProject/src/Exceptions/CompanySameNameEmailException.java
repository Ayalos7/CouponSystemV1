package Exceptions;

/**
 * custom exception that checks if company name or email repeats in db
 */
public class CompanySameNameEmailException extends Exception{
    /**
     * content of the message
     */
    public CompanySameNameEmailException(){
        super("Exception: Company Name/Email is already taken\n----------");
    }
}
