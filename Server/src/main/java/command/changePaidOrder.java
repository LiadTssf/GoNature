package command;

import data.Account;
import database.DatabaseController;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
public class changePaidOrder implements ServerCommand{
    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof UUID)) {
            return new Message("ChangePaidStatusFailed", "Invalid parameter type sent");
        }

        UUID orderId = (UUID) param;

        DatabaseController DB = new DatabaseController();

        String updateQuery = "UPDATE `order` SET `paid` = 1 WHERE `order_id_pk` = ? AND `cancelled` = 0 AND `on_waiting_list` = 0";
        try {
            PreparedStatement pstmt = DB.getConnection().prepareStatement(updateQuery);
            pstmt.setString(1, orderId.toString());
            int updatedRows = pstmt.executeUpdate();

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
