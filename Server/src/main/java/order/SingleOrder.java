package order;

import account.DB.CRUD;

import java.sql.SQLException;
import java.time.LocalTime;

public class SingleOrder extends Order{

    boolean OrderExist = false; // need to check if the order exists in DB
    private double price;
    public SingleOrder (int parkID, String email, int amountOfVisitors, String phoneNumber, LocalTime time) throws NumberOutOfBoundException, SQLException {
        super(parkID,email,amountOfVisitors,phoneNumber,time);
        this.price = calculatePrice(0.85);
    }


    public void sendToSQL(){
    }

    @Override
    public double calculatePrice(double discount_percentage) throws NumberOutOfBoundException {
        if (OrderExist){ //if the
            if (this.getAmountOfVisitors() >= 1 && this.getAmountOfVisitors() <= 5){
                 return calculatePrice(discount_percentage);
            }else{
                throw new NumberOutOfBoundException();
            }
        }
        return calculatePrice(1);
    }
}
