package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private TextField id_number_txtfield;
    @FXML
    private TextField username_txtfield;
    @FXML
    private TextField password_txtfield;
    @FXML
    private Button login_id_btn;
    @FXML
    private Button login_account_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void login_id(ActionEvent actionEvent) {

    }

    @FXML
    public void login_account(ActionEvent actionEvent) {

    }
}
