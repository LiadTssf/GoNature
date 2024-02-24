package Main;


import javafx.application.Application;
import javafx.stage.Stage;

import gui.ServerInfoFrameController;
import Main.ServerHandler;

public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;

	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub				  		
		ServerInfoFrameController aFrame = new ServerInfoFrameController(); // create StudentFrame
		 
		aFrame.start(primaryStage);
	}
	
	public static void runServer(String p)
	{
		 int port = 0; //Port to listen on

	        try
	        {
	        	port = Integer.parseInt(p); //Set port to 5555
	          
	        }
	        catch(Throwable t)
	        {
	        	System.out.println("ERROR - Could not connect!");
	        }
	    	
	        ServerHandler sv = new ServerHandler(port);
	        
	        try 
	        {
	          sv.listen(); //Start listening for connections
	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	        }
	}
	

}
