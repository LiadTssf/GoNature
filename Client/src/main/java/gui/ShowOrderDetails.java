package gui;

import command.Message;
import handler.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import order.Order;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowOrderDetails implements Initializable {
    private Order order;

    @FXML
    private TextField orderNumber;
    @FXML
    private TextField parkNameField;
    @FXML
    private TextField numberOfVisitors;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField timeOfVisit;
    @FXML
    private TextField email;
    @FXML
    private Button updatebtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        order = (Order) ClientHandler.getCommandReturn();
        orderNumber.setText(order.order_id_pk);
        parkNameField.setText(order.park_name);
        numberOfVisitors.setText(order.number_of_visitors);
        phoneNumber.setText(order.telephone);
        timeOfVisit.setText(order.visit_time);
        email.setText(order.email);
    }

    @FXML
    public void getUpdatebtn(ActionEvent actionEvent) {
            order.order_id_pk = orderNumber.getText();
            order.park_name = parkNameField.getText();
            order.number_of_visitors = numberOfVisitors.getText();
            order.telephone = phoneNumber.getText();
            order.visit_time = timeOfVisit.getText();
            order.email = email.getText();
            ClientHandler.request(new Message("UpdateOrderByNumber", order));
            ClientUI.changeScene("LoadOrder");
    }
}
