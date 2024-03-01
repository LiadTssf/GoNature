package gui;

import data.ConnectionTableData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowConnections implements Initializable {

    @FXML
    private TableView<ConnectionTableData> clients_table;

    private ObservableList<ConnectionTableData> connectionTableData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Create new observable array list
        connectionTableData = FXCollections.observableArrayList();

        //Setup columns
        TableColumn<ConnectionTableData, String> clientIDCol = new TableColumn<>("Client ID");
        TableColumn<ConnectionTableData, String> ipCol = new TableColumn<>("IP");
        TableColumn<ConnectionTableData, String> hostCol = new TableColumn<>("Host");
        TableColumn<ConnectionTableData, String> accountIDCol = new TableColumn<>("Account ID");
        TableColumn<ConnectionTableData, String> lastRequestCol = new TableColumn<>("Last Request");
        TableColumn<ConnectionTableData, String> statusCol = new TableColumn<>("Status");
        clientIDCol.setCellValueFactory(new PropertyValueFactory<>("client_id"));
        ipCol.setCellValueFactory(new PropertyValueFactory<>("ip"));
        hostCol.setCellValueFactory(new PropertyValueFactory<>("host"));
        accountIDCol.setCellValueFactory(new PropertyValueFactory<>("account_id"));
        lastRequestCol.setCellValueFactory(new PropertyValueFactory<>("lastRequest"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        clients_table.setItems(connectionTableData);

        //Insert columns to table
        clients_table.getColumns().add(clientIDCol);
        clients_table.getColumns().add(ipCol);
        clients_table.getColumns().add(hostCol);
        clients_table.getColumns().add(accountIDCol);
        clients_table.getColumns().add(lastRequestCol);
        clients_table.getColumns().add(statusCol);
    }

    public void addOrUpdateConnection(ConnectionTableData connection) {
        boolean exist = false;
        for(ConnectionTableData connectionData : connectionTableData) {
            if(connectionData.getClient_id().equals(connection.getClient_id())) {
                //Update existing client connection
                connectionData.setAccount_id(connection.getAccount_id());
                connectionData.setIp(connection.getIp());
                connectionData.setHost(connection.getHost());
                connectionData.setLastRequest(connection.getLastRequest());
                connectionData.setStatus(connection.getStatus());
                exist = true;
            }
        }
        if(!exist) {
            //Add new client connection
            connectionTableData.add(connection);
        }
        clients_table.refresh();
    }
}
