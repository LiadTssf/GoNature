package command;

import data.Account;
import data.Order;
import database.DatabaseController;
import handler.ServerHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            return new Message("OrderFailed", "The Email you entered is incorrect");
        }
        if (orderToCreate.visit_time.isBefore(LocalTime.of(7, 0)) || orderToCreate.visit_time.isAfter(LocalTime.of(20, 0))) {
            return new Message("OrderFailed", "The opening hours is between 7:00 to 20:00");
        }
        if (orderToCreate.phone.length() != 10) {
            return new Message("OrderFailed", "Please Enter phone number without '-' or check your phone again");
        }
        if (orderToCreate.visit_date.isBefore(LocalDate.now())) {
            return new Message("OrderFailed", "Please choose date that is after " + LocalDate.now());
        }

        DatabaseController DB = new DatabaseController();

        String query = ("INSERT INTO order (order_id_pk,account_id,park_id_pk,visit_time,exit_time,number_of_visitors,email,phone,guided_order,on_arrival_order,on_waiting_list,canceled) VALUES (" + orderToCreate.order_id_pk +
                "," + orderToCreate.account_id + "," + orderToCreate.park_id_fk + "," + orderToCreate.visit_time + "," + orderToCreate.exit_time + "," + orderToCreate.number_of_visitors + "," + orderToCreate.email + "," + orderToCreate.phone + "," +
                orderToCreate.guided_order + "," + orderToCreate.on_arrival_order + "," + orderToCreate.on_waiting_list + "," + orderToCreate.cancelled + ")");

        try {
            PreparedStatement pstmt = DB.getConnection().prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            if (ServerHandler.getClientFromAccount(orderToCreate.account_id) == -1) {
                return new Message("OrderCreated", orderToCreate);
            }
            return new Message("OrderFailed", "Order creation failed in DB");
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("Error", "An error occurred while trying to create order.");
        }
    }
}
