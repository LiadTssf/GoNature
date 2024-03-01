package command;

import data.Account;

public class LogoutAccount implements ServerCommand {
    /**
     * Command called by client letting the server know they're logging out of the current client
     * Used to remove account from client in the connection table
     * @param param not used
     * @param account not used
     * @return response message telling the client logout message has been received
     */
    @Override
    public Message execute(Object param, Account account) {
        return new Message("DeauthenticateClient", "Logging client out");
    }
}
