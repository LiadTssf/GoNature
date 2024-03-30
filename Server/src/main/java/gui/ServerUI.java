package gui;

import handler.ServerHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerUI extends Application {
    private static ServerUI instance;
    private static Object currentController;
    private Stage stage;
    private ScheduledExecutorService scheduler;
    private ThreadToCancel threadToCancel;
    private ThreadParkFullChecker ThreadParkFullChecker;
    private ThreadSmsReminder threadSmsReminder;

    public static void main(String[] args) { launch(); }

    /**
     * Launches the main server application with the main window
     * @param stage javafx assigned primary stage
     */
    @Override
    public void start(Stage stage) {
        instance = this;
        this.stage = stage;

        changeScene("DatabaseConnection");
//        startBackgroundScheduler();
//        startBackroundReminderThread();
    }

    /**
     * Handles closing the application, closes the server handler
     */
    @Override
    public void stop() throws InterruptedException{
        ThreadParkFullChecker.interrupt();
        threadSmsReminder.interrupt();
        threadToCancel.interrupt();
        ServerHandler.closeServerHandler();
        scheduler.shutdown();
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
        stage.setResizable(false);
        stage.setTitle("GoNature Server");
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

    private void startBackgroundScheduler() {
        /**
         * run cancelation thread every hour
         * run ParkFullChecker thread every 1 minute
         */
        threadToCancel = new ThreadToCancel();
        ThreadParkFullChecker  = new ThreadParkFullChecker();

        ScheduledExecutorService cancelScheduler = Executors.newSingleThreadScheduledExecutor();
        cancelScheduler.scheduleAtFixedRate(threadToCancel, 0, 1, TimeUnit.HOURS); // Run every hour

        ScheduledExecutorService parkCheckerScheduler = Executors.newSingleThreadScheduledExecutor();
        parkCheckerScheduler.scheduleAtFixedRate(ThreadParkFullChecker, 0, 1, TimeUnit.MINUTES); // Run every 1 minute

    }

    private void startBackroundReminderThread(){
        //will run once a day
//        ThreadSmsReminder threadSmsReminder = new ThreadSmsReminder();
//        scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.scheduleAtFixedRate(threadSmsReminder, 0, 2, TimeUnit.SECONDS); // Run every hour
    }

}