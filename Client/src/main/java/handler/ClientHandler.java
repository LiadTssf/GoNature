package handler;

import command.ClientCommand;
import command.ClientCommandDictionary;
import command.Message;
import data.Account;
import ocsf.client.AbstractClient;
import java.io.IOException;

public class ClientHandler extends AbstractClient {
    private static ClientHandler instance;
    private final ClientCommandDictionary commandDict;
    private Message lastResponse;
    private Object commandReturn;
    private Account account;
    private boolean await;

    /**
     * Constructs new client handler
     * @param host the server's host name
     * @param port the port number
     */
    public ClientHandler(String host, int port) {
        super(host, port);
        instance = this;
        commandDict = new ClientCommandDictionary();
    }

    /**
     * Receives message from server, stores in response and stops waiting
     * @param msg the message received from server
     */
    @Override
    protected void handleMessageFromServer(Object msg) {
        ClientCommand command;
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
        System.out.println("Server: " + msg.toString());
        msg.setAccount(account);
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


    public static void setAccount(Account account) { instance.account = account; }
    public static Object getLastResponse() { return instance.lastResponse; }
    public static Object getCommandReturn() { return instance.commandReturn; }
    public static Account getAccount() { return instance.account; }
}
