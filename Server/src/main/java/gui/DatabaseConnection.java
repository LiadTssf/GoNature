package gui;

import database.SqlConnection;
import handler.ServerHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DatabaseConnection implements Initializable {
    public static boolean isConnected = false;
    public static final Object lock = new Object();

    @FXML
    private TextField url_txtfield;
    @FXML
    private TextField username_txtfield;
    @FXML
    private TextField password_txtfield;
    @FXML
    private Button connect_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Called when connect button is pressed. Connects to database, starts the server
     * and changes to the main server connections scene
     * @param actionEvent javafx actionEvent
     */
    @FXML
    public void ConnectToDB(ActionEvent actionEvent) {
        SqlConnection.setConnectionFields(url_txtfield.getText(), username_txtfield.getText(), password_txtfield.getText());
        ServerHandler serverHandler = new ServerHandler(1234);
        try {
            serverHandler.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized(DatabaseConnection.lock) {
            isConnected = true;
            DatabaseConnection.lock.notifyAll();
        }
        ServerUI.changeScene("ShowConnections");
    }

}
