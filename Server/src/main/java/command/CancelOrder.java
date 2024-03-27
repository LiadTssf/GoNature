package command;

import data.Account;
import database.DatabaseController;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
public class CancelOrder implements ServerCommand{
    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof UUID)) {
            return new Message("CancelOrderFailed", "Invalid parameter type sent");
        }

        UUID orderId = (UUID) param;

        DatabaseController DB = new DatabaseController();

        String updateQuery = "UPDATE `order` SET `cancelled` = 1 WHERE `order_id_pk` = ? AND `cancelled` =  AND `paid` = 0";
        try {
            PreparedStatement pstmt = DB.getConnection().prepareStatement(updateQuery);
            pstmt.setString(1, orderId.toString());
            int updatedRows = pstmt.executeUpdate();

            if (updatedRows > 0) {
                return new Message("OrderCancelled", "Order cancelled successfully");
            } else {
                return new Message("OrderNotCancelled", "Order could not be cancelled");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("CancelOrderFailed", "order alredy paid or cancelled already");
        }
    }
}
