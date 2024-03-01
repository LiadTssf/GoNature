package gui;

import command.Message;
import handler.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class Logout implements Initializable {
    @FXML
    private Button yes_btn;
    @FXML
    private Button no_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Called when logout yes button is clicked
     * Logs the account out and sends a message to the server that the client has logged out of the account
     * Changes main window scene back to the login page and sets the sidebar items appropriately
     * @param actionEvent javafx actionEvent
     */
    @FXML
    public void LogoutAction(ActionEvent actionEvent) {
        ClientHandler.setAccount(null);
        ClientHandler.request(new Message("LogoutAccount"));
        ClientUI.changeScene("Login");
        ClientUI.removeAllMainMenuItems();
        ClientUI.addMainMenuItem("Login", "Login");
    }

    /**
     * Called when logout no button is clicked
     * Returns the user to the main window welcome scene
     * @param actionEvent javafx actionEvent
     */
    @FXML
    public void ReturnToWelcome(ActionEvent actionEvent) {
        ClientUI.changeScene("Welcome");
    }
}
