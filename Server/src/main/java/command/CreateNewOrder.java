package command;

import data.Account;
import data.MessagesToSend;
import data.Order;
import database.DatabaseController;
import handler.ServerHandler;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

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
        if (orderToCreate.visit_date.isBefore(LocalDate.now()) || orderToCreate.visit_time == null) {
            return new Message("OrderDate", "Please choose date that is after " + LocalDate.now());
        }
        if (orderToCreate.park_id_fk.equals("Park Location")){
            return new Message("LocationError");
        }

        DatabaseController DB = new DatabaseController();

        String query = ("INSERT INTO `order` (order_id_pk, account_id, park_id_fk, visit_date, visit_time, exit_time, number_of_visitors, email, phone, guided_order, on_arrival_order, on_waiting_list, cancelled, paid,approve) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
        String queryGetID = ("SELECT park_id_pk FROM park WHERE park_name = ?"); //Get park ID

        String queryToIncrease = ("SELECT current_visitors,capacity,capacity_offset FROM park WHERE park_id_pk = ?"); //Updates current visitors+specific order visitors number by using park ID

        String queryCapacity = ("SELECT capacity,capacity_offset,average_visit_time FROM park WHERE park_id_pk = ?"); // get the park capacity

        String queryToSumVisitors =("SELECT SUM(number_of_visitors) AS total_visitors FROM `order` WHERE visit_date = ? AND visit_time = ?");

        try {
            int parkID = 0;
            int offset = 0;
            int capacity = 0;
            int currentVisitors = 0;
            int totalVisitors = 0;
            int avgTime = 0;

            PreparedStatement pstmt = DB.getConnection().prepareStatement(queryGetID); // ID from DB
            pstmt.setString(1, orderToCreate.park_id_fk);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                parkID = rs.getInt("park_id_pk");
            }

            pstmt = DB.getConnection().prepareStatement(queryCapacity); // capacity from DB
            pstmt.setInt(1,parkID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                capacity = rs.getInt("capacity");
                offset = rs.getInt("capacity_offset");
                avgTime = rs.getInt("average_visit_time");
            }

            pstmt = DB.getConnection().prepareStatement(queryToSumVisitors);
            pstmt.setDate(1,Date.valueOf(orderToCreate.visit_date));
            pstmt.setTime(2,Time.valueOf(orderToCreate.visit_time));

            rs = pstmt.executeQuery();
            if (rs.next()){
                totalVisitors = rs.getInt("total_visitors");
            }

            pstmt = DB.getConnection().prepareStatement(queryToIncrease);
            pstmt.setInt(1,parkID);
            rs = pstmt.executeQuery();
            if (rs.next()){
                currentVisitors = rs.getInt("current_visitors");
            }
            if (currentVisitors+orderToCreate.number_of_visitors>capacity && orderToCreate.on_arrival_order){
                return new Message("ParkFull","There is no space is the park,Try later");
            }

            if (!orderToCreate.on_waiting_list && !orderToCreate.on_arrival_order && currentVisitors+orderToCreate.number_of_visitors>capacity){
                return new Message("NotOnWaitingList","Order cancelled because chose not to get in waiting list+ Park full");
            }
            orderToCreate.exit_time = orderToCreate.visit_time.plusHours(avgTime);
            pstmt = DB.getConnection().prepareStatement(query);
            pstmt.setString(1, String.valueOf(orderToCreate.order_id_pk));
            pstmt.setInt(2, orderToCreate.account_id);
            pstmt.setInt(3, parkID);
            pstmt.setDate(4, Date.valueOf(orderToCreate.visit_date));
            pstmt.setTime(5, Time.valueOf(orderToCreate.visit_time));
            pstmt.setTime(6, Time.valueOf(orderToCreate.exit_time));
            pstmt.setInt(7, orderToCreate.number_of_visitors);
            pstmt.setString(8, orderToCreate.email);
            pstmt.setString(9, orderToCreate.phone);
            pstmt.setBoolean(10, orderToCreate.guided_order);
            pstmt.setBoolean(11, orderToCreate.on_arrival_order);
            pstmt.setBoolean(12, orderToCreate.on_waiting_list);
            pstmt.setBoolean(13, false);
            pstmt.setBoolean(14,false);
            pstmt.setBoolean(15,false);
            pstmt.execute();
            if (orderToCreate.on_waiting_list){
                return new Message("OnWaitingList");
            }


            MessagesToSend MSG = new MessagesToSend();
            MSG.MessageText ="Congratulations!!\nYour Order Created Successfully.\nWe'll be Happy To see you!";
            MSG.MessageTitle = "SMS Approve\n";
            MSG.phone = orderToCreate.phone;
            MSG.Email = orderToCreate.email;
            ArrayList<Object> objArr = new ArrayList<>();
            objArr.add(MSG);
            objArr.add(account);
            return new Message("OrderCreated", objArr);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message("OrderFailed", "An error occurred while trying to create order.");
        }
    }
}
