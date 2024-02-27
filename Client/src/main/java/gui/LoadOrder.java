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

public class LoadOrder implements Initializable {
    @FXML
    private TextField orderNumber;
    @FXML
    private Button loadOrderFromDb;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    @FXML
    public void handleLoadOrderFromDbButton(ActionEvent actionEvent) {
        String orderNum = orderNumber.getText();
        Message msg = new Message("GetOrderByNumber", orderNum);
        ClientHandler.request(msg);
        ClientUI.changeScene("ShowOrderDetails");
    }
}
