package infoDB;

import Beans.Category;
import Beans.Coupon;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * class that contains mock data on coupons
 */
public class CouponGenerator {

    /**
     * returns list of coupons that will be used for electricity company
     * @return list of coupons that will be used for electricity company
     */
    public static List<Coupon> getElectricCoupons() {
        Coupon ElectricalCoupon1 = new Coupon(1, 1, Category.Electricity, "FridgeDiscount", "20% off", Date.valueOf("2021-06-25"), Date.valueOf("2021-06-26"), 11, 50, "fridge.jpeg");
        Coupon ElectricalCoupon2 = new Coupon(2, 1, Category.Electricity, "MicrowaveDiscount", "30% off", Date.valueOf("2021-06-26"), Date.valueOf("2021-07-12"), 22, 60, "micro.jpeg");
        Coupon ElectricalCoupon3 = new Coupon(3, 1, Category.Electricity, "LightDiscount", "40% off", Date.valueOf("2021-06-27"), Date.valueOf("2021-07-13"), 33, 70, "light.jpeg");
        Coupon ElectricalCoupon4 = new Coupon(4, 1, Category.Electricity, "DishwasherDiscount", "45% off", Date.valueOf("2021-06-28"), Date.valueOf("2021-07-15"), 44, 80, "dishWasher.jpeg");
        List<Coupon> electricList = new ArrayList<>();
        electricList.add(ElectricalCoupon1);
        electricList.add(ElectricalCoupon2);
        electricList.add(ElectricalCoupon3);
        electricList.add(ElectricalCoupon4);
        return electricList;
    }

    /**
     * returns list of coupons that will be used for food company
     * @return list of coupons that will be used for food company
     */
    public static List<Coupon> getFoodCoupons() {
        Coupon foodCoupon1 = new Coupon(5, 2, Category.Food, "Free meal", "100% off", Date.valueOf("2021-06-29"), Date.valueOf("2021-07-10"), 55, 51, "meal.jpeg");
        Coupon foodCoupon2 = new Coupon(6, 2, Category.Food, "LargeDiscount", "50% off", Date.valueOf("2021-06-24"), Date.valueOf("2021-07-14"), 66, 61, "hamburger.jpeg");
        Coupon foodCoupon3 = new Coupon(7, 2, Category.Food, "Free side dish", "chips", Date.valueOf("2021-06-22"), Date.valueOf("2021-07-14"), 77, 71, "chips.jpeg");
        Coupon foodCoupon4 = new Coupon(8, 2, Category.Food, "Small discount", "20% off", Date.valueOf("2021-06-28"), Date.valueOf("2021-07-15"), 88, 81, "noPhoto.jpeg");
        List<Coupon> foodList = new ArrayList<>();
        foodList.add(foodCoupon1);
        foodList.add(foodCoupon2);
        foodList.add(foodCoupon3);
        foodList.add(foodCoupon4);
        return foodList;
    }

    /**
     * returns list of coupons that will be used for vacation company
     * @return list of coupons that will be used for vacation company
     */
    public static List<Coupon> getVacationCoupons() {
        Coupon vacationCoupon1 = new Coupon(9, 3, Category.Vacation, "Free vacation", "free", Date.valueOf("2021-06-20"), Date.valueOf("2021-07-18"), 99, 52, "vacation.jpeg");
        Coupon vacationCoupon2 = new Coupon(10, 3, Category.Vacation, "free night", "1 night free", Date.valueOf("2021-06-22"), Date.valueOf("2021-07-18"), 0, 62, "hotel.jpeg");
        Coupon vacationCoupon3 = new Coupon(11, 3, Category.Vacation, "free pool access", "free pool usage", Date.valueOf("2021-06-24"), Date.valueOf("2021-07-02"), 111, 72, "pool.jpeg");
        List<Coupon> vacationList = new ArrayList<>();
        vacationList.add(vacationCoupon1);
        vacationList.add(vacationCoupon2);
        vacationList.add(vacationCoupon3);
        return vacationList;
    }

    /**
     * returns list of coupons that will be used for deletion company
     * @return list of coupons that will be used for deletion company
     */
    public static List<Coupon> getDeletionCoupons() {

        Coupon deletionCoupon = new Coupon(12, 3, Category.Vacation, "free pool access", "free pool usage", Date.valueOf("2021-06-24"), Date.valueOf("2021-07-02"), 111, 72, "deletion.jpeg");
        List<Coupon> deletionList = new ArrayList<>();

        deletionList.add(deletionCoupon);
        return deletionList;
    }

    public static Coupon getPreparedCoupon() {
        return new Coupon(0, 3, Category.Food, "Added coupon", "added coupon", Date.valueOf("2021-06-23"), Date.valueOf("2021-07-18"), 99, 52, "addedCoupon.jpeg");
    }
}
