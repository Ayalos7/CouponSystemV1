package infoDB;

import Beans.Company;

/**
 * class that contains mock data on companies
 */
public class CompaniesGenerator {

    /**
     * returns company named electricCompany
     * @return company named electricCompany
     */
    public static Company electricCompany() {
        return new Company(1, "ElectricCompany", "elec@gmail", "elecPass", CouponGenerator.getElectricCoupons());
    }

    /**
     * returns company named foodCompany
     * @return company named foodCompany
     */
    public static Company foodCompany() {
        return new Company(2, "FoodCompany", "food@gmail", "foodPass", CouponGenerator.getFoodCoupons());
    }

    /**
     * returns company named vacationCompany
     * @return company named vacationCompany
     */
    public static Company vacationCompany() {
        return new Company(3, "VacationCompany", "vacation@gmail", "vacationPass", CouponGenerator.getVacationCoupons());
    }

    /**
     * returns company named companyForDeletion
     * @return company named companyForDeletion
     */
    public static Company companyForDeletion() {
        return new Company(4, "deletionCompany", "deletion@gmail", "deletionPass", CouponGenerator.getDeletionCoupons());
    }
}
