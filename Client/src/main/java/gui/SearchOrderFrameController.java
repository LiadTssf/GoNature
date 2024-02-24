package gui;

import ocsf.client.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Order;
import main.ClientHandler;
import main.ClientUI;
import javafx.scene.control.Label;


import javafx.beans.property.SimpleBooleanProperty;

public class SearchOrderFrameController {
	private VisitorOrderFormController ofc;

	@FXML
	private Button btnSearch = null;
	
	@FXML
	private TextField Ordertxt;

	@FXML
    private Label errorMsg;
	
	

	String getOrder() {
		return Ordertxt.getText();
	}
	
	public void Search(ActionEvent event) throws Exception {
		String orderNum;
		FXMLLoader loader = new FXMLLoader();
		
		orderNum=getOrder();
		if(orderNum.trim().isEmpty())
		{
			errorMsg.setText("You must enter an order number");		}
		else
		{
			//ClientUI.chat.accept(orderNum); //need in full code
			//ClientHandler.o1.setOrderNumber("Error"); //was build for debuging 
			if (ClientHandler.o1.getOrderNumber().equals("Error")) {
				errorMsg.setText("Order not found.");		
			}

			else {
				System.out.println("Order Number Found");
				((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
				Stage primaryStage = new Stage();
				Pane root = loader.load(getClass().getResource("/gui/OrderInfoForm.fxml").openStream());
				VisitorOrderFormController orderFormController = loader.getController();		
				orderFormController.loadOrder(ClientHandler.o1);
				Scene scene = new Scene(root);			
				scene.getStylesheets().add(getClass().getResource("/gui/OrderInfoForm.css").toExternalForm());
				primaryStage.setTitle("Order Managment Tool");
	
				primaryStage.setScene(scene);		
				primaryStage.show();
			}
		}
	}
	
	
	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/SearchOrder.fxml"));
				
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/SearchOrder.css").toExternalForm());
		primaryStage.setTitle("Order Search");
		primaryStage.setScene(scene);
		
		primaryStage.show();	 	   
	}
	@FXML
	private Button btnExit = null;
	
	public void loadOrder(Order o1) {
		this.ofc.loadOrder(o1);
	}	


	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("Exit Order, bye ahi");	
		System.exit(0); 
	}
	public  void display(String message) {
		System.out.println("message");
	}
}
