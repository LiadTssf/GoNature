package gui;

import command.Message;
import handler.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class Login implements Initializable {
    @FXML
    private TextField id_number_txtfield;
    @FXML
    private TextField username_txtfield;
    @FXML
    private PasswordField password_txtfield;
    @FXML
    private Button login_id_btn;
    @FXML
    private Button login_account_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Make id_number_txtfield numerical only using a text formatter and a regex match
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if(text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> digitsOnlyTextFormatter = new TextFormatter<>(filter);
        id_number_txtfield.setTextFormatter(digitsOnlyTextFormatter);
    }

    /**
     * Called when identify with id button is clicked. If the account id in the text field
     * is numerical, sends a message to the server attempting to login.
     * If the login is denied by the server, popup a notification to the user
     * If the login is successful change main window scene to Welcome scene and change
     * sidebar items appropriate for an unregistered account
     * @param actionEvent javafx actionEvent
     */
    @FXML
    public void login_id(ActionEvent actionEvent) {
        //Send request
        try {
            int account_id = Integer.parseInt(id_number_txtfield.getText());
            ClientHandler.request(new Message("LoginWithID", account_id));
        } catch (NumberFormatException exception) {
            ClientUI.popupNotification("ID number was not entered");
        }

        //Handle response
        if(ClientHandler.getLastResponse().getCommand().equals("LoginDenied")) {
            //Denied response
            ClientUI.popupNotification((String) ClientHandler.getLastResponse().getParam());
        }
        if(ClientHandler.getLastResponse().getCommand().equals("AuthenticateClient")) {
            //Accepted response
            ClientHandler.request(new Message("ConnectClient"));

            ClientUI.changeScene("Welcome");
            ClientUI.removeAllMainMenuItems();
            ClientUI.addMainMenuItem("Welcome", "Welcome");
            ClientUI.addMainMenuItem("Order Visit", "OrderVisit");
            ClientUI.addMainMenuItem("Orders List", "OrderList");
            ClientUI.addMainMenuItem("Register Account", "Register");
            ClientUI.addMainMenuItem("Logout", "Logout");
        }
    }

    /**
     * Called when login with username and password is clicked.
     * Checks if the username and password text fields have been filled
     * and sends a message to the server attempting to login.
     * If the login is denied by the server, popup a notification to the user
     * If the login is successful change main window scene to Welcome scene and change
     * sidebar items appropriate for the received account type
     * @param actionEvent javafx actionEvent
     */
    @FXML
    public void login_account(ActionEvent actionEvent) {

    }
}
