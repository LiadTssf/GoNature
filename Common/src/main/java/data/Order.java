package data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Order implements Serializable {
    public UUID order_id_pk;
    public int          account_id;
    public String          park_id_fk;
    public LocalTime visit_time;
    public LocalTime    exit_time;
    public  LocalDate visit_date;
    public int          number_of_visitors;
    public String       email;
    public String       phone;
    public Boolean      guided_order;
    public Boolean      on_arrival_order;
    public Boolean      on_waiting_list;
    public Boolean      cancelled;
}
