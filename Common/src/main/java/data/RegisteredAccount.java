package data;

public class RegisteredAccount extends Account {
    public String   username;
    public String   password;
    public String   email;
    public String   phone;

    public RegisteredAccount(int account_id_pk, String account_type) {
        super(account_id_pk, account_type);
    }
}
