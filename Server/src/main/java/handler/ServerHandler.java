package handler;

import command.DisconnectClient;
import command.Message;
import command.ServerCommand;
import command.ServerCommandDictionary;
import data.Account;
import data.ConnectionTableData;
import gui.ServerUI;
import gui.ShowConnections;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import java.io.IOException;

public class ServerHandler extends AbstractServer {
    private static ServerHandler instance;
    private final ServerCommandDictionary commandDict;

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

        System.out.println("Client " + client.getId() + ": " + msg.toString());
        Message request = (Message) msg;

        //Process message
        if ((command = commandDict.getCommand(request.getCommand())) != null) {
            response = command.execute(request.getParam(), request.getAccount());
        } else {
            response = new Message("UnknownRequest", "Could not find command to execute");
        }

        //Send response
        try {
            client.sendToClient(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Send client information to gui
        if(request.getCommand().equals("DisconnectClient")) {
            System.out.println("Disconnecting");
            sendClientInfo(client, request.getAccount(), request, "Disconnected");
        } else {
            System.out.println("Connecting");
            sendClientInfo(client, request.getAccount(), request, "Connected");
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

    public void sendClientInfo(ConnectionToClient client, Account account, Message lastRequest, String status) {
        if(ServerUI.getCurrentController() instanceof ShowConnections) {
            ShowConnections controller = (ShowConnections) ServerUI.getCurrentController();
            String clientString = client.toString();
            String client_id = String.valueOf(client.getId());
            String ip = clientString.substring(clientString.indexOf("(") + 1, clientString.indexOf(")"));
            String host = clientString.substring(0, clientString.indexOf("(") - 1);
            String account_id = "N/A";
            String lastRequestString = lastRequest.toString();
            if(account != null) {
                account_id = String.valueOf(account.account_id_pk);
            }
            ConnectionTableData connectionTableData = new ConnectionTableData();
            connectionTableData.setClient_id(client_id);
            connectionTableData.setIp(ip);
            connectionTableData.setHost(host);
            connectionTableData.setAccount_id(account_id);
            connectionTableData.setLastRequest(lastRequestString);
            connectionTableData.setStatus(status);
            controller.addOrUpdateConnection(connectionTableData);
        }
    }

    public static void closeServerHandler() {
        try {
            instance.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
