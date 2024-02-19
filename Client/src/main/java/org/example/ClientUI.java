package org.example;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 need add all javafx related packetges
 and add the currect logic
 */


public class ClientUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/java/resources/Login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Your JavaFX Application");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main( String args[] ) throws Exception
    {
        launch(args);
    } // end main



}