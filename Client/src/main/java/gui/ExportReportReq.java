package gui;

import command.Message;
import data.ReportRequest;
import handler.ClientHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ExportReportReq implements Initializable {

    @FXML
    private Spinner<Integer> day_picker;
    @FXML
    private Spinner<Integer> day_picker1;
    @FXML
    private Spinner<Integer> month_picker;
    @FXML
    private Spinner<Integer> month_picker1;
    @FXML
    private Spinner<Integer> year_picker;
    @FXML
    private Spinner<Integer> year_picker1;
    @FXML
    private RadioButton To_Date;
    @FXML
    private SplitMenuButton AreaManu;
    @FXML
    private MenuItem haifa;
    @FXML
    private MenuItem telAviv;
    @FXML
    private MenuItem karmiel;
    @FXML
    private RadioButton isCancellation;
    private boolean isPressed = false;
    private boolean cancelReport = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        day_picker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,31));
        month_picker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,12));
        year_picker.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2018,2025));

        To_Date.setOnAction(event -> {
            if (To_Date.isSelected()) {
                // Enable editing for day_picker1
                day_picker1.setDisable(false);
                month_picker1.setDisable(false);
                year_picker1.setDisable(false);
                isPressed = true;
            } else {
                // Disable editing for day_picker1
                day_picker1.setDisable(true);
                month_picker1.setDisable(true);
                year_picker1.setDisable(true);
                isPressed = false;
            }
        });

        isCancellation.setOnAction(event -> {
            if (isCancellation.isSelected()){
                cancelReport = true;
            }else{
                cancelReport = false;
            }
        });

        day_picker1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,31));
        month_picker1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,12));
        year_picker1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2018,2025));

        karmiel.setOnAction(event -> AreaManu.setText(karmiel.getText()));
        haifa.setOnAction(event -> AreaManu.setText(haifa.getText()));
        telAviv.setOnAction(event -> AreaManu.setText(telAviv.getText()));
    }

    @FXML
    public void ExportReportReq(javafx.event.ActionEvent actionEvent){ // get all data from user after pressing and creates new Request to report
        ReportRequest rp = new ReportRequest();
        LocalDate report_to_export_date;
        LocalDate to_report_to_export_date;

        try{
            int month = month_picker.getValue();
            int year = year_picker.getValue();
            int day = day_picker.getValue();
            report_to_export_date = LocalDate.of(year,month,day);

            rp.dates.add(report_to_export_date);

            rp.areaOfReport = String.valueOf(AreaManu.getText());

            rp.cancelled = cancelReport;

            if (isPressed){
                int month1 = month_picker.getValue();
                int year1 = year_picker.getValue();
                int day1 = day_picker.getValue();

                to_report_to_export_date = LocalDate.of(year1,month1,day1);

                rp.dates.add(to_report_to_export_date);
            }
        }catch (DateTimeException e){
            ClientUI.popupNotification("Wrong data input");
        }

        ClientHandler.request(new Message("ExportReportRequest",rp));

        if (ClientHandler.getLastResponse().getCommand().equals("VisitExportReport")){
            ClientUI.openNewWindow("ExReport");
        }
        if (ClientHandler.getLastResponse().getCommand().equals("ErrorInDates")){
            ClientUI.popupNotification("please select second date that is after the first");
        }
        if (ClientHandler.getLastResponse().getCommand().equals("CancellationReport")){
            ClientUI.openNewWindow("CancelExportReport");
        }
    }

}
