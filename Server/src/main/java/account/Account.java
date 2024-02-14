package account;

public abstract class Account {

    private int accountID;
    private String userName;
    private String password;
    private String email;
    private int phoneNumber;
    private String firstName;
    private String lastName;


    public Account(String userName, String password, String email, int phoneNumber,String firstName, String lastName){
        accountID = 1 ;  // generate new account id
        this.userName = userName;
        this.password = password;
        this.email= email;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;

    }


    public void createSingleOrder(){}

    public void insertToWaitingList(){}

    public void cancelOrder(){}

}
