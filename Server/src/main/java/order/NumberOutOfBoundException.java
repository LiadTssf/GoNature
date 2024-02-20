package order;

public class NumberOutOfBoundException extends Exception{
    public NumberOutOfBoundException(){
        super("Number is out of bound,Please check visitors number");
    }
}
