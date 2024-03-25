package command;

import data.Order;
import gui.ClientUI;
import handler.ClientHandler;

import java.time.Clock;

public class FailMessage implements ClientCommand {

    @Override
    public Object execute(Object param) {
//        if (param instanceof String){
//            System.out.println((String)param);
//            ClientUI.popupNotification(param.toString());
//        }
        return param;
    }
}
