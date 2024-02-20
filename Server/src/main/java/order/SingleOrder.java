package order;

import account.DB.CRUD;

public class SingleOrder extends Order{

    boolean OrderExist = false; // need to check if the order exists in DB
    private double price;
    public SingleOrder (int parkID,String email,int amountOfVisitors) throws NumberOutOfBoundException {
        super(parkID,email,amountOfVisitors);
        this.price = calculatePrice(0.85);
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
