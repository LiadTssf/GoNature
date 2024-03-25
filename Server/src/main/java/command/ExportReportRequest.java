package command;

import data.Account;
import data.Order;
import data.ReportRequest;
import database.DatabaseController;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class ExportReportRequest implements ServerCommand{
    /**
     * receives a report PDF file from MySQL DB by searching the orders on the order table at MySQL DB
     * the report format is different between Cancellation and Visit.
     * @param param   LocalDate object localReportDate
     * @param account client authorized account
     * @return message to client authenticating with new order created or denying the creation
     */
    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof ReportRequest)){
            return new Message("ExportFailed","The param is not a LocalDate");
        }

        ArrayList<Order> orderArrayList = new ArrayList<>();

        ReportRequest exportReport = (ReportRequest) param;
        LocalDate from_date = exportReport.dates.get(0);
        String area = exportReport.areaOfReport;
        LocalDate to_date = null;
        int parkID = 0;
        if (exportReport.dates.size() == 2){
           to_date = exportReport.dates.get(1);

           if (from_date.isAfter(to_date)){
               return new Message("ErrorInDates","The dates are not logical");
           }
        }


        DatabaseController DB = new DatabaseController();

        String parkIdQuery = ("SELECT park_id_pk FROM park WHERE park_name = ?");

        String queryVisit = ("SELECT * FROM `order` WHERE park_id_fk = ? AND visit_date BETWEEN ? AND ?");
        String queryCancelled = ("SELECT * FROM `order` WHERE park_id_fk = ? AND visit_date BETWEEN ? AND ? AND cancelled = ?");

        try{
            PreparedStatement pstmt = DB.getConnection().prepareStatement(parkIdQuery);

            pstmt.setString(1,area);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                parkID = rs.getInt("park_id_pk");
            }

            if (!exportReport.cancelled) {
                pstmt = DB.getConnection().prepareStatement(queryVisit);

            }else {
                pstmt = DB.getConnection().prepareStatement(queryCancelled);
                pstmt.setBoolean(4,true);
            }

            pstmt.setInt(1, parkID);
            pstmt.setDate(2, Date.valueOf(from_date));
            if (to_date != null) {
                pstmt.setDate(3, Date.valueOf(to_date));
            } else {
                pstmt.setDate(3, Date.valueOf(LocalDate.now()));
            }
            rs = pstmt.executeQuery();

            while (rs.next()){
                Order exportOrder = new Order();
                exportOrder.account_id = rs.getInt("account_id");
                exportOrder.order_id_pk = UUID.fromString(rs.getString("order_id_pk"));
                exportOrder.park_id_fk = area;
                exportOrder.visit_date = rs.getDate("visit_date").toLocalDate();
                exportOrder.visit_time = rs.getTime("visit_time").toLocalTime();
                exportOrder.exit_time = rs.getTime("exit_time").toLocalTime();
                exportOrder.number_of_visitors = rs.getInt("number_of_visitors");
                exportOrder.email = rs.getString("email");
                exportOrder.phone = rs.getString("phone");
                exportOrder.guided_order = rs.getBoolean("guided_order");
                exportOrder.on_arrival_order = rs.getBoolean("on_arrival_order");
                exportOrder.on_waiting_list = rs.getBoolean("on_waiting_list");
                exportOrder.cancelled = rs.getBoolean("cancelled");

                orderArrayList.add(exportOrder);
            }

            if (!exportReport.cancelled) {
                return new Message("VisitExportReport", orderArrayList);
            }
            return new Message("CancellationReport",orderArrayList);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("OrderExportFailed");
        }
    }
}
