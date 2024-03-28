package gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    @FXML
    private VBox options_list_vbox;
    @FXML
    private Pane main_hbox;
    @FXML
    private ImageView profilePicture;
    @FXML
    private Label name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadPane("ConnectToHost");
            //addMenuItem("Server Connection", "ConnectToHost");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the main window scene that will be displayed
     * All fxml files used in this command should be in resources/gui/ directory
     * @param fxmlName fxml file without .fxml
     * @return controller of the new scene
     * @throws IOException FXML loader failed to load file
     */
    public Object loadPane (String fxmlName) throws IOException {
        main_hbox.getChildren().removeAll(main_hbox.getChildren());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/" + fxmlName + ".fxml"));
        Parent childScene = loader.load();
        main_hbox.getChildren().add(childScene);
        return loader.getController();
    }

    /**
     * Adds a new item on the sidebar to access new scenes from the UI
     * @param text test to appear on the button
     * @param fxmlName fxml filename without .fxml to open when clicking the item
     */
    public void addMenuItem (String text, String fxmlName) {
        Button newButton = new Button();
        newButton.setText(text);
        newButton.setId(fxmlName + "_btn");
        newButton.setPrefSize(140, 30);
        newButton.setStyle("-fx-background-color: #Caffd1; -fx-text-fill: #000; -fx-font-size: 14; -fx-margin: 5 0 5 0;");
        newButton.setOnMouseEntered(event -> newButton.setStyle("-fx-background-color: #Abdab1; -fx-text-fill: #000; -fx-font-size: 14; -fx-margin: 3 0 3 0;")); // Darker color when mouse pointer is over the button
        newButton.setOnMouseExited(event -> newButton.setStyle("-fx-background-color: #Caffd1; -fx-text-fill: #000; -fx-font-size: 14; -fx-margin: 3 0 3 0;")); // Original color when mouse pointer leaves the button
        newButton.setOnAction(event -> {
            ClientUI.changeScene(fxmlName);
        });
        options_list_vbox.getChildren().add(newButton);
    }
    /**
     * Adds a profile image to the sidebar of the UI.
     * If the image is not found or the input is null, a default image is used.
     * All image files used in this command should be in the /Images/ directory.
     * @param image The filename of the image without the extension (.jpg).
     */
    public void addMenuImage(String image) {
        URL imageUrl = null;
        if (image == null) {
            image = "/Images/profile picture.jpg";
        } else {
            // Get the URL of the image resource
            imageUrl = getClass().getResource("/Images/" + image + ".jpg");
            if (imageUrl == null) {
                image = "/Images/profile picture.jpg";
                imageUrl = getClass().getResource(image);
            }
        }
        // Create a new Image object
        javafx.scene.image.Image img = new javafx.scene.image.Image(imageUrl.toExternalForm());

        // Set the image to the ImageView
        profilePicture.setImage(img);
    }
    /**
     * Give text to the username on the sidebar of the UI.
     * If the input name is null, it defaults to "User".
     * The name is displayed in a label with a styled border.
     * @param name The name to be displayed.
     */
    public void addMenuName(String name) {
        // If the name is null, set it to "User"
        if (name == null) {
            name = "User";
        }

        // Set the text of the label
        this.name.setText(name);

        // Apply the style
        this.name.setStyle("-fx-border-color: black; -fx-border-width: 3 3 3 3;");
    }




    /**
     * Removes a specific item from the main window sidebar
     * @param fxmlName fxml filename without .fxml
     */
    public void removeMenuItem(String fxmlName) {
        ObservableList<Node> childrenList = options_list_vbox.getChildren();
        childrenList.removeIf(e -> e.getId().equals(fxmlName + "_btn"));
    }

    /**
     * Completely clears the main window sidebar from all items
     */
    public void removeAllMenuItems() {
        options_list_vbox.getChildren().clear();
    }
}
