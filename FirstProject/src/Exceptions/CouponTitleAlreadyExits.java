package Exceptions;

/**
 * custom exception that checks if coupon title already exists in db
 */
public class CouponTitleAlreadyExits extends Exception{
    /**
     * content of the message
     */
    public CouponTitleAlreadyExits() {
        super("Exception: Coupon Title already exists in this company\n----------");
    }
}
