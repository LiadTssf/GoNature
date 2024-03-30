package command;

import data.Account;
import data.MessagesToSend;
import data.Order;
import handler.ClientHandler;

import java.util.ArrayList;

public class OrderCreated implements ClientCommand{

    @Override
    public Object execute(Object param) {
        if (param instanceof ArrayList){
            ArrayList<Object> arrayList = (ArrayList<Object>) param;
            if (arrayList.get(1) instanceof Account) {
                ClientHandler.setAccount((Account) arrayList.get(1));
            }
            if (arrayList.get(0) instanceof MessagesToSend){
                ClientHandler.setMessageToSend((MessagesToSend) arrayList.get(0));
            }
        }
        return param;
    }
}
