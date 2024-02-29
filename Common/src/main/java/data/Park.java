package data;

import java.io.Serializable;

public class Park implements Serializable {
    public int      park_id_pk;
    public String   park_name;
    public int      capacity;
    public int      current_visitors;
    public int      average_visit_time;
    public int      capacity_offset;
}
