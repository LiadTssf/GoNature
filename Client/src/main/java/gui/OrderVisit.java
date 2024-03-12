package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderVisit implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void OrderVisit(ActionEvent actionEvent) {
        TextField idField = new TextField();
        idField.setPromptText("ID");

        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");

        Spinner<Integer> visitorCountSpinner = new Spinner<>(1, 100, 1);

        DatePicker datePicker = new DatePicker();

        ChoiceBox<String> timeChoiceBox = new ChoiceBox<>();
        timeChoiceBox.getItems().addAll("Morning", "Afternoon", "Evening");

        // Add these to your scene or layout
        // For example, if you're using a VBox layout:
        VBox layout = new VBox(10);
        layout.getChildren().addAll(idField, fullNameField, visitorCountSpinner, datePicker, timeChoiceBox);
    }

}
