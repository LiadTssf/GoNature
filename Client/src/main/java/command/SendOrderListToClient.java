package command;

import data.Order;
import gui.OrderList;

import java.util.ArrayList;

public class SendOrderListToClient implements ClientCommand {
    /**
     * Receives a list of orders and sends it to the OrderList class
     *
     * @param param   ArrayList of Order objects
     * @return message to client with list of orders or denial message
     */
    @Override
    public Object execute(Object param) {
        if (param instanceof ArrayList<?>) {
            return param;
        }
        return null;
    }
}
