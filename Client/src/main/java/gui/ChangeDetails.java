package gui;

import command.Message;
import data.DataParkChange;
import data.WorkerAccount;
import handler.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeDetails implements Initializable {
    @FXML
    public TextField parkName;
    @FXML
    public TextField newOffSet;
    @FXML
    public TextField newStayingTime;
    @FXML
    public TextField newCapacity;

    private WorkerAccount workerAccount;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        workerAccount = (WorkerAccount) ClientHandler.getAccount();
        parkName.setText(workerAccount.park_id_fk);

    }

    @FXML
    public void send(ActionEvent actionEvent) {
        DataParkChange dataParkChange = new DataParkChange();
        dataParkChange.ChangesApply = true;
        try{
            dataParkChange.parkName = workerAccount.park_id_fk;
            dataParkChange.newOffset = Integer.parseInt(newOffSet.getText());
            dataParkChange.newStayingTime = Integer.parseInt(newStayingTime.getText());
            dataParkChange.newCapacity = Integer.parseInt(newCapacity.getText());
        }catch (NumberFormatException e){
            ClientUI.popupNotification("Please Enter Number");
        }

        ClientHandler.request(new Message("GetDataFromPark",dataParkChange));
        if (ClientHandler.getLastResponse().getCommand().equals("capacity_VS_offset")){
            ClientUI.popupNotification("The Offset Number is bigger then capacity");
        }
        if (ClientHandler.getLastResponse().getCommand().equals("IllegalTime")){
            ClientUI.popupNotification("The Time is 24 hours OR above");
        }
        if (ClientHandler.getLastResponse().getCommand().equals("DataAchieved")){
            ClientUI.changeScene("ChangesCommit");
        }

    }
}
