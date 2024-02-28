package order;

import java.io.Serializable;

public class Order implements Serializable {
    public String order_id_pk;
    public String park_name;
    public String visit_time;
    public String number_of_visitors;
    public String telephone;
    public String email;

    /**
     * Constructs a new empty order, details need to be filled by accessing public members
     */
    public Order() { }

    /**
     * Constructs a new order and fills in details
     * @param order_id_pk order number or id
     * @param park_name park name for the order
     * @param visit_time time of visit
     * @param number_of_visitors number of visitors
     * @param telephone phone number for whom ordered
     * @param email email for whom ordered
     */
    public Order(String order_id_pk, String park_name, String visit_time, String number_of_visitors, String telephone, String email) {
        this.order_id_pk = order_id_pk;
        this.park_name = park_name;
        this.visit_time = visit_time;
        this.number_of_visitors = number_of_visitors;
        this.telephone = telephone;
        this.email = email;
    }

    public String toString() {
        return "Order{ " + order_id_pk + ", " + park_name + ", " + visit_time + ", " + number_of_visitors + ", " + telephone + ", " + email + " }";
    }
}
