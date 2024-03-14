package gui;

import command.Message;
import data.Order;
import handler.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.LocalTimeStringConverter;
import static gui.ClientUI.popupNotification;
import java.lang.invoke.StringConcatException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class OrderVisit extends Login implements Initializable {
    @FXML
    private Spinner<Integer> numberOfVisitors;
    @FXML
    private DatePicker dateToVisit;
    @FXML
    private Spinner<LocalTime> timeOfVisit;
    @FXML
    private TextField customer_id;

    @FXML RadioButton waitingList;
    @FXML
    private TextField customer_email;

    @FXML
    private TextField customer_phone_number;
    @FXML
    private SplitMenuButton location_pick;
    @FXML
    private MenuItem menuItem_karmiel;

    @FXML
    private MenuItem menuItem_Haifa;
    @FXML
    private MenuItem menuItem_Tel_aviv;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customer_id.setText(String.valueOf(ClientHandler.getAccount().account_id_pk));
        if (ClientHandler.getAccount().account_type.equals("TourGuide")){
            numberOfVisitors.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,15));
        }else {
            numberOfVisitors.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5));
        }

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

        timeOfVisit.setValueFactory(valueFactory);
        timeOfVisit.setEditable(true);

        menuItem_karmiel.setOnAction(event -> location_pick.setText(menuItem_karmiel.getText()));
        menuItem_Haifa.setOnAction(event -> location_pick.setText(menuItem_Haifa.getText()));
        menuItem_Tel_aviv.setOnAction(event -> location_pick.setText(menuItem_Tel_aviv.getText()));


    }



    @FXML
    public void OrderVisit(ActionEvent actionEvent) {
        Order newOrder = new Order();
        newOrder.guided_order = false;
            try {
                newOrder.account_id = Integer.parseInt(customer_id.getText());
            } catch (NullPointerException e) {
                ClientUI.popupNotification("Please enter your ID Number");
            } catch (NumberFormatException e1) {
                ClientUI.popupNotification("Please check your ID Number");
            }
            try {
                String email = String.valueOf(customer_email.getText());
                newOrder.email = email;
            } catch (NullPointerException e) {
                ClientUI.popupNotification("Please enter your Email name");
            }

            try {
                String phoneNumber = String.valueOf(customer_phone_number.getText());
                newOrder.phone = phoneNumber;
            } catch (NullPointerException e) {
                ClientUI.popupNotification("Please enter your Email name");
            }
            try {
                int numOfVisitors = Integer.parseInt(numberOfVisitors.getPromptText());
                newOrder.number_of_visitors = numOfVisitors;
            } catch (NumberFormatException exception) {
                ClientUI.popupNotification("ID number was not entered");
            }

            try {
                LocalTime visitTime = timeOfVisit.getValue();
                newOrder.visit_time = visitTime;


            } catch (NullPointerException e) {
                ClientUI.popupNotification("Enter visit time");
            }
            try {
                LocalDate visitDate = dateToVisit.getValue();
                newOrder.visit_date = visitDate;
            } catch (NullPointerException e) {
                ClientUI.popupNotification("Please enter visit Date");
            }

            newOrder.on_waiting_list = waitingList.isPressed();
            newOrder.order_id_pk = UUID.randomUUID();
            newOrder.cancelled = false;
            newOrder.exit_time = newOrder.visit_time.minusHours(4);
            newOrder.park_id_fk = String.valueOf(location_pick.getText());

            ClientHandler.request(new Message("CreateNewOrder",newOrder));
    }

}
