package gui;

import database.DatabaseController;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ThreadToCancel implements Runnable{

    /*Thread method that checks every row and IF the exit time and visit date is the same as real values
    * set this order as cancelled
    * */
    @Override
    public void run() {
        try {
            DatabaseController DB = new DatabaseController();
            String queryToCancel = "UPDATE `order` SET cancelled = ? WHERE exit_time = ? AND visit_date = ? AND  paid = ?";
            try {
                PreparedStatement pstmt = DB.getConnection().prepareStatement(queryToCancel);
                pstmt.setBoolean(1, true);
                pstmt.setTime(2, Time.valueOf(LocalTime.of(LocalTime.now().getHour(), 0, 0)));
                pstmt.setDate(3, Date.valueOf(LocalDate.now()));
                pstmt.setBoolean(4,false);
                int rowsUpdated = pstmt.executeUpdate();
                System.out.println(rowsUpdated + " rows updated");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("cancel orders every hour");

    }
}
