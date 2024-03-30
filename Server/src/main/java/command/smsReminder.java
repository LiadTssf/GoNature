package command;

import data.Account;
import data.Order;
import database.DatabaseController;
import gui.CancelNotApprovedOrderThread;
import java.util.UUID;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        SerachForOrdersToRemind(accountId);
        CancelNotApprovedOrderThread cancelNotApprovedOrderThread = new CancelNotApprovedOrderThread(orderToReturn.account_id, orderToReturn.order_id_pk.toString());
        cancelNotApprovedOrderThread.run();
        return new Message("popUpOrderReminder" , orderToReturn);
    }



    private void SerachForOrdersToRemind(int accountId){

        LocalDate tomorrow = LocalDate.now().plusDays(2); // Tomorrow's date
        String sql = "SELECT * FROM `gonature`.`order` WHERE `visit_date` = ? AND cancelled = ? AND account_id = ? ";
        String queryParkName = "SELECT park_name FROM park WHERE park_id_pk = ?";
        try  {
            DatabaseController DB = new DatabaseController();
            DatabaseController db = new DatabaseController();
            PreparedStatement pstmt = DB.getConnection().prepareStatement(sql);
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(queryParkName);
            pstmt.setDate(1, Date.valueOf(tomorrow)); // Set tomorrow's date as parameter
            pstmt.setBoolean(2,false);
            pstmt.setInt(3,accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    preparedStatement.setInt(1,rs.getInt("park_id_fk"));
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()){
                        order.park_id_fk = resultSet.getString("park_name");
                    }
                    order.order_id_pk = UUID.fromString(rs.getString("order_id_pk"));
                    order.account_id = rs.getInt("account_id");
                    order.visit_time = rs.getTime("visit_time").toLocalTime();
                    order.visit_date = rs.getDate("visit_date").toLocalDate();
                    order.email= rs.getString("email");
                    order.phone = rs.getString("phone");
                    orderToReturn = order;

                    System.out.println("order details:" + orderToReturn.getEmail()+orderToReturn.getPhone());

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }

}
