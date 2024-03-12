package gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    @FXML
    private VBox options_list_vbox;
    @FXML
    private HBox main_hbox;

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
        newButton.setPrefSize(140, 50);
        newButton.setOnAction(event -> {
            ClientUI.changeScene(fxmlName);
        });
        options_list_vbox.getChildren().add(newButton);
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
