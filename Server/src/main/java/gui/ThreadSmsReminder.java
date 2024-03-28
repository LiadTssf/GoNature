package gui;

import data.Order;
import database.DatabaseController;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ThreadSmsReminder implements Runnable{

    @Override
    public void run() {
        while(!(DatabaseConnection.isConnected))
        {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        List<Order> orders = new ArrayList<>();
            LocalDate tomorrow = LocalDate.now().plusDays(2); // Tomorrow's date


            String sql = "SELECT * FROM `gonature`.`order` WHERE `visit_date` = ? AND cancelled = ? ";

            try  {

                DatabaseController DB = new DatabaseController();
                PreparedStatement pstmt = DB.getConnection().prepareStatement(sql);
                pstmt.setDate(1, Date.valueOf(tomorrow)); // Set tomorrow's date as parameter
                pstmt.setBoolean(2,false);
                try (ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        Order order = new Order();
                        order.email= rs.getString("email");
                        order.phone = rs.getString("phone");
                        orders.add(order);
                    }

                }
                sendCustomerReminder(orders);
                Thread.sleep(10000);
                cancelNotApprovedOrders(orders,DB);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


    }


    private void sendCustomerReminder(List<Order> orders){

        for ( Order order : orders){
            System.out.println("send to the customers" + order.getEmail());

        }

    }


    // TODO  - finish this method
    private void cancelNotApprovedOrders(List<Order> orders,DatabaseController DB) throws SQLException { // need to add approve column
        for (Order order : orders) {
            // Update the order status to canceled
            String updateSql = "UPDATE `gonature`.`order` SET cancelled = ? WHERE email = ?";
            PreparedStatement updateStmt = DB.getConnection().prepareStatement(updateSql);
            updateStmt.setBoolean(1, true);
            updateStmt.setString(2, order.getEmail());
            updateStmt.executeUpdate();
        }
    }

}





