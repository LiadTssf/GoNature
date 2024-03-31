package command;

import data.Account;
import data.Order;
import database.DatabaseController;
import gui.CancelNotApprovedOrderThread;

import java.sql.*;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;



public class smsReminder implements ServerCommand{


    private Order orderToReturn;
    private List<Order> orders = new ArrayList<>();
    private LocalTime localTime= LocalTime.now();
    @Override
    public Message execute(Object param, Account account) throws SQLException {
        if (!(param instanceof Integer)) {
            return new Message("OrderListFailed", "Invalid parameter type sent(Integer)");
        }

        int accountId = (Integer) param;
        System.out.println("enter sms reminder methos with accound id : " + accountId);
        try {
            orders = SerachForOrdersToRemind(accountId); // Corrected the method name
            System.out.println(orders);
            for (Order order : orders) { // Corrected the for-each loop syntax
                System.out.println("orders check thread do");

                if (order != null) { // Added the missing variable in the if condition
                    CancelNotApprovedOrderThread cancelNotApprovedOrderThread = new CancelNotApprovedOrderThread(order.account_id, order.order_id_pk.toString());
                    System.out.println("Starting new thread for CancelNotApprovedOrderThread");
                    Thread thread = new Thread(cancelNotApprovedOrderThread);
                    thread.start();
                    System.out.println("Thread started");
                } else {
                    // This else block is unnecessary because if 'order' is null, it wouldn't be in the 'orders' list
                    System.out.println("order is null");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return new Message("popUpOrderReminder" , orders);

    }


    private List<Order> SerachForOrdersToRemind(int accountId) {

        List<Order> ordersToRemind = new ArrayList<>();
        LocalDate today = LocalDate.now(); // Tomorrow's date
        LocalDate tomorrow = LocalDate.now().plusDays(1); // Tomorrow's date
        LocalTime now = LocalTime.now().plusHours(2); // Current time

        String sql = "SELECT * FROM `gonature`.`order` WHERE (`visit_date` = ? OR `visit_date` = ?) AND cancelled = false AND account_id = ?";

        String queryParkName = "SELECT park_name FROM park WHERE park_id_pk = ?";



        System.out.println("query checking");
        try  {
            DatabaseController db = new DatabaseController();
            DatabaseController db2 = new DatabaseController();
            PreparedStatement pstmt = db2.getConnection().prepareStatement(sql);
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(queryParkName);
            pstmt.setDate(1, Date.valueOf(tomorrow.plusDays(1))); // Set the first placeholder with tomorrow's date
            pstmt.setDate(2, Date.valueOf(today.plusDays(1))); // Set the second placeholder with today's date
            pstmt.setInt(3, accountId); // Set the third placeholder with the account ID


            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("__________________________");
                    System.out.println("Ac ID: "+rs.getInt("account_id"));
                    LocalDate visitDate = rs.getDate("visit_date").toLocalDate();
                    LocalTime visitTime = rs.getTime("visit_time").toLocalTime();
                    // Check if the visit time is before the current time
                    if ((visitDate.equals(today) && now.isBefore(visitTime)) || (visitDate.equals(tomorrow) && visitTime.isBefore(now))) {                        System.out.println("entered for visitTime: "+visitTime);
                        Order order = new Order();
                        preparedStatement.setInt(1, rs.getInt("park_id_fk"));
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            if (resultSet.next()) {
                                order.park_id_fk = resultSet.getString("park_name");
                            }
                        }
                        order.order_id_pk = UUID.fromString(rs.getString("order_id_pk"));
                        order.account_id = rs.getInt("account_id");
                        order.setVisitTime(visitTime);

                        order.visit_date = rs.getDate("visit_date").toLocalDate();
                        order.email = rs.getString("email");
                        order.phone = rs.getString("phone");

                        ordersToRemind.add(order);


                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Or handle the exception as needed
        }
        System.out.println(ordersToRemind);
        return ordersToRemind;
    }
}


