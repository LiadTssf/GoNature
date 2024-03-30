package gui;
import command.Message;
import data.Order;
import data.WorkerAccount;
import handler.ClientHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class OrderView implements Initializable {
    @FXML
    private Button searchButton;

    @FXML
    private Button paymentButton;
    @FXML
    private TableView<Order> orderTableView;
    @FXML
    private TextField client_id_search;
    @FXML
    private TableColumn<Order, UUID> orderId;
    @FXML
    private TableColumn<Order, String> parkId;
    @FXML
    private TableColumn<Order, LocalTime> visitTime;
    @FXML
    private TableColumn<Order, LocalTime> exitTime;
    @FXML
    private TableColumn<Order, Integer> numVisitors;
    @FXML
    private TableColumn<Order, String> email;
    @FXML
    private TableColumn<Order, String> phone;
    @FXML
    private TableColumn<Order, Boolean> guidedOrder;
    @FXML
    private TableColumn<Order, Boolean> onArrivalOrder;
    @FXML
    private TableColumn<Order, Boolean> onWaitingList;
    @FXML
    private TableColumn<Order, Boolean> cancelled;
    @FXML
    private TableColumn<Order, Boolean> paid;
    private ArrayList<Order> orderList;
    @FXML
    private Modality modality = Modality.WINDOW_MODAL;
    private String accountIdText;
    private ArrayList<Order> filteredOrders=new ArrayList<Order>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         //Set up the cell value factories for the table columns
        orderId.setCellValueFactory(new PropertyValueFactory<>("OrderIdPk"));
        //parkId.setCellValueFactory(new PropertyValueFactory<>("ParkIdFk"));
        visitTime.setCellValueFactory(new PropertyValueFactory<>("VisitTime"));
        exitTime.setCellValueFactory(new PropertyValueFactory<>("ExitTime"));
        numVisitors.setCellValueFactory(new PropertyValueFactory<>("NumberOfVisitors"));
        email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        guidedOrder.setCellValueFactory(new PropertyValueFactory<>("GuidedOrder"));
        onArrivalOrder.setCellValueFactory(new PropertyValueFactory<>("OnArrivalOrder"));
        onWaitingList.setCellValueFactory(new PropertyValueFactory<>("OnWaitingList"));
        cancelled.setCellValueFactory(new PropertyValueFactory<>("Cancelled"));
        paid.setCellValueFactory(new PropertyValueFactory<>("Paid"));

    }

    @FXML
    private void onSearchButtonClick(ActionEvent event) {

        // Extract the account ID from the TextField
        accountIdText = client_id_search.getText();
        if (accountIdText.isEmpty()) {
            ClientUI.popupNotification("Please enter an account ID");
            return; // Exit the method if the TextField is empty
        }



        try {
            WorkerAccount workeraccount=(WorkerAccount) ClientHandler.getAccount();
            String parkID = workeraccount.getPark_id();
            int accountId = Integer.parseInt(accountIdText);

            // Send request to server to search for orders
            ClientHandler.request(new Message("GetOrderListById", accountId));

            // Handle response from server

            Object param = ClientHandler.getLastResponse().getParam();
            ArrayList<?> list = (ArrayList<?>) param;
            System.out.println("Received orders: " + param); // Print the received orders

                if (param != null && param instanceof ArrayList<?>) {

                    if (!list.isEmpty() && list.get(0) instanceof Order) {
                        orderList = (ArrayList<Order>) list;
                        // Clear the filteredOrders list before filtering again
                        filteredOrders.clear();
                        // Filter the orderList to show only orders with the matching parkID
                        for (Order order : orderList) {
                            if (order.getParkIdFk().equals(parkID)) {

                                filteredOrders.add(order);
                            }
                        }
                    }
                    if (orderTableView != null) {
                        orderTableView.getItems().clear();
                        if (!filteredOrders.isEmpty()) {
                            orderTableView.getItems().addAll(filteredOrders);
                        }
                    }
                }
                else {
                   ClientUI.popupNotification("No Orders Found");
                }
        } catch (NumberFormatException e) {
            ClientUI.popupNotification("Invalid account ID");
        }
    }

    @FXML
    private void onCancelOrderButtonClick(ActionEvent event) {
        // Get the selected item from the ListView
        Order selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            ClientUI.popupNotification("No order selected");
            return;
        }

        // Send request to server to cancel the selected order
        // Assuming the order ID is included in the selectedOrder string
        try {
            UUID orderId = selectedOrder.getOrderIdPk();
            ClientHandler.request(new Message("CancelOrder", orderId));
        } catch (NumberFormatException e) {
            ClientUI.popupNotification("Error parsing order ID");
            return;
        }

        // Handle response from server
        if (ClientHandler.getLastResponse().getCommand().equals("OrderCancelled")) {
            ClientUI.popupNotification("Order cancelled successfully");
        } else if (ClientHandler.getLastResponse().getCommand().equals("OrderNotCancelled")) {
            ClientUI.popupNotification("Order could not be cancelled");
        }
    }
    @FXML
    public void onOpenPaymentWindow(ActionEvent actionEvent) {
        try {
            // Load the Payment.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Payment.fxml"));
            AnchorPane paymentPane = loader.load();

            // Get the PaymentController
            PaymentController paymentController = loader.getController();

            // Pass the selected order to the PaymentController
            Order selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
            paymentController.initData(selectedOrder);

            // Create a new stage for the Payment window
            Stage paymentStage = new Stage();
            paymentStage.setTitle("Payment");
            paymentStage.setScene(new Scene(paymentPane));

            // Set the modality of the payment stage to WINDOW_MODAL
            paymentStage.initModality(Modality.WINDOW_MODAL);
            paymentStage.initOwner(orderTableView.getScene().getWindow()); // Set the owner of the payment stage to the main window

            paymentStage.showAndWait(); // Show the payment window as a dialog and wait for it to be closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRefreshButtonClick(ActionEvent event) {
        // Send request to server to refresh the order list
        ClientHandler.request(new Message("GetOrderListById", accountIdText));

        // Handle response from server
        Object param = ClientHandler.getLastResponse().getParam();
        if (param != null && param instanceof ArrayList<?>) {
            ArrayList<?> list = (ArrayList<?>) param;
            if (!list.isEmpty() && list.get(0) instanceof Order) {
                orderList = (ArrayList<Order>) list;
            }
            if (orderTableView != null) {
                orderTableView.getItems().clear();
                if (orderList != null) {
                    orderTableView.getItems().addAll(orderList);
                }
            }
        } else {
            ClientUI.popupNotification("No Orders Found");
        }
    }
}
