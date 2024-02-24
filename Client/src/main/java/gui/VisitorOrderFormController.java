package gui;

import main.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Order;


public class VisitorOrderFormController {
	private Order o;
	
	@FXML
    private Label lblOrderNumber;
    @FXML
    private Label lblTimeOfVisit;
    @FXML
    private Label lblNumberOfVisitors;
    @FXML
    private Label lblTelephoneNumber;
    @FXML
    private Label lblEmail;

    @FXML
    private TextField txtOrderNumber;
    @FXML
    private TextField txtTimeOfVisit;
    @FXML
    private TextField txtNumberOfVisitors;
    @FXML
    private TextField txtTelephoneNumber;
    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnClose;

    

    @FXML
	public void getCloseBtn(ActionEvent event) throws Exception {
		
		((Node)event.getSource()).getScene().getWindow().hide();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/SearchOrder.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/SearchOrder.css").toExternalForm());
		primaryStage.setTitle("Search Order");
		primaryStage.setScene(scene);		
		primaryStage.show();		

	}
    
    @FXML
    public void getSaveBtn(ActionEvent event) throws Exception {
        System.out.println("Save Academic Tool");
        o.setOrderNumber(txtOrderNumber.getText());
        o.setTimeOfVisit(txtTimeOfVisit.getText());
        o.setNumberOfVisitors(txtNumberOfVisitors.getText());
        o.setPhoneNumber(txtTelephoneNumber.getText());
        o.setEmail(txtEmail.getText());
        //ClientUI.chat.accept(o.toString()); //need in full code
        System.out.println("\n" + o);
    }

	
    public void loadOrder(Order o1) {
        this.o = o1;
        this.txtOrderNumber.setText(o1.getOrderNumber());
        this.txtTimeOfVisit.setText(o1.getTimeOfVisit());
        this.txtNumberOfVisitors.setText(String.valueOf(o1.getNumberOfVisitors()));
        this.txtTelephoneNumber.setText(o1.getPhoneNumber());
        this.txtEmail.setText(o1.getEmail());
    }

}
