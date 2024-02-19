package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Here, you can implement your login logic
        // For demonstration purposes, let's just print the username and password
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

//    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/java/resources/Login.fxml"));
//        Parent root = loader.load();
//
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("/java/resources/Login.css").toExternalForm());
//        primaryStage.setTitle("Academic Managment Tool");
//        primaryStage.setScene(scene);
//
//        primaryStage.show();
//    }


}