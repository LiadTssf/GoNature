package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReportRequest implements Serializable {

    public String areaOfReport;
    public ArrayList<LocalDate> dates = new ArrayList<>();

    public boolean cancelled;
    public boolean allOrders;

}
