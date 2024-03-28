package gui;

import command.Message;
import data.ConnectionTableData;
import data.Park;
import data.ParkChangesView;
import handler.ClientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;





public class OfficeMangerWelcomeWindow implements Initializable {


    private ObservableList<ParkChangesView> parkChangesData;

    @FXML
    private TableView<ParkChangesView> parkChanges;

    @FXML
    private Button refresh;
    @FXML
    private Button confirm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        parkChangesData = FXCollections.observableArrayList();
        parkChanges.setItems(parkChangesData);

    }
    @FXML
    private void getParkChanges() {
        try {
            ClientHandler.request(new Message("GetParkChanges", ""));
            Object param = ClientHandler.getLastResponse().getParam();
            if (param instanceof ArrayList<?>) {
                ArrayList<ParkChangesView> parkChangesList = (ArrayList<ParkChangesView>) param;
                parkChangesData.clear(); // Clear existing data
                parkChangesData.addAll(parkChangesList); // Add new data

            } else {
                System.out.println("Parameter is not an ArrayList");
            }
        } catch (Exception e) {
            ClientUI.popupNotification("cannot get data ");
        }
        parkChanges.refresh();
    }
    @FXML
    public void confirmChanges() {
        // Get the selected row
        ParkChangesView selectedRow = parkChanges.getSelectionModel().getSelectedItem();

        if (selectedRow != null) {
            Park updatedParkDetails = new Park();
            updatedParkDetails.setPark_name(selectedRow.getParkName());
            updatedParkDetails.setCapacity(selectedRow.getNewCapacity());
            updatedParkDetails.setAverage_visit_time(selectedRow.getNewAverageVisitTime());
            updatedParkDetails.setCapacity_offset(selectedRow.getNewCapacityOffset());
            ClientHandler.request(new Message("UpdateParkDetails", updatedParkDetails));

            // Refresh the TableView to reflect the changes
            getParkChanges();
        } else {
            // If no row is selected, display a message
            ClientUI.popupNotification("Please select a row to confirm changes.");
        }
    }

//    @FXML
//    public void confirmChanges(){
//
//        for(ParkChangesView changes : parkChangesData ){
//            Park updatedParkDetails = new Park();
//            updatedParkDetails.setPark_name(changes.getParkName());
//            updatedParkDetails.setCapacity(changes.getNewCapacity());
//            updatedParkDetails.setAverage_visit_time(changes.getNewAverageVisitTime());
//            updatedParkDetails.setCapacity_offset(changes.getNewCapacityOffset());
//            ClientHandler.request(new Message("UpdateParkDetails",updatedParkDetails));
//        }
//        getParkChanges();
//    }
}