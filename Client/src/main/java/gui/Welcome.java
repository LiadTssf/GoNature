package gui;

import command.Message;
import data.Order;
import handler.ClientHandler;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Welcome implements Initializable {
    @FXML
    private ImageView photoView;
    @FXML
    private Button leftArrow, rightArrow;

    private int accountId;
    private int currentIndex = 0;
    private Image[] images =new Image[] {
            new Image(getClass().getResourceAsStream("/Images/desert1.jpg")),
            new Image(getClass().getResourceAsStream("/Images/river3.jpg")),
            new Image(getClass().getResourceAsStream("/Images/river4.jpg")),
            new Image(getClass().getResourceAsStream("/Images/river5.jpg")),
            new Image(getClass().getResourceAsStream("/Images/mountain1.jpg"))
    };

    private Timeline slideshow;
    private Duration slideDuration = Duration.seconds(10); // Set the duration for each slide
    private Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnchorPane anchorPane = (AnchorPane) photoView.getParent(); // Assuming photoView is a child of the AnchorPane
        anchorPane.getStylesheets().add(getClass().getResource("Welcome.css").toExternalForm());
        // Set the fitWidth and fitHeight properties to maintain a consistent size
        photoView.setPreserveRatio(false); // Disable preserving the aspect ratio
        photoView.setFitWidth(600); // Set the width to match the window width
        photoView.setFitHeight(400); // Set the height to match the window height
        // Initialize the ImageView with the first image
        photoView.setImage(images[currentIndex]);
        // Print the paths of the images being loaded
        // Set up the button actions
        leftArrow.setOnAction(event -> showPreviousImage());
        rightArrow.setOnAction(event -> showNextImage());
        accountId = ClientHandler.getAccount().account_id_pk; // current account id
        // Set up the slideshow
        slideshow = new Timeline(
                new KeyFrame(slideDuration, event -> showNextImage())
        );
        slideshow.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        slideshow.play();
        CheckReminder();


    }

    @FXML
    private void showPreviousImage() {
        if (currentIndex > 0) {
            currentIndex--;
        } else {
            currentIndex = images.length - 1; // Loop back to the last image
        }
        photoView.setImage(images[currentIndex]);
    }

    @FXML
    private void showNextImage() {
        if (currentIndex < images.length - 1) {
            currentIndex++;
        } else {
            currentIndex = 0; // Loop back to the first image
        }
        photoView.setImage(images[currentIndex]);
    }

    private void CheckReminder(){
        ClientHandler.request(new Message("smsReminder",accountId));
        System.out.println(ClientHandler.getLastResponse().getParam());
        Object param = ClientHandler.getLastResponse().getParam();
        ArrayList<?> list = (ArrayList<?>) param;
        if(!(((ArrayList<?>) param).isEmpty())) {
            System.out.println("print client param" + ClientHandler.getLastResponse().getParam() );
            ClientUI.smsReminderPopUpNotification("You have visits next 24 hours from now, would you like to approve them?");
        }
    }
}



