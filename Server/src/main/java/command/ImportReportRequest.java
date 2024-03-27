package command;

import data.Account;
import data.ReportRequest;
import database.DatabaseController;

import java.io.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ImportReportRequest implements ServerCommand{
    private File reportFile;
    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof ReportRequest)) {
            return new Message("paramFailed", "parameter is not ReportRequest type");
        }

        ReportRequest reportRequest = (ReportRequest) param;

        DatabaseController DB = new DatabaseController();
        String queryToRun;
        String fileName;
        String queryToSort = "";
        String fileQuery = "INSERT INTO files (file_name,file_data) VALUES (?,?)";

        String queryToSearchParkID = "SELECT park_id_pk FROM park WHERE park_name = ?";
        int park_id = 0;
        if (reportRequest.capacityReport) {
            fileName = "capacity_Report_" + reportRequest.areaOfReport + ".txt";
            reportFile = new File(fileName);
            queryToRun = "SELECT";
        } else {
            fileName = "by_group_report_" + reportRequest.areaOfReport + ".txt";
            reportFile = new File(fileName);
            queryToSort = "SELECT guided_order FROM `order` ORDER BY guided_order ASC";
            queryToRun = "SELECT * FROM `order` WHERE park_id_fk = ? AND visit_date BETWEEN ? AND ?";
        }
            try {
                Writer writer = new FileWriter(reportFile);
                PreparedStatement pstmt = DB.getConnection().prepareStatement(queryToSort);
                pstmt.execute();

                pstmt = DB.getConnection().prepareStatement(queryToSearchParkID);
                pstmt.setString(1, reportRequest.areaOfReport);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    park_id = rs.getInt("park_id_pk");
                }

                pstmt = DB.getConnection().prepareStatement(queryToRun);
                pstmt.setInt(1, park_id);
                pstmt.setDate(2, Date.valueOf(reportRequest.dates.get(0)));
                pstmt.setDate(3, Date.valueOf(reportRequest.dates.get(1)));

                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("order_id_pk");
                    String guidedOrder;
                    int accountID = rs.getInt("account_id");
                    LocalDate visitDate = rs.getDate("visit_date").toLocalDate();
                    LocalTime visitTime = rs.getTime("visit_time").toLocalTime();
                    int groupSize = rs.getInt("number_of_visitors");
                    if (rs.getBoolean("guided_order")) {
                        guidedOrder = "Guided";
                    } else if (groupSize <= 5 && groupSize > 1) {
                        guidedOrder = "Small Group";
                    } else {
                        guidedOrder = "Individual";
                    }

                    String printString = String.format("OrderID: %s, Customer ID: %d, Park: %s, Order Date: %s, Order Time: %s, Visitors Number: %d, Group: %s", orderID, accountID, reportRequest.areaOfReport, visitDate.toString(), visitTime.toString(), groupSize, guidedOrder);
                    writer.write(printString);
                    writer.write(System.lineSeparator());
                }

                FileInputStream fis = new FileInputStream(reportFile);
                byte[] fileData = new byte[(int) reportFile.length()];
                fis.read(fileData);

                pstmt = DB.getConnection().prepareStatement(fileQuery);
                pstmt.setString(1, fileName);
                pstmt.setBytes(2, fileData);

                pstmt.executeUpdate();
                return new Message("FileSucceed");


            } catch (SQLException | IOException e) {
                e.printStackTrace();
                return new Message("Error", "File OR SQL failed");
            }
        }

}
