package org.example;

import java.io.IOException;

import org.example.ClientHandler;
import common.ChatIF;

public class ClientController implements ChatIF 
{
	  //Class variables *************************************************
	  
	  /**
	   * The default port to connect on.
	   */
	   public static int DEFAULT_PORT ;
	  
	  //Instance variables **********************************************
	  
	  /**
	   * The instance of the client that created this ConsoleChat.
	   */
	   ClientHandler client;


	  public ClientController(String host, int port) 
	  {
	    try 
	    {
	      client= new ClientHandler(host, port, this);
	    } 
	    catch(IOException exception) 
	    {
	      System.out.println("Error: Can't setup connection!"+ " Terminating client.");
	      System.exit(1);
	    }
	  }


	  public void accept(String str) 
	  {
		  client.handleMessageFromClientUI(str);
	  }
	  

	  public void display(String message) 
	  {
	    System.out.println("> " + message);
	  }
}