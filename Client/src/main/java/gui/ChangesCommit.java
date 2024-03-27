package gui;

import data.DataParkChange;
import handler.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangesCommit implements Initializable {
    @FXML
    public Label parkName;
    @FXML
    public Label newCapacity;
    @FXML
    public Label newOffSet;
    @FXML
    public Label newTime;
    @FXML
    public Label oldCapacity;
    @FXML
    public Label oldOffSet;
    @FXML
    public Label oldTime;

    private DataParkChange dataParkChange = new DataParkChange();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (ClientHandler.getLastResponse().getParam() instanceof DataParkChange){
            dataParkChange = (DataParkChange) ClientHandler.getLastResponse().getParam();
        }else {
            ClientUI.popupNotification("The Data is not DataParkChanges");
        }

        parkName.setText(dataParkChange.parkName);
        newCapacity.setText(String.valueOf(dataParkChange.newCapacity));
        newOffSet.setText(String.valueOf(dataParkChange.newOffset));
        newTime.setText(String.valueOf(dataParkChange.newStayingTime));
        oldTime.setText(String.valueOf(dataParkChange.oldStayingTime));
        oldOffSet.setText(String.valueOf(dataParkChange.oldOffset));
        oldCapacity.setText(String.valueOf(dataParkChange.oldCapacity));
    }

    @FXML
    public void sendToManager(ActionEvent actionEvent) {
        ClientUI.popupNotification("Your Changes sent to CEO to Approve,\nYou Can Close The Window");
    }
}
