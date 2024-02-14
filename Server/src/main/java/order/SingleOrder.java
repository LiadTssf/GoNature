package order;

public class SingleOrder extends Order{


    public SingleOrder (int parkID,String email,int amountOfVisitors){
        super(parkID,email,amountOfVisitors);
    }


    @Override
    public double calculatePrice() { // need to be implmented

        return 0 ;
    }
}
