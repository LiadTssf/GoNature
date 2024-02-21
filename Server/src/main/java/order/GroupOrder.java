package order;

import account.DB.CRUD;

import java.sql.SQLException;
import java.time.LocalTime;

public class GroupOrder extends Order{

    boolean prePaid = false; // need to get from DB
    final int MAX_GROUP_CAPACITY = 15;
    private double price;
    public GroupOrder (int parkID, String email, int amountOfVisitors, String phoneNumber, LocalTime time) throws NumberOutOfBoundException, SQLException {

        super(parkID,email,amountOfVisitors,phoneNumber,time);
        super.setPrice(calculatePrice(0.75));
    }


    @Override
    public double calculatePrice(double discount_percentage) throws NumberOutOfBoundException {
        if (this.getAmountOfVisitors()<=MAX_GROUP_CAPACITY && this.getAmountOfVisitors()>=2){
            if (prePaid){
                return (this.calculatePrice(discount_percentage))*0.85;
            }
            return calculatePrice(discount_percentage);
        }else{
           throw new NumberOutOfBoundException();
        }
    }
}
