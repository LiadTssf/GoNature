package gui;

import command.Message;
import data.Order;
import handler.ClientHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class OrderList implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView <Order> orderTable;

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
    private TableColumn<Order, String> cancelled;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    @FXML
    private Label saveMessage;
    private int customerId;
    private ArrayList<Order> orderList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up the cell value factories for the table columns
        orderId.setCellValueFactory(new PropertyValueFactory<>("OrderIdPk"));
        parkId.setCellValueFactory(new PropertyValueFactory<>("ParkIdFk"));
        visitTime.setCellValueFactory(new PropertyValueFactory<>("VisitTime"));
        exitTime.setCellValueFactory(new PropertyValueFactory<>("ExitTime"));
        numVisitors.setCellValueFactory(new PropertyValueFactory<>("NumberOfVisitors"));
        email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        guidedOrder.setCellValueFactory(new PropertyValueFactory<>("GuidedOrder"));
        onArrivalOrder.setCellValueFactory(new PropertyValueFactory<>("OnArrivalOrder"));
        onWaitingList.setCellValueFactory(new PropertyValueFactory<>("OnWaitingList"));
        cancelled.setCellValueFactory(new PropertyValueFactory<>("Cancelled"));



        customerId = ClientHandler.getAccount().account_id_pk;
        // Load orders based on customer ID
        loadOrdersFromDatabase(customerId);


        Object param = ClientHandler.getLastResponse().getParam();
        ArrayList<?> list = (ArrayList<?>) param;
        if (!list.isEmpty() && list.get(0) instanceof Order) {
            orderList = (ArrayList<Order>) list;
        }

        if (orderList != null) {
            System.out.println(orderList);
            orderTable.getItems().addAll(orderList);
        }


        // Set an onMouseClicked event for the TableView
        orderTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) { // Checking double click
                    handleRowDoubleClick();
                }
            }
        });



        // Set button actions
        saveButton.setOnAction(event -> handleSaveChanges());
        backButton.setOnAction(event -> handleBack());
    }

    private void handleRowDoubleClick() {
        // Get the selected order
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();

        // Check if the selected order is null
        if (selectedOrder == null) {
            showMessage("Choose a line with order!", Color.RED);
            return;
        }

        try {
            // Load the UpdateOrder.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateOrder.fxml"));
            Parent root = loader.load();

            // Get the UpdateOrder controller and pass the selected order and this controller to it
            UpdateOrder controller = loader.getController();
            controller.setOrder(selectedOrder);
            controller.setOrderListController(this); // Pass this controller

            // Create a new stage and scene for the UpdateOrder window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void refreshTable() {
        orderTable.getItems().clear();
        orderTable.getItems().addAll(orderList);
    }

    public void updateOrder(Order updatedOrder) {
        int index = orderList.indexOf(updatedOrder);
        if (index != -1) {
            orderList.set(index, updatedOrder);
        }
    }

    private void loadOrdersFromDatabase(int customerId) {
        ClientHandler.request(new Message("GetOrderListById", customerId));
    }
    private void handleSaveChanges() {
        ClientHandler.request(new Message("SetOrderListById", orderList));
        showMessage("Orders Changes Saved!", Color.GREEN);
    }
    public void showMessage (String msg,  Color c)
    {
        // Update the Label
        saveMessage.setVisible(true);
        saveMessage.setText(msg);
        saveMessage.setTextFill(c); // Set the text color to c

        // Create a timeline for the pulse animation
        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), evt -> saveMessage.setOpacity(1.0)),
                new KeyFrame(Duration.seconds(0.1), evt -> saveMessage.setOpacity(0.9)),
                new KeyFrame(Duration.seconds(0.2), evt -> saveMessage.setOpacity(0.8)),
                new KeyFrame(Duration.seconds(0.3), evt -> saveMessage.setOpacity(0.7)),
                new KeyFrame(Duration.seconds(0.4), evt -> saveMessage.setOpacity(0.6)),
                new KeyFrame(Duration.seconds(0.5), evt -> saveMessage.setOpacity(0.5)),
                new KeyFrame(Duration.seconds(0.6), evt -> saveMessage.setOpacity(0.4)),
                new KeyFrame(Duration.seconds(0.7), evt -> saveMessage.setOpacity(0.3)),
                new KeyFrame(Duration.seconds(0.8), evt -> saveMessage.setOpacity(0.2)),
                new KeyFrame(Duration.seconds(0.9), evt -> saveMessage.setOpacity(0.1)),
                new KeyFrame(Duration.seconds(1.0), evt -> saveMessage.setOpacity(0.0))
        );
        timeline.setCycleCount(3); // Set it to pulse 3 times

        // Add an onFinished event to the timeline to hide the label after the animation
        timeline.setOnFinished(event -> saveMessage.setVisible(false));

        timeline.play(); // Start the animation
    }


    private void handleBack() {
        // Implement logic to navigate back to the previous screen
    }
}

