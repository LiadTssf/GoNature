package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Order implements Serializable {
    public UUID order_id_pk;
    public int account_id;
    public String park_id_fk;
    public LocalTime visit_time;
    public LocalTime exit_time;
    public LocalDate visit_date;
    public int number_of_visitors;
    public String email;
    public String phone;
    public Boolean guided_order;
    public Boolean on_arrival_order;
    public Boolean on_waiting_list;
    public Boolean cancelled;
    public Boolean paid;



    // Getters
    public UUID getOrderIdPk() {
        return order_id_pk;
    }

    public int getAccountId() {
        return account_id;
    }

    public String getParkIdFk() {
        return park_id_fk;
    }

    public LocalTime getVisitTime() {
        return visit_time;
    }

    public LocalTime getExitTime() {
        return exit_time;
    }

    public LocalDate getVisitDate() {
        return visit_date;
    }

    public int getNumberOfVisitors() {
        return number_of_visitors;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean getGuidedOrder() {
        return guided_order;
    }

    public Boolean getOnArrivalOrder() {
        return on_arrival_order;
    }

    public Boolean getOnWaitingList() {
        return on_waiting_list;
    }

    public Boolean getCancelled() {
        return cancelled;
    }
    public Boolean getPaid() {
        return paid;
    }
    public void setNumVisitors(int numVisitors) {
        this.number_of_visitors = numVisitors;
    }

    public void setDateToVisit(LocalDate dateToVisit) {
        this.visit_date = dateToVisit;
    }

    public void setVisitTime(LocalTime visitTime) {
        this.visit_time = visitTime;
    }
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
    public void setPaid(boolean c) {
        this.paid = c;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id_pk=" + order_id_pk +
                ", account_id=" + account_id +
                ", park_id_fk=" + park_id_fk +
                ", visit_date=" + visit_date +
                ", visit_time=" + visit_time +
                ", exit_time=" + exit_time +
                ", number_of_visitors=" + number_of_visitors +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", guided_order=" + guided_order +
                ", on_arrival_order=" + on_arrival_order +
                ", on_waiting_list=" + on_waiting_list +
                ", cancelled=" + cancelled +
                '}';
    }

    public double getFinalPrice() {

        double costCalc;
        if (guided_order){
            costCalc = (number_of_visitors * 80) * 0.75;
            if (!on_arrival_order) {
                return costCalc*0.88;
            }
            return costCalc;
        }
        if (!on_arrival_order){
            return (number_of_visitors*80)*0.85;
        }
        return number_of_visitors*80;
    }

}
