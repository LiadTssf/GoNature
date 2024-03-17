package gui;

import command.Message;
import handler.ClientHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExportCancellationReport implements Initializable {

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
    private boolean isPressed = false;
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

        day_picker1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,31));
        month_picker1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,12));
        year_picker1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2018,2025));
    }

    @FXML
    public void ExportCancellationReport(javafx.event.ActionEvent actionEvent){
        ArrayList<LocalDate> localDateArrayList = new ArrayList<>();
        LocalDate report_to_export_date = null;
        LocalDate to_report_to_export_date = null;
        try{
            int month = month_picker.getValue();
            int year = year_picker.getValue();
            int day = day_picker.getValue();
            report_to_export_date = LocalDate.of(year,month,day);

            localDateArrayList.add(report_to_export_date);

            if (isPressed){
                int month1 = month_picker.getValue();
                int year1 = year_picker.getValue();
                int day1 = day_picker.getValue();

                to_report_to_export_date = LocalDate.of(year1,month1,day1);

                localDateArrayList.add(to_report_to_export_date);
            }
        }catch (DateTimeException e){
            ClientUI.popupNotification("Wrong data input");
        }

        ClientHandler.request(new Message("ExportReport",localDateArrayList));
    }

}
