package gui;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
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
import java.util.Set;

public class MainWindow implements Initializable {
    @FXML
    private VBox options_list_vbox;
    @FXML
    private HBox main_hbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadPane("ConnectToHost");
            addMenuItem("Server Connection", "ConnectToHost");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object loadPane (String fxmlName) throws IOException {
        main_hbox.getChildren().removeAll(main_hbox.getChildren());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/" + fxmlName + ".fxml"));
        Parent childScene = loader.load();
        System.out.println("Loading pane");
        main_hbox.getChildren().add(childScene);
        return loader.getController();
    }

    public void addMenuItem (String text, String fxmlName) throws IOException {
        Button newButton = new Button();
        newButton.setText(text);
        newButton.setId(fxmlName + "_btn");
        newButton.setPrefSize(140, 50);
        newButton.setOnAction(event -> {
            ClientUI.changeScene(fxmlName);
        });
        options_list_vbox.getChildren().add(newButton);
    }

    public void removeMenuItem(String fxmlName) {
        ObservableList<Node> childrenList = options_list_vbox.getChildren();
        childrenList.removeIf(e -> e.getId().equals(fxmlName + "_btn"));
    }
}
