package commands;

@FunctionalInterface
public interface CommandHandlerMethod<T extends Command> {
    void handler(T command);
}
