package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplyChange implements Initializable {

    @FXML
    public TableView<Integer> changeApply;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void ConfirmChange(ActionEvent actionEvent) {
    }

    public void discardChange(ActionEvent actionEvent) {
    }
}
