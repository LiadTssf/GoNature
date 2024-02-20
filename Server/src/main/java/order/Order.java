package order;
import java.time.LocalTime;

public abstract class Order {

    private int parkID;
    private LocalTime enterTime;
    private LocalTime  exitTime;  // need to add this method too
    private String email;
    private int amountOfVisitors;
    private double price;


    public Order(int parkID,String email,int amountOfVisitors) throws NumberOutOfBoundException {
        this.parkID = parkID;
        this.email = email;
        this.amountOfVisitors= amountOfVisitors;
        this.enterTime = LocalTime.now();
    }

    public int getParkID() {
        return parkID;
    }

    public LocalTime getEnterTime() {
        return enterTime;
    }

    public LocalTime getExitTime() {
        return exitTime;
    }

    public String getEmail() {
        return email;
    }

    public int getAmountOfVisitors() {
        return amountOfVisitors;
    }

    public double calculatePrice(double discount_percentage) throws NumberOutOfBoundException{
        this.price=(amountOfVisitors*80)*discount_percentage;
        return this.price;
    } // get visitors amount to calculate the correct price

}
