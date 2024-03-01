package gui;

import command.Message;
import handler.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectToHost implements Initializable {

    @FXML
    private TextField hostip_txtfield;
    @FXML
    private Button connect_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Called when connection button is clicked, attempts to send a connection message to the server
     * If the connection is successful changes the main window scene to login and adds login sidebar item
     * If the connection fails, error handling is done by the client handler
     * @param actionEvent javafx actionEvent
     */
    @FXML
    public void ConnectToServer(ActionEvent actionEvent) {
        new ClientHandler(hostip_txtfield.getText(), 1234);
        String response = (String) ClientHandler.request(new Message("ConnectClient"));
        if(response != null && response.equals("Connection successful")) {
            ClientUI.changeScene("Login");
            ClientUI.removeMainMenuItem("ConnectToHost");
            ClientUI.addMainMenuItem("Login", "Login");
        }
    }
}