package gui;
import command.Message;
import data.Order;
import handler.ClientHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.LocalTimeStringConverter;


import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.RED;

public class UpdateOrder implements Initializable {

    @FXML
    private Label orderIdLabel;

    @FXML
    private Spinner <LocalTime> timeOfVisit;

    @FXML
    private DatePicker dateToVisit;

    @FXML
    private Button cancelOrderButton;



    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;
    @FXML
    private Label msgToUser;

    private Order order;

    private OrderList orderListController;

    private Boolean isCancelled=false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnchorPane anchorPane = (AnchorPane) orderIdLabel.getParent(); // Assuming orderIdLabel is a child of the AnchorPane
        anchorPane.getStylesheets().add(getClass().getResource("UpdateOrder.css").toExternalForm());
//        if (ClientHandler.getAccount().account_type.equals("TourGuide")){
//            numberOfVisitors.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,15));
//        }else {
//            numberOfVisitors.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5));
//        }

        SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new LocalTimeStringConverter(FormatStyle.MEDIUM));
            }

            @Override
            public void decrement(int steps) {
                if (getValue() == null)
                    setValue(LocalTime.of(7,0));
                else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.minusMinutes(30));
                }
            }

            @Override
            public void increment(int steps) {
                if (this.getValue() == null)
                    setValue(LocalTime.of(7,0));
                else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.plusMinutes(30));                }
            }
        };

        // Set the value factory to your timeOfVisit Spinner
        timeOfVisit.setValueFactory(valueFactory);
    }

    public void setOrderListController(OrderList orderListController) {
        this.orderListController = orderListController;
    }

    public void setOrder(Order order) {
        this.order = order;
        // Update the label and fields with the order details
        orderIdLabel.setText("Order ID: " + order.getOrderIdPk().toString());
        timeOfVisit.getValueFactory().setValue(order.getVisitTime());
        dateToVisit.setValue(order.getVisitDate());
        //numberOfVisitors.getValueFactory().setValue(order.getNumberOfVisitors());
    }

    @FXML
    private void handleSaveChangesAndBack() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        // Get the values from the text box, calendar, and time chooser
        //int newNumberOfVisitors = Integer.parseInt(String.valueOf(numberOfVisitors.getValue()));
        LocalDate newDateToVisit = dateToVisit.getValue();
        LocalTime newTimeOfVisit = timeOfVisit.getValue();
        // Check if newTimeOfVisit is before order.getExitTime()
        if (newDateToVisit.isBefore(today)) {
            showMessage("The visit date has to be today or in the future", RED);
            return;
        } else if (newDateToVisit.equals(today) && newTimeOfVisit.isBefore(now.plusHours(1))) {
            showMessage("If the visit date is today, the visit time has to be at least one hour from now", RED);
            return;
        }
        if (newTimeOfVisit.isBefore( LocalTime.of(7, 0)) || newTimeOfVisit.isAfter(LocalTime.of(20, 0))) {
            showMessage("The visit time has to be between 7:00 and 20:00", RED);
            return;
        }


        // Update the order with the new values
        //order.setNumVisitors(newNumberOfVisitors);
        order.setDateToVisit(newDateToVisit);
        order.setVisitTime(newTimeOfVisit);
        if(isCancelled)
            order.setCancelled(true);
        // Update the order in the OrderList class and refrest table
        orderListController.updateOrder(order);
        orderListController.refreshTable();

        // Close the UpdateOrder window and go back to the OrderList window
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleBack() {
        // Get the current stage
        Stage stage = (Stage) backButton.getScene().getWindow();

        // Close the current stage
        stage.close();
    }

    public void showMessage (String msg,  Color c)
    {
        // Update the Label
        msgToUser.setVisible(true);
        msgToUser.setText(msg);
        msgToUser.setTextFill(c); // Set the text color to c

        // Create a timeline for the pulse animation
        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), evt -> msgToUser.setOpacity(1.0)),
                new KeyFrame(Duration.seconds(0.1), evt -> msgToUser.setOpacity(0.9)),
                new KeyFrame(Duration.seconds(0.2), evt -> msgToUser.setOpacity(0.8)),
                new KeyFrame(Duration.seconds(0.3), evt -> msgToUser.setOpacity(0.7)),
                new KeyFrame(Duration.seconds(0.4), evt -> msgToUser.setOpacity(0.6)),
                new KeyFrame(Duration.seconds(0.5), evt -> msgToUser.setOpacity(0.5)),
                new KeyFrame(Duration.seconds(0.6), evt -> msgToUser.setOpacity(0.4)),
                new KeyFrame(Duration.seconds(0.7), evt -> msgToUser.setOpacity(0.3)),
                new KeyFrame(Duration.seconds(0.8), evt -> msgToUser.setOpacity(0.2)),
                new KeyFrame(Duration.seconds(0.9), evt -> msgToUser.setOpacity(0.1)),
                new KeyFrame(Duration.seconds(1.0), evt -> msgToUser.setOpacity(0.0))
        );
        timeline.setCycleCount(3); // Set it to pulse 3 times

        // Add an onFinished event to the timeline to hide the label after the animation
        timeline.setOnFinished(event -> msgToUser.setVisible(false));

        timeline.play(); // Start the animation
    }
    private void popupAcceptWindow(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order Cancellation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            isCancelled = true;
            timeOfVisit.setDisable(true);
            dateToVisit.setDisable(true);
        }
    }
    @FXML
    private void handleCancelOrder() {
        popupAcceptWindow("Are you sure you want to cancel the order?");
    }
}
