package gui;

import command.Message;
import handler.ClientHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientUI extends Application {
    private static ClientUI instance;
    private static Object currentController;
    private Stage stage;

    @Override
    public void start(Stage stage) {
        instance = this;
        this.stage = stage;

        changeScene("ConnectToHost");
    }

    public static void main(String[] args) { launch(); }

    /**
     * Closes current gui window and opens new window with specified scene
     * This method can only be called by methods in the javafx run thread
     * i.e. gui controllers
     * @param fxmlName filename without .fxml extension
     */
    public static void changeScene(String fxmlName) {
        try { instance.loadScene(fxmlName); } catch (IOException e) { e.printStackTrace(); }
    }

    private void loadScene(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlName + ".fxml"));
        Scene scene = new Scene(loader.load());
        currentController = loader.getController();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Starts and connects ClientHandler
     * @param ip host ip to connect to
     * @param port port to connect to
     */
    public static void connect(String ip, int port) { ClientHandler clientHandler = new ClientHandler(ip, port); }

    /**
     * Gets current javafx controller instance
     * @return javafx controller instance
     */
    public static Object getCurrentController() { return currentController; }
}