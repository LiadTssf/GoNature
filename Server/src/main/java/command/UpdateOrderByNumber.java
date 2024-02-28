package command;

import database.DatabaseController;
import order.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateOrderByNumber implements Command {
    /**
     * Calls an sql query to update order in database by its id
     * @param param Order object to update database by
     * @return Message update successful or message order not found
     */
    @Override
    public Object execute(Object param) {
        Message response = new Message("OrderNotFound", "Could not find requested order");
        DatabaseController databaseController = new DatabaseController();
        Order order = (Order) param;
        //TODO: Replace this mess with prepared statement
        String queryToRun = "UPDATE `order` SET `park_name` = '" + order.park_name +
                                                "', `visit_time` = '" + order.visit_time +
                                                "', `number_of_visitors` = '" + order.number_of_visitors +
                                                "', `telephone` = '" + order.telephone +
                                                "', `email` = '" + order.email +
                                                "' WHERE (`order_id_pk` = '" + order.order_id_pk + "');";

        try {
            //Run query
            PreparedStatement statement = databaseController.getConnection().prepareStatement(queryToRun);
            databaseController.executeUpdate(statement);

            //Build response
            response.setCommand("OrderUpdateSuccess");
            response.setParam("Order has been updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseController.closeConnection();
        return response;
    }
}
