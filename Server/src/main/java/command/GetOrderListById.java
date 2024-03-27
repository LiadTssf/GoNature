package command;

import data.Account;
import data.Order;
import database.DatabaseController;
import handler.ServerHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class GetOrderListById implements ServerCommand {
    /**
     * Receives an account id and returns all orders associated with that account
     *
     * @param param   account id
     * @param account client authorized account
     * @return message to client with list of orders or denial message
     */
    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof Integer)) {
            return new Message("OrderListFailed", "Invalid parameter type sent(Integer)");
        }

        int accountId = (Integer) param;
        DatabaseController DB = new DatabaseController();
        ArrayList<Order> orderList = new ArrayList<>();

        String query = "SELECT * from `order` WHERE account_id = ?";
        try {
            PreparedStatement pstmt = DB.getConnection().prepareStatement(query);
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                try {
                    order.order_id_pk = UUID.fromString(rs.getString("order_id_pk"));
                }
                catch (IllegalArgumentException e)
                {
                    System.out.println("Error here");
                }
                order.account_id = rs.getInt("account_id");
                order.park_id_fk = String.valueOf(rs.getInt("park_id_fk"));
                order.visit_date = rs.getDate("visit_date").toLocalDate();
                order.visit_time = rs.getTime("visit_time").toLocalTime();
                order.exit_time = rs.getTime("exit_time").toLocalTime();
                order.number_of_visitors = rs.getInt("number_of_visitors");
                order.email = rs.getString("email");
                order.phone = rs.getString("phone");
                order.guided_order = rs.getBoolean("guided_order");
                order.on_arrival_order = rs.getBoolean("on_arrival_order");
                order.on_waiting_list = rs.getBoolean("on_waiting_list");
                order.cancelled = rs.getBoolean("cancelled");
                order.paid = rs.getBoolean("paid");
                orderList.add(order);
            }

            return new Message("SendOrderListToClient", orderList);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("FailMessage", "An error occurred while trying to fetch order list.");
        }
    }

}
