package main;



import ocsf.client.*;
import java.io.*;

import common.ChatIF;
import logic.Order;



public class ClientHandler extends AbstractClient {
	  
	ChatIF clientUI; 
	  public static Order  o1 = new Order("","","","" ,"","");
	  public static boolean awaitResponse = false;


		 
	  public ClientHandler(String host, int port, ChatIF clientUI) 
	    throws IOException 
	  {
	    super(host, port); //Call the superclass constructor
	    this.clientUI = clientUI;
	    //openConnection();
	  }



	  public void handleMessageFromServer(Object msg) {
		    System.out.println("--> handleMessageFromServer");

		    awaitResponse = false;
		    if (msg instanceof Order) {
		        Order order = (Order) msg;
		        if (order.getOrderNumber().equals("Error")) {
		            o1.setOrderNumber(order.getOrderNumber());
		        } else {
		            // Assuming the fields in Order class are accessible through getters and setters
		            o1.setParkName(order.getParkName());
		            o1.setOrderNumber(order.getOrderNumber());
		            o1.setTimeOfVisit(order.getTimeOfVisit());
		            o1.setNumberOfVisitors(order.getNumberOfVisitors());
		            o1.setPhoneNumber(order.getPhoneNumber());
		            o1.setEmail(order.getEmail());
		        }
		    } else {
		        // Handle other types of messages or unexpected input
		        System.out.println("Received unexpected message type: " + msg.getClass().getName());
		    }
		}



    public void handleMessageFromClientUI(Object message) 
    {
    	try
        {
        	openConnection();//in order to send more than one message
           	awaitResponse = true;
        	sendToServer(message);
    		// wait for response
    		while (awaitResponse) {
    			try {
    				Thread.sleep(100);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
        }
        catch(IOException e)
        {
        	e.printStackTrace();
          clientUI.display("Could not send message to server: Terminating client."+ e);
          quit();
        }
    }


    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
        }
        System.exit(0);
    }
}
//End of ChatClient class
