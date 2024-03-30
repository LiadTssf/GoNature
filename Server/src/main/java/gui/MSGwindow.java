package gui;

import data.Order;
import database.DatabaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class MSGwindow extends Thread implements Initializable {
    @FXML
    public Label client_id;
    @FXML
    public Label location;
    @FXML
    public Label number;
    @FXML
    public Label time;
    @FXML
    public Label date;
    @FXML
    public Label msg_type;
    @FXML
    public Label to;

    private Order orderToSend;
    private String type;
    private LocalTime now;

    public void setTypeSMS(String type) {
        this.type = type;
    }

    public void setNow(LocalTime now) {
        this.now = now;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client_id.setText(String.valueOf(orderToSend.account_id));
        location.setText(String.valueOf(orderToSend));
        number.setText(String.valueOf(number));
        time.setText(String.valueOf(date));
        msg_type.setText(type);
        while (now.isBefore(orderToSend.visit_time.plusHours(2)));
        // Update the order status to canceled
        String updateSql = "UPDATE `gonature`.`order` SET cancelled = ? WHERE email = ?";
        DatabaseController DB = new DatabaseController();
        try {
           PreparedStatement updateStmt = DB.getConnection().prepareStatement(updateSql);
            updateStmt.setBoolean(1, true);
            updateStmt.setString(2, orderToSend.getEmail());
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void setOrderToSend(Order order){
        this.orderToSend = order;
    }

}
