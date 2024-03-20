package gui;

import command.Message;
import data.RegisteredAccount;
import handler.ClientHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Register implements Initializable {
    @FXML
    private CheckBox tour_guide_check_box;
    @FXML
    private TextField full_name_register;
    @FXML
    private TextField email_regitser;
    @FXML
    private PasswordField userPassword;
    @FXML
    private TextField phone_register;
    @FXML
    private TextField user_name_register;
    @FXML
    private TextField id_register;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (ClientHandler.getAccount().account_type.equals("OfficeWorker")){
            id_register.setDisable(false);
            tour_guide_check_box.setSelected(true);
        }else{
            id_register.setText(String.valueOf(ClientHandler.getAccount().account_id_pk));
        }
    }

    @FXML
    public void Register(javafx.event.ActionEvent actionEvent){
        RegisteredAccount rg = new RegisteredAccount(0,"");
        try{
            rg.account_id_pk = Integer.parseInt(id_register.getText());
        }catch (NumberFormatException exception) {
            ClientUI.popupNotification("ID number was not entered");
        }

        if (tour_guide_check_box.isSelected()){
            rg.account_type = "TourGuide";
        }else {
            rg.account_type = "Client";
        }

        try{
            rg.username = user_name_register.getText();
        }catch (NullPointerException e){
            ClientUI.popupNotification("Please choose UserName");
        }

        try{
            rg.password = userPassword.getText();
        }catch (NullPointerException e){
            ClientUI.popupNotification("Please choose password");
        }

        try{
            rg.email = email_regitser.getText();
        }catch (NullPointerException e){
            ClientUI.popupNotification("Please choose password");
        }

        try{
            if (phone_register.getLength() == 10) {
                rg.phone = phone_register.getText();
            }
        }catch (NullPointerException e){
            ClientUI.popupNotification("Please enter your phone");
        }

        ClientHandler.request(new Message("RegisterUser",rg));

        if (ClientHandler.getLastResponse().getCommand().equals("UserNameInvalid")){
            ClientUI.popupNotification("");
        }

    }
}
