package command;

import data.Account;

public class ConnectClient implements ServerCommand {
    /**
     * Command called by the client to verify and update connection between client and server
     * Used for initial connection verification and to update account information in the connections table
     * @param param not used
     * @param account not used
     * @return response message to the client letting them know the connection is working
     */
    @Override
    public Message execute(Object param, Account account) {
        return new Message("ClientConnectionSuccess", "Connection successful");
    }
}
