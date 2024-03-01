package command;

import data.Account;

public class DisconnectClient implements ServerCommand {
    /**
     * Command called by client letting the server know they're closing the connection
     * Used to set connection status and remove account assigned to client in connections table
     * @param param not used
     * @param account no used
     * @return response message telling the client disconnect message has been received
     */
    @Override
    public Message execute(Object param, Account account) {
        return new Message("ClientDisconnection", "Connection has been closed");
    }
}
