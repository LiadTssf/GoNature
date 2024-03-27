package gui;

import command.Message;
import handler.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import data.Order;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class PaymentController implements Initializable {
    @FXML
    private Button cashButton;

    @FXML
    private Button closeButton;
    @FXML
    private Button creditCardButton;

    @FXML
    private AnchorPane paymentDetailsPane;
    @FXML
    private Label finalPriceLabel;


    private Order selectedOrder;
    public void initData(Order selectedOrder) {
        this.selectedOrder = selectedOrder;
        // Use the selectedOrder to update the payment details
        // For example:
        finalPriceLabel.setText("Final Price: $" + selectedOrder.getFinalPrice());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the label with the final price
        //finalPriceLabel.setText("Final Price: $" + selectedOrder.getFinalPrice());
    }

    private double getFinalPrice() {
        // This method should return the final price
        // You can implement this method to calculate the final price
        return 100.0; // Example value, replace with your implementation
    }
    @FXML
    private void handleCashButton(ActionEvent event) {
        // Handle the action for the Cash button
        PayOrder("Cash", selectedOrder.getFinalPrice());
    }
    @FXML
    private void handleCreditCardButton(ActionEvent event) {
        // Handle the action for the Credit Card button
        PayOrder("Credit Card", selectedOrder.getFinalPrice());
    }
    private void PayOrder(String paymentMethod, double finalPrice) {
        // Set the 'paid' field to true in the selectedOrder
        selectedOrder.setPaid(true);

        // Send request to server to change the paid status of the selected order
        try {
            UUID orderId = selectedOrder.getOrderIdPk();
            ClientHandler.request(new Message("changePaidOrder", orderId));
        } catch (NumberFormatException e) {
            ClientUI.popupNotification("Error parsing order ID");
            return;
        }

        // Handle response from server
        if (ClientHandler.getLastResponse().getCommand().equals("PaidStatusChanged")) {
            // Display a notification to the user
            ClientUI.popupNotification("Payment successful for " + paymentMethod + " method. Order can be processed.");
        } else {
            // Display an error notification if the paid status could not be changed
            ClientUI.popupNotification("An error occurred while processing the payment. Please try again or use a different payment method.");
        }
    }

    @FXML
    private void handleCloseButton(ActionEvent event) {
        // Close the payment window
        closeButton.getScene().getWindow().hide();

    }

}
