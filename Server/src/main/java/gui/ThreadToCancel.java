package gui;

import database.DatabaseController;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class ThreadToCancel implements Runnable{

    /*Thread method that checks every row and IF the exit time and visit date is the same as real values
    * set this order as cancelled
    * */

        public void run() {
            LocalTime now = LocalTime.now();
            LocalDate today = LocalDate.now();
                while(!DatabaseConnection.isConnected) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }


            DatabaseController DB = new DatabaseController();
            try {
                // First, select the orders that are not up to date, not cancelled, and not paid
                String selectQuery = "SELECT order_id_pk, exit_time, visit_date, paid, cancelled FROM `order`";
                PreparedStatement selectStmt = DB.getConnection().prepareStatement(selectQuery);
                ResultSet selectedOrders = selectStmt.executeQuery();
                while (selectedOrders.next()) {
                    String orderId = selectedOrders.getString("order_id_pk");
                    Time exitTime = selectedOrders.getTime("exit_time");
                    Date visitDate = selectedOrders.getDate("visit_date");
                    boolean paid = selectedOrders.getBoolean("paid");
                    int cancelledInt = selectedOrders.getInt("cancelled");

                    // Check if the visit date is today, the order is not paid and not cancelled, and the exit time is less than or equal to the current time plus 2 hours
                    if (visitDate.toLocalDate().isEqual(today) && !paid && cancelledInt == 0 && (exitTime.toLocalTime().minusHours(2)).isBefore(now)) {
                        String updateQuery = "UPDATE `order` SET cancelled = ? WHERE order_id_pk = ?";
                        PreparedStatement updateStmt = DB.getConnection().prepareStatement(updateQuery);
                        updateStmt.setBoolean(1, true);
                        updateStmt.setString(2, orderId);
                        int rowsUpdated = updateStmt.executeUpdate();
                        System.out.println(rowsUpdated + " rows updated");
                    }


                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            System.out.println("cancel orders every hour");
        }
    }
