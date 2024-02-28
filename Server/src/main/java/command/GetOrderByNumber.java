package command;

import database.DatabaseController;
import order.Order;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetOrderByNumber implements Command {
    /**
     * Calls an SQL query to get order by order_id and sends response
     * @param param String order_id
     * @return Message calling for LoadAndShowOrder with Order object in param or message order not found
     */
    @Override
    public Object execute(Object param) {
        Message response = new Message("OrderNotFound", "Could not find requested order");
        DatabaseController databaseController = new DatabaseController();
        String queryToRun = "SELECT * FROM `order` WHERE order_id_pk = ?";

        try {
            //Run query
            PreparedStatement statement = databaseController.getConnection().prepareStatement(queryToRun);
            statement.setString(1, (String) param);
            ResultSet resultSet = databaseController.executeQuery(statement);

            //Build order object
            Order order = new Order();
            resultSet.first();
            order.order_id_pk = resultSet.getString("order_id_pk");
            order.park_name = resultSet.getString("park_name");
            order.visit_time = resultSet.getString("visit_time");
            order.number_of_visitors = resultSet.getString("number_of_visitors");
            order.telephone = resultSet.getString("telephone");
            order.email = resultSet.getString("email");

            //Build response
            response.setCommand("ReturnParam");
            response.setParam(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        databaseController.closeConnection();
        return response;
    }
}
