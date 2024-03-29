package command;

import data.Account;
import data.Order;
import handler.ClientHandler;

public class OrderCreated implements ClientCommand{

    @Override
    public Object execute(Object param) {
        if (param instanceof Account){
            ClientHandler.setAccount((Account) param);
        }
        return param;
    }
}
