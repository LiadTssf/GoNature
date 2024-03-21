package gui;

import command.Message;
import data.Order;
import handler.ClientHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderConfirmation implements Initializable {
    @FXML
    private Label orderID;
    @FXML
    private Label orderEmail;
    @FXML
    private Label orderPhone;
    @FXML
    private Label orderNumOfVisitors;
    @FXML
    private Label orderLocation;
    @FXML
    private Label orderDate;
    @FXML
    private Label orderTime;
    @FXML
    private Label orderFinalCost;

    private Order compOrder;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!(ClientHandler.getLastResponse().getParam() instanceof Order)){
            ClientUI.popupNotification("parameter is not order");

        }

        compOrder =(Order) ClientHandler.getLastResponse().getParam();
        orderID.setText(Integer.toString(compOrder.account_id));
        orderEmail.setText(compOrder.email);
        orderPhone.setText(compOrder.phone);
        orderNumOfVisitors.setText(String.valueOf(compOrder.number_of_visitors));
        orderLocation.setText(compOrder.park_id_fk);
        orderDate.setText(String.valueOf(compOrder.visit_date));
        orderTime.setText(String.valueOf(compOrder.visit_time));
        orderFinalCost.setText(String.valueOf(finalCost()));
    }


    private double finalCost(){
        double costCalc;
        if (compOrder.guided_order){
            costCalc = (compOrder.number_of_visitors * 80) * 0.75;
            if (!compOrder.on_arrival_order) {
                return costCalc*0.88;
            }
            return costCalc;
        }
        if (!compOrder.on_arrival_order){
            return (compOrder.number_of_visitors*80)*0.85;
        }
        return compOrder.number_of_visitors*80;
    }

    @FXML
    public void orderConfirm(javafx.event.ActionEvent actionEvent){
        ClientUI.popupNotification("Order Confirmed");
        ClientHandler.request(new Message("Message",compOrder));

        ClientUI.changeScene("Welcome");
    }

    @FXML
    public void orderBack(javafx.event.ActionEvent actionEvent){
        ClientUI.changeScene("OrderVisit");

    }
}
