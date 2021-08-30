package Beans;

/**
 * class of coupon
 */

public class Coupon {
    private int id, companyID, amount;
    private Category category;
    private String title, description, image;
    private java.sql.Date startDate, endDate;
    private double price;

    /**
     * full constructor
     * @param id int
     * @param companyID int
     * @param category category
     * @param title string
     * @param description string
     * @param startDate string
     * @param endDate string
     * @param amount string
     * @param price string
     * @param image string
     */
    public Coupon(int id, int companyID,Category category, String title, String description, java.sql.Date startDate, java.sql.Date endDate,int amount, double price,String image) {
        this.id=id;
        setCompanyID(companyID);
        setAmount(amount);
        setCategory(category);
        setTitle(title);
        setDescription(description);
        setImage(image);
        setStartDate(startDate);
        setEndDate(endDate);
        setPrice(price);
    }

    /**
     * empty constructor
     */
    public Coupon() {

    }

    public int getId() {
        return id;
    }



    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public java.sql.Date getStartDate() {
        return startDate;
    }

    public void setStartDate(java.sql.Date startDate) {
        this.startDate = startDate;
    }

    public java.sql.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.sql.Date endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * to string method
     * @return toString with all parameters
     */
    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyID=" + companyID +
                ", amount=" + amount +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                '}';
    }
}
