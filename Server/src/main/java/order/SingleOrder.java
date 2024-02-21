package order;

import account.DB.CRUD;

import java.sql.SQLException;
import java.time.LocalTime;

public class SingleOrder extends Order{

    private double price;
    public SingleOrder (String parkID, String email, int amountOfVisitors, String phoneNumber, LocalTime time) throws NumberOutOfBoundException, SQLException {
        super(parkID,email,amountOfVisitors,phoneNumber,time);
        super.setPrice(calculatePrice(0.85));
    }


    public void sendToSQL(){
    }

    @Override
    public double calculatePrice(double discount_percentage) throws NumberOutOfBoundException {
        if (this.getAmountOfVisitors() >= 1 && this.getAmountOfVisitors() <= 5){
            return super.calculatePrice(discount_percentage);
        }else{
            throw new NumberOutOfBoundException();
        }
    }

    public static void main(String[] args) throws SQLException, NumberOutOfBoundException {
        SingleOrder so = new SingleOrder("Karmiel","ASDA@gmail.com",2,"0504567899",LocalTime.of(16,00));
        LoadToDB(so.getOrderNumber(),so.getParkID(),so.getEnterTime(),so.getAmountOfVisitors(), so.getPhoneNumber());
    }
}
