package gui;

import command.Message;
import data.ReportRequest;
import data.WorkerAccount;
import handler.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ImportReports implements Initializable {
    @FXML
    public RadioButton capacityReport;
    @FXML
    public DatePicker importFromDate;
    @FXML
    public RadioButton ToDate;
    @FXML
    public Label importToDateLabal;
    @FXML
    public DatePicker importToDate;

    private boolean isCapacity;
    private boolean isToDate;

    private WorkerAccount workerAccount;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        workerAccount = (WorkerAccount) ClientHandler.getAccount();
        capacityReport.setOnAction(event-> {
            if (capacityReport.isSelected()){
                isCapacity = true;
            }else{
                isCapacity = false;
            }
        });

        ToDate.setOnAction(event-> {
            if (ToDate.isSelected()){
                importToDateLabal.setDisable(false);
                importToDate.setDisable(false);
                isToDate = true;
            }else {
                importToDateLabal.setDisable(true);
                importToDate.setDisable(true);
                isToDate = false;
            }
        });
    }

    @FXML
    public void ImportReport(ActionEvent actionEvent) {
        ReportRequest reportRequest = new ReportRequest();
        reportRequest.areaOfReport = workerAccount.park_id_fk;
        reportRequest.capacityReport = isCapacity;
        try {
            reportRequest.dates.add(importFromDate.getValue());
            if (isToDate){
                reportRequest.dates.add(importToDate.getValue());
            }else{
                reportRequest.dates.add(LocalDate.now());
            }
        }catch (DateTimeException e){
            ClientUI.popupNotification("Please choose dates");
        }

        ClientHandler.request(new Message("ImportReportRequest",reportRequest));

        if (ClientHandler.getLastResponse().getCommand().equals("Error")){
            ClientUI.popupNotification("Fuckkkkkkkkkkk");
        }
        if (ClientHandler.getLastResponse().getCommand().equals("FileSucceed")){
            ClientUI.popupNotification("Report created successfully\n and sent to Department Manger");
        }
    }
}
