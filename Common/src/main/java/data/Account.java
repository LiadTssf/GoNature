package data;

import java.io.Serializable;

public class Account implements Serializable {
    public int account_id_pk;
    public String account_type;                       // { Unregistered, Registered, Guide, Worker }

    public Account(int account_id_pk, String account_type) {
        this.account_id_pk = account_id_pk;
        this.account_type = account_type;
    }

    public String toString() {
        return String.valueOf(account_id_pk);
    }
}
