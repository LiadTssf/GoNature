package gui;

import database.SqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DatabaseConnection implements Initializable {
    @FXML
    private TextField url_txtfield;
    @FXML
    private TextField username_txtfield;
    @FXML
    private TextField password_txtfield;
    @FXML
    private Button connect_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    @FXML
    public void ConnectToDB(ActionEvent actionEvent) {
        SqlConnection.setConnectionFields(url_txtfield.getText(), username_txtfield.getText(), password_txtfield.getText());
        ServerUI.changeScene("ShowConnections");
    }
}
