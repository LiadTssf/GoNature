package command;

import data.Account;
import data.Order;
import database.DatabaseController;
import handler.ServerHandler;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class CreateNewOrder implements ServerCommand {
    /**
     * Receives an authentication attempt using only account_id and either accepts it and tells the client to
     * authenticate or denies it and sends appropriate denial message
     *
     * @param param   Order object newOrder
     * @param account client authorized account
     * @return message to client authenticating with new order created or denying the creation
     */
    @Override
    public Message execute(Object param, Account account) {
        if (!(param instanceof Order)) {
            return new Message("OrderFailed", "Invalid parameter type sent(Order)");
        }

        Order orderToCreate = (Order) param;
        if (!orderToCreate.email.contains("@")) {
            return new Message("OrderEmail", "The Email you entered is incorrect");
        }
        if (orderToCreate.visit_time.isBefore(LocalTime.of(7, 0)) || orderToCreate.visit_time.isAfter(LocalTime.of(20, 0))) {
            return new Message("OrderTime", "The opening hours is between 7:00 to 20:00");
        }
        if (orderToCreate.phone.length() != 10) {
            return new Message("OrderPhone", "Please Enter phone number without '-' or check your phone again");
        }
        if (orderToCreate.visit_date.isBefore(LocalDate.now())) {
            return new Message("OrderDate", "Please choose date that is after " + LocalDate.now());
        }

        DatabaseController DB = new DatabaseController();

        String query = ("INSERT INTO order (order_id_pk,account_id,park_id_pk,visit_date,visit_time,exit_time,number_of_visitors,email,phone,guided_order,on_arrival_order,on_waiting_list,canceled) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?");
        String queryToCount = ("SELECT COUNT(*) AS rowcount FROM `order`");
        try {
            PreparedStatement pstmt = DB.getConnection().prepareStatement(query);

            pstmt.setString(1, String.valueOf(orderToCreate.order_id_pk));
            pstmt.setInt(2, orderToCreate.account_id);
            pstmt.setString(3, orderToCreate.park_id_fk);
            pstmt.setDate(4, Date.valueOf(orderToCreate.visit_date));
            pstmt.setTime(5, Time.valueOf(orderToCreate.visit_time));
            pstmt.setTime(6, Time.valueOf(orderToCreate.exit_time));
            pstmt.setInt(7, orderToCreate.number_of_visitors);
            pstmt.setString(8, orderToCreate.email);
            pstmt.setString(9, orderToCreate.phone);
            pstmt.setBoolean(10, orderToCreate.guided_order);
            pstmt.setBoolean(11, orderToCreate.on_arrival_order);
            pstmt.setBoolean(12, orderToCreate.on_waiting_list);
            pstmt.setBoolean(13, orderToCreate.cancelled);
            pstmt.execute();
            return new Message("OrderCreated", orderToCreate);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("OrderFailed", "An error occurred while trying to create order.");
        }
    }
}
