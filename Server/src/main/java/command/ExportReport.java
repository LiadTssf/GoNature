package command;

import data.Account;
import database.DatabaseController;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ExportReport implements ServerCommand{
    /**
     * receives a report PDF file from MySQL DB by searching the orders on the order table at MySQL DB
     * the report format is different between Cancellation and Visit.
     * @param param   LocalDate object localReportDate
     * @param account client authorized account
     * @return message to client authenticating with new order created or denying the creation
     */
    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof ArrayList)){
            return new Message("ExportFailed","The param is not a LocalDate");
        }

        ArrayList<LocalDate> exportReport = (ArrayList<LocalDate>) param;
        LocalDate from_date = exportReport.get(0);
        LocalDate to_date = null;
        if (exportReport.size() == 2){
           to_date = exportReport.get(1);
        }

        DatabaseController DB = new DatabaseController();

        String query = ("SELECT * FROM order WHERE order_date,cancelled BETWEEN ? AND ?");

        try{
            PreparedStatement pstmt = DB.getConnection().prepareStatement(query);
            pstmt.setDate(1, Date.valueOf(from_date));
            if (to_date != null){
                pstmt.setDate(2, Date.valueOf(to_date));
            }else{
                pstmt.setDate(2,Date.valueOf(LocalDate.now()));
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
