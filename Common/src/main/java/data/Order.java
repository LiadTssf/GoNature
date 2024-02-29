package data;

import java.io.Serializable;
import java.sql.Timestamp;

public class Order implements Serializable {
    public int          order_id_pk;
    public int          account_id;
    public int          park_id_fk;
    public Timestamp    visit_time;
    public Timestamp    exit_time;
    public int          number_of_visitors;
    public String       email;
    public String       phone;
    public Boolean      guided_order;
    public Boolean      on_arrival_order;
    public Boolean      on_waiting_list;
    public Boolean      cancelled;
}
