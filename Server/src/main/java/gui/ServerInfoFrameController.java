package gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Main.ServerHandler;

public class ServerInfoFrameController {
    
    @FXML
    private Button btnExit = null;

    @FXML
    private Label IP;

    @FXML
    private Label Host;

    @FXML
    private Label ConnectionStatus;

    @FXML
    private Label txtIP;

    @FXML
    private Label txtHost;

    @FXML
    private Label txtConnectionStatus;
    
    @FXML
    private void initialize() {
        // Initialize UI components and set their properties
        txtIP.setText(ServerHandler.ip);
        txtHost.setText(ServerHandler.host);
        txtConnectionStatus.setText(ServerHandler.connectionStatus);
    }
    
    // Thread to continuously monitor and update connection status
    private Thread connectionStatusThread;

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ServerInfoFrame.fxml"));
        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("/ServerInfoFrame.css").toExternalForm());
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        
        // Start the thread to continuously monitor and update connection status
        startConnectionStatusThread();

        primaryStage.show();        
    }

    // Method to start the thread for monitoring connection status
    private void startConnectionStatusThread() {
        connectionStatusThread = new Thread(() -> {
            while (true) {
                try {
                    // Sleep for 1 second
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Update connection status label
                updateConnectionStatus();
            }
        });
        connectionStatusThread.setDaemon(true); // Set the thread as daemon so it will terminate with the application
        connectionStatusThread.start();
    }

    // Method to update connection status label
    private void updateConnectionStatus() {
        // Run the update on the JavaFX Application Thread
        // to avoid "Not on FX application thread" exception
        javafx.application.Platform.runLater(() -> {
            ConnectionStatus.setText(ServerHandler.connectionStatus);
        });
    }
    
    public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("exit Academic Tool");
        System.exit(0);         
    }

}
