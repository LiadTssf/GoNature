package command;

import handler.ClientHandler;

import java.util.ArrayList;

public class VisitExportReport implements ClientCommand{

    @Override
    public Object execute(Object param) {
        if (param instanceof ArrayList){
            return param;
        }
        return null;
    }
}
