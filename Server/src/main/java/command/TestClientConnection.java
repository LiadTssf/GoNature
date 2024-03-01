package command;

import data.Account;

public class TestClientConnection implements ServerCommand {
    @Override
    public Message execute(Object param, Account account) {
        return new Message("ClientConnectionSuccess", "Connection successfully established");
    }
}
