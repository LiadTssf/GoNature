package command;

import handler.ClientHandler;

public class DeauthenticateClient implements ClientCommand{
    /**
     * Removes the client assigned account
     * @param param not used
     * @return null
     */
    @Override
    public Object execute(Object param) {
        ClientHandler.setAccount(null);
        return null;
    }
}
