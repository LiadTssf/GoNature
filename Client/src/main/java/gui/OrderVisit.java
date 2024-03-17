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
    @FXML
    private RadioButton waitingList;
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
    @FXML
    private TextField final_cost;

    private float priceCalculation(int numberOfVisitors,double discount){
        return 0;
    }

    /* The initialize function sets the ID field with the ID from Login
    * the diffrence between Client and tour guide is the number of visitors in the group
    * the Spinner local method it's for set time (the steps are by half hour)*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customer_id.setText(String.valueOf(ClientHandler.getAccount().account_id_pk));
        if (ClientHandler.getAccount().account_type.equals("TourGuide")){
            numberOfVisitors.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,15));
        }else {
            numberOfVisitors.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5));
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
        if(ClientHandler.getAccount().account_type.equals("TourGuide")) {
            newOrder.guided_order = true;
        }else{
            newOrder.guided_order = false;
        }

            try {
                String email = String.valueOf(customer_email.getText());
                newOrder.email = email;
            } catch (NullPointerException e) {
                ClientUI.popupNotification("Please enter your Email");
            }

            try {
                String phoneNumber = String.valueOf(customer_phone_number.getText());
                newOrder.phone = phoneNumber;
            } catch (NullPointerException e) {
                ClientUI.popupNotification("Please enter your Phone Number");
            }
            int numOfVisitors = Integer.parseInt(String.valueOf(numberOfVisitors.getValue()));
            newOrder.number_of_visitors = numOfVisitors;

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
            newOrder.exit_time = newOrder.visit_time.plusHours(4);
            newOrder.park_id_fk = String.valueOf(location_pick.getText());

            ClientHandler.request(new Message("CreateNewOrder",newOrder));
    }

}
