package data;

import java.io.Serializable;

public class Account implements Serializable {
    public int account_id_pk;

    public String toString() { return String.valueOf(account_id_pk); }
}
