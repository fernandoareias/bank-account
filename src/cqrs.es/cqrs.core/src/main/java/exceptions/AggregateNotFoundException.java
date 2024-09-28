package exceptions;

public class AggregateNotFoundException extends RuntimeException {

    public AggregateNotFoundException(String msg)
    {
        super(msg);
    }
}
