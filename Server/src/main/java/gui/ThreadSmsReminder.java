package gui;

import data.Account;
import data.MessagesToSend;
import data.Order;
import database.DatabaseController;
import gui.DatabaseConnection;
import handler.ServerHandler;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class ThreadSmsReminder extends Thread implements Runnable{
    private List<Order> orders = new ArrayList<>();
    private LocalTime localTime= LocalTime.now();
    @Override
    public void run() {
        synchronized(DatabaseConnection.lock) {
            while(!DatabaseConnection.isConnected) {
                try {
                    DatabaseConnection.lock.wait();
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

            LocalDate tomorrow = LocalDate.now().plusDays(2); // Tomorrow's date



            String sql = "SELECT * FROM `gonature`.`order` WHERE `visit_date` = ? AND cancelled = ? ";
            String queryParkName = "SELECT park_name FROM park WHERE park_id_pk = ?";

            try  {

                DatabaseController DB = new DatabaseController();
                DatabaseController db = new DatabaseController();
                PreparedStatement pstmt = DB.getConnection().prepareStatement(sql);
                PreparedStatement preparedStatement = db.getConnection().prepareStatement(queryParkName);
                pstmt.setDate(1, Date.valueOf(tomorrow)); // Set tomorrow's date as parameter
                pstmt.setBoolean(2,false);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Order order = new Order();
                        preparedStatement.setInt(1,rs.getInt("park_id_fk"));
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()){
                            order.park_id_fk = resultSet.getString("park_name");
                        }
                        order.account_id = rs.getInt("account_id");
                        order.visit_time = rs.getTime("visit_time").toLocalTime();
                        order.visit_date = rs.getDate("visit_date").toLocalDate();
                        order.email= rs.getString("email");
                        order.phone = rs.getString("phone");
                        orders.add(order);
                    }

                }
                sendCustomerReminder(orders);
                Thread.sleep(10000);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


    }

//    private void sendCustomerReminder(List<Order> orders){
//
//        for ( Order order : orders){
//            System.out.println("send to the customers" + order.getEmail());
//
//        }
//
//    }


    // TODO  - finish this method
    private void sendCustomerReminder(List<Order> orders){ // need to add approve column
        for (Order order : orders) {
            MSGwindow msGwindow = new MSGwindow();
            MSGwindow msGwindow1 = new MSGwindow();
            msGwindow.setOrderToSend(order);
            msGwindow.setNow(LocalTime.now());
            msGwindow.setTypeSMS("SMS");
            msGwindow1.setOrderToSend(order);
            msGwindow.setNow(LocalTime.now());
            msGwindow.setTypeSMS("Email");
            msGwindow.start();
            msGwindow1.start();
            // Update the order status to canceled
//            String updateSql = "UPDATE `gonature`.`order` SET cancelled = ? WHERE email = ?";
//            PreparedStatement updateStmt = DB.getConnection().prepareStatement(updateSql);
//            updateStmt.setBoolean(1, true);
//            updateStmt.setString(2, order.getEmail());
//            updateStmt.executeUpdate();
        }
    }
}





