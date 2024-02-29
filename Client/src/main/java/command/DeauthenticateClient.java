package command;

import handler.ClientHandler;

public class DeauthenticateClient implements ClientCommand{
    @Override
    public Object execute(Object param) {
        ClientHandler.setAccount(null);
        return null;
    }
}
