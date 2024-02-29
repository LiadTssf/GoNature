package command;

import data.Account;
import handler.ClientHandler;

public class AuthenticateClient implements ClientCommand {
    @Override
    public Object execute(Object param) {
        if (param instanceof Account) {
            ClientHandler.setAccount((Account) param);
        }
        return param;
    }
}
