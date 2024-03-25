package gui;

import data.ConnectionTableData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowConnections implements Initializable {
    private ObservableList<ConnectionTableData> connectionTableData;

    @FXML
    private TableView<ConnectionTableData> clients_table;

    private Thread cancellationThread;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectionTableData = FXCollections.observableArrayList();
        clients_table.setItems(connectionTableData);
        cancellationThread = new Thread(new ThreadToCancel());
        cancellationThread.start();
    }

    /**
     * Adds a new client connection or updates and existing one at the connections table
     * @param connection table client data
     */
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

    /**
     * Returns client id assigned by the server handler by assigned account id
     * Returns -1 if no client id exists for the account
     * Used to verify client is assigned a specific account or to check whether an account
     * is not logged in from another session
     * @param account_id assigned account id of the client looked for
     * @return client id as used by the server handler
     */
    public long getClientFromAccount(int account_id) {
        for(ConnectionTableData connectionData : connectionTableData) {
            if (connectionData.getAccount_id().equals(String.valueOf(account_id))) {
                return Long.parseLong(connectionData.getClient_id());
            }
        }
        return -1;
    }
}
