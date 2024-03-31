package gui;

import command.Message;
import data.Order;
import handler.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Welcome implements Initializable {
    @FXML
    private ImageView photoView;
    @FXML
    private Button leftArrow, rightArrow;

    private int accountId;
    private int currentIndex = 0;
    private Image[] images =new Image[] {
            new Image(getClass().getResourceAsStream("/images/desert1.jpg")),
            new Image(getClass().getResourceAsStream("/images/river3.jpg")),
            new Image(getClass().getResourceAsStream("/images/river4.jpg")),
            new Image(getClass().getResourceAsStream("/images/river5.jpg")),
            new Image(getClass().getResourceAsStream("/images/mountain1.jpg"))
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the fitWidth and fitHeight properties to maintain a consistent size
        photoView.setPreserveRatio(false); // Disable preserving the aspect ratio
        photoView.setFitWidth(600); // Set the width to match the window width
        photoView.setFitHeight(400); // Set the height to match the window height
        // Initialize the ImageView with the first image
        photoView.setImage(images[currentIndex]);

        // Set up the button actions
        leftArrow.setOnAction(event -> showPreviousImage());
        rightArrow.setOnAction(event -> showNextImage());
        accountId = ClientHandler.getAccount().account_id_pk; // current account id
        //CheckReminder();

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
        if(ClientHandler.getLastResponse().getParam() != null) {
            System.out.println("print client param" + ClientHandler.getLastResponse().getParam() );
            ClientUI.smsReminderPopUpNotification("new Reminder to approve your order for tomorrow");
        }
    }


}
