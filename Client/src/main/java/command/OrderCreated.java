package command;

import data.Order;
import handler.ClientHandler;

public class OrderCreated implements ClientCommand{

    @Override
    public Object execute(Object param) {
        if (param instanceof Order){
            ClientHandler.setAccount(null);
        }
        return null;
    }
}
