package command;

import java.util.ArrayList;

public class setParkChanges implements ClientCommand {
    @Override
    public Object execute(Object param) {
        if (param instanceof ArrayList<?>) {
            return param;
        }
        return null;
    }
}
