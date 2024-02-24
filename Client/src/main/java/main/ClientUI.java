package main;
import main.ClientController;
import gui.SearchOrderFrameController;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 need add all javafx related packetges
 and add the currect logic
 */


public class ClientUI extends Application {
	public static ClientController chat; //only one instance
    public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		 chat= new ClientController("localhost", 5555);
		// TODO Auto-generated method stub
						  		
		SearchOrderFrameController aFrame = new SearchOrderFrameController(); // create StudentFrame
		 
		aFrame.start(primaryStage);
	}
	


}