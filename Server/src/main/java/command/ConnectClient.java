package command;

import data.Account;

public class ConnectClient implements ServerCommand {
    @Override
    public Message execute(Object param, Account account) {
        return new Message("ClientConnectionSuccess", "Connection successfully established");
    }
}
