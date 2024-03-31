package command;

import data.Account;
import database.DatabaseController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
public class changePaidOrder implements ServerCommand{
    /**
     * Executes the command to change the paid status of an order.
     *
     * @param param   The parameter containing the order ID to be updated.
     * @param account The account associated with the command execution (not used in this command).
     * @return A message indicating the result of the operation.
     */
    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof UUID)) {
            return new Message("ChangePaidStatusFailed", "Invalid parameter type sent");
        }

        UUID orderId = (UUID) param;

        DatabaseController DB = new DatabaseController();
        int parkID = 0;
        int numOfVisitors = 0;
        int currentVisitors = 0;

        String updateQuery = "UPDATE `order` SET `paid` = 1 WHERE `order_id_pk` = ? AND `cancelled` = 0 AND `on_waiting_list` = 0";
        String selQuery = "SELECT number_of_visitors,park_id_fk FROM `order` WHERE `order_id_pk` = ?";
        String selParkVisitQuery = "SELECT current_visitors FROM park WHERE `park_id_pk` = ?";
        String increaseQuery = "UPDATE park SET current_visitors = ? WHERE park_id_pk = ?";
        try {
            PreparedStatement pstmt = DB.getConnection().prepareStatement(updateQuery);
            pstmt.setString(1, orderId.toString());
            int updatedRows = pstmt.executeUpdate();

            pstmt = DB.getConnection().prepareStatement(selQuery);
            pstmt.setString(1,orderId.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                numOfVisitors = rs.getInt("number_of_visitors");
                parkID = rs.getInt("park_id_fk");
            }

            pstmt = DB.getConnection().prepareStatement(selParkVisitQuery);
            pstmt.setInt(1,parkID);
            rs = pstmt.executeQuery();
            if (rs.next()){
                currentVisitors = rs.getInt("current_visitors");
            }

            int capNew = currentVisitors+numOfVisitors;

            pstmt = DB.getConnection().prepareStatement(increaseQuery);
            pstmt.setInt(1,capNew);
            pstmt.setInt(2,parkID);
            pstmt.execute();
            if (updatedRows > 0) {
                return new Message("PaidStatusChanged", "Paid status changed successfully");
            } else {
                return new Message("PaidStatusNotChanged", "Paid status could not be changed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("ChangePaidStatusFailed", "Database error(check if cancelled or in waiting list");
        }
    }
}
