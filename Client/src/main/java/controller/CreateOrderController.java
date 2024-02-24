package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.ClientController;
import main.ClientUI;
public class CreateOrderController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField amountOfVisitorsField;

    @FXML
    private Button createOrderButton;


    @FXML
    void handleCreateOrderButton(ActionEvent event) {
        String email = emailField.getText();
        String amountOfVisitors = amountOfVisitorsField.getText();
        ClientUI.chat.accept( "INSERT"+","+email+","+amountOfVisitors);
    }

    public void start(Stage primaryStage) throws Exception {



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateOrder.fxml"));

        Scene scene = new Scene(loader.load());
//scene.getStylesheets().add(getClass().getResource("/java/resources/Login.css").toExternalForm());
        primaryStage.setTitle("Order ");
        primaryStage.setScene(scene);

        primaryStage.show();





    }
}
