package infrastructure;

import commands.Command;
import commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends Command> void registerHandler(Class<T> type, CommandHandlerMethod<T> handle);
    void send(Command command);
}
