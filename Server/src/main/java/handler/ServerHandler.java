package handler;

import command.ServerCommandDictionary;
import command.Message;
import command.ServerCommand;
import data.Account;
import gui.ServerUI;
import gui.ShowConnections;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("FieldCanBeLocal")
public class ServerHandler extends AbstractServer {
    private static ServerHandler instance;
    private ServerCommandDictionary commandDict;
    private Map<ConnectionToClient, Account> clientAccountDict;

    /**
     * constructs new server handler
     * @param port the port number on which to listen
     */
    public ServerHandler(int port) {
        super(port);
        commandDict = new ServerCommandDictionary();
        instance = this;
    }

    /**
     * receives message from client, tries to execute command and sends response
     * @param msg   the message sent
     * @param client the connection connected to the client that
     */
    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        Message response;
        ServerCommand command;

        System.out.println("Client " + client.getId() + " - " + msg.toString());

        //Process message
        Message request = (Message) msg;
        if((command = commandDict.getCommand(request.getCommand())) != null) {
            //TODO: replace null with client-account dictionary result
            response = command.execute(request.getParam(), null);
        } else {
            response = new Message("UnknownRequest", "Could not find command to execute");
        }

        //Send response
        try {
            client.sendToClient(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * not used, client-server communication only with message objects
     * @param msg
     * @param client
     */
    @Override
    protected void handleMessageFromClient(String msg, ConnectionToClient client) {

    }

    /**
     * method called when a client connects
     * @param client the connection connected to the client
     */
    @Override
    protected void clientConnected(ConnectionToClient client) {
        String str = client.toString();
        String hostname = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
        String ip = str.substring(0, str.indexOf("(") - 1);
        if(ServerUI.getCurrentController() instanceof ShowConnections) {
            ((ShowConnections)ServerUI.getCurrentController()).updateStatus(ip, hostname, "Connected");
        }
    }

    /**
     * method called when a client disconnects
     * @param client the connection with the client
     */
    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        String str = client.toString();
        String hostname = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
        String ip = str.substring(0, str.indexOf("(") - 1);
        if(ServerUI.getCurrentController() instanceof ShowConnections) {
            ((ShowConnections)ServerUI.getCurrentController()).updateStatus(ip, hostname, "Disconnected");
        }
    }
}
