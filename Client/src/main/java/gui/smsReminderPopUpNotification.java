package gui;


import command.Message;
import handler.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class smsReminderPopUpNotification implements Initializable {
    @FXML
    private Button close_btn;

    @FXML
    private Button accept;
    @FXML
    private Label notif_lbl;
    private int accountId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Called when notification is closed
     * Closes the notification window
     * @param actionEvent javafx actionEvent
     */
    @FXML
    public void close_btn(ActionEvent actionEvent) {
        Stage currentStage = (Stage) close_btn.getScene().getWindow();
        currentStage.close();
    }
    @FXML
    public void acceptBtn(ActionEvent actionEvent) {
        Stage currentStage = (Stage) close_btn.getScene().getWindow();
        currentStage.close();
        accountId = ClientHandler.getAccount().account_id_pk;
        System.out.println("order accepted");
        ClientHandler.request(new Message("ApproveOrder",accountId));
    }

    /**
     * Updates notification text
     * @param label new text to apply
     */
    public void update_label(String label) {
        notif_lbl.setText(label);
    }
}
