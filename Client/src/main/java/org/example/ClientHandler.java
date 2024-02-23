package org.example;



import ocsf.client.*;
import java.io.*;

import common.ChatIF;
import logic.Order;



public class ClientHandler extends AbstractClient {
	  
	ChatIF clientUI; 
	  public static Order  o1 = new Order(null,null,null,null ,null,null);
	  public static boolean awaitResponse = false;


		 
	  public ClientHandler(String host, int port, ChatIF clientUI) 
	    throws IOException 
	  {
	    super(host, port); //Call the superclass constructor
	    this.clientUI = clientUI;
	    //openConnection();
	  }



    public void handleMessageFromServer(Object msg) {

    }


    public void handleMessageFromClientUI(Object message) {

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
