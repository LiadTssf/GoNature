package command;

import data.Account;
import data.Order;
import database.DatabaseController;

import java.sql.*;
import java.util.ArrayList;

public class SetOrderListById implements ServerCommand {
    /**
     * Receives a list of orders and updates the orders for the associated account in the database
     *
     * @param param   ArrayList of Order objects
     * @param account client authorized account
     * @return message to client indicating success or failure
     */
    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof ArrayList<?>)) {
            return new Message("FailMessage", "Invalid parameter type sent(ArrayList)");
        }

        ArrayList<?> list = (ArrayList<?>) param;
        if (!list.isEmpty() && !(list.get(0) instanceof Order)) {
            return new Message("FailMessage", "Invalid ArrayList type sent(Order)");
        }

        ArrayList<Order> orderList = (ArrayList<Order>) list;
        DatabaseController DB = new DatabaseController();

        // Check if orderList is not empty before trying to access its elements
        if (orderList.isEmpty()) {
            return new Message("FailMessage", "Order list is empty.");
        }

        int accountId = orderList.get(0).getAccountId();

        // Delete all existing orders for the account
        String deleteQuery = "DELETE FROM `order` WHERE account_id = ?";

        try {
            PreparedStatement deletePstmt = DB.getConnection().prepareStatement(deleteQuery);
            System.out.println("acc>>>>>>>>>>"+accountId);
            deletePstmt.setInt(1, accountId);
            deletePstmt.executeUpdate(); // Use executeUpdate() instead of executeQuery()
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("FailMessage", "An error occurred while trying to delete existing orders.");
        }


        // Insert the new orders
        String insertQuery = "INSERT INTO `order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled,paid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement insertPstmt = DB.getConnection().prepareStatement(insertQuery);
            for (Order order : orderList) {
                System.out.println(order.getVisitTime());
                insertPstmt.setString(1, order.getOrderIdPk().toString());
                insertPstmt.setInt(2, order.getAccountId());
                insertPstmt.setString(3, order.getParkIdFk());
                insertPstmt.setDate(4, Date.valueOf(order.getVisitDate().plusDays(1)));

                // Create a java.sql.Time object and set it to the prepared statement
                Time visitTime = Time.valueOf(order.getVisitTime());
                insertPstmt.setTime(5, visitTime);

                Time exitTime = Time.valueOf(order.getExitTime());
                insertPstmt.setTime(6, exitTime);
                insertPstmt.setInt(7, order.getNumberOfVisitors());
                insertPstmt.setString(8, order.getEmail());
                insertPstmt.setString(9, order.getPhone());
                insertPstmt.setBoolean(10, order.getGuidedOrder());
                insertPstmt.setBoolean(11, order.getOnArrivalOrder());
                insertPstmt.setBoolean(12, order.getOnWaitingList());
                insertPstmt.setBoolean(13, order.getCancelled());
                insertPstmt.setBoolean(14, order.getPaid());
                System.out.println("here");
                insertPstmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("FailMessage", "An error occurred while trying to insert new orders.");
        }

        return new Message("FailMessage", "Order list has been successfully updated.");
    }
}
