package org.example;



import ocsf.client.*;
import java.io.*;



public class ClientHandler extends AbstractClient {


    public ClientHandler(String host, int port) throws IOException {
        super(host, port);

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
