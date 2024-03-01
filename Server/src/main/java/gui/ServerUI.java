package gui;

import handler.ServerHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ServerUI extends Application {
    private static ServerUI instance;
    private static Object currentController;
    private Stage stage;

    @Override
    public void start(Stage stage) {
        instance = this;
        this.stage = stage;

        //TODO Temporary location for starting server
        ServerHandler serverHandler = new ServerHandler(1234);
        try {
            serverHandler.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //!Temporary location for starting server

        changeScene("DatabaseConnection");
    }

    @Override
    public void stop() {
        ServerHandler.closeServerHandler();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Closes current gui window and opens new window with specified scene
     * @param fxmlName filename without .fxml extension
     */
    public static void changeScene(String fxmlName) {
        try {
            instance.loadScene(fxmlName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadScene(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlName + ".fxml"));
        Scene scene = new Scene(loader.load());
        currentController = loader.getController();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Gets current javafx controller instance
     * @return javafx controller instance
     */
    public static Object getCurrentController() {
        return currentController;
    }
}