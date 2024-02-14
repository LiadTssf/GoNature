package order;

import DB.CRUD;

public class GroupOrder extends Order{

    public GroupOrder (int parkID,String email,int amountOfVisitors){
        super(parkID,email,amountOfVisitors);
    }


    @Override
    public double calculatePrice() { // need to be implmented
        return 0 ;
    }


}
