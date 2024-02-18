package org.example;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class ServerHandler extends AbstractServer {
    /**
     * Constructs a new server.
     *
     * @param port the port number on which to listen.
     */
    public ServerHandler(int port) {
        super(port);
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {

    }
}
