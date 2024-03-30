package gui;

import data.Order;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MSGwindow implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



}
