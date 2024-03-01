package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowConnections implements Initializable {
    @FXML
    private TextField connectionIPTxtfield;
    @FXML
    private TextField hostNameTxtfield;
    @FXML
    private TextField statusTxtfield;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void updateStatus(String ip, String hostname, String status) {
        connectionIPTxtfield.setText(ip);
        hostNameTxtfield.setText(hostname);
        statusTxtfield.setText(status);
    }
}
