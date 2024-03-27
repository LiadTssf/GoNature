package data;

import java.io.Serializable;

public class Park implements Serializable {
    public int      park_id_pk;
    public String   park_name;
    public int      capacity;
    public int      current_visitors;
    public int      average_visit_time;
    public int      capacity_offset;

    // Getters
    public int getPark_id_pk() {
        return park_id_pk;
    }

    public String getPark_name() {
        return park_name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrent_visitors() {
        return current_visitors;
    }

    public int getAverage_visit_time() {
        return average_visit_time;
    }

    public int getCapacity_offset() {
        return capacity_offset;
    }

    // Setters
    public void setPark_id_pk(int park_id_pk) {
        this.park_id_pk = park_id_pk;
    }

    public void setPark_name(String park_name) {
        this.park_name = park_name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCurrent_visitors(int current_visitors) {
        this.current_visitors = current_visitors;
    }

    public void setAverage_visit_time(int average_visit_time) {
        this.average_visit_time = average_visit_time;
    }

    public void setCapacity_offset(int capacity_offset) {
        this.capacity_offset = capacity_offset;
    }
}
