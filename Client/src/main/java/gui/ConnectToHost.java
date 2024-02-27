package gui;

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

    @FXML
    public void ConnectToServer(ActionEvent actionEvent) {
        ClientUI.connect(hostip_txtfield.getText(), 1234);
    }
}
