//package gui;
//
//import database.DatabaseController;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class ThreadForWaitingList implements Runnable {
//
//
//    private String parkName;
//    private int parkId;
//
//    private int currentParkVisitors;
//    private DatabaseController DB;
//
//    public ThreadForWaitingList(String parkName) {
//        this.parkName = parkName;
//        DB = new DatabaseController();
//    }
//
//    @Override
//    public void run() {
//        String queryToRun = "";
//        try {
//            PreparedStatement pstmt = DB.getConnection().prepareStatement(queryToRun);
//            pstmt.setBoolean(1, true);
//            pstmt.setString(2, parkName); // Assuming parkName is a String representing the park_id_fk
//            ResultSet result = pstmt.executeQuery();
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private int getCurrentParkVisitors() throws SQLException {
//        String selectQuery = "SELECT current_visitors FROM park WHERE park_name = ?";
//        PreparedStatement pstmt = DB.getConnection().prepareStatement(selectQuery);
//        pstmt.setString(1, parkName);
//        try (ResultSet resultSet = pstmt.executeQuery()) {
//            if (resultSet.next()) {
//                currentParkVisitors  = resultSet.getInt("current_visitors");
//            } else {
//                // Handle the case where no park with the specified name was found
//                throw new SQLException("cant run query");
//            }
//        }
//    }
//
//    private void getParkId(String parkName) throws SQLException {
//        String selectQuery = "SELECT park_id_pk FROM park WHERE park_name = ?";
//        PreparedStatement pstmt = DB.getConnection().prepareStatement(selectQuery);
//        pstmt.setString(1, parkName);
//
//        try (ResultSet resultSet = pstmt.executeQuery()) {
//            if (resultSet.next()) {
//                parkId =  resultSet.getInt("park_id_pk");
//            } else {
//                // Handle the case where no park with the specified name was found
//                throw new SQLException("No park found with the name: " + parkName);
//            }
//        }
//    }
//
//
//
//}
