package gui;

import database.DatabaseController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ThreadForWaitingList implements Runnable{

    @Override
    public void run() {

        // add enter_waiting_list_time to DB  to know which order is the first
        DatabaseController DB = new DatabaseController();
        String queryToRun = "SELECT *\n" +
                "FROM `order`\n" +
                "WHERE on_waiting_list = ?\n" +
                "ORDER BY enter_waiting_list_time ASC\n" +
                "LIMIT 1;";

        try {
            PreparedStatement pstmt = DB.getConnection().prepareStatement(queryToRun);
            pstmt.setBoolean(1,true);
            ResultSet result = pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
