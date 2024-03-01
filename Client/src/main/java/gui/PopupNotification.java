package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PopupNotification implements Initializable {
    @FXML
    private Button close_btn;
    @FXML
    private Label notif_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void close_btn(ActionEvent actionEvent) {
        Stage currentStage = (Stage) close_btn.getScene().getWindow();
        currentStage.close();
    }

    public void update_label(String label) {
        notif_lbl.setText(label);
    }
}
