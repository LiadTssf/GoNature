package order;
import java.time.LocalTime;

public abstract class Order {

    private int parkID;
    private LocalTime enterTime;
    private LocalTime  exitTime;  // need to add this method too
    private String email;
    private int amountOfVisitors;
    private double price;


    public Order(int parkID,String email,int amountOfVisitors){
        this.parkID = parkID;
        this.email = email;
        this.amountOfVisitors= amountOfVisitors;
        this.enterTime = LocalTime.now();
        this.price = calculatePrice();
    }


    public abstract double calculatePrice(); // implemented in the sons
}
