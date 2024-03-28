package gui;

import command.Message;
import data.FilesData;
import handler.ClientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GetReportFiles implements Initializable {

    @FXML
    private TableView<FilesData> fileTable;

    private ObservableList<FilesData> filesData;
    private String fileName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filesData = FXCollections.observableArrayList();
        fileTable.setItems(filesData);

    }

    @FXML
    public void refresh(ActionEvent actionEvent) {
        try {
            ClientHandler.request(new Message("GetFilesFromDB"));
            Object param = ClientHandler.getLastResponse().getParam();
            if (!(param instanceof ArrayList)){
                ClientUI.popupNotification("No Files found");
            }
            ArrayList<FilesData> arrayListFiles = (ArrayList<FilesData>) param;
            filesData.clear();
            filesData.addAll(arrayListFiles);
        } catch (Exception e) {
            ClientUI.popupNotification("cannot get data ");
        }
        fileTable.refresh();
    }

    public void download(ActionEvent actionEvent) {
        fileName = fileTable.getSelectionModel().getSelectedItem().getFileName();
        if (fileName != null){
            ClientHandler.request(new Message("DownloadFileFromDB",fileName));
        }
        if (ClientHandler.getLastResponse().getCommand().equals("DownloadFailed")){
            ClientUI.popupNotification("Download failed, please Call group 15 IT team");
        }
        if (ClientHandler.getLastResponse().getCommand().equals("FileExist")){
            ClientUI.popupNotification("You already downloaded this file Check in your folder");
        }
        if (ClientHandler.getLastResponse().getCommand().equals("FileDownloaded")){
            ClientUI.popupNotification("Download completed successfully");
            refresh(actionEvent);
        }
    }
}
