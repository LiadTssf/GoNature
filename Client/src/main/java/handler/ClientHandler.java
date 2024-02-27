package handler;

import command.Command;
import command.CommandDictionary;
import command.Message;
import ocsf.client.AbstractClient;
import java.io.IOException;

public class ClientHandler extends AbstractClient {
    private static ClientHandler instance;
    private CommandDictionary commandDict;
    private static Object lastResponse;
    private static Object commandReturn;
    private boolean await;

    /**
     * Constructs new client handler
     * @param host the server's host name
     * @param port the port number
     */
    public ClientHandler(String host, int port) {
        super(host, port);
        commandReturn = null;
        commandDict = new CommandDictionary();
        instance = this;
    }

    /**
     * Receives message from server, stores in response and stops waiting
     * @param msg the message received from server
     */
    @Override
    protected void handleMessageFromServer(Object msg) {
        Command command;
        Message response = (Message) msg;
        lastResponse = response;

        System.out.println("Server -> Client: " + msg.toString());

        //Process message and executes command if applicable
        if((command = commandDict.getCommand(response.getCommand())) != null) {
            commandReturn = command.execute(response.getParam());
        }

        await = false;
    }

    /**
     * Sends a message to the server, waits for response and returns response
     * @param msg the message to send to server
     * @return response from server
     */
    public static Object request(Message msg) { return instance.executeRequest(msg); }
    private Object executeRequest(Message msg) {
        System.out.println("Client -> Server: " + msg.toString());
        try {
            openConnection();
            await = true;
            sendToServer(msg);
        } catch (IOException e) { e.printStackTrace(); }
        while(await) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) { e.printStackTrace(); }
        }
        return lastResponse;
    }

    /**
     * Get the last response message from the server
     * @return the last response message from the server
     */
    public static Object getLastResponse() { return lastResponse; }

    /**
     * Get the last returned object from executing server command on the client
     * @return returned object
     */
    public static Object getCommandReturn() { return commandReturn; }
}
