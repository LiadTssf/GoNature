package handler;

import command.ClientCommand;
import command.ClientCommandDictionary;
import command.Message;
import data.Account;
import data.RegisteredAccount;
import gui.ClientUI;
import ocsf.client.AbstractClient;
import java.io.IOException;

public class ClientHandler extends AbstractClient {
    private static ClientHandler instance;
    private final ClientCommandDictionary commandDict;
    private Message lastResponse;
    private Object commandReturn;
    private Account account;
    private RegisteredAccount registeredAccount;
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

        System.out.println("Server: " + msg.toString());

        //Process message and executes command if applicable
        if ((command = commandDict.getCommand(response.getCommand())) != null) {
            commandReturn = command.execute(response.getParam());
        } else {
            commandReturn = response.getParam();
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
        int secondsToTimeout = 5;

        System.out.println("Client: " + msg.toString());
        msg.setAccount(account);

        //Send request
        try {
            openConnection();
            await = true;
            sendToServer(msg);
        } catch (IOException e) {
            //Connection refused handling
            ClientUI.popupNotification("Failed to establish connection");
            ClientUI.changeScene("ConnectToHost");
            ClientUI.removeAllMainMenuItems();
            ClientUI.addMainMenuItem("Server Connection", "ConnectToHost");
        }

        //Await response
        while(await && secondsToTimeout > 0) {
            try {
                Thread.sleep(1000);
                secondsToTimeout--;
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Timeout handling
        if(await) {
            ClientUI.popupNotification("Timed out\nServer did not send response or client command run threw exception");
        }

        return commandReturn;
    }

    /**
     * Sends a disconnection message to the server and closes the connection
     */
    public static void closeClientHandler() {
        if(instance != null) {
            try {
                ClientHandler.setAccount(null);
                instance.executeRequest(new Message("DisconnectClient", "No parameters"));
                instance.closeConnection();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Assigns account to the client
     * This account is passed to all messages to the server
     * @param account to be assigned to client
     */
    public static void setAccount(Account account) {
        instance.account = account;
    }

    /**
     * Returns last message response received from the server
     * @return server response
     */
    public static Message getLastResponse() {
        return instance.lastResponse;
    }

    /**
     * Returns last command return value
     * @return command return value
     */
    public static Object getCommandReturn() {
        return instance.commandReturn;
    }

    /**
     * Returns account assigned to the client
     * @return account
     */
    public static Account getAccount() {
        return instance.account;
    }

    public static void setRegisterAccount(RegisteredAccount registeredAccount){
        instance.registeredAccount = registeredAccount;
    }

    public static RegisteredAccount getRegisterAccount(){
        return instance.registeredAccount;
    }
}
