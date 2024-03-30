package command;

import data.Account;
import data.Order;
import command.Message;
import database.DatabaseController;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class CheckParkCapacity implements ServerCommand {
    /**
     * Checks if the park is not full for a specific order
     *
     * @param param   Order object representing the order details
     * @param account unused parameter
     * @return message indicating whether the park is not full or an error occurred
     */
    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof Order)) {
            return new Message("Error", "Invalid parameter type");
        }

        Order order = (Order) param;
        DatabaseController dbController = new DatabaseController();
        int parkID = -1; // Initialize park ID to a default value
        String queryGetID = "SELECT park_id_pk FROM park WHERE park_name = ?";
        try {
            PreparedStatement pstmt = dbController.getConnection().prepareStatement(queryGetID);
            //System.out.println(order.getParkIdFk());
            pstmt.setString(1, order.getParkIdFk());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                parkID = rs.getInt("park_id_pk");
            } else {
                return new Message("Error", "Failed to retrieve park ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("Error", "An error occurred while retrieving park ID.");
        }

        String queryCapacity = "SELECT capacity,capacity_offset,average_visit_time FROM park WHERE park_id_pk = ?";
        try {
            PreparedStatement pstmt = dbController.getConnection().prepareStatement(queryCapacity);
            pstmt.setInt(1, parkID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int capacity = rs.getInt("capacity");
                int offset = rs.getInt("capacity_offset");
                int avgTime = rs.getInt("average_visit_time");

                String queryToSumVisitors = "SELECT SUM(number_of_visitors) AS total_visitors FROM `order` WHERE visit_date = ? AND visit_time = ? AND `cancelled` = 0 AND `on_waiting_list` = 0";

                pstmt = dbController.getConnection().prepareStatement(queryToSumVisitors);
                pstmt.setDate(1, Date.valueOf(order.getVisitDate()));
                pstmt.setTime(2, Time.valueOf(order.getVisitTime()));

                ResultSet rs2 = pstmt.executeQuery();
                if (rs2.next()) {
                    int totalVisitors = rs2.getInt("total_visitors");

                    if (totalVisitors + order.getNumberOfVisitors() > capacity - offset) {
                        return new Message("ParkFull", "The park is full at the specified date and time.");
                    } else {
                        return new Message("ParkNotFull", "The park is not full at the specified date and time.");
                    }
                } else {
                    return new Message("Error", "Failed to retrieve total visitors.");
                }
            } else {
                return new Message("Error", "Failed to retrieve park capacity.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("Error", "An error occurred while checking park capacity.");
        } finally {
            dbController.closeConnection(); // Ensure the database connection is closed
        }
    }
}
