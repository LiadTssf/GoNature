package gui;

import data.Order;
import handler.ClientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CancelExportReport implements Initializable {
    @FXML
    private TableView<Order> cancellationTbl;
    private ArrayList<Order> orderList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (ClientHandler.getLastResponse().getCommand().equals("VisitExportReport")){
            orderList = (ArrayList<Order>) ClientHandler.getLastResponse().getParam();
        }

        // Convert the ArrayList to an ObservableList
        ObservableList<Order> observableList = FXCollections.observableArrayList();

        cancellationTbl.setItems(observableList);


    }
}
