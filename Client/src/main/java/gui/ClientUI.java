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
//     private static OfficeMangerWelcomeWindow mainWindowController;


    public static void main(String[] args) { launch(); }

    /**
     * Launches the main client application with the main window
     * @param stage javafx assigned primary stage
     */
    @Override
    public void start(Stage stage) {
        instance = this;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/window/mainWindow.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            currentController = loader.getController();
            stage.setResizable(false);
            stage.setTitle("GoNature Client");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainWindowController = loader.getController();

    }

    /**
     * Handles closing the application, sends a disconnect message to the server
     * and closes the connection before exiting the application
     */
    @Override
    public void stop() { ClientHandler.closeClientHandler(); }

    /**
     * Changes the main window scene that will be displayed
     * All fxml files used in this command should be in resources/gui/ directory
     * @param fxmlName fxml filename without .fxml
     */
    public static void changeScene(String fxmlName) {
        try {
            currentController = mainWindowController.loadPane(fxmlName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new item on the sidebar to access new scenes from the UI
     * @param text text to appear on the button
     * @param fxmlName fxml filename without .fxml to open when clicking the item
     */
    public static void addMainMenuItem(String text, String fxmlName) {
        mainWindowController.addMenuItem(text, fxmlName);
    }
    /**
     * adds an image on the sidebar a profile picture based on the connected type
     * @param Image "image of the user" that connected
     */
    public static void addMainMenuImage(String Image) {
        mainWindowController.addMenuImage(Image);
    }
    /**
     * adds a name on the sidebar the name of the connected person
     * @param name name of the user that connected
     */
    public static void addMainMenuName(String name) {
        mainWindowController.addMenuName(name);
    }


    /**
     * Removes a specific item from the main window sidebar
     * @param fxmlName fxml filename without .fxml
     */
    public static void removeMainMenuItem(String fxmlName) {
        mainWindowController.removeMenuItem(fxmlName);
    }

    /**
     * Completely clears the main window sidebar from all items
     */
    public static void removeAllMainMenuItems() {
        mainWindowController.removeAllMenuItems();
    }

    /**
     * Opens a completely new window with the passed fxml file
     * @param fxmlName fxml filename without .fxml
     * @return controller object of the newly opened window
     */
    public static Object openNewWindow(String fxmlName) { return instance.loadNewWindow(fxmlName); }
    private Object loadNewWindow(String fxmlName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/window/" + fxmlName + ".fxml"));
        Stage secondaryStage = new Stage();
        try {
            Scene scene = new Scene(loader.load());
            secondaryStage.setScene(scene);
            secondaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader.getController();
    }

    /**
     * Opens a pop-up notification with the specified text string
     * The notification only has a text field and a button to close it
     * @param notification text string to display on the notification
     */
    public static void popupNotification(String notification) {
        PopupNotification controller = (PopupNotification) openNewWindow("PopupNotification");
        if(controller != null) { controller.update_label(notification); }
    }

    public static void smsReminderPopUpNotification(String notificationData){
        smsReminderPopUpNotification controller = (smsReminderPopUpNotification ) openNewWindow("smsReminderPopUpNotification");
        if(controller != null) { controller.update_label(notificationData); }
    }

    /**
     * Gets current javafx controller instance from the sub-scene displayed in the main window
     * @return javafx controller instance
     */
    public static Object getCurrentController() {
        return currentController;
    }

    /**
     * Gets current javafx controller instance from the main window
     * @return mainWindow controller instance
     */
    public static Object getMainWindowController() {
        return mainWindowController;
    }
}