package order;
import java.sql.*;
import java.time.LocalTime;

import java.util.UUID;
import account.DB.CRUD;
import account.DB.SqlConnection;

public abstract class Order {

    private String parkID;
    private LocalTime enterTime;
    private LocalTime  exitTime;  // need to add this method too
    private String email;
    private int amountOfVisitors;
    private double price;
    private String PhoneNumber;
    private UUID OrderNumber;
    private static CRUD crud;

    static {
        try {
            crud = new CRUD();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Order(String parkID, String email, int amountOfVisitors, String PhoneNumber,LocalTime time) throws NumberOutOfBoundException, SQLException {
        this.parkID = parkID;
        this.email = email;
        this.amountOfVisitors= amountOfVisitors;
        this.PhoneNumber = PhoneNumber;
        OrderNumber = UUID.randomUUID();
        this.enterTime = time;
    }

    public String getParkID() {
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

    public UUID getOrderNumber(){
        return OrderNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmountOfVisitors() {
        return amountOfVisitors;
    }

    public double calculatePrice(double discount_percentage) throws NumberOutOfBoundException{
        this.price=(amountOfVisitors*80)*discount_percentage;
        return this.price;
    } // get visitors amount to calculate the correct price

    public static void LoadToDB(UUID OrderNumber, String parkID, LocalTime enterTime, int amountOfVisitors, String phoneNumber,String type) throws SQLException {
        String queryToLoad = "INSERT INTO `order` (OrderNumber, ParkName, TimeOfVisit, NumberOfVisitors, TelephoneNumber,OrderType) VALUES ('" +
                OrderNumber + "', '" + parkID + "', '" + enterTime + "', " + amountOfVisitors + ", '" + phoneNumber + "','"+type+"')";
        crud.insertData(queryToLoad);
    }
}
