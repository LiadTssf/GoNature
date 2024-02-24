package logic;

public class Order {
    private String parkName;
    private String orderNumber;
    private String timeOfVisit;
    private String numberOfVisitors;
    private String phoneNumber;
    private String email;

    // Constructor
    public Order(String parkName, String orderNumber, String timeOfVisit, String numberOfVisitors, String phoneNumber, String email) {
        this.parkName = parkName;
        this.orderNumber = orderNumber;
        this.timeOfVisit = timeOfVisit;
        this.numberOfVisitors = numberOfVisitors;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and setters
    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTimeOfVisit() {
        return timeOfVisit;
    }

    public void setTimeOfVisit(String timeOfVisit) {
        this.timeOfVisit = timeOfVisit;
    }

    public String getNumberOfVisitors() {
        return numberOfVisitors;
    }

    public void setNumberOfVisitors(String numberOfVisitors) {
        this.numberOfVisitors = numberOfVisitors;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
// toString method to display order details
    @Override
    public String toString() {
        return "Order{" +
                "Park Name: " + parkName +
                ", Order Number: " + orderNumber +
                ", Time of Visit: " + timeOfVisit +
                ", Number of Visitors: " + numberOfVisitors +
                ", Phone Number: " + phoneNumber +
                ", Email: " + email +
                '}';
    }
}
