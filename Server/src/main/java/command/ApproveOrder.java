package command;

import data.Account;
import database.DatabaseController;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


// TODO ADD APPROVE COLOUMN TO ORDER IN DB
public class ApproveOrder implements ServerCommand{
    @Override
    public Message execute(Object param, Account account) throws SQLException {
        if (!(param instanceof Integer)) {
            return new Message("OrderListFailed", "Invalid parameter type sent(Integer)");
        }
        LocalDate tomorrow = LocalDate.now().plusDays(2);
        int accountId = (Integer) param;

        DatabaseController DB = new DatabaseController();
        try {

            String query = "UPDATE `gonature`.`order` SET approve = ? WHERE visit_date = ? AND account_id = ?";
            PreparedStatement statement = DB.getConnection().prepareStatement(query);
            statement.setBoolean(1,true);
            statement.setDate(2,Date.valueOf(tomorrow));
            statement.setInt(3,accountId);
            statement.executeUpdate();
            System.out.println("order approved ! ");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Message("FailMessage", "Order approved");
    }
}
