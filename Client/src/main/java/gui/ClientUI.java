package gui;

import handler.ClientHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientUI extends Application {
    private static ClientUI instance;
    private static Object currentController;
    private static MainWindow mainWindowController;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        instance = this;
        this.primaryStage = stage;

        mainWindowController = (MainWindow) changeWindow("MainWindow");
    }

    @Override
    public void stop() { ClientHandler.closeClientHandler(); }

    public static void main(String[] args) { launch(); }

    /**
     * Closes current gui window and opens new window with specified scene
     * This method can only be called by methods in the javafx run thread
     * i.e. gui controllers
     * @param fxmlName filename without .fxml extension
     */
    public static Object changeWindow(String fxmlName) {
        try {
            return instance.loadWindow(fxmlName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Object loadWindow(String fxmlName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/window/" + fxmlName + ".fxml"));
        try {
            Scene scene = new Scene(loader.load());
            currentController = loader.getController();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IllegalStateException e) {
            ClientUI.popupNotification("Could not find fxml file: /" + fxmlName + ".fxml");
        }
        return loader.getController();
    }

    public static Object changeScene(String fxmlName) {
        return instance.loadScene(fxmlName);
    }
    private Object loadScene(String fxmlName) {
        try {
            currentController = mainWindowController.loadPane(fxmlName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentController;
    }

    public static void addMainMenuItem(String text, String fxmlName) {
        try {
            mainWindowController.addMenuItem(text, fxmlName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeMainMenuItem(String fxmlName) {
        mainWindowController.removeMenuItem(fxmlName);
    }

    public static Object openNewWindow(String fxmlName) {
        try {
            return instance.loadNewWindow(fxmlName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Object loadNewWindow(String fxmlName) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/window/" + fxmlName + ".fxml"));
        Stage secondaryStage = new Stage();
        Scene scene = new Scene(loader.load());
        secondaryStage.setScene(scene);
        secondaryStage.show();
        return loader.getController();
    }

    public static void popupNotification(String notification) {
        PopupNotification controller = (PopupNotification) openNewWindow("PopupNotification");
        if(controller != null) { controller.update_label(notification); }
    }

    /**
     * Gets current javafx controller instance
     * @return javafx controller instance
     */
    public static Object getCurrentController() {
        return currentController;
    }

    public static Object getMainWindowController() {
        return mainWindowController;
    }
}