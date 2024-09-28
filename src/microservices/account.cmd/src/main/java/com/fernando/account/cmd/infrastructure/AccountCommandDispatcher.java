package com.fernando.account.cmd.infrastructure;

import commands.Command;
import commands.CommandHandlerMethod;
import infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends Command>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends Command> void registerHandler(Class<T> type, CommandHandlerMethod<T> handle) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handle);

    }

    @Override
    public void send(Command command) {
        var handlers = routes.get(command.getClass());

        if(handlers == null || handlers.size() == 0)
            throw new RuntimeException("No command handler was registered!");

        if(handlers.size() > 1)
            throw new RuntimeException("Cannot send command to more than one handler!");

        handlers.get(0).handler(command);
    }
}
