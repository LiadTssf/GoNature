package gui;

import data.ConnectionTableData;
import database.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void installGoNature(ActionEvent actionEvent) {
        try {
            //serverHandler.listen();

            // Use the existing connection to execute the SQL file
            Connection connection = SqlConnection.getConnection();
            Statement statement = connection.createStatement();

            // Specify the path to the SQL file
            String sqlFilePath = "InstallGoNature.sql";


            // Read the SQL file
            StringBuilder sqlStatements = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(sqlFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sqlStatements.append(line).append("\n"); // Append newline character to ensure complete lines are read
                }
            } catch (IOException e) {
                System.out.println("Change File.SQL PATH!!!!!!");
            }

            // Execute the SQL statements in the file
            String[] sqlQueries = sqlStatements.toString().split(";");
            for (String query : sqlQueries) {
                // Skip empty queries
                if (!query.trim().isEmpty()) {
                    System.out.println("Query: " + query); // Print each query before executing
                    statement.addBatch(query);
                }
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Print message to server console
        System.out.println("Installation successful!");
    }
}
