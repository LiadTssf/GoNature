package command;

public class ReturnParam implements Command {
    /**
     * Returns the same parameter object received from server to ClientHandler.commandReturn
     * For use when server sent objects are handled by gui controllers in the javafx thread
     * and not by command methods
     * @param param object
     * @return object
     */
    @Override
    public Object execute(Object param) { return param; }
}
